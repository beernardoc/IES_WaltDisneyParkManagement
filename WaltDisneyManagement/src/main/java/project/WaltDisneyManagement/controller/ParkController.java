package project.WaltDisneyManagement.controller;




import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.WaltDisneyManagement.entity.*;
import project.WaltDisneyManagement.service.*;

import java.util.List;

@Controller
public class ParkController {


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AttractionService attractionService;

    @Autowired
    private ParkService parkService;

    @Autowired
    private ParkCarsService parkCarsService;

    @Autowired
    private MaintenanceService maintenanceService;








    @GetMapping("/park/{parkName}")
    public String park(Model model, @PathVariable("parkName") String parkName, HttpServletRequest request) {

        var email = request.getSession().getAttribute("employee_email");

        if (email == null) {
            return "redirect:/";
        }

        Employee employee = employeeService.findByEmail(email.toString());
        Park park = parkService.findByName(parkName);
        List<Park> parks = parkService.findAll();
        List<ParkCars> parkCars = parkCarsService.findAll();
        List<Attraction> attractions = attractionService.findByPark(park.getParkId());
        List<Maintenance> maintenance = maintenanceService.findByPark(parkName);

        model.addAttribute("parks", parks);
        model.addAttribute("parkCars", parkCars);
        model.addAttribute("park", park.getName());
        model.addAttribute("attractions", attractions);
        model.addAttribute("maintenanceHistory", maintenance);
        model.addAttribute("role", employee.getRole());
        model.addAttribute("username", employee.getName());

        return "park";
    }

    @GetMapping("/park/{parkName}/attraction/{attractionName}")
    public String attraction(Model model, @PathVariable("parkName") String parkName, @PathVariable("attractionName") String attractionName, HttpServletRequest request) {


        var email = request.getSession().getAttribute("employee_email");

        if (email == null) {
            return "redirect:/";
        }

        Attraction attraction = attractionService.findByName(attractionName);
        Employee employee = employeeService.findByEmail(email.toString());
        List<Maintenance> maintenance = maintenanceService.findByAttraction(attractionName);
        List<Park> parks = parkService.findAll();
        List<ParkCars> parkCars = parkCarsService.findAll();

        model.addAttribute("parks", parks);
        model.addAttribute("parkCars", parkCars);
        model.addAttribute("maintenanceHistory", maintenance);
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
