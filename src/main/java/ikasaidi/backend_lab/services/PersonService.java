package ikasaidi.backend_lab.services;

import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    private static final Logger logger = Logger.getLogger(PersonService.class.getName());
    public static final String COMMA_DELIMITER = ",";

    //Retourne une liste
    public List<Person> listPersons() {
        String personFilePath = "data/people.csv";
        List<Person> memoirePerson = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(personFilePath))) {
            //Pour ignorer la premiere ligne du fichier
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] peopleData = line.split(COMMA_DELIMITER);
                if (peopleData.length >= 5) {

                    int id = Integer.parseInt(peopleData[0].trim());
                    String name = (peopleData[1].trim()+ " " + peopleData[2].trim()).trim();
                    String email = peopleData[3].trim();
                    String gender = peopleData[4].trim();

                    memoirePerson.add(new Person(id, name, gender, email));
                }
            }
        }  catch (IOException e) {
            logger.info(e.getMessage());
        }
        return memoirePerson;
    }

    public void bdFromTheStart() {
        List<Person> persons = listPersons();
        if (!persons.isEmpty()) {
            personRepository.saveAll(persons);
            System.out.println("Import CSV terminé: " + persons.size() + " personnes");
        }
    }


    //Rechercher avec nom
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

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    // Trouver une personne
    public Person findPersonById(int id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne non trouvée"));
    }

    public Person addPerson(Person newPerson) {
        return personRepository.save(newPerson);
    }

    public Person updatePerson(int id, Person newData) {
        Person p = findPersonById(id);
        p.setName(newData.getName());
        p.setEmail(newData.getEmail());
        p.setGender(newData.getGender());
        return personRepository.save(p);
    }

    public boolean deletePerson(int id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
