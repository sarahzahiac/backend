package ikasaidi.backend_lab.controllers;

import ikasaidi.backend_lab.models.User;
import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.repositories.UserRepository;
import ikasaidi.backend_lab.repositories.SeriesRepository;
import ikasaidi.backend_lab.services.PersonService;
import ikasaidi.backend_lab.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
@CrossOrigin()
public class UserController {

    private final PersonService personService;
    private final RecommendationService recommendationService;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    UserRepository userRepository;

    public UserController(PersonService personService, RecommendationService recommendationService) {
        this.personService = personService;
        this.recommendationService = recommendationService;
    }
    @GetMapping()
    public List<User> getAllPersonsFromDB() {
        return personService.getAllPersons();
    }

    @GetMapping("/search")
    public List<User> searchPerson(@RequestParam String name) {
        return personService.searchByName(name);
    }

    @PostMapping()
    public User createPerson(@RequestBody User newUser) {
        return personService.addPerson(newUser);
    }

    @GetMapping("/{id}")
    public User getPerson(@PathVariable int id) {
        return personService.findPersonById(id);
    }


    @PutMapping("/{id}")
    public User updatePerson(@PathVariable int id, @RequestBody User updatedUser) {
        return personService.updatePerson(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable int id) {
        boolean removed = personService.deletePerson(id);
        return removed ? "Personne supprimée avec succès" : "Personne non trouvée";
    }

    @GetMapping("/{id}/history")
    public List<Series> getUserHistory(@PathVariable int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne non trouvée"));

        return user.getHistory();
    }

    @PostMapping("/{id}/history/{seriesId}")
    public User addSerieToHistory(@PathVariable int id, @PathVariable Long seriesId){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne non trouvée"));
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new RuntimeException("Série non trouvée"));

        user.getHistory().add(series);
        return userRepository.save(user);
    }



    @GetMapping("/{id}/recommendation")
    public List<Series> getRecommendation(@PathVariable int id) {
        return recommendationService.getPersonsRecommendation(id);
    }

}
