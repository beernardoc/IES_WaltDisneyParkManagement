package project.WaltDisneyManagement.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


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

    @Column(name = "attraction_type", length = 45)
    private String type;

    @Column(name = "attraction_lastMaintenance", length = 45)
    private LocalDate lastMaintenance;


    @ManyToOne
    @JoinColumn(name = "park_id", nullable = false)
    @JsonBackReference
    private Park park;

    public Attraction() {
    }

    public Attraction(String name, Park park, String type) {
        this.name = name;
        this.park = park;
        this.type = type;
        this.status = "Open";
        this.lastMaintenance = LocalDate.of(2023, 11, 14);
    }


    public boolean testValuesRollerCoaster(Double velocityKmh, Double height, Double temperature, Double vibration){
        if (velocityKmh > 20){
            return false;
        }
        if(height > 100){
            return false;
        }
        if (temperature > 100){
            return false;
        }
        if (vibration > 100){
            return false;
        }
            return true;


    }

    public boolean testValuesDarkRide(Double velocityKmh, Double temperature, Double vibration){
        if (velocityKmh > 100){
            return false;
        }
        if (temperature > 100){
            return false;
        }
        if (vibration > 100){
            return false;
        }
        return true;

    }



    @Override
    public String toString() {
        return "Attraction{" +
                "attractionId=" + attractionId +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", park=" + park +
                '}';
    }
}

