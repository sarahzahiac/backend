package ikasaidi.backend_lab.controllers;

import ikasaidi.backend_lab.DTO.TrendingDto;
import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.services.SeriesService;
import ikasaidi.backend_lab.services.TrendingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series")
@CrossOrigin()
public class SeriesController {

    private final SeriesService seriesService;
    private final TrendingService trendingService;

    public SeriesController(SeriesService seriesService, TrendingService trendingService) {
        this.seriesService = seriesService;
        this.trendingService = trendingService;
    }

    @GetMapping
    public List<Series> getAllSeries() {
        return seriesService.findAll();
    }

    @GetMapping("/{id}")
    public Series getOneSerie(@PathVariable long id ) {
        return seriesService.findById(id);
    }


    @PostMapping()
    public Series addNewSerie(@RequestBody Series newSeries) {
        return seriesService.newSerie(newSeries);
    }

    @PutMapping("/{id}")
    public Series updateSerie(@PathVariable Long id,@RequestBody Series updatedSerie) {
        return seriesService.updateSerie(id, updatedSerie);
    }

    @DeleteMapping("/{id}")
    public void DeleteSerie(@PathVariable Long id) {
         seriesService.deleteSerie(id);
    }

    @GetMapping("/search")
    public List<Series> searchSerie (@RequestParam(required = false) String genre, @RequestParam(required = false) Integer nbEpisodes) {
        return seriesService.searchSerie(genre, nbEpisodes);
    }

    @GetMapping("/trending")
    public List<TrendingDto> getTrending() {
        return trendingService.getTrending();
    }




}
