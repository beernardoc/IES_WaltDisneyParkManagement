package project.WaltDisneyManagement.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import project.WaltDisneyManagement.entity.Maintenance;
import project.WaltDisneyManagement.entity.Park;
import project.WaltDisneyManagement.entity.ParkCars;
import project.WaltDisneyManagement.service.EmployeeService;
import project.WaltDisneyManagement.service.MaintenanceService;
import project.WaltDisneyManagement.service.ParkCarsService;
import project.WaltDisneyManagement.service.ParkService;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MaintenanceService maintenanceService;

    @Autowired
    private ParkService parkService;

    @Autowired
    private ParkCarsService parkCarsService;


    @RequestMapping("/")
    public String login(HttpServletRequest request, Model model) {

        var email = request.getSession().getAttribute("employee_email");

        if (email != null) {

            var employee = employeeService.findByEmail(email.toString());
            var username = employee.getName();
            var role = employee.getRole();

            List<Maintenance> maintenance = maintenanceService.findAll();
            List<Park> parks = parkService.findAll();
            List<ParkCars> parkCars = parkCarsService.findAll();
            model.addAttribute("parks", parks);
            model.addAttribute("parkCars", parkCars);
            model.addAttribute("maintenanceHistory", maintenance);
            model.addAttribute("role", role);
            model.addAttribute("username", username);

            return "index";
        }
        return "login";
    }



}