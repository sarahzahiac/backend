package ikasaidi.backend_lab.controllers;

import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {


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

}

