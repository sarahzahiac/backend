package ikasaidi.backend_lab.services;

import ikasaidi.backend_lab.models.Person;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PersonService {

    public static final String COMMA_DELIMITER = ",";

    //Retourne une liste
    public void listPersons() {
        String personFilePath = "data/people.csv";
        List<List<String>> memoire = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(personFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] person = line.split(COMMA_DELIMITER);
                memoire.add(Arrays.asList(person));
            }
        }  catch (IOException e) {
            e.getMessage();
        }


    }
}
