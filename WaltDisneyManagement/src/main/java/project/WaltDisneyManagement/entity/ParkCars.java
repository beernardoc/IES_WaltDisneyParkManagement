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

    @Column(name = "status", length = 45)
    private String status;


    public ParkCars() {
    }
    
    public ParkCars(String name, int maxcap, int atual, String status){
        this.name = name;
        this.maxcap = maxcap;
        this.atual = atual;
        this.status = status;

    }

    public int getCarsUpdate(int cars_in, int cars_out,int atual){
        atual = atual + cars_in - cars_out;
        return atual;
    }

    public boolean isFull(int atual, int maxcap){
        if(atual >= maxcap){
            return true;
        }
        else{
            return false;
        }
    }


    @Override
    public String toString(){
        return "ParkCars{" + 
                "parkCarsId=" + ParkCarsId + 
                ", name='" + name + '\'' + 
                ", maxcap=" + maxcap +
                ", atual=" + atual +
                ", status=" + status +
                "}";
    }

}
