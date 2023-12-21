package project.WaltDisneyManagement.controller;


import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import project.WaltDisneyManagement.entity.Park;
import project.WaltDisneyManagement.repository.EmployeeRepo;
import project.WaltDisneyManagement.repository.ParkCarsRepo;
import project.WaltDisneyManagement.entity.Attraction;
import project.WaltDisneyManagement.entity.Employee;
import project.WaltDisneyManagement.entity.ParkCars;
import project.WaltDisneyManagement.service.EmployeeService;
import project.WaltDisneyManagement.service.ParkCarsService;
import project.WaltDisneyManagement.service.ParkService;

import java.util.List;


@Controller
public class ParkCarsController {


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ParkService parkService;


    @Autowired
    private ParkCarsService parkCarsService;

    @GetMapping("/ParkCars/{parkCarsName}")
    public String park(Model model, @PathVariable("parkCarsName") String parkCarsName, HttpServletRequest request) {


        var email = request.getSession().getAttribute("employee_email");
        if (email == null) {
            return "redirect:/";
        }

        Employee employee = employeeService.findByEmail(email.toString());
        model.addAttribute("role", employee.getRole());
        model.addAttribute("username", employee.getName());

        ParkCars parkCar = parkCarsService.findByName(parkCarsName);
        model.addAttribute("status", parkCar.getStatus());
        model.addAttribute("capacity", parkCar.getMaxcap());
        model.addAttribute("atual", parkCar.getAtual());
        model.addAttribute("parkCarName", parkCar.getName());

        List<Park> parks = parkService.findAll();
        List<ParkCars> parkCars = parkCarsService.findAll();

        model.addAttribute("parks", parks);
        model.addAttribute("parkCars", parkCars);

        

        return "parking-lot";
    }

}


