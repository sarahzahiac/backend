package ikasaidi.backend_lab.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
public class Ratings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score; // note entre 1 et 5

    @ManyToOne
    @JoinColumn(name = "person_id")
    @JsonIgnoreProperties({"ratings", "history", "viewsHistories"})
    private Person person;   // utilisateur qui a noté

    @ManyToOne
    @JoinColumn(name = "series_id")
    @JsonIgnoreProperties({"ratings", "viewsHistories", "persons"})
    private Series series;   // série notée

    @ManyToOne
    @JoinColumn(name = "episode_id")
    @JsonIgnoreProperties({"ratings", "series"})
    private Episodes episode;



    public Ratings() {}

    public Ratings(int score, Person person, Series series) {
        this.score = score;
        this.person = person;
        this.series = series;
    }

    public Ratings(int score, Person person, Episodes episode) {
        this.score = score;
        this.person = person;
        this.episode = episode;
        this.series = episode.getSeries();
    }

    // --- Getters & Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Episodes getEpisode() {
        return episode;
    }
    public void setEpisode(Episodes episode) {
        this.episode = episode;
    }
}