package project.WaltDisneyManagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ParkCars")
@Getter
@Setter

public class ParkCars {

    @Id
    @Column(name = "ParkCarsId", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ParkCarsId;

    @Column(name = "ParkCarsName", length = 45)
    private String name;

    @Column(name = "Maxcapacity", length = 45)
    private int maxcap;

    @Column(name = "atual", length = 45)
    private int atual;


    public ParkCars() {
    }
    
    public ParkCars(String name, int maxcap, int atual){
        this.name = name;
        this.maxcap = maxcap;
        this.atual = atual;
    }


    @Override
    public String toString(){
        return "ParkCars{" + 
                "parkCarsId=" + ParkCarsId + 
                ", name='" + name + '\'' + 
                ", maxcap=" + maxcap +
                ", atual=" + atual +
                "}";
    }

}
