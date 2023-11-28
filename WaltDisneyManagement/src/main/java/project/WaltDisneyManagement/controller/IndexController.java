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
    public String login(HttpServletRequest request) {

        if (request.getSession().getAttribute("employee_role") != null) {
            return "redirect:/index";
        }
        return "pages-login";
    }




    @GetMapping("/index")
    public String index(HttpServletRequest request, Model model) {

        if (request.getSession().getAttribute("employee_role") == null) {
            return "redirect:/";
        }

        var role = request.getSession().getAttribute("employee_role");

        model.addAttribute("role", role);

        return "index";
    }


}
