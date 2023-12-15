package project.WaltDisneyManagement.rest;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import project.WaltDisneyManagement.Dto.AttractionDto;
import project.WaltDisneyManagement.entity.Attraction;
import project.WaltDisneyManagement.repository.AttractionRepo;
import project.WaltDisneyManagement.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@RestController
public class AttractionRest {

    @Autowired
    private AttractionService attractionService;


    
    @PostMapping("/api/attraction")
    public ResponseEntity<Attraction> addAttraction(@RequestBody AttractionDto attractionDto) {
        Attraction attraction = attractionService.addAttraction(attractionDto);
        return ResponseEntity.ok(attraction);
    }


    @GetMapping("/api/attraction")
    public ResponseEntity<List<Attraction>> getAttractions() {

        List<Attraction> attractions = attractionService.findAll();

        if(!attractions.isEmpty()){
            return ResponseEntity.ok(attractions);
        }else{
            return ResponseEntity.notFound().build();
        }


    }

    @PutMapping("/api/attraction")
    public ResponseEntity<Attraction> updateAttraction(@RequestBody AttractionDto attractionDto) {
        Attraction attraction = attractionService.updateAttraction(attractionDto);

        if (attraction != null) {
            return ResponseEntity.ok(attraction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
    @DeleteMapping("/api/attraction/{attractionName}")
    public ResponseEntity<String> deleteAttraction(@PathVariable("attractionName") String attractionName) {
        String name = attractionService.deleteAttraction(attractionName);
        if (name != null) {
            return ResponseEntity.ok("Attraction " + name + " deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/api/attraction/{attractionName}")
    public ResponseEntity<Attraction> getAttractionByName(@PathVariable("attractionName") String attractionName) {
        System.out.println(attractionName);
        Attraction attraction = attractionService.findByName(attractionName);

        if (attraction != null) {
            return ResponseEntity.ok(attraction);
        } else {
            return ResponseEntity.notFound().build();
        }


    }



}
