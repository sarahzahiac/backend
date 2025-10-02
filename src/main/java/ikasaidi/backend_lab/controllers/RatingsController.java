package ikasaidi.backend_lab.controllers;

import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Ratings;
import ikasaidi.backend_lab.repositories.PersonRepository;
import ikasaidi.backend_lab.repositories.RatingsRepository;
import ikasaidi.backend_lab.repositories.SeriesRepository;
import ikasaidi.backend_lab.repositories.PersonRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingsController {

    private final RatingsRepository ratingsRepository;
    private final PersonRepository personRepository;
    private final SeriesRepository seriesRepository;

    public RatingsController(RatingsRepository ratingsRepository,
                             PersonRepository personRepository,
                             SeriesRepository seriesRepository) {
        this.ratingsRepository = ratingsRepository;
        this.personRepository = personRepository;
        this.seriesRepository = seriesRepository;
    }

    // 🔹 Récupérer toutes les évaluations
    @GetMapping
    public List<Ratings> getAllRatings() {
        return ratingsRepository.findAll();
    }

}