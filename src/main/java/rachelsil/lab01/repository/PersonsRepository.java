package rachelsil.lab01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import rachelsil.lab01.model.Person;

@RepositoryRestResource(collectionResourceRel = "persons")
public interface PersonsRepository extends JpaRepository<Person, Integer> {

    Person findById(int id);
}
