package ikasaidi.backend_lab.controllers;

import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.repositories.PersonRepository;
import ikasaidi.backend_lab.repositories.SeriesRepository;
import ikasaidi.backend_lab.services.PersonService;
import ikasaidi.backend_lab.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST responsable de la gestion des utilisateurs (Person).
 * Ce contrôleur expose plusieurs endpoints pour :
 * <ul>
 *     <li>Consulter, créer, modifier et supprimer des personnes.</li>
 *     <li>Gérer l’historique de visionnement d’un utilisateur.</li>
 *     <li>Obtenir des recommandations personnalisées de séries.</li>
 * </ul>
 *
 *
 * Les données sont manipulées via les services {@link PersonService} et
 * {@link RecommendationService}, ainsi que les repositories correspondants.
 *
 * @author Rachel
 * @author Sarah
 * @author Ikram
 * @version 1.0
 */
@RestController
@RequestMapping("/persons")
@CrossOrigin
public class PersonController {

    private final PersonService personService;
    private final RecommendationService recommendationService;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private PersonRepository personRepository;

    /**
     * Constructeur du contrôleur.
     *
     * @param personService service de gestion des personnes
     * @param recommendationService service de recommandations de séries
     */
    public PersonController(PersonService personService, RecommendationService recommendationService) {
        this.personService = personService;
        this.recommendationService = recommendationService;
    }

    /**
     * Récupère la liste complète des utilisateurs.
     *
     * @return une liste de toutes les personnes enregistrées dans la base de données
     *
     * <ul>
     *     <li><b>200: </b>La liste est retournée avec succès</li>
     * </ul>
     */
    @GetMapping()
    public List<Person> getAllPersonsFromDB() {
        return personService.getAllPersons();
    }

    /**
     * Recherche des utilisateurs par nom.
     *
     * @param name nom ou fragment de nom à rechercher
     * @return liste des utilisateurs dont le nom correspond à la recherche
     *
     * <ul>
     *     <li><b>200: </b>Si la recherche est réussie (même si liste vide)</li>
     * </ul>
     */
    @GetMapping("/search")
    public List<Person> searchPerson(@RequestParam String name) {
        return personService.searchByName(name);
    }

    /**
     * Crée un nouvel utilisateur et l’ajoute à la base de données.
     *
     * @param newPerson objet {@link Person} contenant les informations du nouvel utilisateur
     * @return la personne nouvellement enregistrée
     *
     * <ul>
     *     <li><b>200: </b>Si la création réussit</li>
     *     <li><b>400: </b>Si les données sont invalides</li>
     * </ul>
     */
    @PostMapping()
    public Person createPerson(@RequestBody Person newPerson) {
        return personService.addPerson(newPerson);
    }

    /**
     * Récupère une personne à partir de son identifiant.
     *
     * @param id identifiant unique de la personne
     * @return la personne correspondante
     *
     * @throws RuntimeException si la personne n’existe pas
     *
     * <ul>
     *     <li><b>200: </b>Si la personne est trouvée</li>
     *     <li><b>404: </b>Si la personne est introuvable</li>
     * </ul>
     */
    @GetMapping("/{id}")
    public Person getPerson(@PathVariable int id) {
        return personService.findPersonById(id);
    }

    /**
     * Met à jour les informations d’une personne existante.
     *
     * @param id identifiant de la personne à modifier
     * @param updatedPerson objet contenant les nouvelles informations
     * @return la personne mise à jour
     *
     * <ul>
     *     <li><b>200: </b>Si la mise à jour est réussie</li>
     *     <li><b>404: </b>Si la personne est introuvable</li>
     * </ul>
     */
    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable int id, @RequestBody Person updatedPerson) {
        return personService.updatePerson(id, updatedPerson);
    }

    /**
     * Supprime une personne de la base de données.
     *
     * @param id identifiant de la personne à supprimer
     * @return un message indiquant le résultat de la suppression
     *
     * <ul>
     *     <li><b>200: </b>Si la suppression est réussie</li>
     *     <li><b>404: </b>Si la personne n'a pas été trouvée</li>
     * </ul>
     */
    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable int id) {
        boolean removed = personService.deletePerson(id);
        return removed ? "Personne supprimée avec succès" : "Personne non trouvée";
    }

    /**
     * Récupère l’historique de visionnement d’un utilisateur.
     *
     * @param id identifiant de la personne
     * @return la liste des séries visionnées par cette personne
     *
     * @throws RuntimeException si la personne n’existe pas
     *
     * <ul>
     *     <li><b>200: </b>Si la liste est retournée</li>
     *     <li><b>404: </b>Si la personne est introuvable</li>
     * </ul>
     */
    @GetMapping("/{id}/history")
    public List<Series> getUserHistory(@PathVariable int id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne non trouvée"));

        return person.getHistory();
    }

    /**
     * Ajoute une série à l’historique de visionnement d’un utilisateur.
     *
     * @param id identifiant de la personne
     * @param seriesId identifiant de la série à ajouter à l’historique
     * @return la personne mise à jour avec la nouvelle série ajoutée
     *
     * @throws RuntimeException si la personne ou la série n’existe pas
     *
     * <ul>
     *     <li><b>200: </b>Si l'ajout est réussi</li>
     *     <li><b>404: </b>Si la personne ou la série n'est pas trouvée</li>
     * </ul>
     */
    @PostMapping("/{id}/history/{seriesId}")
    public Person addSerieToHistory(@PathVariable int id, @PathVariable Long seriesId) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne non trouvée"));
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new RuntimeException("Série non trouvée"));

        person.getHistory().add(series);
        return personRepository.save(person);
    }

    /**
     * Retourne une liste de recommandations de séries pour un utilisateur donné.
     *
     * @param id identifiant de la personne
     * @return liste des séries recommandées
     *
     * <ul>
     *     <li><b>200: </b>Si la liste est générée avec succès</li>
     * </ul>
     */
    @GetMapping("/{id}/recommendation")
    public List<Series> getRecommendation(@PathVariable int id) {
        return recommendationService.getPersonsRecommendation(id);
    }
}
