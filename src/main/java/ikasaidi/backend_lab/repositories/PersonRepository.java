package ikasaidi.backend_lab.repositories;

import ikasaidi.backend_lab.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository de gestion des entités {@link Person}.
 *
 * Cette interface hérite de {@link JpaRepository}, ce qui lui permet de bénéficier
 * de toutes les opérations CRUD standards (création, lecture, mise à jour et suppression).
 *
 *
 * En plus des méthodes de base fournies par JPA, elle définit des requêtes
 * personnalisées pour rechercher une personne selon son nom ou son adresse courriel.
 *
 *
 * Méthodes héritées utiles :
 * <ul>
 *     <li>{@code findAll()} — Récupère toutes les personnes enregistrées.</li>
 *     <li>{@code findById(Integer id)} — Trouve une personne par son identifiant.</li>
 *     <li>{@code save(Person person)} — Ajoute ou met à jour une personne.</li>
 *     <li>{@code deleteById(Integer id)} — Supprime une personne.</li>
 * </ul>
 *
 *
 * Ce repository est utilisé dans :
 * <ul>
 *     <li>{@link ikasaidi.backend_lab.services.PersonService}</li>
 *     <li>{@link ikasaidi.backend_lab.services.AuthentificationService}</li>
 * </ul>
 *
 * @author Sarah
 * @author Aya
 * @version 1.0
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    /**
     * Recherche toutes les personnes dont le nom contient une chaîne donnée (insensible à la casse).
     *
     * @param name une partie du nom à rechercher
     * @return une liste de personnes correspondant au critère
     */
    List<Person> findByNameContainingIgnoreCase(String name);

    /**
     * Recherche une personne par son adresse courriel.
     *
     * Cette méthode est utilisée notamment pour l’authentification
     * dans le {@link ikasaidi.backend_lab.services.AuthentificationService}.
     *
     *
     * @param email l’adresse courriel de la personne
     * @return un {@link Optional} contenant la personne si elle existe, sinon vide
     */
    Optional<Person> findByEmail(String email);
}
