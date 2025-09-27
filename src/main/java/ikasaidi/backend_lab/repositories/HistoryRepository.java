package ikasaidi.backend_lab.repositories;

import ikasaidi.backend_lab.models.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
