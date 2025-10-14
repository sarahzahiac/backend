package ikasaidi.backend_lab.services;

import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.repositories.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

/**
 * Service responsable de la gestion des séries dans l’application.
 *
 * Cette classe encapsule la logique métier liée à l’entité {@link Series},
 * notamment :
 * <ul>
 *     <li>La récupération de toutes les séries ou d’une seule par ID.</li>
 *     <li>L’ajout, la modification et la suppression d’une série.</li>
 *     <li>La recherche de séries selon un genre ou un nombre minimal d’épisodes.</li>
 * </ul>
 *
 *
 *
 * Elle communique directement avec le {@link SeriesRepository} pour interagir
 * avec la base de données.
 *
 *
 * @author Rachel
 * @author Ikram
 * @author Sarah
 * @version 1.0
 */
@Service
public class SeriesService {

    /** Logger pour l’enregistrement d’informations et de messages d’erreur. */
    private static final Logger logger = Logger.getLogger(SeriesService.class.getName());

    /** Repository permettant l’accès et la gestion des séries. */
    private final SeriesRepository seriesRepository;

    /**
     * Constructeur injectant le repository des séries.
     *
     * @param seriesRepository repository pour accéder aux données des séries
     */
    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    // ------------------------------------------------------------
    // ---------- MÉTHODES CRUD PRINCIPALES ----------
    // ------------------------------------------------------------

    /**
     * Récupère toutes les séries disponibles dans la base de données.
     *
     * @return une liste de toutes les séries enregistrées
     */
    public List<Series> findAll() {
        return seriesRepository.findAll();
    }

    /**
     * Récupère une série à partir de son identifiant unique.
     *
     * @param id identifiant de la série recherchée
     * @return la série correspondante ou {@code null} si elle n’existe pas
     */
    public Series findById(Long id) {
        return seriesRepository.findById(id).orElse(null);
    }

    /**
     * Ajoute une nouvelle série dans la base de données.
     *
     * L’ID est remis à {@code null} avant la sauvegarde afin de garantir
     * la création d’un nouvel enregistrement.
     *
     *
     * @param s série à ajouter
     * @return la série sauvegardée avec son identifiant généré
     */
    public Series newSerie(Series s) {
        s.setId(null);
        return seriesRepository.save(s);
    }

    /**
     * Met à jour les informations d’une série existante.
     *
     * @param id identifiant de la série à mettre à jour
     * @param s objet contenant les nouvelles valeurs
     * @return la série mise à jour ou {@code null} si la série n’existe pas
     */
    public Series updateSerie(Long id, Series s) {
        return seriesRepository.findById(id)
                .map(serie -> {
                    serie.setNbEpisodes(s.getNbEpisodes());
                    serie.setGenre(s.getGenre());
                    serie.setTitle(s.getTitle());
                    serie.setNote(s.getNote());
                    return seriesRepository.save(serie);
                })
                .orElse(null);
    }

    /**
     * Supprime une série de la base de données.
     *
     * @param id identifiant de la série à supprimer
     */
    public void deleteSerie(Long id) {
        if (seriesRepository.existsById(id)) {
            seriesRepository.deleteById(id);
        } else {
            logger.warning("Série non trouvée : suppression impossible");
        }
    }

    // ------------------------------------------------------------
    // ---------- MÉTHODE DE RECHERCHE ----------
    // ------------------------------------------------------------

    /**
     * Recherche des séries en fonction d’un ou plusieurs critères.
     *
     * Cette méthode permet plusieurs combinaisons :
     * <ul>
     *     <li>Genre + nombre d’épisodes minimal.</li>
     *     <li>Genre uniquement.</li>
     *     <li>Nombre d’épisodes minimal uniquement.</li>
     *     <li>Sans critère (retourne toutes les séries).</li>
     * </ul>
     *
     *
     * @param genre genre des séries à rechercher (optionnel)
     * @param nbEpisodes nombre minimal d’épisodes (optionnel)
     * @return une liste de séries correspondant aux critères
     */
    public List<Series> searchSerie(String genre, Integer nbEpisodes) {
        if (genre != null && nbEpisodes != null) {
            return seriesRepository.findByGenreAndNbEpisodesGreaterThanEqual(genre, nbEpisodes);
        } else if (genre != null) {
            return seriesRepository.findByGenre(genre);
        } else if (nbEpisodes != null) {
            return seriesRepository.findByNbEpisodesGreaterThanEqual(nbEpisodes);
        } else {
            return seriesRepository.findAll();
        }
    }
}
