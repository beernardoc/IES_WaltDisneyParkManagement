package project.WaltDisneyManagement.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class ParkCarsController {

    @GetMapping("/Cars/{parkName}")
    public String park(Model model, @PathVariable("parkName") String parkName, HttpServletRequest request) {

        if (request.getSession().getAttribute("employee_role") == null) {
            return "redirect:/";
        }

        model.addAttribute("role", request.getSession().getAttribute("employee_role"));
        model.addAttribute("username", request.getSession().getAttribute("employee_username"));

        return "parking-lot";
    }

}


