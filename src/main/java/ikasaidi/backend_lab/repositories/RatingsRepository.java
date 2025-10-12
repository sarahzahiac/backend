package ikasaidi.backend_lab.repositories;

import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Ratings;
import ikasaidi.backend_lab.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingsRepository extends JpaRepository<Ratings, Long> {
    List<Ratings> findByPerson(Person person);

    //Seed uniquement les (person, série) qui n’ont pas encore de rating
    boolean existsByPersonAndSeries(Person person, Series series);

}