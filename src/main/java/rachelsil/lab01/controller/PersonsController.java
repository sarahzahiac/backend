package rachelsil.lab01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rachelsil.lab01.model.Persons;
import rachelsil.lab01.repository.PersonsRepository;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonsController {

    @Autowired
    PersonsRepository personsRepository;

    @GetMapping
    public List<Persons> getAllPersons(){
        return personsRepository.findAll();
    }

    @PostMapping
    public String createNewPerson(@RequestBody Persons newPerson){
        personsRepository.save(newPerson);
        return "Personne ajoutée avec succès";
    }

    @PutMapping("/{id}")
    public String updatePersonsById(@PathVariable int id, @RequestBody Persons updatedPerson){
        Persons person = personsRepository.findById(id);

        if(person == null){
            return "Personne avec id" + id + " non trouvée";
        }

        person.setFirst_name(updatedPerson.getFirst_name());
        person.setLast_name(updatedPerson.getLast_name());
        person.setEmail(updatedPerson.getEmail());
        person.setGender(updatedPerson.getGender());

        personsRepository.save(person);

        return "Personne modifiée avec succès";
    }

    @DeleteMapping("/{id}")
    public String deletePersonsById(@PathVariable int id){
        Persons persons = personsRepository.findById(id);

        if(persons == null){
            return "Personne avec id" + id + " non trouvée";
        }


        personsRepository.delete(persons);

        return "Personne supprimée avec succès";
    }

}
