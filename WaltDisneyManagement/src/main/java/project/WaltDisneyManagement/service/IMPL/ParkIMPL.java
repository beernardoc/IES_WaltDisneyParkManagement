package project.WaltDisneyManagement.service.IMPL;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.WaltDisneyManagement.Dto.ParkDto;
import project.WaltDisneyManagement.entity.Park;
import project.WaltDisneyManagement.repository.EmployeeRepo;
import project.WaltDisneyManagement.repository.ParkRepo;
import project.WaltDisneyManagement.service.ParkService;

@Service
public class ParkIMPL implements ParkService {



    @Autowired
    private ParkRepo parkRepo;


    @Override
    public Park addPark(ParkDto parkDto) {
        System.out.println("Received parkDto: " + parkDto);

        Park park = new Park(parkDto.name(),parkDto.attractions());

        parkRepo.save(park);

        System.out.println("park saved successfully.");

        return park;



    }
}
