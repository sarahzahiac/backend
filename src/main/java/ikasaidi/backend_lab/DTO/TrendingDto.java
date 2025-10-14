package ikasaidi.backend_lab.DTO;

import lombok.Data;

/**
 * Objet de transfert de données (DTO) représentant les séries ou contenus
 * les plus populaires (tendances actuelles).
 *
 * Cet objet regroupe les informations essentielles d’une série ou d’un média
 * pour le classement des tendances, telles que :
 * <ul>
 *     <li>le nombre total de vues,</li>
 *     <li>la note moyenne des utilisateurs,</li>
 *     <li>et un score global calculé à partir de ces données.</li>
 * </ul>
 *
 *
 * <p>Ce DTO est généralement renvoyé par le
 * {@link ikasaidi.backend_lab.controllers.SeriesController}
 * via le service {@link ikasaidi.backend_lab.services.TrendingService}.</p>
 *
 * @author Ikram
 * @version 1.0
 */
@Data
public class TrendingDto {

    /** Identifiant unique de la série ou du contenu. */
    private Long id;

    /** Titre de la série ou du contenu. */
    private String title;

    /** Nombre total de vues enregistrées. */
    private long views;

    /** Note moyenne attribuée par les utilisateurs. */
    private double avgRating;

    /** Score global calculé à partir du nombre de vues et de la note moyenne. */
    private double score;

    /**
     * Constructeur par défaut (nécessaire pour la sérialisation JSON).
     */
    public TrendingDto() {
    }

    /**
     * Constructeur complet du DTO de tendance.
     *
     * @param id identifiant unique du contenu
     * @param title titre du contenu
     * @param views nombre total de vues
     * @param avgRating note moyenne
     * @param score score global calculé
     */
    public TrendingDto(Long id, String title, long views, double avgRating, double score) {
        this.id = id;
        this.title = title;
        this.views = views;
        this.avgRating = avgRating;
        this.score = score;
    }

    /** @return identifiant unique du contenu */
    public Long getId() {
        return id;
    }

    /** @param id identifiant unique à définir */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return titre de la série ou du contenu */
    public String getTitle() {
        return title;
    }

    /** @param title nouveau titre à définir */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return nombre total de vues enregistrées */
    public long getViews() {
        return views;
    }

    /** @param views nombre total de vues à définir */
    public void setViews(long views) {
        this.views = views;
    }

    /** @return note moyenne attribuée au contenu */
    public double getAvgRating() {
        return avgRating;
    }

    /** @param avgRating nouvelle note moyenne à définir */
    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    /** @return score global du contenu */
    public double getScore() {
        return score;
    }

    /** @param score nouveau score global à définir */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Retourne une représentation textuelle lisible de l’objet pour le débogage.
     *
     * @return chaîne de caractères décrivant le contenu du DTO
     */
    @Override
    public String toString() {
        return "TrendingDto{id=%d, title='%s', views=%d, avgRating=%.2f, score=%.2f}"
                .formatted(id, title, views, avgRating, score);
    }
}
