package ikasaidi.backend_lab.repositories;

import ikasaidi.backend_lab.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour la gestion des entités {@link Series}.
 *
 * Cette interface hérite de {@link JpaRepository}, ce qui permet d’accéder
 * à toutes les opérations CRUD standards (création, lecture, mise à jour, suppression)
 * pour les séries enregistrées en base de données.
 *
 *
 *
 * Elle définit également plusieurs méthodes de recherche dérivées
 * basées sur la convention de nommage de Spring Data JPA,
 * permettant de filtrer les séries selon différents critères.
 *
 *
 * Méthodes héritées utiles :
 * <ul>
 *     <li>{@code findAll()} — retourne toutes les séries.</li>
 *     <li>{@code findById(Long id)} — cherche une série par son identifiant.</li>
 *     <li>{@code save(Series s)} — ajoute ou met à jour une série.</li>
 *     <li>{@code deleteById(Long id)} — supprime une série.</li>
 * </ul>
 *
 *
 * Ce repository est utilisé dans :
 * <ul>
 *     <li>{@link ikasaidi.backend_lab.services.SeriesService}</li>
 *     <li>{@link ikasaidi.backend_lab.services.RecommendationService}</li>
 *     <li>{@link ikasaidi.backend_lab.services.TrendingService}</li>
 * </ul>
 *
 *
 * @author Ikram
 * @author Sarah
 * @version 1.0
 */
@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {

    /**
     * Recherche toutes les séries correspondant à un genre donné.
     *
     * @param genre le genre recherché (ex. : "Drame", "Comédie")
     * @return une liste de séries correspondant au genre
     */
    List<Series> findByGenre(String genre);

    /**
     * Recherche toutes les séries ayant un nombre d’épisodes supérieur ou égal à la valeur donnée.
     *
     * @param nbEpisodes le nombre minimal d’épisodes
     * @return une liste de séries répondant au critère
     */
    List<Series> findByNbEpisodesGreaterThanEqual(int nbEpisodes);

    /**
     * Recherche les séries correspondant à un genre donné et ayant
     * au moins un certain nombre d’épisodes.
     *
     * @param genre le genre de la série
     * @param nbEpisodes le nombre minimal d’épisodes
     * @return une liste de séries correspondant aux deux critères
     */
    List<Series> findByGenreAndNbEpisodesGreaterThanEqual(String genre, int nbEpisodes);

    /**
     * Recherche toutes les séries dont le titre contient une chaîne donnée.
     *
     * @param title partie du titre à rechercher (insensible à la casse selon la BD)
     * @return une liste de séries dont le titre contient le texte fourni
     */
    List<Series> findByTitleContaining(String title);
}
