package ikasaidi.backend_lab.repositories;

import ikasaidi.backend_lab.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findByNameContainingIgnoreCase(String name);
    Optional<Person> findByEmail(String email);

}
