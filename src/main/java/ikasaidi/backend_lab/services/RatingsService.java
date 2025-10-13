package ikasaidi.backend_lab.services;

import ikasaidi.backend_lab.models.Episodes;
import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Ratings;
import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.repositories.EpisodesRepository;
import ikasaidi.backend_lab.repositories.PersonRepository;
import ikasaidi.backend_lab.repositories.RatingsRepository;
import ikasaidi.backend_lab.repositories.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service gérant la logique métier des évaluations (ratings) dans l’application.
 *
 * Cette classe permet :
 * <ul>
 *     <li>D’ajouter ou de modifier une évaluation pour une série ou un épisode.</li>
 *     <li>De calculer la moyenne des notes pour une série ou un épisode donné.</li>
 * </ul>
 *
 *
 *
 * Elle interagit directement avec les repositories :
 * <ul>
 *     <li>{@link RatingsRepository}</li>
 *     <li>{@link SeriesRepository}</li>
 *     <li>{@link EpisodesRepository}</li>
 *     <li>{@link PersonRepository}</li>
 * </ul>
 *
 *
 * @author Sarah
 * @version 1.0
 */
@Service
public class RatingsService {

    /** Repository des évaluations. */
    private final RatingsRepository ratingsRepository;

    /** Repository des séries. */
    private final SeriesRepository seriesRepository;

    /** Repository des utilisateurs (personnes). */
    private final PersonRepository personRepository;

    /** Repository des épisodes. */
    private final EpisodesRepository episodesRepository;

    /**
     * Constructeur injectant les dépendances.
     *
     * @param ratingsRepository repository pour les évaluations
     * @param seriesRepository repository pour les séries
     * @param personRepository repository pour les utilisateurs
     * @param episodesRepository repository pour les épisodes
     */
    public RatingsService(RatingsRepository ratingsRepository, SeriesRepository seriesRepository,
                          PersonRepository personRepository, EpisodesRepository episodesRepository) {
        this.ratingsRepository = ratingsRepository;
        this.seriesRepository = seriesRepository;
        this.personRepository = personRepository;
        this.episodesRepository = episodesRepository;
    }

    // ------------------------------------------------------------
    // ---------- GESTION DES SERIES ----------
    // ------------------------------------------------------------

    /**
     * Ajoute ou met à jour une évaluation d’une série pour un utilisateur donné.
     *
     * Si l’utilisateur a déjà noté cette série, la note est mise à jour.
     * Sinon, une nouvelle évaluation est créée.
     *
     *
     * @param seriesId identifiant de la série à évaluer
     * @param personId identifiant de l’utilisateur
     * @param score note attribuée (entre 1 et 5)
     * @return l’objet {@link Ratings} enregistré ou mis à jour
     * @throws IllegalArgumentException si la note est hors limites ou si la série/utilisateur n’existe pas
     */
    public Ratings addOrUpdatingRatingBySerie(Long seriesId, int personId, int score) {
        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("La note doit être entre 1 et 5");
        }

        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new IllegalArgumentException("Série introuvable"));
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

        List<Ratings> existingRatings = ratingsRepository.findAllByPersonAndSeries(person, series);

        Ratings rating;
        if (!existingRatings.isEmpty()) {
            rating = existingRatings.get(0);
            rating.setScore(score);
        } else {
            rating = new Ratings(score, person, series);
        }

        return ratingsRepository.save(rating);
    }

    /**
     * Calcule la moyenne des notes attribuées à une série.
     *
     * @param seriesId identifiant de la série
     * @return la moyenne des notes (0.0 si aucune note)
     * @throws IllegalArgumentException si la série n’existe pas
     */
    public double getAverageRatingsBySerie(Long seriesId) {
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new IllegalArgumentException("Série introuvable"));
        Double avg = ratingsRepository.findAverageBySeries(series);
        return avg != null ? avg : 0.0;
    }

    // ------------------------------------------------------------
    // ---------- GESTION DES EPISODES ----------
    // ------------------------------------------------------------

    /**
     * Ajoute ou met à jour une évaluation d’un épisode pour un utilisateur donné.
     *
     * Si l’utilisateur a déjà noté cet épisode, la note est mise à jour.
     * Sinon, une nouvelle évaluation est créée.
     *
     *
     * @param episodeId identifiant de l’épisode à évaluer
     * @param personId identifiant de l’utilisateur
     * @param score note attribuée (entre 1 et 5)
     * @return l’objet {@link Ratings} enregistré ou mis à jour
     * @throws IllegalArgumentException si la note est hors limites ou si l’épisode/utilisateur n’existe pas
     */
    public Ratings addOrUpdatingRatingByEpisode(Long episodeId, int personId, int score) {
        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("La note doit être entre 1 et 5");
        }

        Episodes episode = episodesRepository.findById(episodeId)
                .orElseThrow(() -> new IllegalArgumentException("Épisode introuvable"));
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

        List<Ratings> existingRatings = ratingsRepository.findAllByPersonAndEpisode(person, episode);

        Ratings rating;
        if (!existingRatings.isEmpty()) {
            rating = existingRatings.get(0);
            rating.setScore(score);
        } else {
            rating = new Ratings(score, person, episode);
        }

        return ratingsRepository.save(rating);
    }

    /**
     * Calcule la moyenne des notes attribuées à un épisode.
     *
     * @param episodeId identifiant de l’épisode
     * @return la moyenne des notes (0.0 si aucune note)
     * @throws IllegalArgumentException si l’épisode n’existe pas
     */
    public double getAverageRatingsByEpisode(Long episodeId) {
        Episodes episode = episodesRepository.findById(episodeId)
                .orElseThrow(() -> new IllegalArgumentException("Épisode introuvable"));
        Double avg = ratingsRepository.findAverageByEpisode(episode);
        return avg != null ? avg : 0.0;
    }
}
