package ikasaidi.backend_lab.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
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

    public Person(int id, String name, String gender, String email) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
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
