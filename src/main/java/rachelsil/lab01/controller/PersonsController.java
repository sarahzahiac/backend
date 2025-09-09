package rachelsil.lab01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rachelsil.lab01.model.Person;
import rachelsil.lab01.repository.PersonsRepository;
import rachelsil.lab01.service.PersonService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonsController {

    @Autowired
    PersonsRepository personsRepository;

    private final PersonService personService;

    public PersonsController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/getAllPerson")
    public List<Person> getAllPerson() {
        return personService.listPersons();
    }

    @GetMapping("/search")
    public List<Person> searchPerson(@RequestParam String name){
        return personService.searchByName(name);
    }

    @GetMapping
    public List<Person> getAllPersons(){
        return personsRepository.findAll();
    }

    @PostMapping
    public Person createPerson(@RequestBody Person newPerson){
        return personService.addPerson(newPerson);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable int id, @RequestBody Person updatedPerson){
        return personService.updatePerson(id, updatedPerson);
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable int id){
        boolean removed = personService.deletePerson(id);
        return removed ? "Personne supprimée avec succès" : "Personne non trouvée";
    }

}
