package ikasaidi.backend_lab.services;

import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.repositories.PersonRepository;
import ikasaidi.backend_lab.repositories.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class SeriesService {

    private static final Logger logger = Logger.getLogger(SeriesService.class.getName());
    private final SeriesRepository seriesRepository;

    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    //*GET /series→ liste toutes les séries
    public List<Series> findAll() {
        return seriesRepository.findAll();
    }

    //GET /series/{id}→ détail d'une série
    public Series findById(Long id) {
        return seriesRepository.findById(id).orElse(null);
    }

    //POST /series→ ajouter une série
    public Series newSerie(Series s) {

        s.setId(null);

        // Sauvegarder en base de données
        return seriesRepository.save(s);
    }

    //PUT /series/{id}→ modifier une série
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

    //DELETE /series/{id}→ supprimer une série
    public void deleteSerie(Long id) {
        if (seriesRepository.existsById(id)) {
            seriesRepository.deleteById(id);
        }else {
            logger.warning("Series not found");
        }

    }





}
