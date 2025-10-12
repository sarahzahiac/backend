package ikasaidi.backend_lab.services;

import ikasaidi.backend_lab.DTO.TrendingDto;
import ikasaidi.backend_lab.models.Ratings;
import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.models.VuesHistory;
import ikasaidi.backend_lab.repositories.RatingsRepository;
import ikasaidi.backend_lab.repositories.SeriesRepository;
import ikasaidi.backend_lab.repositories.VuesHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TrendingService (avec filtre "7 jours")
 *
 * Étapes :
 *  1) Lire toutes les séries, toutes les notes, toutes les vues
 *  2) Compter TOUTES les vues par série par date
 *  3) Calculer la moyenne des notes par série
 *  4) score = views * factor1 + avgRating * factor2
 *  5) Trier par score (desc) et garder Top 10
 * - On ignore les vues dont la date est antérieure à (aujourd'hui - 7 jours).
 */

@Service
public class TrendingService {

    // === Constantes (modifiable ici si besoin) === //
    // +1 étoile de moyenne ~ +10 points de score
    private static final int DAYS = 7; //  "récents" en jours
    private static final double FACTOR_VIEWS  = 1.0;   // poids des vues
    private static final double FACTOR_RATING = 10.0;  // poids de la note moyenne
    private static final int    TOP_LIMIT     = 10;    // Les Top 10

    private final RatingsRepository ratingsRepository;
    private final SeriesRepository seriesRepository;
    private final VuesHistoryRepository vuesHistoryRepository;

    public TrendingService(RatingsRepository ratingsRepository, SeriesRepository seriesRepository, VuesHistoryRepository vuesHistoryRepository) {
        this.ratingsRepository = ratingsRepository;
        this.seriesRepository = seriesRepository;
        this.vuesHistoryRepository = vuesHistoryRepository;
    }

    public List<TrendingDto> getTrending() {

        // 1) Charger les données
        List<Series> allSeries     = seriesRepository.findAll();
        List<Ratings> allRatings   = ratingsRepository.findAll();
        List<VuesHistory> allViews = vuesHistoryRepository.findAll();

        // 2) la date min qu'on accepte
        LocalDate minDate = LocalDate.now().minusDays(DAYS);

        // 3) Compter les vues des 7 derniers jours par serie
        // <seriesId, nbVue>
        Map<Long, Long> views7BySeries = new HashMap<>();
        for (VuesHistory vh : allViews) {
            //On prend que si c'est récent et non null
            LocalDate d = vh.getDateWatched();
            if (d == null) continue;
            if (d.isBefore(minDate)) continue;

            Long seriesId = vh.getSeries().getId();

            // on lit le compteur actuel pour la serie, sil est pas la on met 0
            Long currentViewId = views7BySeries.getOrDefault(seriesId, 0L);
            views7BySeries.put(seriesId, currentViewId + 1); //ajoute +1

        }

        // 4) Calculer la moyenne des notes par serie
        Map<Long, Integer> sumBySeries = new HashMap<>();
        Map<Long, Integer> countBySeries = new HashMap<>();

        for (Ratings r : allRatings) {
            Long seriesId = r.getSeries().getId();
            int score = r.getScore();

            sumBySeries.put(seriesId, sumBySeries.getOrDefault(seriesId, 0) + score);
            countBySeries.put(seriesId, countBySeries.getOrDefault(seriesId, 0) + 1);
        }

        //en transforme en moy
        Map <Long, Double> averageBySeries = new HashMap<>();
        for (Long seriesId : sumBySeries.keySet()) {
            int sum = sumBySeries.get(seriesId);
            int count = countBySeries.getOrDefault(seriesId, 0);
            double avg = (count == 0) ? 0.0 : (( double) sum) / count;

            avg = round2(avg);
            averageBySeries.put(seriesId, avg);
        }

        // 5) Faire la liste
        List<TrendingDto> resulat = new ArrayList<>();

        for (Series s : allSeries) {
            long views7d = views7BySeries.getOrDefault(s.getId(), 0L);
            double avg = averageBySeries.getOrDefault(s.getId(), 0.0);

            // formule
            double score = views7d * FACTOR_VIEWS + avg * FACTOR_RATING;
            score = round2(score);

            resulat.add(new TrendingDto(
                    s.getId(),
                    s.getTitle(),
                    views7d,
                    avg,
                    score
            ));
        }

        // 6) Trier et prendre le top 10
        resulat.sort((a,b) -> Double.compare(b.getScore(), a.getScore()));
        if (resulat.size() > TOP_LIMIT){
            return new ArrayList<>(resulat.subList(0, TOP_LIMIT));
        }

        return resulat;




    }

    // pour arrondir à 2 décimales
    private static double round2(double x) {
        return Math.round(x * 100.0) / 100.0;

    }

}
