package ikasaidi.backend_lab.config;

import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.models.VuesHistory;
import ikasaidi.backend_lab.repositories.PersonRepository;
import ikasaidi.backend_lab.repositories.SeriesRepository;
import ikasaidi.backend_lab.repositories.VuesHistoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Component
public class VuesHistorySeeder implements CommandLineRunner {

    private final PersonRepository personRepository;
    private final SeriesRepository seriesRepository;
    private final VuesHistoryRepository vuesHistoryRepository;


    public VuesHistorySeeder(PersonRepository personRepository, SeriesRepository seriesRepository, VuesHistoryRepository vuesHistoryRepository) {
        this.personRepository = personRepository;
        this.seriesRepository = seriesRepository;
        this.vuesHistoryRepository = vuesHistoryRepository;
    }

    @Override
    public void run(String... args) {
        List<Person> persons = personRepository.findAll();
        List<Series> seriesList = seriesRepository.findAll();

        if (persons.isEmpty() || seriesList.isEmpty()) {
            System.out.println("Pas de personnes ou de séries → aucun historique généré.");
            return;
        }

        Random random = new Random();

        for (Person person : persons) {
            // Chaque personne regarde entre 1 et 3 séries
            int nbSeries = 1 + random.nextInt(3);

            for (int i = 0; i < nbSeries; i++) {
                Series series = seriesList.get(random.nextInt(seriesList.size()));

                // Date dans les 30 derniers jours
                LocalDate randomDate = LocalDate.now().minusDays(random.nextInt(30));

                // Progression (épisodes vus)
                int progress = 1 + random.nextInt(Math.max(series.getNbEpisodes(), 1));

                // Création et sauvegarde
                VuesHistory vh = new VuesHistory(randomDate, progress, person, series);
                vuesHistoryRepository.save(vh);
            }
        }

        System.out.println(" VuesHistory simulées insérées !");
    }
}
