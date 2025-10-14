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
import java.util.*;

/**
 * Service responsable du calcul des séries les plus populaires ("Trending").
 *
 * Cette classe analyse les données de visionnage et de notation pour identifier
 * les séries les plus regardées et les mieux notées des 7 derniers jours.
 *
 *Étapes du traitement :
 * <ol>
 *     <li>Récupération de toutes les séries, notes et vues récentes.</li>
 *     <li>Filtrage des vues sur les 7 derniers jours.</li>
 *     <li>Calcul du nombre total de vues récentes par série.</li>
 *     <li>Calcul de la moyenne des notes pour chaque série.</li>
 *     <li>Calcul d’un score de popularité basé sur : <code>score = views * factor1 + avgRating * factor2</code>.</li>
 *     <li>Tri des séries selon leur score décroissant et sélection du Top 10.</li>
 * </ol>
 *
 *
 * Ce service alimente le endpoint <code>/series/trending</code> via le {@link ikasaidi.backend_lab.controllers.SeriesController}.
 *
 *
 * @author Ikram
 * @version 1.0
 */
@Service
public class TrendingService {

    // === Constantes configurables === //

    /** Nombre de jours pris en compte pour la tendance (7 derniers jours). */
    private static final int DAYS = 7;

    /** Poids appliqué au nombre de vues dans le calcul du score. */
    private static final double FACTOR_VIEWS = 1.0;

    /** Poids appliqué à la note moyenne dans le calcul du score. */
    private static final double FACTOR_RATING = 10.0;

    /** Nombre maximal d’éléments renvoyés dans le classement (Top 10). */
    private static final int TOP_LIMIT = 10;

    // === Dépendances === //
    private final RatingsRepository ratingsRepository;
    private final SeriesRepository seriesRepository;
    private final VuesHistoryRepository vuesHistoryRepository;

    /**
     * Constructeur injectant les repositories nécessaires.
     *
     * @param ratingsRepository repository des évaluations
     * @param seriesRepository repository des séries
     * @param vuesHistoryRepository repository de l’historique des vues
     */
    public TrendingService(RatingsRepository ratingsRepository,
                           SeriesRepository seriesRepository,
                           VuesHistoryRepository vuesHistoryRepository) {
        this.ratingsRepository = ratingsRepository;
        this.seriesRepository = seriesRepository;
        this.vuesHistoryRepository = vuesHistoryRepository;
    }

    /**
     * Calcule la liste des séries les plus populaires des 7 derniers jours.
     *
     * Le score de tendance est calculé selon la formule :
     * <code>score = (nombre_de_vues * FACTOR_VIEWS) + (note_moyenne * FACTOR_RATING)</code>.
     *
     *
     * @return une liste triée (desc) de {@link TrendingDto} contenant les 10 séries les plus populaires
     */
    public List<TrendingDto> getTrending() {

        // 1️⃣ Charger toutes les données nécessaires
        List<Series> allSeries = seriesRepository.findAll();
        List<Ratings> allRatings = ratingsRepository.findAll();
        List<VuesHistory> allViews = vuesHistoryRepository.findAll();

        // 2️⃣ Calculer la date minimale (fenêtre de 7 jours)
        LocalDate minDate = LocalDate.now().minusDays(DAYS);

        // 3️⃣ Compter les vues récentes par série (<seriesId, nbVue>)
        Map<Long, Long> views7BySeries = new HashMap<>();
        for (VuesHistory vh : allViews) {
            LocalDate d = vh.getDateWatched();
            if (d == null || d.isBefore(minDate)) continue;

            Long seriesId = vh.getSeries().getId();
            Long currentViews = views7BySeries.getOrDefault(seriesId, 0L);
            views7BySeries.put(seriesId, currentViews + 1);
        }

        // 4️⃣ Calculer la moyenne des notes par série
        Map<Long, Integer> sumBySeries = new HashMap<>();
        Map<Long, Integer> countBySeries = new HashMap<>();

        for (Ratings r : allRatings) {
            Long seriesId = r.getSeries().getId();
            int score = r.getScore();

            sumBySeries.put(seriesId, sumBySeries.getOrDefault(seriesId, 0) + score);
            countBySeries.put(seriesId, countBySeries.getOrDefault(seriesId, 0) + 1);
        }

        // Convertir en moyenne
        Map<Long, Double> averageBySeries = new HashMap<>();
        for (Long seriesId : sumBySeries.keySet()) {
            int sum = sumBySeries.get(seriesId);
            int count = countBySeries.getOrDefault(seriesId, 0);
            double avg = (count == 0) ? 0.0 : ((double) sum) / count;
            averageBySeries.put(seriesId, round2(avg));
        }

        // 5️⃣ Calculer le score global de chaque série
        List<TrendingDto> result = new ArrayList<>();
        for (Series s : allSeries) {
            long views7d = views7BySeries.getOrDefault(s.getId(), 0L);
            double avg = averageBySeries.getOrDefault(s.getId(), 0.0);
            double score = round2(views7d * FACTOR_VIEWS + avg * FACTOR_RATING);

            result.add(new TrendingDto(
                    s.getId(),
                    s.getTitle(),
                    views7d,
                    avg,
                    score
            ));
        }

        // 6️⃣ Trier le résultat par score décroissant et limiter à 10
        result.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));

        if (result.size() > TOP_LIMIT) {
            return new ArrayList<>(result.subList(0, TOP_LIMIT));
        }

        return result;
    }

    /**
     * Arrondit un nombre à deux décimales.
     *
     * @param x valeur à arrondir
     * @return valeur arrondie à deux décimales
     */
    private static double round2(double x) {
        return Math.round(x * 100.0) / 100.0;
    }
}
