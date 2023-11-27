package project.WaltDisneyManagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.client.RpcClient.Response;

import project.WaltDisneyManagement.Dto.EmployeeDto;
import project.WaltDisneyManagement.Dto.LoginDto;
import project.WaltDisneyManagement.service.EmployeeService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public String saveEmployee(@RequestBody EmployeeDto employeeDto) {
        String id = employeeService.addEmployee(employeeDto);
        return "index";
    }

    @PostMapping("/login")
    public String loginEmployee(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        boolean loginMessage = employeeService.loginEmployee(loginDto);
        if(loginMessage == true){
            System.out.println(request.getSession().getAttribute("employee_role"));
            System.out.println("Login successful");
            return "redirect:/index";
        }
        else{
            System.out.println("Login failed");
            return "redirect:/";
        }
    }


}
