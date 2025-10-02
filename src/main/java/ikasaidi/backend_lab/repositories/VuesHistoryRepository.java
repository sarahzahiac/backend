package ikasaidi.backend_lab.repositories;

import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.models.VuesHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VuesHistoryRepository extends JpaRepository<VuesHistory, Long> {
}
