package ikasaidi.backend_lab.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Entité JPA représentant une série.
 *
 * Cette classe contient les informations principales d'une série :
 * son titre, son genre, son nombre d'épisodes et sa note moyenne.
 *
 *
 *
 * Elle est liée à plusieurs autres entités :
 * <ul>
 *     <li>{@link Episodes} — pour la liste des épisodes appartenant à la série.</li>
 *     <li>{@link Ratings} — pour les évaluations données par les utilisateurs.</li>
 *     <li>{@link VuesHistory} — pour les enregistrements d’historique de visionnage.</li>
 * </ul>
 *
 *
 * Les données sont mappées à la table <strong>series</strong> dans la base de données.
 *
 * @author Rachel
 * @author Sarah
 * @author Ikram
 * @version 1.0
 */
@Data
@Entity
@Table(name = "series")
public class Series {

    /** Identifiant unique de la série (clé primaire). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Titre de la série. */
    private String title;

    /** Genre principal de la série (ex. "Action", "Comédie", "Drame"). */
    private String genre;

    /** Nombre total d'épisodes dans la série. */
    private int nbEpisodes;

    /** Note moyenne attribuée à la série (souvent basée sur les évaluations des utilisateurs). */
    private double note;

    /**
     * Constructeur complet de la série.
     *
     * @param id identifiant unique
     * @param title titre de la série
     * @param genre genre de la série
     * @param nbEpisodes nombre total d'épisodes
     * @param note note moyenne de la série
     */
    public Series(Long id, String title, String genre, int nbEpisodes, double note) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.nbEpisodes = nbEpisodes;
        this.note = note;
    }

    /** Constructeur par défaut (requis par JPA). */
    public Series() {}

    /** @return identifiant unique de la série */
    public Long getId() {
        return id;
    }

    /** @param id identifiant unique à définir */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return titre de la série */
    public String getTitle() {
        return title;
    }

    /** @param title nouveau titre à définir */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return genre de la série */
    public String getGenre() {
        return genre;
    }

    /** @param genre nouveau genre à définir */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /** @return nombre total d’épisodes */
    public int getNbEpisodes() {
        return nbEpisodes;
    }

    /** @param nbEpisodes nouveau nombre d’épisodes */
    public void setNbEpisodes(int nbEpisodes) {
        this.nbEpisodes = nbEpisodes;
    }

    /** @return note moyenne de la série */
    public double getNote() {
        return note;
    }

    /** @param note nouvelle note moyenne à définir */
    public void setNote(double note) {
        this.note = note;
    }

    // 🔹 Relations avec d'autres entités

    /**
     * Liste des épisodes appartenant à cette série.
     *
     * Relation OneToMany avec {@link Episodes}.
     *
     */
    @OneToMany(mappedBy = "series")
    @JsonIgnoreProperties("series")
    private List<Episodes> episodes = new ArrayList<>();

    /**
     * Liste des évaluations associées à cette série.
     *
     * Relation OneToMany avec {@link Ratings}.
     *
     */
    @OneToMany(mappedBy = "series")
    @JsonIgnoreProperties({"series", "person"})
    private List<Ratings> ratings = new ArrayList<>();

    /**
     * Liste des historiques de visionnage liés à cette série.
     *
     * Relation OneToMany avec {@link VuesHistory}.
     *
     */
    @OneToMany(mappedBy = "series")
    @JsonIgnoreProperties({"series", "person"})
    private List<VuesHistory> viewsHistories = new ArrayList<>();

    /** @return liste des évaluations associées */
    public List<Ratings> getRatings() {
        return ratings;
    }

    /** @param ratings nouvelle liste d’évaluations à définir */
    public void setRatings(List<Ratings> ratings) {
        this.ratings = ratings;
    }
}
