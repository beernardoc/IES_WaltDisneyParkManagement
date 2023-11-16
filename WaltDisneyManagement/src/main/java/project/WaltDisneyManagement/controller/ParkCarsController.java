package project.WaltDisneyManagement.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class ParkCarsController {

    @GetMapping("/Cars/{parkName}")
    public String park(Model model, @PathVariable("parkName") String parkName) {

        return "parking-lot";
    }

}


