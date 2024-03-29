package project.WaltDisneyManagement.service.IMPL;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.WaltDisneyManagement.Dto.ParkCarsDto;
import project.WaltDisneyManagement.entity.ParkCars;
import project.WaltDisneyManagement.repository.ParkCarsRepo;
import project.WaltDisneyManagement.service.ParkCarsService;

import java.util.List;

@Service
public class ParkCarsIMPL implements ParkCarsService {

    @Autowired
    private ParkCarsRepo parkCarsRepo;

    @Override
    public ParkCars addParkCars(ParkCarsDto parkCarsDto) {

        ParkCars parkCars = new ParkCars(parkCarsDto.name(), parkCarsDto.maxcap(), parkCarsDto.atual(), parkCarsDto.status());

        parkCarsRepo.save(parkCars);

        return parkCars;
    }

    @Override
    public String deleteParkCars(String parkCarsName) {
        ParkCars parkCars = parkCarsRepo.findByName(parkCarsName);

        if (parkCars == null) {
            return null;
        }

        parkCarsRepo.delete(parkCars);

        return parkCars.getName();
    }
    

    @Override
    public ParkCars findByName(String parkName) {
        return parkCarsRepo.findByName(parkName);
    }

    @Override
    public List<ParkCars> findAll() {
        return parkCarsRepo.findAll();
    }


}
