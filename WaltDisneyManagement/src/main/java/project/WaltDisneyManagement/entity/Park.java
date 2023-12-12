package project.WaltDisneyManagement.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "park")
@Getter
@Setter
public class Park {

    @Id
    @Column(name = "park_id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int parkId;

    @Column(name = "park_name", length = 45)
    private String name;

    @Column(name = "park_visitors", length = 45)
    private int visitors;

    @OneToMany(mappedBy = "park", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Attraction> attractions;

    public Park() {
    }

    public Park(String name, List<Attraction> attractions) {
        this.name = name;
        this.attractions = attractions;
        this.visitors = 0;
    }

    public void addVisitor(int visitors) {
        this.visitors += visitors;
    }

    public void removeVisitor(int visitors) {
        this.visitors -= visitors;
    }

    @Override
    public String toString() {
        return "Park{" +
                "parkId=" + parkId +
                ", name='" + name + '\'' +
                ", attractions=" + attractions +
                '}';
    }
}

