package project.WaltDisneyManagement.controller;




import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.WaltDisneyManagement.entity.Employee;

@Controller
public class ParkController {




    @GetMapping("/parks/{parkName}")
    public String park(Model model, @PathVariable("parkName") String parkName, HttpServletRequest request) {

        if (request.getSession().getAttribute("employee_role") == null) {
            return "redirect:/";
        }


        return "park";
    }

    @GetMapping("/parks/{parkName}/attractions/{attractionName}")
    public String attraction(Model model, @PathVariable("parkName") String parkName, @PathVariable("attractionName") String attractionName, HttpServletRequest request) {

        if (request.getSession().getAttribute("employee_role") == null) {
            return "redirect:/";
        }


        return "attraction";
    }

}
