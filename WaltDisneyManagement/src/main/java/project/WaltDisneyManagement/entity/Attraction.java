package project.WaltDisneyManagement.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "attraction")
@Getter
@Setter
public class Attraction {

    @Id
    @Column(name = "attraction_id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int attractionId;

    @Column(name = "attraction_name", length = 45)
    private String name;

    @Column(name = "attraction_status", length = 45)
    private String status;

    @ManyToOne
    @JoinColumn(name = "park_id", nullable = false)
    @JsonBackReference
    private Park park;

    public Attraction() {
    }

    public Attraction(String name, Park park) {
        this.name = name;
        this.park = park;
        this.status = "Open";
    }
}

