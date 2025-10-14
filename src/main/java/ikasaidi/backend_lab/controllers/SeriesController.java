package ikasaidi.backend_lab.controllers;

import ikasaidi.backend_lab.DTO.TrendingDto;
import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.services.SeriesService;
import ikasaidi.backend_lab.services.TrendingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST responsable de la gestion des séries.
 *
 * Ce contrôleur fournit plusieurs endpoints permettant de :
 * <ul>
 *     <li>Afficher, créer, modifier et supprimer des séries.</li>
 *     <li>Rechercher des séries selon leur genre ou leur nombre d’épisodes.</li>
 *     <li>Obtenir les séries tendances (trending).</li>
 * </ul>
 *
 *
 * Les opérations métier sont assurées par {@link SeriesService} et {@link TrendingService}.
 *
 * @author Rachel
 * @author Ikram
 * @author Sarah
 * @version 1.0
 */
@RestController
@RequestMapping("/series")
@CrossOrigin
public class SeriesController {

    private final SeriesService seriesService;
    private final TrendingService trendingService;

    /**
     * Constructeur du contrôleur des séries.
     *
     * @param seriesService service gérant les opérations CRUD sur les séries
     * @param trendingService service fournissant les séries tendances
     */
    public SeriesController(SeriesService seriesService, TrendingService trendingService) {
        this.seriesService = seriesService;
        this.trendingService = trendingService;
    }

    /**
     * Récupère la liste complète de toutes les séries disponibles.
     *
     * @return une liste de toutes les séries enregistrées
     *
     * <ul>
     *     <li><b>200: </b>Si la liste est retournée avec succès</li>
     * </ul>
     *
     */
    @GetMapping
    public List<Series> getAllSeries() {
        return seriesService.findAll();
    }

    /**
     * Récupère une série spécifique par son identifiant unique.
     *
     * @param id identifiant de la série recherchée
     * @return la série correspondante
     *
     * @throws RuntimeException si la série n’est pas trouvée
     *
     * <ul>
     *     <li><b>200: </b>Si la série est trouvée</li>
     *     <li><b>404: </b>Si l'identifiant ne correspond pas à aucune série</li>
     * </ul>
     */
    @GetMapping("/{id}")
    public Series getOneSerie(@PathVariable long id) {
        return seriesService.findById(id);
    }

    /**
     * Ajoute une nouvelle série dans la base de données.
     *
     * @param newSeries objet {@link Series} contenant les informations de la série à créer
     * @return la série nouvellement enregistrée
     *
     * <ul>
     *     <li><b>201: </b>Si la série est créée avec succès</li>
     *     <li><b>400: </b>Si les données sont invalides</li>
     * </ul>
     */
    @PostMapping
    public Series addNewSerie(@RequestBody Series newSeries) {
        return seriesService.newSerie(newSeries);
    }

    /**
     * Met à jour une série existante.
     *
     * @param id identifiant de la série à modifier
     * @param updatedSerie objet {@link Series} contenant les nouvelles informations
     * @return la série mise à jour
     *
     * @throws RuntimeException si la série n’existe pas
     *
     * <ul>
     *     <li><b>200: </b>Si la mise à jour est réussie</li>
     *     <li><b>404: </b>Si la série n'existe pas</li>
     * </ul>
     */
    @PutMapping("/{id}")
    public Series updateSerie(@PathVariable Long id, @RequestBody Series updatedSerie) {
        return seriesService.updateSerie(id, updatedSerie);
    }

    /**
     * Supprime une série à partir de son identifiant.
     *
     * @param id identifiant de la série à supprimer
     *
     * <ul>
     *           <li><b>204: </b>Si la suppression est réussie</li>
     *           <li><b>404: </b>Si la série n'existe pas</li>
     * </ul>
     */
    @DeleteMapping("/{id}")
    public void DeleteSerie(@PathVariable Long id) {
        seriesService.deleteSerie(id);
    }

    /**
     * Recherche des séries selon leur genre et/ou leur nombre d’épisodes.
     *
     * @param genre (optionnel) genre de la série à rechercher
     * @param nbEpisodes (optionnel) nombre minimal d’épisodes
     * @return une liste de séries correspondant aux critères fournis
     *
     * <ul>
     *     <li><b>200: </b> Si la recherche est effectuée avec succès (liste vide possible)</li>
     * </ul>
     */
    @GetMapping("/search")
    public List<Series> searchSerie(@RequestParam(required = false) String genre,
                                    @RequestParam(required = false) Integer nbEpisodes) {
        return seriesService.searchSerie(genre, nbEpisodes);
    }

    /**
     * Récupère la liste des séries tendances.
     *
     * @return une liste de séries populaires au format {@link TrendingDto}
     *
     * <ul>
     *     <li><b>200: </b>Si la liste est générée avec succès</li>
     * </ul>
     *
     */
    @GetMapping("/trending")
    public List<TrendingDto> getTrending() {
        return trendingService.getTrending();
    }
}
