package ikasaidi.backend_lab.services;

import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service de gestion des personnes (utilisateurs).
 *
 * Cette classe offre plusieurs fonctionnalités :
 * <ul>
 *     <li>Chargement des données d’utilisateurs à partir d’un fichier CSV.</li>
 *     <li>Importation initiale dans la base de données.</li>
 *     <li>Recherche, ajout, modification et suppression de personnes.</li>
 * </ul>
 *
 *
 *
 * Elle interagit directement avec le {@link PersonRepository}
 * pour manipuler les entités {@link Person}.
 *
 *
 * @author Rachel
 * @author Ikram
 * @author Sarah
 * @version 1.0
 */
@Service
public class PersonService {

    /** Repository pour la gestion des entités {@link Person}. */
    private final PersonRepository personRepository;

    /** Logger utilisé pour enregistrer les messages et erreurs liées aux fichiers. */
    private static final Logger logger = Logger.getLogger(PersonService.class.getName());

    /** Délimiteur utilisé pour lire les fichiers CSV. */
    public static final String COMMA_DELIMITER = ",";

    /**
     * Constructeur injectant le repository.
     *
     * @param personRepository repository de gestion des personnes
     */
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Lit le fichier CSV contenant les utilisateurs et retourne une liste de {@link Person}.
     *
     * Le fichier est lu ligne par ligne à partir du chemin <code>data/people.csv</code>.
     * Les colonnes attendues sont :
     * <ul>
     *     <li>id</li>
     *     <li>prénom et nom</li>
     *     <li>email</li>
     *     <li>genre</li>
     *     <li>mot de passe</li>
     * </ul>
     *
     *
     * @return une liste de personnes lues depuis le fichier CSV
     */
 public List<Person> listPersons() {
    String personFilePath = "data/people.csv";
    List<Person> memoirePerson = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(personFilePath))) {
        // Ignorer la première ligne (en-têtes du fichier)
        br.readLine();

        String line;
        int lineNumber = 1; // pour debug (1 = header)
        while ((line = br.readLine()) != null) {
            lineNumber++;
            if (line.trim().isEmpty()) {
                // ignorer les lignes vides
                continue;
            }

            // split en conservant les champs vides éventuels
            String[] peopleData = line.split(COMMA_DELIMITER, -1);
            // debug : afficher la ligne si jamais on a un souci
            // logger.info("DEBUG: ligne " + lineNumber + " -> " + Arrays.toString(peopleData));

            try {
                // Cas le plus complet attendu : id, prénom, nom, email, genre, password (6 colonnes)
                if (peopleData.length >= 6) {
                    int id = Integer.parseInt(peopleData[0].trim());
                    String name = (peopleData[1].trim() + " " + peopleData[2].trim()).trim();
                    String email = peopleData[3].trim();
                    String gender = peopleData[4].trim();
                    String password = peopleData[5].trim();

                    memoirePerson.add(new Person(id, name, gender, email, password));
                }
                // Cas alternatif : 5 colonnes (id, nom complet, email, genre, password)
                else if (peopleData.length == 5) {
                    int id = Integer.parseInt(peopleData[0].trim());
                    String name = peopleData[1].trim();
                    String email = peopleData[2].trim();
                    String gender = peopleData[3].trim();
                    String password = peopleData[4].trim();

                    memoirePerson.add(new Person(id, name, gender, email, password));
                }
                // Autres formats : ignorer mais logger pour debug
                else {
                    logger.warning("Ligne " + lineNumber + " ignorée : format inattendu -> " + Arrays.toString(peopleData));
                }
            } catch (NumberFormatException nfe) {
                logger.warning("Ligne " + lineNumber + " ignorée : id non numérique -> " + Arrays.toString(peopleData));
            } catch (Exception ex) {
                logger.warning("Erreur lors du parsing ligne " + lineNumber + " : " + ex.getMessage());
            }
        }
    } catch (FileNotFoundException fnf) {
        logger.info("Fichier non trouvé : " + fnf.getMessage());
    } catch (IOException e) {
        logger.info(e.getMessage());
    }
    return memoirePerson;
}
    /**
     * Charge les données du fichier CSV et les insère dans la base de données.
     *
     * Cette méthode est utile pour initialiser la table <strong>person</strong>
     * au démarrage de l’application avec des données simulées.
     *
     */
    public void bdFromTheStart() {
        List<Person> persons = listPersons();
        if (!persons.isEmpty()) {
            personRepository.saveAll(persons);
            System.out.println("Import CSV terminé: " + persons.size() + " personnes");
        }
    }

    /**
     * Recherche une liste de personnes dont le nom contient une chaîne donnée.
     *
     * @param name partie du nom à rechercher (insensible à la casse)
     * @return une liste de personnes correspondant au critère
     */
    public List<Person> searchByName(String name) {
        List<Person> allPerson = listPersons();
        List<Person> filtered = new ArrayList<>();

        for (Person person : allPerson) {
            if (person.getName().toLowerCase().contains(name.toLowerCase())) {
                filtered.add(person);
            }
        }
        return filtered;
    }

    /**
     * Récupère toutes les personnes stockées dans la base de données.
     *
     * @return une liste complète de {@link Person}
     */
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    /**
     * Recherche une personne par son identifiant unique.
     *
     * @param id identifiant de la personne à rechercher
     * @return la personne correspondante
     * @throws RuntimeException si la personne n’existe pas
     */
    public Person findPersonById(int id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne non trouvée"));
    }

    /**
     * Ajoute une nouvelle personne dans la base de données.
     *
     * @param newPerson personne à ajouter
     * @return la personne ajoutée
     */
    public Person addPerson(Person newPerson) {
        return personRepository.save(newPerson);
    }

    /**
     * Met à jour les informations d’une personne existante.
     *
     * @param id identifiant de la personne à modifier
     * @param newData nouvelles informations à enregistrer
     * @return la personne mise à jour
     */
    public Person updatePerson(int id, Person newData) {
        Person p = findPersonById(id);
        p.setName(newData.getName());
        p.setEmail(newData.getEmail());
        p.setGender(newData.getGender());
        return personRepository.save(p);
    }

    /**
     * Supprime une personne de la base de données.
     *
     * @param id identifiant de la personne à supprimer
     * @return {@code true} si la suppression est réussie, {@code false} sinon
     */
    public boolean deletePerson(int id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Méthode de hachage de mot de passe (désactivée pour le moment)
    // public Person people(Person person) {
    //     String hashedPassword = bCryptPasswordEncoder.encode(person.getPassword());
    //     person.setPassword(hashedPassword);
    //     return personRepository.save(person);
    // }
}
