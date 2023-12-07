package project.WaltDisneyManagement.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.WaltDisneyManagement.entity.MaintenanceHistory;
import project.WaltDisneyManagement.repository.EmployeeRepo;
import project.WaltDisneyManagement.repository.MaintenanceHistoryRepo;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private MaintenanceHistoryRepo maintenanceHistoryRepo;


    @RequestMapping("/")
    public String login(HttpServletRequest request, Model model) {

        var email = request.getSession().getAttribute("employee_email");

        if (email != null) {

            var employee = employeeRepo.findByEmail(email.toString());
            var username = employee.getName();
            var role = employee.getRole();

            List<MaintenanceHistory> maintenanceHistory = maintenanceHistoryRepo.findAll();

            model.addAttribute("maintenanceHistory", maintenanceHistory);
            model.addAttribute("role", role);
            model.addAttribute("username", username);

            return "index";
        }
        return "login";
    }



}