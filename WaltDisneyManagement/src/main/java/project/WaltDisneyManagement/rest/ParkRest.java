package project.WaltDisneyManagement.rest;



import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import project.WaltDisneyManagement.Dto.ParkDto;
import project.WaltDisneyManagement.entity.Park;
import org.springframework.beans.factory.annotation.Autowired;
import project.WaltDisneyManagement.service.ParkService;

import java.util.List;


@RestController
public class ParkRest {


    @Autowired
    private ParkService parkService;


    
    @PostMapping("/api/park")
    public ResponseEntity<Park> addPark(@RequestBody ParkDto parkDto) {
        Park park = parkService.addPark(parkDto);
        return ResponseEntity.ok(park);
    }

    
    @GetMapping("/api/park")
    public ResponseEntity<List<Park>> getParks(){
        List<Park> parks = parkService.findAll();

        if(!parks.isEmpty()){
            return ResponseEntity.ok(parks);
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/api/park")
    public ResponseEntity<String> deletePark(@RequestBody String parkName) {
        String name = parkService.deletePark(parkName);

        if (name != null) {
            return ResponseEntity.ok("Park " + name + " deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



   
    @GetMapping("/api/park/{parkName}")
    public ResponseEntity<Park> getParkData(@PathVariable("parkName") String parkName) {

        Park park = parkService.findByName(parkName);

        if (park != null) {
            return ResponseEntity.ok(park);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}