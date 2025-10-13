package ikasaidi.backend_lab.repositories;

import ikasaidi.backend_lab.models.Episodes;
import ikasaidi.backend_lab.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour la gestion des entités {@link Episodes}.
 *
 * Cette interface hérite de {@link JpaRepository}, fournissant ainsi toutes les
 * opérations CRUD standard (création, lecture, mise à jour, suppression)
 * pour les épisodes enregistrés dans la base de données.
 *
 *
 *
 * Elle définit également une méthode personnalisée permettant
 * de rechercher tous les épisodes associés à une série spécifique.
 *
 *
 * Méthodes héritées utiles :
 * <ul>
 *     <li>{@code findAll()} — récupère tous les épisodes.</li>
 *     <li>{@code findById(Long id)} — trouve un épisode par son identifiant.</li>
 *     <li>{@code save(Episodes e)} — ajoute ou met à jour un épisode.</li>
 *     <li>{@code deleteById(Long id)} — supprime un épisode.</li>
 * </ul>
 *
 *
 * Ce repository est principalement utilisé dans :
 * <ul>
 *     <li>{@link ikasaidi.backend_lab.services.RatingsService}</li>
 *     <li>{@link ikasaidi.backend_lab.config.RatingsSeeder}</li>
 * </ul>
 *
 *
 * @author Sarah
 * @version 1.0
 */
@Repository
public interface EpisodesRepository extends JpaRepository<Episodes, Long> {

    /**
     * Recherche tous les épisodes appartenant à une série donnée.
     *
     * @param series la série pour laquelle on souhaite obtenir la liste des épisodes
     * @return une liste d’épisodes associés à cette série
     */
    List<Episodes> findBySeries(Series series);
}
