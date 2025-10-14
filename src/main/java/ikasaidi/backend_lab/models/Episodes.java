package ikasaidi.backend_lab.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entité JPA représentant un épisode appartenant à une série.
 *
 * Chaque épisode contient des informations telles que son titre, son numéro
 * dans la saison, la série à laquelle il est rattaché et les évaluations
 * associées par les utilisateurs.
 *
 *
 *
 * Cette classe est mappée à la table <strong>episodes</strong> de la base
 * de données et est liée à :
 * <ul>
 *     <li>{@link Series} (relation ManyToOne)</li>
 *     <li>{@link Ratings} (relation OneToMany)</li>
 * </ul>
 *
 *
 * @author Sarah
 * @version 1.0
 */
@Entity
@Table(name = "episodes")
public class Episodes {

    /** Identifiant unique de l’épisode (clé primaire). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Titre de l’épisode. */
    private String title;

    /** Numéro de l’épisode dans la saison. */
    private int episodeNumber;

    /**
     * Série à laquelle cet épisode appartient.
     *
     * Relation ManyToOne : plusieurs épisodes peuvent appartenir à une seule série.
     *
     */
    @ManyToOne
    @JoinColumn(name = "series_id")
    @JsonIgnoreProperties({"episodes", "ratings"})
    private Series series;

    /**
     * Liste des évaluations (ratings) associées à cet épisode.
     *
     * Relation OneToMany : un épisode peut avoir plusieurs notes attribuées
     * par différents utilisateurs.
     *
     */
    @OneToMany(mappedBy = "episode", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"episode", "series"})
    private List<Ratings> ratings = new ArrayList<>();

    /**
     * Constructeur par défaut (requis par JPA).
     */
    public Episodes() {}

    /**
     * Constructeur complet permettant de créer un nouvel épisode.
     *
     * @param title titre de l’épisode
     * @param episodeNumber numéro de l’épisode dans la saison
     * @param series série à laquelle l’épisode est rattaché
     */
    public Episodes(String title, int episodeNumber, Series series) {
        this.title = title;
        this.episodeNumber = episodeNumber;
        this.series = series;
    }

    /** @return identifiant unique de l’épisode */
    public Long getId() {
        return id;
    }

    /** @param id identifiant unique à définir */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return titre de l’épisode */
    public String getTitle() {
        return title;
    }

    /** @param title nouveau titre à définir */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return numéro de l’épisode dans la saison */
    public int getEpisodeNumber() {
        return episodeNumber;
    }

    /** @param episodeNumber nouveau numéro d’épisode à définir */
    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    /** @return série à laquelle l’épisode appartient */
    public Series getSeries() {
        return series;
    }

    /** @param series nouvelle série à associer à l’épisode */
    public void setSeries(Series series) {
        this.series = series;
    }
}
