package project.WaltDisneyManagement.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {


    @RequestMapping("/")
    public String login(HttpServletRequest request, Model model) {

        var role = request.getSession().getAttribute("employee_role");

        if (role != null) {

            var username = request.getSession().getAttribute("employee_username");



            model.addAttribute("role", role);
            model.addAttribute("username", username);
            return "index";
        }
        return "login";
    }



}
