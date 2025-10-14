package ikasaidi.backend_lab.repositories;

import ikasaidi.backend_lab.models.Episodes;
import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Ratings;
import ikasaidi.backend_lab.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository de gestion des entités {@link Ratings}.
 *
 * Cette interface hérite de {@link JpaRepository}, ce qui lui permet de bénéficier
 * des opérations CRUD standards pour les évaluations.
 *
 *
 *
 * Elle contient également plusieurs méthodes personnalisées permettant :
 * <ul>
 *     <li>De rechercher les évaluations associées à un utilisateur ou à une série.</li>
 *     <li>De vérifier si une personne a déjà évalué une série ou un épisode.</li>
 *     <li>De calculer la moyenne des notes pour une série ou un épisode spécifique.</li>
 * </ul>
 *
 *
 *
 * Ce repository est utilisé dans :
 * <ul>
 *     <li>{@link ikasaidi.backend_lab.services.RatingsService}</li>
 *     <li>{@link ikasaidi.backend_lab.controllers.RatingsController}</li>
 * </ul>
 *
 *
 * @author Rachel
 * @author Aya
 * @author Ikram
 * @author Sarah
 * @version 1.0
 */
@Repository
public interface RatingsRepository extends JpaRepository<Ratings, Long> {

    /**
     * Récupère toutes les évaluations laissées par un utilisateur donné.
     *
     * @param person la personne dont on veut récupérer les évaluations
     * @return une liste d’évaluations associées à cette personne
     */
    List<Ratings> findByPerson(Person person);

    /**
     * Vérifie si une personne a déjà évalué une série donnée.
     *
     * @param person la personne concernée
     * @param series la série concernée
     * @return {@code true} si une évaluation existe déjà, sinon {@code false}
     */
    boolean existsByPersonAndSeries(Person person, Series series);

    /**
     * Vérifie si une personne a déjà évalué un épisode donné.
     *
     * @param person la personne concernée
     * @param episode l’épisode concerné
     * @return {@code true} si une évaluation existe déjà, sinon {@code false}
     */
    boolean existsByPersonAndEpisode(Person person, Episodes episode);

    // ------------------------------------------------------------
    // ---------- SERIES ----------
    // ------------------------------------------------------------

    /**
     * Récupère toutes les évaluations d’une personne pour une série spécifique.
     *
     * @param person la personne concernée
     * @param series la série concernée
     * @return une liste d’évaluations correspondant à la série
     */
    List<Ratings> findAllByPersonAndSeries(Person person, Series series);

    /**
     * Calcule la moyenne des notes attribuées à une série.
     *
     * @param series la série pour laquelle on veut calculer la moyenne
     * @return la note moyenne, ou {@code null} si aucune note n’existe
     */
    @Query("SELECT AVG(r.score) FROM Ratings r WHERE r.series = :series")
    Double findAverageBySeries(@Param("series") Series series);

    // ------------------------------------------------------------
    // ---------- EPISODES ----------
    // ------------------------------------------------------------

    /**
     * Récupère toutes les évaluations d’une personne pour un épisode spécifique.
     *
     * @param person la personne concernée
     * @param episode l’épisode concerné
     * @return une liste d’évaluations correspondant à l’épisode
     */
    List<Ratings> findAllByPersonAndEpisode(Person person, Episodes episode);

    /**
     * Calcule la moyenne des notes attribuées à un épisode.
     *
     * @param episode l’épisode pour lequel on veut calculer la moyenne
     * @return la note moyenne, ou {@code null} si aucune note n’existe
     */
    @Query("SELECT AVG(r.score) FROM Ratings r WHERE r.episode = :episode")
    Double findAverageByEpisode(@Param("episode") Episodes episode);
}
