package rachelsil.lab01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rachelsil.lab01.model.Person;
import rachelsil.lab01.repository.PersonsRepository;
import rachelsil.lab01.service.PersonService;

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
    public String createNewPerson(@RequestBody Person newPerson){
        personsRepository.save(newPerson);
        return "Personne ajoutée avec succès";
    }

    @PutMapping("/{id}")
    public String updatePersonsById(@PathVariable int id, @RequestBody Person updatedPerson){
        Person person = personsRepository.findById(id);

        if(person == null){
            return "Personne avec id" + id + " non trouvée";
        }

        person.setName(updatedPerson.getName());
        person.setEmail(updatedPerson.getEmail());
        person.setGender(updatedPerson.getGender());

        personsRepository.save(person);

        return "Personne modifiée avec succès";
    }

    @DeleteMapping("/{id}")
    public String deletePersonsById(@PathVariable int id){
        Person persons = personsRepository.findById(id);

        if(persons == null){
            return "Personne avec id" + id + " non trouvée";
        }


        personsRepository.delete(persons);

        return "Personne supprimée avec succès";
    }

}
