package project.WaltDisneyManagement.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Maintenance")
@Getter
@Setter
public class Maintenance {

    @Id
    @Column(name = "Maintenance_id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int MaintenanceHistoryId;

    @Column(name = "Maintenance_park", length = 45)
    private String park;

    @Column(name = "Maintenance_attraction", length = 45)
    private String attraction;

    @Column(name = "Maintenance_description", length = 45)
    private String description;

    @Column(name = "Maintenance_technician", length = 45)
    private String technician;

    @Column(name = "Maintenance_date", length = 45)
    private LocalDate date;



    public Maintenance() {
    }

    public Maintenance(String park, String attraction, String description, String technician , LocalDate date) {
        this.park = park;
        this.attraction = attraction;
        this.description = description;
        this.technician = technician;
        this.date = date;
    }



}
