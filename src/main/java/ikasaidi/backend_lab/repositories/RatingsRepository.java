package ikasaidi.backend_lab.repositories;

import ikasaidi.backend_lab.models.Episodes;
import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Ratings;
import ikasaidi.backend_lab.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RatingsRepository extends JpaRepository<Ratings, Long> {
    List<Ratings> findByPerson(Person person);

    boolean existsByPersonAndSeries(Person person, Series series);
    boolean existsByPersonAndEpisode(Person person, Episodes episode);

    // SERIES
    List<Ratings> findAllByPersonAndSeries(Person person, Series series);

    @Query("SELECT AVG(r.score) FROM Ratings r WHERE r.series = :series")
    Double findAverageBySeries(@Param("series") Series series);

    // EPISODES
    List<Ratings> findAllByPersonAndEpisode(Person person, Episodes episode);

    @Query("SELECT AVG(r.score) FROM Ratings r WHERE r.episode = :episode")
    Double findAverageByEpisode(@Param("episode") Episodes episode);
}