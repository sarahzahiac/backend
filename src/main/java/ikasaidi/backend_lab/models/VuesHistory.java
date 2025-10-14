package ikasaidi.backend_lab.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entité JPA représentant l’historique de visionnage d’un utilisateur.
 *
 * Chaque instance de cette classe correspond à une série visionnée par
 * un utilisateur à une certaine date, avec une progression donnée.
 *
 *
 *
 * Cette entité permet de suivre les activités des utilisateurs :
 * <ul>
 *     <li>La date du visionnage</li>
 *     <li>Le pourcentage ou le numéro d’épisode atteint</li>
 *     <li>Les relations avec {@link Person} et {@link Series}</li>
 * </ul>
 *
 *
 * Les données sont persistées dans la table <strong>vues_history</strong>.
 *
 * @author Rachel
 * @author Sarah
 * @author Ikram
 * @version 1.0
 */
@Entity
public class VuesHistory {

    /** Identifiant unique de l’enregistrement de visionnage (clé primaire). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Date à laquelle l’utilisateur a visionné le contenu. */
    @Column(name = "date_watched", nullable = false)
    private LocalDate dateWatched;

    /** Progression du visionnage (numéro d’épisode ou pourcentage vu). */
    private int progress;

    /**
     * Personne ayant visionné la série.
     *
     * Relation ManyToOne : un utilisateur peut avoir plusieurs historiques de visionnage.
     *
     */
    @ManyToOne
    @JsonIgnoreProperties({"viewsHistories", "history", "ratings"})
    private Person person;

    /**
     * Série visionnée par l’utilisateur.
     *
     * Relation ManyToOne : une série peut apparaître plusieurs fois dans
     * l’historique (visionnée par différents utilisateurs).
     *
     */
    @ManyToOne
    @JsonIgnoreProperties({"viewsHistories", "ratings", "persons"})
    private Series series;

    /** Constructeur par défaut (requis par JPA). */
    public VuesHistory() {}

    /**
     * Constructeur complet de l’historique de visionnage.
     *
     * @param dateWatched date à laquelle le visionnage a eu lieu
     * @param progress progression (épisode ou pourcentage)
     * @param person utilisateur ayant regardé la série
     * @param series série visionnée
     */
    public VuesHistory(LocalDate dateWatched, int progress, Person person, Series series) {
        this.dateWatched = dateWatched;
        this.progress = progress;
        this.person = person;
        this.series = series;
    }

    /**
     * Définit automatiquement la date de visionnage au moment de la persistance,
     * si aucune date n’a été fournie.
     */
    @PrePersist
    void ensureDate() {
        if (this.dateWatched == null) {
            this.dateWatched = LocalDate.now();
        }
    }

    /** @return identifiant unique de l’enregistrement */
    public Long getId() {
        return id;
    }

    /** @param id identifiant à définir */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return date de visionnage */
    public LocalDate getDateWatched() {
        return dateWatched;
    }

    /** @param dateWatched nouvelle date de visionnage à définir */
    public void setDateWatched(LocalDate dateWatched) {
        this.dateWatched = dateWatched;
    }

    /** @return progression du visionnage */
    public int getProgress() {
        return progress;
    }

    /** @param progress nouvelle progression à définir */
    public void setProgress(int progress) {
        this.progress = progress;
    }

    /** @return utilisateur ayant effectué le visionnage */
    public Person getPerson() {
        return person;
    }

    /** @param person nouvelle personne à associer */
    public void setPerson(Person person) {
        this.person = person;
    }

    /** @return série associée au visionnage */
    public Series getSeries() {
        return series;
    }

    /** @param series nouvelle série à associer */
    public void setSeries(Series series) {
        this.series = series;
    }
}
