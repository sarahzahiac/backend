package ikasaidi.backend_lab.repositories;

import ikasaidi.backend_lab.models.Episodes;
import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Ratings;
import ikasaidi.backend_lab.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodesRepository extends JpaRepository<Episodes, Long> {
    List<Episodes> findBySeries(Series series);
}
