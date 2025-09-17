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

@RestController
@RequestMapping("/persons")
@CrossOrigin()
public class PersonController {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    SeriesRepository seriesRepository;
    private final PersonService personService;

    private final RecommendationService recommendationService;

    public PersonController(PersonService personService, RecommendationService recommendationService) {

        this.personService = personService;
        this.recommendationService = recommendationService;
    }

    @GetMapping("/getAllPerson")
    @ResponseBody
    public List<Person> getAllPerson() {
        return personService.listPersons();
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Person> searchPerson(@RequestParam String name) {
        return personService.searchByName(name);
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @PostMapping
    public Person createPerson(@RequestBody Person newPerson) {
        return personService.addPerson(newPerson);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable int id,@RequestBody Person updatedPerson) {
        return personService.updatePerson(id, updatedPerson);
    }
    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable int id) {
        boolean removed = personService.deletePerson(id);
        return removed ? "Personne supprimée avec succès" : "Personne non trouvée";
    }


    // Il va récupérer l'utisateur avec son historique selon son id
    @GetMapping("/{id}/history")
    public List<Series> getUserHistory(@PathVariable int id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne non trouvée"));

        return person.getHistory();
    }

    // Il va ajouter l'id de la nouvelle série vue dans l'historique de la personne selon son id
    @PostMapping("/{id}/history/{seriesId}")
    public Person addSerieToHistory(@PathVariable int id, @PathVariable Long seriesId){
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne non trouvée"));
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new RuntimeException("Série non trouvée"));

        person.getHistory().add(series);
        return personRepository.save(person);
    }

    @GetMapping("/{id}/recommendation")
    public List<Series> getRecommendation(@PathVariable int id){
        return recommendationService.getPersonsRecommendation(id);
    }


}

