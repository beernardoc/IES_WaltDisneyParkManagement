package project.WaltDisneyManagement.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.WaltDisneyManagement.Dto.ParkCarsDto;
import project.WaltDisneyManagement.entity.Park;
import project.WaltDisneyManagement.entity.ParkCars;
import project.WaltDisneyManagement.repository.ParkCarsRepo;
import project.WaltDisneyManagement.service.ParkCarsService;
import org.springframework.beans.factory.annotation.Autowired;
import project.WaltDisneyManagement.service.ParkCarsService;

@RestController
public class ParkCarsRest {
    
    @Autowired
    private ParkCarsService ParkCarsService;

    @Autowired
    private ParkCarsRepo parkCarsRepo;


    @PostMapping("/api/parkCars")
    public ResponseEntity<ParkCars> addParkCars(@RequestBody ParkCarsDto parkCarsDto){
        ParkCars parkCars = ParkCarsService.addParkCars(parkCarsDto);
        return ResponseEntity.ok(parkCars);
    }

    @GetMapping("/api/parkCars/{parkCarsName}")
    public ResponseEntity<ParkCars> getParkCarsData(@PathVariable("parkCarsName") String parkCarsName) {

        ParkCars parkCars = parkCarsRepo.findByName(parkCarsName);

        if (parkCars != null) {
            return ResponseEntity.ok(parkCars);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
