package project.WaltDisneyManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import project.WaltDisneyManagement.entity.ParkCars;

@EnableJpaRepositories
@Repository
public interface ParkCarsRepo extends JpaRepository <ParkCars, Integer> {
    
    ParkCars findByName(String ParkCars_name);
    
}
