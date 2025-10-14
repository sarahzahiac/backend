package ikasaidi.backend_lab.services;

import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.repositories.PersonRepository;
import ikasaidi.backend_lab.repositories.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service responsable de la génération de recommandations personnalisées.
 *
 * Ce service analyse les habitudes de visionnage d’un utilisateur (historique)
 * pour lui proposer des séries qu’il n’a pas encore vues,
 * basées sur ses genres préférés.
 *
 *
 * Il fonctionne en trois étapes :
 * <ol>
 *     <li>Analyse des genres les plus regardés par l’utilisateur.</li>
 *     <li>Sélection des trois genres les plus fréquents.</li>
 *     <li>Proposition de séries non encore visionnées correspondant à ces genres.</li>
 * </ol>
 *
 *
 *
 * Il interagit principalement avec :
 * <ul>
 *     <li>{@link PersonRepository} pour récupérer les utilisateurs et leur historique.</li>
 *     <li>{@link SeriesRepository} pour accéder à toutes les séries disponibles.</li>
 * </ul>
 *
 *
 * @author Rachel
 * @version 1.0
 */
@Service
public class RecommendationService {

    /** Repository des utilisateurs (personnes). */
    private final PersonRepository personRepository;

    /** Repository des séries. */
    private final SeriesRepository seriesRepository;

    /**
     * Constructeur injectant les dépendances nécessaires.
     *
     * @param personRepository repository pour accéder aux utilisateurs
     * @param seriesRepository repository pour accéder aux séries
     */
    public RecommendationService(PersonRepository personRepository, SeriesRepository seriesRepository) {
        this.personRepository = personRepository;
        this.seriesRepository = seriesRepository;
    }

    /**
     * Génère une liste de séries recommandées pour un utilisateur donné.
     *
     * Le processus de recommandation repose sur les genres les plus regardés
     * dans l’historique de la personne. Les trois genres les plus fréquents sont extraits,
     * puis jusqu’à trois séries non encore vues sont proposées pour chaque genre.
     *
     *
     * @param id identifiant de la personne pour laquelle on souhaite générer des recommandations
     * @return une liste de séries recommandées selon les genres favoris de l’utilisateur
     * @throws RuntimeException si la personne n’existe pas dans la base de données
     */
    public List<Series> getPersonsRecommendation(Integer id) {

        // 1️⃣ Récupérer la personne et son historique
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne non trouvée"));
        List<Series> history = person.getHistory();

        // 2️⃣ Compter le nombre de séries regardées par genre
        Map<String, Integer> genreCount = new HashMap<>();
        for (Series series : history) {
            String genre = series.getGenre();
            genreCount.put(genre, genreCount.getOrDefault(genre, 0) + 1);
        }

        // 3️⃣ Identifier les 3 genres les plus populaires
        List<String> topGenres = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String bestGenre = null;
            int max = 0;

            for (Map.Entry<String, Integer> entry : genreCount.entrySet()) {
                if (!topGenres.contains(entry.getKey()) && entry.getValue() > max) {
                    bestGenre = entry.getKey();
                    max = entry.getValue();
                }
            }

            if (bestGenre != null) {
                topGenres.add(bestGenre);
            }
        }

        // 4️⃣ Rechercher dans toutes les séries celles non vues correspondant aux top genres
        List<Series> allSeries = seriesRepository.findAll();
        List<Series> recommendations = new ArrayList<>();

        for (String genre : topGenres) {
            int count = 0;
            for (Series series : allSeries) {
                if (series.getGenre().equals(genre) && !history.contains(series)) {
                    recommendations.add(series);
                    count++;
                    if (count == 3) break; // max 3 recommandations par genre
                }
            }
        }

        return recommendations;
    }
}
