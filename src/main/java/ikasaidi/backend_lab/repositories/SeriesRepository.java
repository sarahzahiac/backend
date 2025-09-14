package ikasaidi.backend_lab.repositories;

import ikasaidi.backend_lab.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    List<Series> findByGenre(String genre);

    List<Series> findByNbEpisodesGreaterThanEqual(int nbEpisodes);

    List<Series> findByGenreAndNbEpisodesGreaterThanEqual(String genre, int nbEpisodes);

    List<Series> findByTitleContaining(String title);



}
