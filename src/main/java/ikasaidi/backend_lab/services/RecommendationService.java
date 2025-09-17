package ikasaidi.backend_lab.services;


import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.repositories.PersonRepository;
import ikasaidi.backend_lab.repositories.SeriesRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class RecommendationService {

    //private final PersonService personService;
    private final PersonRepository personRepository;
    private final SeriesRepository seriesRepository;

    public RecommendationService(PersonService personService, PersonRepository personRepository, SeriesRepository seriesRepository){
        //this.personService = personService;
        this.personRepository = personRepository;
        this.seriesRepository = seriesRepository;
    }

    public List<Series> getPersonsRecommendation(Integer id){

        // Il va compter le nombre de genres le plus vus dans la liste history

            Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("Personne non trouvée"));
            List<Series> history = person.getHistory();

            Map<String, Integer> genreCount = new HashMap<>();
            for(Series series : history){
                String genre = series.getGenre();
                genreCount.put(genre, genreCount.getOrDefault(genre, 0) + 1);
            }

            // Filtrer maximum 3 top de genres

            List<String> topGenres = new ArrayList<>();

            for(int i = 0; i < 3; i++){
                String bestGenre = null;
                int max = 0;

                for(Map.Entry<String, Integer> results : genreCount.entrySet()){
                    if(!topGenres.contains(results.getKey()) && results.getValue() > max){
                        bestGenre = results.getKey();
                        max = results.getValue();
                    }
                }

                if(bestGenre != null){
                    topGenres.add(bestGenre);
                }
            }

            // Rechercher les séries non vus pour chaque genre, maximum 3
            List<Series> allSeries = seriesRepository.findAll();
            List<Series> recommendations = new ArrayList<>();

        for (String genre : topGenres) {
            int count = 0;
            for (Series series : allSeries ){
                if (series.getGenre().equals(genre) && !history.contains(series)){
                    recommendations.add(series);
                    count++;
                    if(count == 3)break;
                }
            }
        }

            return recommendations;


    }

}
