package ikasaidi.backend_lab.repositories;

import ikasaidi.backend_lab.models.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingsRepository extends JpaRepository<Ratings, Long> {
}