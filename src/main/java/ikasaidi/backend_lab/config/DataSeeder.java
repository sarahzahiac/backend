package ikasaidi.backend_lab.config;

import ikasaidi.backend_lab.models.*;
import ikasaidi.backend_lab.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final SeriesRepository seriesRepository;
    private final HistoryRepository historyRepository;
    private final EvaluationRepository evaluationRepository;

    public DataSeeder(UserRepository userRepository, SeriesRepository seriesRepository,
                      HistoryRepository historyRepository, EvaluationRepository evaluationRepository){
        this.userRepository = userRepository;
        this.seriesRepository = seriesRepository;
        this.historyRepository = historyRepository;
        this.evaluationRepository = evaluationRepository;
    }

    // Générer les simulations des données

    @Override
    public void run(String... args) {
        if(userRepository.count() > 0 || seriesRepository.count() > 0) return;

        // Mocker les utilisateurs
        User u1 = new User(1, "Rachel", "Féminin", "rachel@email.com");
        User u2 = new User(2, "Sarah", "Féminin", "sarah@email.com");
        User u3 = new User(3, "Aya", "Féminin", "aya@email.com");
        User u4 = new User(4, "Ikram", "Féminin", "ikram@email.com");
        userRepository.saveAll(List.of(u1, u2, u3, u4));

        // Mocker les séries
        Series s1 = new Series(null, "Breaking Bad", "Action", 20, 5);
        Series s2 = new Series(null, "Stranger Things", "Mystère", 36, 3);
        Series s3 = new Series(null, "The Bear", "Drama", 18, 4);
        Series s4 = new Series(null, "One Piece", "Suspense", 1000, 4);
        seriesRepository.saveAll(List.of(s1, s2, s3, s4));

        // Mocker les historiques de série des utilisateurs
        u1.getHistory().addAll(List.of(s1, s2));
        u2.getHistory().add(s2);
        u3.getHistory().add(s3);
        u4.getHistory().addAll(List.of(s1, s4));
        userRepository.saveAll(List.of(u1, u2, u3, u4));

        // Partie Mocker les Trending selon les vues et les notes données aux séries

        // Mocker les vues des utilisateurs par dates
        Trending(u1, s2, 15, 0, 6); // Stranger Things : la plus vues
        Trending(u2, s2, 10, 0, 6); // encore Stranger Things : plus de vues récemment
        Trending(u3, s1, 5, 10, 20); // Breaking Bad = pas vraiment vues
        Trending(u4, s4, 2, 20, 40); // One Piece = pas très très vues
        Trending(u2, s3, 4, 15, 20); // etc

        // Mocker les evaluations des utilisateurs par séries
        evaluationRepository.saveAll(List.of(
                new Evaluation(5, u1, s2),
                new Evaluation(4, u2, s4),
                new Evaluation(2, u1, s1),
                new Evaluation(3, u3, s3)
        ));
    }

    // Générer des dates de vues
    private void Trending(User u, Series s, int count, int minDaysAgo, int maxDaysAgo){
        Random random = new Random();
        for (int i = 0; i < count; i++){
            int days = minDaysAgo + random.nextInt(maxDaysAgo - minDaysAgo + 1);
            History history = new History(LocalDateTime.now().minusDays(days), u, s);
            historyRepository.save(history);
        }
    }
}
