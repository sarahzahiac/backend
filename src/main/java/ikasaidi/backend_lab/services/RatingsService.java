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
import java.util.Optional;

@Service
public class RatingsService {
    private final RatingsRepository ratingsRepository;
    private final SeriesRepository seriesRepository;
    private final PersonRepository personRepository;
    private final EpisodesRepository episodesRepository;

    public RatingsService(RatingsRepository ratingsRepository, SeriesRepository seriesRepository,
                          PersonRepository personRepository, EpisodesRepository episodesRepository) {
        this.ratingsRepository = ratingsRepository;
        this.seriesRepository = seriesRepository;
        this.personRepository = personRepository;
        this.episodesRepository = episodesRepository;
    }

    // ---------- SERIES ---------- //
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

    public double getAverageRatingsBySerie(Long seriesId) {
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new IllegalArgumentException("Série introuvable"));
        Double avg = ratingsRepository.findAverageBySeries(series);
        return avg != null ? avg : 0.0;
    }

    // ---------- EPISODES ---------- //
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

    public double getAverageRatingsByEpisode(Long episodeId) {
        Episodes episode = episodesRepository.findById(episodeId)
                .orElseThrow(() -> new IllegalArgumentException("Épisode introuvable"));
        Double avg = ratingsRepository.findAverageByEpisode(episode);
        return avg != null ? avg : 0.0;
    }
}
