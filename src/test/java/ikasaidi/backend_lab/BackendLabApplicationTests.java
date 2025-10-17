package ikasaidi.backend_lab;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Classe de test principale pour le projet {@link BackendLabApplication}.
 *
 * <b>Objectif :</b>
 * Vérifier que le contexte Spring Boot se charge correctement sans erreur.
 *
 * <b>Technologie utilisée :</b>
 * <ul>
 *   <li>JUnit 5 pour l’exécution du test.</li>
 *   <li>Spring Boot Test pour la configuration du contexte d’application.</li>
 * </ul>
 *
 * <b>Résultat attendu :</b>
 * L’application doit démarrer sans lever d’exception, confirmant que les beans et les dépendances
 * sont bien configurés.
 *
 * @author Ikram
 * @version 1.0
 */
@SpringBootTest
class BackendLabApplicationTests {

    /**
     * Teste le chargement du contexte Spring Boot.
     * <ul>
     *   <li>Le test réussit si le contexte démarre correctement.</li>
     *   <li>Aucune exception ne doit être levée durant l’initialisation.</li>
     * </ul>
     */
    @Test
    void contextLoads() {
    }
}
