package ikasaidi.backend_lab.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "series")
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String genre;
    private int nbEpisodes;
    private double note;

    public Series(Long id, String title, String genre, int nbEpisodes, double note) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.nbEpisodes = nbEpisodes;
        this.note = note;
    }

    public Series() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getNbEpisodes() {
        return nbEpisodes;
    }

    public void setNbEpisodes(int nbEpisodes) {
        this.nbEpisodes = nbEpisodes;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }


}