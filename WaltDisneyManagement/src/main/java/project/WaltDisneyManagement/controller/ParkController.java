package project.WaltDisneyManagement.controller;




import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.WaltDisneyManagement.entity.Employee;
import project.WaltDisneyManagement.repository.EmployeeRepo;

@Controller
public class ParkController {

    @Autowired
    private EmployeeRepo employee;




    @GetMapping("/parks/{parkName}")
    public String park(Model model, @PathVariable("parkName") String parkName, HttpServletRequest request) {

        var email = request.getSession().getAttribute("employee_email");

        if (email == null) {
            return "redirect:/";
        }

        Employee employee = this.employee.findByEmail(email.toString());

        model.addAttribute("role", employee.getRole());
        model.addAttribute("username", employee.getName());

        return "park";
    }

    @GetMapping("/parks/{parkName}/attractions/{attractionName}")
    public String attraction(Model model, @PathVariable("parkName") String parkName, @PathVariable("attractionName") String attractionName, HttpServletRequest request) {

        var email = request.getSession().getAttribute("employee_email");


        if (email == null) {
            return "redirect:/";
        }

        Employee employee = this.employee.findByEmail(email.toString());

        model.addAttribute("role", employee.getRole());
        model.addAttribute("username", employee.getName());

        return "attraction";
    }

}
