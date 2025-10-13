package ikasaidi.backend_lab.config;

import ikasaidi.backend_lab.models.Episodes;
import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.repositories.EpisodesRepository;
import ikasaidi.backend_lab.repositories.SeriesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Classe responsable de l’initialisation des épisodes dans la base de données.
 *
 * Cette classe insère automatiquement des épisodes de test
 * au démarrage de l’application afin de simuler des données
 * pour les séries existantes.
 *
 *
 * Localisation des données générées : table <strong>episodes</strong>
 *
 * @author Sarah
 * @version 1.0
 */
@Component
public class EpisodeSeeder implements CommandLineRunner {

    private final EpisodesRepository episodesRepository;
    private final SeriesRepository seriesRepository;


    /**
     * Constructeur injectant le repository des épisodes.
     *
     * @param episodesRepository repository pour accéder aux données des épisodes
     * @param seriesRepository le repository des séries
     */
    public EpisodeSeeder(EpisodesRepository episodesRepository, SeriesRepository seriesRepository) {
        this.episodesRepository = episodesRepository;
        this.seriesRepository = seriesRepository;
    }

    /**
     * Méthode exécutée automatiquement au lancement de l’application.
     * Ajoute des épisodes de démonstration s’ils n’existent pas déjà.
     *
     * @param args arguments de la ligne de commande (non utilisés)
     */
    @Override
    public void run(String... args) {

        if (episodesRepository.count() > 0) {
            System.out.println("🎬 Les épisodes existent déjà → aucun ajout effectué.");
            return;
        }

        List<Series> allSeries = seriesRepository.findAll();

        if (allSeries.isEmpty()) {
            System.out.println("⚠️ Aucune série trouvée. Impossible d'insérer les épisodes.");
            return;
        }

        for (Series series : allSeries) {
            int nbEpisodes = series.getNbEpisodes();

            if (nbEpisodes <= 0) {
                System.out.println("⏩ Série " + series.getTitle() + " n’a pas de nbEpisodes défini, ignorée.");
                continue;
            }

            for (int i = 1; i <= nbEpisodes; i++) {
                Episodes episode = new Episodes();
                episode.setEpisodeNumber(i);
                episode.setTitle(generateEpisodeTitle(series.getTitle(), i));
                episode.setSeries(series);

                episodesRepository.save(episode);
            }

            System.out.println("✅ " + nbEpisodes + " épisodes ajoutés pour la série : " + series.getTitle());
        }

        System.out.println("🎉 Tous les épisodes ont été insérés avec succès !");
    }

    // Génère un titre thématique selon la série
    private String generateEpisodeTitle(String seriesTitle, int num) {
        return switch (seriesTitle.toLowerCase()) {
            case "dark" -> "Darkness Part " + num;
            case "nah" -> "Funny Chaos " + num;
            case "breaking bad" -> "Meth Lab " + num;
            case "super action" -> "Explosion " + num;
            case "action super 2" -> "Battle Scene " + num;
            case "breaking point" -> "Pressure Point " + num;
            case "family secrets" -> "Hidden Truth " + num;
            case "family" -> "Episode " + num + " - Family Moment";
            case "high school" -> "Episode " + num + " - Campus Life";
            case "the expanse" -> "Orbit " + num;
            case "chernobyl" -> "Reactor " + num;
            case "arcane" -> "Zaun Chronicles " + num;
            case "mr. robot" -> "Hack Sequence " + num;
            default -> "Episode " + num;
        };
    }
}
