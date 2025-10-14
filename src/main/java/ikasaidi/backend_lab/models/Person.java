package ikasaidi.backend_lab.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entité JPA représentant une personne (utilisateur) du système.
 *
 * Cette classe contient les informations de base d’un utilisateur
 * (nom, âge, email, mot de passe, genre) ainsi que ses relations avec :
 * <ul>
 *     <li>Les séries regardées (historique de visionnage).</li>
 *     <li>Les évaluations (ratings) laissées sur les séries ou épisodes.</li>
 *     <li>Les historiques de vues détaillés ({@link VuesHistory}).</li>
 * </ul>
 *
 *
 * <p>Elle est liée à plusieurs entités via des relations JPA :
 * <ul>
 *     <li>{@code @ManyToMany} avec {@link Series} (historique de visionnage)</li>
 *     <li>{@code @OneToMany} avec {@link Ratings}</li>
 *     <li>{@code @OneToMany} avec {@link VuesHistory}</li>
 * </ul>
 *
 *
 * @author Rachel
 * @author Ikram
 * @author Aya
 * @author Sarah
 * @version 1.0
 */
@Entity
public class Person {

    /** Identifiant unique de la personne (clé primaire). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Nom complet de la personne. */
    private String name;

    /** Âge de la personne. */
    private int age;

    /** Adresse courriel unique de l’utilisateur. */
    private String email;

    /** Genre de la personne (ex. "F", "M", "Autre"). */
    private String gender;

    /** Mot de passe de l’utilisateur (chiffré côté serveur). */
    private String password;

    /**
     * Constructeur complet.
     *
     * @param id identifiant unique
     * @param name nom de la personne
     * @param gender genre de la personne
     * @param email adresse courriel
     * @param password mot de passe
     */
    public Person(int id, String name, String gender, String email, String password) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    /** Constructeur par défaut requis par JPA. */
    public Person() {}

    /** @return identifiant unique de la personne */
    public Integer getId() {
        return id;
    }

    /** @param id identifiant unique à définir */
    public void setId(int id) {
        this.id = id;
    }

    /** @return nom complet de la personne */
    public String getName() {
        return name;
    }

    /** @param name nouveau nom à définir */
    public void setName(String name) {
        this.name = name;
    }

    /** @return âge de la personne */
    public int getAge() {
        return age;
    }

    /** @param age nouvel âge à définir */
    public void setAge(int age) {
        this.age = age;
    }

    /** @return genre de la personne */
    public String getGender() {
        return gender;
    }

    /** @param gender nouveau genre à définir */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /** @return adresse courriel de l’utilisateur */
    public String getEmail() {
        return email;
    }

    /** @param email nouvelle adresse courriel */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return mot de passe de l’utilisateur */
    public String getPassword() {
        return password;
    }

    /** @param password nouveau mot de passe */
    public void setPassword(String password) {
        this.password = password;
    }

    // 🔹 Relations avec d'autres entités

    /**
     * Liste des séries visionnées par l’utilisateur (historique global).
     *
     * Relation ManyToMany avec {@link Series}.
     *
     */
    @ManyToMany
    @JsonIgnoreProperties({"viewsHistories", "ratings"})
    private List<Series> history = new ArrayList<>();

    /** @return liste des séries regardées par l’utilisateur */
    public List<Series> getHistory() {
        return history;
    }

    /** @param history nouvelle liste d’historique à définir */
    public void setHistory(List<Series> history) {
        this.history = history;
    }

    /**
     * Liste des évaluations (ratings) laissées par la personne.
     *
     * Relation OneToMany avec {@link Ratings}.
     *
     */
    @OneToMany(mappedBy = "person")
    @JsonIgnoreProperties({"person", "series"})
    private List<Ratings> ratings = new ArrayList<>();

    /** @return liste des évaluations de l’utilisateur */
    public List<Ratings> getRatings() {
        return ratings;
    }

    /** @param ratings nouvelle liste d’évaluations */
    public void setRatings(List<Ratings> ratings) {
        this.ratings = ratings;
    }

    /**
     * Liste des historiques de vues détaillés (chaque visionnement est un enregistrement).
     *
     * Relation OneToMany avec {@link VuesHistory}.
     *
     */
    @OneToMany(mappedBy = "person")
    @JsonIgnoreProperties({"person", "series"})
    private List<VuesHistory> viewsHistories = new ArrayList<>();

    /** @return liste complète des vues de la personne */
    public List<VuesHistory> getViewsHistories() {
        return viewsHistories;
    }

    /** @param viewsHistories nouvelle liste des vues à définir */
    public void setViewsHistories(List<VuesHistory> viewsHistories) {
        this.viewsHistories = viewsHistories;
    }
}
