package project.WaltDisneyManagement.controller;


import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import project.WaltDisneyManagement.repository.EmployeeRepo;
import project.WaltDisneyManagement.repository.ParkCarsRepo;
import project.WaltDisneyManagement.entity.Attraction;
import project.WaltDisneyManagement.entity.Employee;
import project.WaltDisneyManagement.entity.ParkCars;
import project.WaltDisneyManagement.service.EmployeeService;



@Controller
public class ParkCarsController {

    @Autowired
    private ParkCarsRepo ParkCarsRepo;

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/ParkCars/{parkCarsName}")
    public String park(Model model, @PathVariable("parkCarsName") String parkCarsName, HttpServletRequest request) {


        var email = request.getSession().getAttribute("employee_email");
        if (email == null) {
            return "redirect:/";
        }

        Employee employee = employeeService.findByEmail(email.toString());
        model.addAttribute("role", employee.getRole());
        model.addAttribute("username", employee.getName());

        ParkCars parkCars = ParkCarsRepo.findByName(parkCarsName);
        model.addAttribute("status", parkCars.getStatus());
        model.addAttribute("capacity", parkCars.getMaxcap());
        model.addAttribute("atual", parkCars.getAtual());
        model.addAttribute("parkCarName", parkCars.getName());

        

        return "parking-lot";
    }

}


