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


/**
 * Classe responsable de l’initialisation de l’historique des vues.
 *
 * Cette classe simule le comportement des utilisateurs qui ont visionné
 * certains séries, afin de fournir des données de démonstration.
 *
 *
 *Localisation des données générées : table <strong>vues_history</strong>
 *
 * @author Rachel
 * @version 1.0
 */
@Component
public class VuesHistorySeeder implements CommandLineRunner {

    private final PersonRepository personRepository;
    private final SeriesRepository seriesRepository;
    private final VuesHistoryRepository vuesHistoryRepository;


    /**
     * Constructeur du seeder d’historique des vues.
     *
     * @param personRepository repository des utilisateurs
     * @param seriesRepository repository des épisodes
     * @param vuesHistoryRepository repository des historiques de vues
     */
    public VuesHistorySeeder(PersonRepository personRepository, SeriesRepository seriesRepository, VuesHistoryRepository vuesHistoryRepository) {
        this.personRepository = personRepository;
        this.seriesRepository = seriesRepository;
        this.vuesHistoryRepository = vuesHistoryRepository;
    }

    /**
     * Méthode exécutée au démarrage pour insérer des historiques de visionnement factices.
     *
     * @param args arguments de la ligne de commande (non utilisés)
     */
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
