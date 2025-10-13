package ikasaidi.backend_lab.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/**
 * Entité JPA représentant une évaluation (rating) faite par un utilisateur.
 *
 * Cette classe stocke les notes attribuées par les utilisateurs
 * aux séries et aux épisodes. Chaque enregistrement correspond
 * à une note donnée par une personne sur une série ou un épisode précis.
 *
 *
 *
 * Les relations sont définies ainsi :
 * <ul>
 *     <li>{@code @ManyToOne} avec {@link Person} — l'utilisateur ayant noté.</li>
 *     <li>{@code @ManyToOne} avec {@link Series} — la série concernée.</li>
 *     <li>{@code @ManyToOne} avec {@link Episodes} — l’épisode concerné.</li>
 * </ul>
 *
 *
 * <p>Les notes sont généralement comprises entre <strong>1 et 5</strong>.</p>
 *
 * @author Rachel
 * @author Sarah
 * @version 1.0
 */
@Entity
public class Ratings {

    /** Identifiant unique de l’évaluation (clé primaire). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Note attribuée par l’utilisateur (comprise entre 1 et 5). */
    private int score;

    /**
     * Personne ayant laissé cette évaluation.
     *
     * Relation ManyToOne : un utilisateur peut laisser plusieurs évaluations.
     *
     */
    @ManyToOne
    @JoinColumn(name = "person_id")
    @JsonIgnoreProperties({"ratings", "history", "viewsHistories"})
    private Person person;

    /**
     * Série concernée par cette évaluation.
     *
     * Relation ManyToOne : une série peut avoir plusieurs notes.
     *
     */
    @ManyToOne
    @JoinColumn(name = "series_id")
    @JsonIgnoreProperties({"ratings", "viewsHistories", "persons"})
    private Series series;

    /**
     * Épisode concerné par cette évaluation (facultatif si l’évaluation porte sur la série entière).
     *
     * Relation ManyToOne : un épisode peut avoir plusieurs évaluations.
     *
     */
    @ManyToOne
    @JoinColumn(name = "episode_id")
    @JsonIgnoreProperties({"ratings", "series"})
    private Episodes episode;

    /** Constructeur par défaut (requis par JPA). */
    public Ratings() {}

    /**
     * Constructeur utilisé pour noter une série.
     *
     * @param score note attribuée (1–5)
     * @param person utilisateur qui a laissé la note
     * @param series série concernée
     */
    public Ratings(int score, Person person, Series series) {
        this.score = score;
        this.person = person;
        this.series = series;
    }

    /**
     * Constructeur utilisé pour noter un épisode.
     *
     * @param score note attribuée (1–5)
     * @param person utilisateur ayant laissé la note
     * @param episode épisode concerné
     */
    public Ratings(int score, Person person, Episodes episode) {
        this.score = score;
        this.person = person;
        this.episode = episode;
        this.series = episode.getSeries();
    }

    /** @return identifiant unique de l’évaluation */
    public Long getId() {
        return id;
    }

    /** @param id identifiant à définir */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return note attribuée (1–5) */
    public int getScore() {
        return score;
    }

    /** @param score nouvelle note à définir */
    public void setScore(int score) {
        this.score = score;
    }

    /** @return utilisateur ayant laissé la note */
    public Person getPerson() {
        return person;
    }

    /** @param person nouvel utilisateur à associer */
    public void setPerson(Person person) {
        this.person = person;
    }

    /** @return série concernée par l’évaluation */
    public Series getSeries() {
        return series;
    }

    /** @param series nouvelle série à associer */
    public void setSeries(Series series) {
        this.series = series;
    }

    /** @return épisode concerné par l’évaluation */
    public Episodes getEpisode() {
        return episode;
    }

    /** @param episode nouvel épisode à associer */
    public void setEpisode(Episodes episode) {
        this.episode = episode;
    }
}
