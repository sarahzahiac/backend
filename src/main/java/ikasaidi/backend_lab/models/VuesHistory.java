package ikasaidi.backend_lab.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class VuesHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateWatched; // date de visionnage
    private int progress;          // progression (épisode ou % vu)

    @ManyToOne
    @JsonIgnoreProperties({"viewsHistories", "history", "ratings"})
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateWatched() {
        return dateWatched;
    }

    public void setDateWatched(LocalDate dateWatched) {
        this.dateWatched = dateWatched;
    }

    public VuesHistory() {
    }

    public VuesHistory(LocalDate randomDate, int progress, Person person, Series series) {
        this.dateWatched = randomDate;
        this.progress = progress;
        this.person = person;
        this.series = series;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
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

    @ManyToOne
    @JsonIgnoreProperties({"viewsHistories", "ratings", "persons"})
    private Series series;
}
