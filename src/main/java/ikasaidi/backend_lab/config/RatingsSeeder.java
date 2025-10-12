package ikasaidi.backend_lab.config;

import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.models.Ratings;
import ikasaidi.backend_lab.repositories.PersonRepository;
import ikasaidi.backend_lab.repositories.SeriesRepository;
import ikasaidi.backend_lab.repositories.RatingsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class RatingsSeeder implements CommandLineRunner {

    private final PersonRepository personRepository;
    private final SeriesRepository seriesRepository;
    private final RatingsRepository ratingsRepository;

    public RatingsSeeder(PersonRepository personRepository,
                         SeriesRepository seriesRepository,
                         RatingsRepository ratingsRepository) {
        this.personRepository = personRepository;
        this.seriesRepository = seriesRepository;
        this.ratingsRepository = ratingsRepository;
    }

    @Override
    public void run(String... args) {

        List<Person> persons = personRepository.findAll();
        List<Series> seriesList = seriesRepository.findAll();

        if (persons.isEmpty() || seriesList.isEmpty()) {
            System.out.println("Pas de personnes ou de séries disponibles → aucun rating généré");
            return;
        }

        Random random = new Random();
        int inserted = 0;

        for (Person p : persons) {
            for (Series s : seriesList) {
                // Chaque personne ne note pas forcément chaque série

                if (!ratingsRepository.existsByPersonAndSeries(p, s)) {
                    if (random.nextBoolean()) {
                        int score = random.nextInt(5) + 1; // note entre 1 et 5
                        Ratings rating = new Ratings(score, p, s);
                        ratingsRepository.save(rating);

                        inserted++;
                    }
                }
            }
        }

        System.out.println("Ratings ajoutés (nouveaux) : " + inserted);
    }
}
