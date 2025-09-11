package rachelsil.lab01.service;

import org.springframework.stereotype.Service;
import rachelsil.lab01.model.Person;
import rachelsil.lab01.repository.PersonsRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final PersonsRepository personsRepository;

    public PersonService(PersonsRepository personsRepository){
        this.personsRepository = personsRepository;
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
                    String name = (peopleData[1].trim() + " " + peopleData[2].trim()).trim();
                    String email = peopleData[4].trim();
                    String gender = peopleData[5].trim();

                    memoirePerson.add(new Person(id, name, email, gender));
                }
            }
        }  catch (IOException e) {
            logger.info(e.getMessage());
        }
        return memoirePerson;
    }

    private void savePersons(List<Person> persons){
        try(PrintWriter pw = new PrintWriter("data/people.csv")){
            pw.println("id, name, email, gender");
            for(Person p : persons){
                pw.println(p.getId() + "," + p.getName() + "," + p.getEmail() + "," + p.getGender());
            }
        } catch (Exception e){
            e.getMessage();
        }
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

    public Person addPerson(Person newPerson){
        return personsRepository.save(newPerson);
    }

    public Person updatePerson(int id, Person newData) {
        return personsRepository.findById(id)
                .map(p -> {
                    p.setName(newData.getName());
                    p.setEmail(newData.getEmail());
                    p.setGender(newData.getGender());
                    return personsRepository.save(p);
                })
                .orElse(null);
    }

    public boolean deletePerson(int id){
        if (personsRepository.existsById((id))) {

            personsRepository.deleteById(id);
            return true;
        }
        return false;
    }

}

