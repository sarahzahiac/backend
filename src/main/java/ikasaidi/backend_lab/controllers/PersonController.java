package ikasaidi.backend_lab.controllers;

import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonService personService;

//    @GetMapping("/getAllPerson")
//    @ResponseBody
//    public List<Person> getAllPerson() {
//        return personService.listPersons();
//    }

}

