package project.WaltDisneyManagement.controller;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.WaltDisneyManagement.Dto.EmployeeDto;
import project.WaltDisneyManagement.Dto.LoginDto;
import project.WaltDisneyManagement.entity.Employee;
import project.WaltDisneyManagement.repository.EmployeeRepo;
import project.WaltDisneyManagement.service.EmployeeService;
import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@CrossOrigin
@RestController
public class LoginController {



    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepo employeeRepo;


    @PostMapping("/login")
    public ModelAndView loginEmployee(@RequestBody LoginDto loginDto, Model model, HttpServletResponse response) {
        boolean loginMessage = employeeService.loginEmployee(loginDto);
        if(loginMessage){
            var employee = employeeRepo.findByEmail(loginDto.getEmail());
            model.addAttribute("role", employee.getRole());
            model.addAttribute("username", employee.getName());
            return new ModelAndView("index", "employee", employee);
        }
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return new ModelAndView("login", "errorMessage", "Credenciais inválidas. Tente novamente.");
    }


    @GetMapping("/login")
    public ModelAndView loginEmployee(HttpServletRequest request, Model model, HttpServletResponse response) {
        var email = request.getSession().getAttribute("employee_email");

        if (email!= null) {

            var employee = employeeRepo.findByEmail(email.toString());
            model.addAttribute("role", employee.getRole());
            model.addAttribute("username", employee.getName());

            return new ModelAndView("index", "employee", employee);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ModelAndView("login", "errorMessage", "Credenciais inválidas. Tente novamente.");


        }

    }



    @PostMapping("/register")
    public ResponseEntity<String> saveEmployee(@RequestBody EmployeeDto employeeDto) {
        String name = employeeService.addEmployee(employeeDto);
        return ResponseEntity.ok("Employee " + name + " saved");

    }





}
