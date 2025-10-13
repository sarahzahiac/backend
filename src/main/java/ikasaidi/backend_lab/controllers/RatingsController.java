



package ikasaidi.backend_lab.controllers;

import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Ratings;
import ikasaidi.backend_lab.repositories.PersonRepository;
import ikasaidi.backend_lab.repositories.RatingsRepository;
import ikasaidi.backend_lab.repositories.SeriesRepository;
import ikasaidi.backend_lab.repositories.PersonRepository;
import ikasaidi.backend_lab.services.PersonService;
import ikasaidi.backend_lab.services.RatingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/ratings")
public class RatingsController {

    private final RatingsRepository ratingsRepository;
    private final PersonRepository personRepository;
    private final SeriesRepository seriesRepository;
    private final RatingsService ratingsService;


    public RatingsController(RatingsRepository ratingsRepository,
                             PersonRepository personRepository,
                             SeriesRepository seriesRepository,
                             RatingsService ratingsService) {
        this.ratingsRepository = ratingsRepository;
        this.personRepository = personRepository;
        this.seriesRepository = seriesRepository;
        this.ratingsService = ratingsService;
    }

    // Récupérer toutes les évaluations
    @GetMapping
    public List<Ratings> getAllRatings() {
        return ratingsRepository.findAll();
    }

    //Voir tt ls evaluation d'un utilisateur
    @GetMapping("/user/{id}")
    public List<Ratings> getRatingsByUserEmail(@PathVariable Integer id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isEmpty()) {
            return List.of();
        }
        return ratingsRepository.findByPerson(person.get());
    }

    //Evaluer un episode
    @PostMapping("/episode/{episodeId}")
    public Ratings addOrUpdateRatingsByEpisode(@PathVariable Long episodeId,@RequestParam int personId, @RequestParam int score){
        return ratingsService.addOrUpdatingRatingByEpisode(episodeId, personId, score);
    }

    //Evaluer une serie
    @PostMapping("/series/{seriesId}")
    public Ratings addOrUpdateRatingsBySerie(@PathVariable Long seriesId, @RequestParam int personId, @RequestParam int score) {
        return ratingsService.addOrUpdatingRatingBySerie(seriesId, personId, score);
    }

    //Obtenir la moyenne d'une serie
    @GetMapping("/series/{seriesId}/average")
    public double getAverageRatingSerie(@PathVariable Long seriesId) {
        return ratingsService.getAverageRatingsBySerie(seriesId);
    }

    //Obtenir la moyenne d'un episode
    @GetMapping("/episode/{episodeId}/average")
    public double getAverageRatingEpisode(@PathVariable Long episodeId) {
        return ratingsService.getAverageRatingsByEpisode(episodeId);
    }

}

