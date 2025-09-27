package ikasaidi.backend_lab.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @jakarta.persistence.Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private int age;
    private String email;
    private String gender;

    public User(int id, String name, String gender, String email) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
    }

    public User() {
    }

    @OneToMany(mappedBy = "user")
    private List<History> vuesUsers;

    public List<History> getVuesUsers() { return vuesUsers; }
    public void setVuesUsers(List<History> vuesUsers) { this.vuesUsers = vuesUsers; }

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Evaluation> evaluations = new ArrayList<>();

    public List<Evaluation> getEvaluations() { return evaluations; }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
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

    // Plusieurs utilisateurs peuvent avoir une historique de plusieurs séries
    @ManyToMany
    List<Series> history = new ArrayList<>();

    public List<Series> getHistory(){
        return history;
    }

    public void setHistory(List<Series> history){
        this.history = history;
    }
}
