package ikasaidi.backend_lab.services;

import ikasaidi.backend_lab.models.Person;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {

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
}
