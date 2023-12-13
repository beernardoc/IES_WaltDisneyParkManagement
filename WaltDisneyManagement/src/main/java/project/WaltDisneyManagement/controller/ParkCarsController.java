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



@Controller
public class ParkCarsController {

    @Autowired
    private ParkCarsRepo ParkCarsRepo;



    @GetMapping("/ParkCars/{parkCarsName}")
    public String park(Model model, @PathVariable("parkCarsName") String parkCarsName, HttpServletRequest request) {


        var email = request.getSession().getAttribute("employee_email");
        if (email == null) {
            return "redirect:/";
        }

        model.addAttribute("role", request.getSession().getAttribute("employee_role"));
        model.addAttribute("username", request.getSession().getAttribute("employee_username"));

        return "parking-lot";
    }

}


