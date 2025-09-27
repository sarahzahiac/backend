package ikasaidi.backend_lab.controllers;

import ikasaidi.backend_lab.models.Evaluation;
import ikasaidi.backend_lab.repositories.EvaluationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {
    private final EvaluationRepository repo;
    public EvaluationController(EvaluationRepository repo) { this.repo = repo; }
    @GetMapping
    public List<Evaluation> getAll() {
        return repo.findAll();
    }
}