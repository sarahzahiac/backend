package ikasaidi.backend_lab.controllers;

import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Ratings;
import ikasaidi.backend_lab.repositories.PersonRepository;
import ikasaidi.backend_lab.repositories.RatingsRepository;
import ikasaidi.backend_lab.repositories.SeriesRepository;
import ikasaidi.backend_lab.services.RatingsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Contrôleur REST responsable de la gestion des évaluations (ratings)
 * des séries et des épisodes par les utilisateurs.
 *
 * Ce contrôleur fournit des endpoints pour :
 * <ul>
 *     <li>Consulter toutes les évaluations.</li>
 *     <li>Voir les évaluations d’un utilisateur donné.</li>
 *     <li>Ajouter ou mettre à jour une note pour une série ou un épisode.</li>
 *     <li>Calculer la moyenne des notes pour une série ou un épisode.</li>
 * </ul>
 *
 *
 * Les opérations de logique métier sont déléguées au {@link RatingsService}.
 *
 * @author Rachel
 * @author Aya
 * @author Sarah
 * @version 1.0
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/ratings")
public class RatingsController {

    private final RatingsRepository ratingsRepository;
    private final PersonRepository personRepository;
    private final SeriesRepository seriesRepository;
    private final RatingsService ratingsService;

    /**
     * Constructeur du contrôleur des évaluations.
     *
     * @param ratingsRepository repository des évaluations
     * @param personRepository repository des utilisateurs
     * @param seriesRepository repository des séries
     * @param ratingsService service de gestion des évaluations
     */
    public RatingsController(RatingsRepository ratingsRepository,
                             PersonRepository personRepository,
                             SeriesRepository seriesRepository,
                             RatingsService ratingsService) {
        this.ratingsRepository = ratingsRepository;
        this.personRepository = personRepository;
        this.seriesRepository = seriesRepository;
        this.ratingsService = ratingsService;
    }

    /**
     * Récupère la liste complète de toutes les évaluations enregistrées.
     *
     * @return une liste de toutes les évaluations présentes dans la base de données
     *
     * <ul>
     *     <li><b>200: </b>Si les évaluations sont retournées avec succès</li>
     * </ul>
     */
    @GetMapping
    public List<Ratings> getAllRatings() {
        return ratingsRepository.findAll();
    }

    /**
     * Récupère toutes les évaluations effectuées par un utilisateur spécifique.
     *
     * @param id identifiant unique de l’utilisateur
     * @return une liste d’évaluations associées à cet utilisateur
     *
     * <ul>
     *     <li><b>200: </b>Si la liste est retournée</li>
     *     <li><b>404: </b>Si l'utilisateur n'existe pas</li>
     * </ul>
     */
    @GetMapping("/user/{id}")
    public List<Ratings> getRatingsByUserEmail(@PathVariable Integer id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isEmpty()) {
            return List.of();
        }
        return ratingsRepository.findByPerson(person.get());
    }

    /**
     * Ajoute ou met à jour la note d’un utilisateur pour un épisode.
     *
     * @param episodeId identifiant de l’épisode évalué
     * @param personId identifiant de l’utilisateur qui note
     * @param score note attribuée (généralement entre 1 et 5)
     * @return l’évaluation créée ou mise à jour
     *
     * <ul>
     *     <li><b>200: </b>Si l'évaluation est enregistrée avec succès</li>
     *     <li><b>404: </b>Si l'épisode ou l'utilisateur n'existe pas</li>
     * </ul>
     */
    @PostMapping("/episode/{episodeId}")
    public Ratings addOrUpdateRatingsByEpisode(@PathVariable Long episodeId,
                                               @RequestParam int personId,
                                               @RequestParam int score) {
        return ratingsService.addOrUpdatingRatingByEpisode(episodeId, personId, score);
    }

    /**
     * Ajoute ou met à jour la note d’un utilisateur pour une série.
     *
     * @param seriesId identifiant de la série évaluée
     * @param personId identifiant de l’utilisateur
     * @param score note attribuée à la série
     * @return l’évaluation créée ou mise à jour
     *
     * <ul>
     *     <li><b>200: </b>Si l'évaluation est enregistrée avec succès</li>
     *     <li><b>404: </b>Si la série ou l'utilisateur n'existe pas</li>
     * </ul>
     */
    @PostMapping("/series/{seriesId}")
    public Ratings addOrUpdateRatingsBySerie(@PathVariable Long seriesId,
                                             @RequestParam int personId,
                                             @RequestParam int score) {
        return ratingsService.addOrUpdatingRatingBySerie(seriesId, personId, score);
    }

    /**
     * Calcule la note moyenne d’une série donnée.
     *
     * @param seriesId identifiant unique de la série
     * @return la note moyenne (entre 0 et 5)
     *
     * <ul>
     *     <li><b>200: </b>Si la moyenne est calculée avec succès</li>
     *     <li><b>404: </b>Si série n'existe pas</li>
     * </ul>
     */
    @GetMapping("/series/{seriesId}/average")
    public double getAverageRatingSerie(@PathVariable Long seriesId) {
        return ratingsService.getAverageRatingsBySerie(seriesId);
    }

    /**
     * Calcule la note moyenne d’un épisode donné.
     *
     * @param episodeId identifiant unique de l’épisode
     * @return la note moyenne (entre 0 et 5)
     *
     * <ul>
     *     <li><b>200: </b>Si la moyenne est calculée avec succès</li>
     *     <li><b>404: </b>Si l'épisode n'existe pas</li>
     * </ul>
     */
    @GetMapping("/episode/{episodeId}/average")
    public double getAverageRatingEpisode(@PathVariable Long episodeId) {
        return ratingsService.getAverageRatingsByEpisode(episodeId);
    }
}
