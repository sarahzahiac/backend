package ikasaidi.backend_lab.controllers;


import ikasaidi.backend_lab.models.VuesHistory;
import ikasaidi.backend_lab.repositories.VuesHistoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vues")
public class VuesHistoryController {

    private final VuesHistoryRepository vuesHistoryRepository;

    public VuesHistoryController(VuesHistoryRepository vuesHistoryRepository) {
        this.vuesHistoryRepository = vuesHistoryRepository;
    }

    // 🔹 Récupérer tout l’historique
    @GetMapping
    public List<VuesHistory> getAllHistories() {
        return vuesHistoryRepository.findAll();
    }
}
