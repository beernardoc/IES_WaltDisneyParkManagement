package project.WaltDisneyManagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.WaltDisneyManagement.entity.Attraction;
import project.WaltDisneyManagement.entity.Employee;
import project.WaltDisneyManagement.entity.Park;
import project.WaltDisneyManagement.entity.ParkCars;
import project.WaltDisneyManagement.service.AttractionService;
import project.WaltDisneyManagement.service.EmployeeService;
import project.WaltDisneyManagement.service.ParkCarsService;
import project.WaltDisneyManagement.service.ParkService;

import java.util.List;
import java.util.Objects;

@Controller
public class AdminPageController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ParkService parkService;

    @Autowired
    private ParkCarsService parkCarsService;

    @Autowired
    private AttractionService attractionService;

    @GetMapping("/adminpage")
    public String addUser(HttpServletRequest request, Model model) {

            var email = request.getSession().getAttribute("employee_email");
            var employee = employeeService.findByEmail(email.toString());
            if (Objects.equals(employee.getRole(), "ADMIN")) {

                List<Employee> employees = employeeService.findAll();
                model.addAttribute("employees", employees);

                List<Park> parks = parkService.findAll();
                model.addAttribute("parks", parks);

                List<ParkCars> parkCars = parkCarsService.findAll();
                model.addAttribute("parkCars", parkCars);

                List<Attraction> attractions = attractionService.findAll();
                model.addAttribute("attractions", attractions);

                model.addAttribute("role", employee.getRole());
                model.addAttribute("username", employee.getName());

                return "AdminPage";
            }
            return "login";

    }
}
