package project.WaltDisneyManagement.controller;




import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.WaltDisneyManagement.entity.Attraction;
import project.WaltDisneyManagement.entity.Employee;
import project.WaltDisneyManagement.entity.MaintenanceHistory;
import project.WaltDisneyManagement.repository.AttractionRepo;
import project.WaltDisneyManagement.repository.EmployeeRepo;
import project.WaltDisneyManagement.repository.MaintenanceHistoryRepo;

import java.util.List;

@Controller
public class ParkController {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private AttractionRepo attractionRepo;

    @Autowired
    private MaintenanceHistoryRepo maintenanceHistoryrepo;




    @GetMapping("/parks/{parkName}")
    public String park(Model model, @PathVariable("parkName") String parkName, HttpServletRequest request) {

        var email = request.getSession().getAttribute("employee_email");

        if (email == null) {
            return "redirect:/";
        }

        Employee employee = employeeRepo.findByEmail(email.toString());

        List<MaintenanceHistory> maintenanceHistory = maintenanceHistoryrepo.findByPark(parkName);

        model.addAttribute("maintenanceHistory", maintenanceHistory);
        model.addAttribute("role", employee.getRole());
        model.addAttribute("username", employee.getName());

        return "park";
    }

    @GetMapping("/parks/{parkName}/attractions/{attractionName}")
    public String attraction(Model model, @PathVariable("parkName") String parkName, @PathVariable("attractionName") String attractionName, HttpServletRequest request) {


        var email = request.getSession().getAttribute("employee_email");

        if (email == null) {
            return "redirect:/";
        }

        Attraction attraction = attractionRepo.findByName(attractionName);
        Employee employee = employeeRepo.findByEmail(email.toString());
        List<MaintenanceHistory> maintenanceHistory = maintenanceHistoryrepo.findByAttraction(attractionName);

        model.addAttribute("maintenanceHistory", maintenanceHistory);

        model.addAttribute("NextMaintenance", attraction.getNextMaintenance());
        model.addAttribute("lastMaintenance", attraction.getLastMaintenance());
        model.addAttribute("type", attraction.getType());
        model.addAttribute("attraction", attraction.getName());
        model.addAttribute("role", employee.getRole());
        model.addAttribute("username", employee.getName());
        model.addAttribute("status", attraction.getStatus());

        return "attraction";
    }

}
