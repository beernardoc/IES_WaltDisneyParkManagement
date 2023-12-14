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

import java.util.List;

@RestController
public class ParkCarsRest {
    
    @Autowired
    private ParkCarsService parkCarsService;


    @PostMapping("/api/parkCars")
    public ResponseEntity<ParkCars> addParkCars(@RequestBody ParkCarsDto parkCarsDto){
        ParkCars parkCars = parkCarsService.addParkCars(parkCarsDto);
        return ResponseEntity.ok(parkCars);
    }

    @GetMapping("/api/parkCars")
    public ResponseEntity<List<ParkCars>> getParkCarsData() {
        List<ParkCars> parkCars = parkCarsService.findAll();

        if (!parkCars.isEmpty()) {
            return ResponseEntity.ok(parkCars);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/parkCars/{parkCarsName}")
    public ResponseEntity<ParkCars> getParkCarsData(@PathVariable("parkCarsName") String parkCarsName) {

        ParkCars parkCars = parkCarsService.findByName(parkCarsName);

        if (parkCars != null) {
            return ResponseEntity.ok(parkCars);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
