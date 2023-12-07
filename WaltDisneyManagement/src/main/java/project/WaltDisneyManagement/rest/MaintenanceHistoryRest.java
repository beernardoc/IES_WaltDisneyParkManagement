package project.WaltDisneyManagement.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.WaltDisneyManagement.entity.Attraction;
import project.WaltDisneyManagement.entity.MaintenanceHistory;
import project.WaltDisneyManagement.repository.AttractionRepo;
import project.WaltDisneyManagement.repository.MaintenanceHistoryRepo;

import java.time.LocalDate;

@RestController
public class MaintenanceHistoryRest {

    @Autowired
    private AttractionRepo attractionRepo;

    @Autowired
    private MaintenanceHistoryRepo maintenanceHistoryRepo;

    @PostMapping("/api/parks/{parkName}/attractions/{attractionName}/SetMaintenance")
    public ResponseEntity<LocalDate> setMaintenance(Model model, @PathVariable("parkName") String parkName,
                                                    @PathVariable("attractionName") String attractionName,
                                                    @RequestParam("description") String description,
                                                    HttpServletRequest request) {

        var email = request.getSession().getAttribute("employee_email");

        if (email == null) {
            return ResponseEntity.badRequest().build();
        }

        Attraction attraction = attractionRepo.findByName(attractionName);

        if(attraction == null) {
            return ResponseEntity.notFound().build();
        }

        attraction.setLastMaintenance(LocalDate.now());
        attractionRepo.save(attraction);

        MaintenanceHistory maintenanceHistory = new MaintenanceHistory(attraction.getPark().getName()
                ,attraction.getName(),description, LocalDate.now());
        maintenanceHistoryRepo.save(maintenanceHistory);
        System.out.println("maintenanceHistory inserted");

        return ResponseEntity.ok(attraction.getLastMaintenance());



    }







}