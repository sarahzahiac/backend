package ikasaidi.backend_lab.repositories;

import ikasaidi.backend_lab.models.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
