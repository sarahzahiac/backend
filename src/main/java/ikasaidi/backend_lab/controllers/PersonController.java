package ikasaidi.backend_lab.controllers;

import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.repositories.PersonRepository;
import ikasaidi.backend_lab.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    PersonRepository personRepository;
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/getAllPerson")
    public List<Person> getAllPerson() {
        return personService.listPersons();
    }

    @GetMapping("/search")
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

}

