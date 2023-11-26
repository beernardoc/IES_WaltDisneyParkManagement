package project.WaltDisneyManagement.controller;

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

@RestController
@CrossOrigin
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    
    @GetMapping("/")
    public String login() {
        return "login";
    }

    @PostMapping("/save")
    public String saveEmployee(@RequestBody EmployeeDto employeeDto) {
        String id = employeeService.addEmployee(employeeDto);
        return id;
    }

    @PostMapping("/login")
    public String loginEmployee(@RequestBody LoginDto loginDto) {
        boolean loginMessage = employeeService.loginEmployee(loginDto);
        if(loginMessage == true){
            return "index";
        }
        else{
            return "login";
        }
    }


}
