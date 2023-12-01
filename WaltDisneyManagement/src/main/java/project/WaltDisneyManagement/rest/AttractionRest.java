package project.WaltDisneyManagement.rest;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.WaltDisneyManagement.Dto.AttractionDto;
import project.WaltDisneyManagement.entity.Attraction;
import project.WaltDisneyManagement.repository.AttractionRepo;
import project.WaltDisneyManagement.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
public class AttractionRest {

    @Autowired
    private AttractionRepo attractionRepo;

    @Autowired
    private AttractionService attractionService;



    @PostMapping("/api/attraction")
    public ResponseEntity<Attraction> addPark(@RequestBody AttractionDto attractionDto) {
        Attraction attraction = attractionService.addAttraction(attractionDto);
        return ResponseEntity.ok(attraction);
    }



    @GetMapping("/api/attractions/{attractionName}")
    public ResponseEntity<Attraction> getParkData(@PathVariable("attractionName") String attractionName) {
        System.out.println("Received attractionName: " + attractionName);

        Attraction attraction = attractionRepo.findByName(attractionName);

        if (attraction != null) {
            return ResponseEntity.ok(attraction);
        } else {
            return ResponseEntity.notFound().build();
        }


    }


}
