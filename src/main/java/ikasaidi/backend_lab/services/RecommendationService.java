package ikasaidi.backend_lab.services;


import ikasaidi.backend_lab.models.User;
import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.repositories.UserRepository;
import ikasaidi.backend_lab.repositories.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecommendationService {

    //private final PersonService personService;
    private final UserRepository userRepository;
    private final SeriesRepository seriesRepository;

    public RecommendationService(PersonService personService, UserRepository userRepository, SeriesRepository seriesRepository){
        //this.personService = personService;
        this.userRepository = userRepository;
        this.seriesRepository = seriesRepository;
    }

    public List<Series> getPersonsRecommendation(Integer id){

        // Il va compter le nombre de genres le plus vus dans la liste history

            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Personne non trouvée"));
            List<Series> history = user.getHistory();

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
