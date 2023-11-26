package project.WaltDisneyManagement.controller;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {



    @GetMapping("/index")
    public String index() {

        return "index";
    }


}
