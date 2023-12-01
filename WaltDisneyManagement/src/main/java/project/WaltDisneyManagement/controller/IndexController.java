package project.WaltDisneyManagement.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {


    @GetMapping("/")
    public ModelAndView login(HttpServletRequest request, Model model, HttpServletResponse response) {

        var role = request.getSession().getAttribute("employee_role");
        ModelAndView modelAndView = new ModelAndView();

        if (role != null) {

            var username = request.getSession().getAttribute("employee_username");



            model.addAttribute("role", role);
            model.addAttribute("username", username);
            modelAndView.setViewName("index");
            response.setStatus(HttpServletResponse.SC_OK);
            return modelAndView;
        }
        modelAndView.setViewName("login");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        return modelAndView;
    }



}
