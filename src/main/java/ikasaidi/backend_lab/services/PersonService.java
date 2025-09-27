package ikasaidi.backend_lab.services;

import ikasaidi.backend_lab.models.User;
import ikasaidi.backend_lab.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final UserRepository userRepository;
    public PersonService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static final Logger logger = Logger.getLogger(PersonService.class.getName());
    public static final String COMMA_DELIMITER = ",";

    //Retourne une liste
    public List<User> listPersons() {
        String personFilePath = "data/people.csv";
        List<User> memoireUsers = new ArrayList<>();

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

                    memoireUsers.add(new User(id, name, gender, email));
                }
            }
        }  catch (IOException e) {
            logger.info(e.getMessage());
        }
        return memoireUsers;
    }

    public void bdFromTheStart() {
        List<User> users = listPersons();
        if (!users.isEmpty()) {
            userRepository.saveAll(users);
            System.out.println("Import CSV terminé: " + users.size() + " personnes");
        }
    }


    //Rechercher avec nom
    public List<User> searchByName(String name) {
        List<User> allUsers = listPersons();
        List<User> filtered = new ArrayList<>();

        for (User user : allUsers) {
            if (user.getName().toLowerCase().contains(name.toLowerCase())) {
                filtered.add(user);
            }
        }
        return filtered;
    }

    public List<User> getAllPersons() {
        return userRepository.findAll();
    }

    // Trouver une personne
    public User findPersonById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne non trouvée"));
    }

    public User addPerson(User newUser) {
        return userRepository.save(newUser);
    }

    public User updatePerson(int id, User newData) {
        User p = findPersonById(id);
        p.setName(newData.getName());
        p.setEmail(newData.getEmail());
        p.setGender(newData.getGender());
        return userRepository.save(p);
    }

    public boolean deletePerson(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
