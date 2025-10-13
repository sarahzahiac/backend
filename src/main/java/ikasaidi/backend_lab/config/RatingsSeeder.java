package ikasaidi.backend_lab.config;

import ikasaidi.backend_lab.models.Episodes;
import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Ratings;
import ikasaidi.backend_lab.repositories.EpisodesRepository;
import ikasaidi.backend_lab.repositories.PersonRepository;
import ikasaidi.backend_lab.repositories.RatingsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Transactional
@Component
public class RatingsSeeder implements CommandLineRunner {

    private final PersonRepository personRepository;
    private final EpisodesRepository episodeRepository;
    private final RatingsRepository ratingsRepository;

    public RatingsSeeder(PersonRepository personRepository,
                         EpisodesRepository episodeRepository,
                         RatingsRepository ratingsRepository) {
        this.personRepository = personRepository;
        this.episodeRepository = episodeRepository;
        this.ratingsRepository = ratingsRepository;
    }

    @Override
    public void run(String... args) {

        List<Person> persons = personRepository.findAll();
        List<Episodes> episodes = episodeRepository.findAll();

        if (persons.isEmpty() || episodes.isEmpty()) {
            System.out.println("❌ Pas de personnes ou épisodes → aucun rating généré");
            return;
        }

        // 🧹 Nettoyage des anciennes données pour éviter les doublons
        ratingsRepository.deleteAll();

        Random random = new Random();
        int inserted = 0;

        for (Person p : persons) {
            for (Episodes ep : episodes) {

                if (ep.getId() == null || ep.getSeries() == null) {
                    System.out.println("⚠️ Épisode ignoré : id ou série null");
                    continue;
                }

                // Récupère une version gérée de l'épisode
                Episodes epManaged = episodeRepository.findById(ep.getId()).orElse(null);
                if (epManaged == null) continue;

                // Création d’une note aléatoire (pas pour tous les épisodes)
                if (random.nextBoolean()) {
                    int score = random.nextInt(5) + 1; // de 1 à 5

                    Ratings rating = new Ratings(score, p, epManaged);
                    ratingsRepository.save(rating);
                    inserted++;

                    System.out.printf("✅ Rating ajouté — Person: %s | EpisodeID: %d | SerieID: %d | Score: %d%n",
                            p.getName(), epManaged.getId(), epManaged.getSeries().getId(), score);
                }
            }
        }

        System.out.println("🎉 Ratings ajoutés : " + inserted);
    }
}
