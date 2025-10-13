package ikasaidi.backend_lab;

import ikasaidi.backend_lab.services.PersonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Classe principale de l’application Spring Boot.
 *
 * Elle initialise le contexte de l’application BackendLab
 * et démarre automatiquement le serveur intégré.
 *
 * Lors du démarrage, elle vérifie si la base de données est vide
 * et importe les données initiales des utilisateurs à partir du fichier CSV.
 *
 * @author Rachel
 * @author Sarah
 * @author Ikram
 * @author Aya
 * @version 1.0
 */
@SpringBootApplication
public class BackendLabApplication {

    /**
     * Point d’entrée principal de l’application Spring Boot.
     *
     * @param args les arguments de ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        SpringApplication.run(BackendLabApplication.class, args);
    }

    /**
     * Initialise les données de base lors du premier démarrage de l’application.
     *
     * Cette méthode utilise un {@link CommandLineRunner} pour vérifier si la table
     * des utilisateurs est vide. Si c’est le cas, elle charge les données initiales
     * en appelant {@link PersonService#bdFromTheStart()}.
     *
     * @param personService service de gestion des utilisateurs
     * @return un {@link CommandLineRunner} qui s’exécute automatiquement au démarrage
     */
    @Bean
    CommandLineRunner run(PersonService personService) {
        return args -> {
            if (personService.getAllPersons().isEmpty()) {
                personService.bdFromTheStart();
            }
        };
    }
}
