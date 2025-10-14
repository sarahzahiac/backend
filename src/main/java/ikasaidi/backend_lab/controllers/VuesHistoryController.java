package ikasaidi.backend_lab.controllers;

import ikasaidi.backend_lab.models.VuesHistory;
import ikasaidi.backend_lab.repositories.VuesHistoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur REST responsable de la gestion de l’historique des vues.
 * <p>
 * Ce contrôleur permet d’accéder à la liste complète des historiques
 * de visionnement des utilisateurs. Il interagit directement avec
 * le {@link VuesHistoryRepository} pour récupérer les données stockées.
 * </p>
 *
 * <p>Chaque enregistrement représente une action d’un utilisateur
 * ayant visionné un épisode ou une série à une date donnée.</p>
 *
 * @author Rachel
 * @version 1.0
 */
@RestController
@RequestMapping("/vues")
public class VuesHistoryController {

    private final VuesHistoryRepository vuesHistoryRepository;

    /**
     * Constructeur du contrôleur d’historique des vues.
     *
     * @param vuesHistoryRepository repository utilisé pour accéder aux données d’historique
     */
    public VuesHistoryController(VuesHistoryRepository vuesHistoryRepository) {
        this.vuesHistoryRepository = vuesHistoryRepository;
    }

    /**
     * Récupère la liste complète des historiques de visionnement.
     *
     * @return une liste d’objets {@link VuesHistory} contenant les informations
     *         sur les épisodes ou séries visionnés par les utilisateurs
     *
     * <ul>
     *     <li><b>200: </b>Si la liste est retournée avec succès</li>
     * </ul>
     */
    @GetMapping
    public List<VuesHistory> getAllHistories() {
        return vuesHistoryRepository.findAll();
    }
}
