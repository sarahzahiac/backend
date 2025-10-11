package ikasaidi.backend_lab.repositories;

import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingsRepository extends JpaRepository<Ratings, Long> {
    List<Ratings> findByPerson(Person person);

}