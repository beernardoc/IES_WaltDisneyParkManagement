package project.WaltDisneyManagement.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import project.WaltDisneyManagement.entity.Attraction;
import project.WaltDisneyManagement.entity.Employee;
import project.WaltDisneyManagement.entity.MaintenanceHistory;
import project.WaltDisneyManagement.repository.AttractionRepo;
import project.WaltDisneyManagement.repository.MaintenanceHistoryRepo;
import project.WaltDisneyManagement.service.AttractionService;
import project.WaltDisneyManagement.service.EmployeeService;
import project.WaltDisneyManagement.service.MaintenanceHistoryService;

import java.time.LocalDate;
import java.util.List;

@RestController
public class MaintenanceHistoryRest {

    @Autowired
    private AttractionRepo attractionRepo;

    @Autowired
    private AttractionService attractionService;

    @Autowired
    private MaintenanceHistoryRepo maintenanceHistoryRepo;

    @Autowired
    private MaintenanceHistoryService maintenanceHistoryService;

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

        MaintenanceHistory maintenanceHistory = new MaintenanceHistory(attraction.getPark().getName()
                ,attraction.getName(),description,employee.getName() , LocalDate.now());
        maintenanceHistoryRepo.save(maintenanceHistory);
        System.out.println("maintenanceHistory inserted");

        return ResponseEntity.ok(attraction.getLastMaintenance());

    }

  
    @GetMapping("/api/Maintenance")
    public ResponseEntity<List<MaintenanceHistory>> getMaintenanceHistory() {

        List<MaintenanceHistory> maintenanceHistory = maintenanceHistoryService.findAll();

        if (!maintenanceHistory.isEmpty()) {
            return ResponseEntity.ok(maintenanceHistory);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    
    @GetMapping("/api/Maintenance/{id}")
    public ResponseEntity<MaintenanceHistory> getMaintenanceHistoryById(@PathVariable("id") int id) {

        MaintenanceHistory maintenanceHistory = maintenanceHistoryService.findById(id);

        if (maintenanceHistory != null) {
            return ResponseEntity.ok(maintenanceHistory);
        } else {
            return ResponseEntity.notFound().build();
        }

    }






}