package project.WaltDisneyManagement.service.IMPL;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.WaltDisneyManagement.Dto.ParkCarsDto;
import project.WaltDisneyManagement.entity.ParkCars;
import project.WaltDisneyManagement.repository.ParkCarsRepo;
import project.WaltDisneyManagement.service.ParkCarsService;

@Service
public class ParkCarsIMPL implements ParkCarsService {

    @Autowired
    private ParkCarsRepo parkCarsRepo;

    @Override
    public ParkCars addParkCars(ParkCarsDto parkCarsDto) {
        System.out.println("Received ParkCarsDto: " + parkCarsDto);

        ParkCars parkCars = new ParkCars(parkCarsDto.name(), parkCarsDto.maxcap(), parkCarsDto.atual());

        parkCarsRepo.save(parkCars);

        System.out.println("ParkCar saved sucessufly.");

        return parkCars;
    }

}
