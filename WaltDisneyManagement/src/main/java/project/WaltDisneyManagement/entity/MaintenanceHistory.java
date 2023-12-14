package project.WaltDisneyManagement.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "MaintenanceHistory")
@Getter
@Setter
public class MaintenanceHistory {

    @Id
    @Column(name = "MaintenanceHistory_id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int MaintenanceHistoryId;

    @Column(name = "MaintenanceHistory_park", length = 45)
    private String park;

    @Column(name = "MaintenanceHistory_attraction", length = 45)
    private String attraction;

    @Column(name = "MaintenanceHistory_description", length = 45)
    private String description;

    @Column(name = "MaintenanceHistory_technician", length = 45)
    private String technician;

    @Column(name = "MaintenanceHistory_date", length = 45)
    private LocalDate date;



    public MaintenanceHistory() {
    }

    public MaintenanceHistory(String park, String attraction, String description,String technician ,LocalDate date) {
        this.park = park;
        this.attraction = attraction;
        this.description = description;
        this.technician = technician;
        this.date = date;
    }



}
