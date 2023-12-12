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
import project.WaltDisneyManagement.service.AttractionService;
import project.WaltDisneyManagement.service.EmployeeService;
import project.WaltDisneyManagement.service.MaintenanceHistoryService;

import java.util.List;

@Controller
public class ParkController {


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AttractionService attractionService;

    @Autowired
    private MaintenanceHistoryService maintenanceHistoryService;





    @GetMapping("/parks/{parkName}")
    public String park(Model model, @PathVariable("parkName") String parkName, HttpServletRequest request) {

        var email = request.getSession().getAttribute("employee_email");

        if (email == null) {
            return "redirect:/";
        }

        Employee employee = employeeService.findByEmail(email.toString());

        List<MaintenanceHistory> maintenanceHistory = maintenanceHistoryService.findByPark(parkName);

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

        Attraction attraction = attractionService.findByName(attractionName);
        Employee employee = employeeService.findByEmail(email.toString());
        List<MaintenanceHistory> maintenanceHistory = maintenanceHistoryService.findByAttraction(attractionName);

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
