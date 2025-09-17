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

    // Liste csv
    public List<Person> readListPersons() {
        List<Person> memoirePerson = new ArrayList<>();

        try (InputStream is = getClass().getResourceAsStream("/data/people.csv")) {

            if (is == null) {
                throw new RuntimeException("Fichier CSV introuvable !");
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                br.readLine();

                String line;
                while ((line = br.readLine()) != null) {
                    String[] peopleData = line.split(COMMA_DELIMITER);
                    if (peopleData.length >= 5) {
                        int id = Integer.parseInt(peopleData[0].trim());
                        String name = (peopleData[1].trim() + " " + peopleData[2].trim()).trim();
                        String email = peopleData[3].trim();
                        String gender = peopleData[4].trim();

                        memoirePerson.add(new Person(id, name, email, gender));
                    }
                }
            }

        } catch (IOException e) {
            logger.info("Erreur lecture CSV : " + e.getMessage());
        }

        return memoirePerson;
    }

    // Écrire dans un fichier
    public static void writePersonsToCSV(List<Person> persons, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("id,first_name,last_name,email,gender");
            bw.newLine();

            for (Person person : persons) {
                String[] nameParts = person.getName().split(" ", 2);
                String firstName = nameParts.length > 0 ? nameParts[0] : "";
                String lastName = nameParts.length > 1 ? nameParts[1] : "";

                bw.write(person.getId() + "," +
                        firstName + "," +
                        lastName + "," +
                        person.getEmail() + "," +
                        person.getGender());
                bw.newLine();
            }
        } catch (IOException e) {
            logger.warning("Erreur écriture CSV : " + e.getMessage());
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

    public Person findPersonById(int id) {
        return listPersons().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Personne non trouvée"));
    }



    //Ajouter une personne
    public Person addPerson(Person newPerson) {
        String filePath = "data/people.csv";
        List<Person> persons = listPersons();

        // générer un nouvel ID unique
        int newId = persons.isEmpty() ? 1 : persons.get(persons.size() - 1).getId() + 1;
        newPerson.setId(newId);

        persons.add(newPerson);

        writePersonsToCSV(persons, filePath);

        return newPerson;
    }

    //Modifier une personne
    public Person updatePerson(int id, Person newData) {
        String filePath = "data/people.csv";
        List<Person> persons = listPersons();

        for (Person p : persons) {
            if (p.getId() == id) {
                p.setName(newData.getName());
                p.setEmail(newData.getEmail());
                p.setGender(newData.getGender());

                writePersonsToCSV(persons, filePath);
                return p;
            }
        }
        return null;
    }


    //Supprimer une personne
    public boolean deletePerson(int id) {
        String filePath = "data/people.csv";
        List<Person> persons = listPersons();

        boolean removed = persons.removeIf(p -> p.getId() == id);

        if (removed) {
            writePersonsToCSV(persons, filePath);
        }

        return removed;
    }

}
