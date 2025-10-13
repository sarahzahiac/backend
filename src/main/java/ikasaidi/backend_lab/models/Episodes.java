package ikasaidi.backend_lab.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "episodes")
public class Episodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int episodeNumber;

    @ManyToOne
    @JoinColumn(name = "series_id")
    @JsonIgnoreProperties({"episodes", "ratings"})
    private Series series;

    @OneToMany(mappedBy = "episode", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"episode", "series"})
    private List<Ratings> ratings = new ArrayList<>();


    public Episodes() {} // constructeur par défaut

    public Episodes(String title, int episodeNumber, Series series) {
        this.title = title;
        this.episodeNumber = episodeNumber;
        this.series = series;
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

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }
}
