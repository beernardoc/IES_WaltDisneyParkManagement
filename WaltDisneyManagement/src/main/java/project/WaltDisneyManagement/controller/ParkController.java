package project.WaltDisneyManagement.controller;




import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ParkController {




    @GetMapping("/parks/{parkName}")
    public String park(Model model, @PathVariable("parkName") String parkName) {

        return "park";
    }

    @GetMapping("/parks/{parkName}/attractions/{attractionName}")
    public String attraction(Model model, @PathVariable("parkName") String parkName, @PathVariable("attractionName") String attractionName) {
        return "attraction";
    }

}
