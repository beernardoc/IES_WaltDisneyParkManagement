package project.WaltDisneyManagement.service;

import org.springframework.stereotype.Service;
import project.WaltDisneyManagement.Dto.ParkCarsDto;
import project.WaltDisneyManagement.entity.ParkCars;

import java.util.List;

@Service
public interface ParkCarsService {
    
    ParkCars addParkCars(ParkCarsDto parkCarsDto);

    ParkCars findByName(String parkName);

    List<ParkCars> findAll();

    String deleteParkCars(String parkCarsName);
}
