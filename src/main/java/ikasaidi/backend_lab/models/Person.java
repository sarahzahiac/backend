package ikasaidi.backend_lab.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private int age;
    private String email;
    private String gender;
    private String password; // --> Ajout de l'attribut password

    public Person(int id, String name, String gender, String email, String password) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.password = password;

    }

    public Person() {
    }


    public Integer getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    // Plusieurs utilisateurs peuvent avoir une historique de plusieurs séries
    @ManyToMany
    @JsonIgnoreProperties({"viewsHistories", "ratings"})
    List<Series> history = new ArrayList<>();

    public List<Series> getHistory(){
        return history;
    }

    public void setHistory(List<Series> history){
        this.history = history;
    }

    @OneToMany(mappedBy = "person")
    @JsonIgnoreProperties({"person", "series"})
    private List<Ratings> ratings = new ArrayList<>();

    public List<Ratings> getRatings() {
        return ratings;
    }

    public void setRatings(List<Ratings> ratings) {
        this.ratings = ratings;
    }

    @OneToMany(mappedBy = "person")
    @JsonIgnoreProperties({"person", "series"})
    private List<VuesHistory> viewsHistories = new ArrayList<>();

}
