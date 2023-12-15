package project.WaltDisneyManagement.rest;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import project.WaltDisneyManagement.Dto.EmployeeDto;
import project.WaltDisneyManagement.Dto.LoginDto;
import project.WaltDisneyManagement.entity.Employee;
import project.WaltDisneyManagement.repository.EmployeeRepo;
import project.WaltDisneyManagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class LoginRest {

    @Autowired
    private EmployeeService employeeService;


  
    @PostMapping("/api/login")
    public ResponseEntity<Employee> loginEmployee(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        boolean loginMessage = employeeService.loginEmployee(loginDto);
        if(loginMessage){
            var employee = employeeService.findByEmail(loginDto.getEmail());
            return ResponseEntity.ok(employee);
        }
        return ResponseEntity.badRequest().body(null);
    }

   
    @GetMapping("/api/login")
    public ResponseEntity<Employee> loginEmployee(HttpServletRequest request) {
        var email = request.getSession().getAttribute("employee_email");

        if (email!= null) {

            var employee = employeeService.findByEmail(email.toString());
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.badRequest().body(null);

        }

    }




}