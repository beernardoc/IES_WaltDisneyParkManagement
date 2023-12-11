package project.WaltDisneyManagement.service;

import org.springframework.stereotype.Service;
import project.WaltDisneyManagement.Dto.ParkCarsDto;
import project.WaltDisneyManagement.entity.ParkCars;

@Service
public interface ParkCarsService {
    
    ParkCars addParkCars(ParkCarsDto parkCarsDto);

    ParkCars findByName(String parkName);
}
