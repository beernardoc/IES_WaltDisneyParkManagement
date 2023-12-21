package project.WaltDisneyManagement.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import project.WaltDisneyManagement.entity.Attraction;
import project.WaltDisneyManagement.entity.Employee;
import project.WaltDisneyManagement.entity.Maintenance;
import project.WaltDisneyManagement.repository.AttractionRepo;
import project.WaltDisneyManagement.repository.MaintenanceRepo;
import project.WaltDisneyManagement.service.AttractionService;
import project.WaltDisneyManagement.service.EmployeeService;
import project.WaltDisneyManagement.service.MaintenanceService;

import java.time.LocalDate;
import java.util.List;

@RestController
public class MaintenanceRest {

    @Autowired
    private AttractionRepo attractionRepo;

    @Autowired
    private AttractionService attractionService;

    @Autowired
    private MaintenanceRepo maintenanceRepo;

    @Autowired
    private MaintenanceService maintenanceService;

    @Autowired
    private EmployeeService employeeService;


    
    @PostMapping("/api/park/{parkName}/attraction/{attractionName}/SetMaintenance")
    public ResponseEntity<LocalDate> setMaintenance(Model model, @PathVariable("parkName") String parkName,
                                                    @PathVariable("attractionName") String attractionName,
                                                    @RequestParam("description") String description,
                                                    HttpServletRequest request) {

        var email = request.getSession().getAttribute("employee_email");

        if (email == null) {
            return ResponseEntity.badRequest().build();
        }

        Attraction attraction = attractionService.findByName(attractionName);
        Employee employee = employeeService.findByEmail(email.toString());

        if(attraction == null) {
            return ResponseEntity.notFound().build();
        }

        attraction.setLastMaintenance(LocalDate.now());
        attractionRepo.save(attraction);

        Maintenance maintenance = new Maintenance(attraction.getPark().getName()
                ,attraction.getName(),description,employee.getName() , LocalDate.now());
        maintenanceRepo.save(maintenance);

        return ResponseEntity.ok(attraction.getLastMaintenance());

    }

  
    @GetMapping("/api/Maintenance")
    public ResponseEntity<List<Maintenance>> getMaintenanceHistory() {

        List<Maintenance> maintenance = maintenanceService.findAll();

        if (!maintenance.isEmpty()) {
            return ResponseEntity.ok(maintenance);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    
    @GetMapping("/api/Maintenance/{id}")
    public ResponseEntity<Maintenance> getMaintenanceHistoryById(@PathVariable("id") int id) {

        Maintenance maintenance = maintenanceService.findById(id);

        if (maintenance != null) {
            return ResponseEntity.ok(maintenance);
        } else {
            return ResponseEntity.notFound().build();
        }

    }






}