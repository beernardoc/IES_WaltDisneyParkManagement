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
public class LoginRestController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepo employeeRepo;


    @PostMapping("/api/login")
    public ResponseEntity<Employee> loginEmployee(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        boolean loginMessage = employeeService.loginEmployee(loginDto);
        if(loginMessage){

            var employee = employeeRepo.findByEmail(loginDto.getEmail());
            return ResponseEntity.ok(employee);
        }
        return ResponseEntity.badRequest().body(null);
    }


    @GetMapping("/api/login")
    public ResponseEntity<Employee> loginEmployee(HttpServletRequest request) {
        var role = request.getSession().getAttribute("employee_role");

        if (role!= null) {
            var email = request.getSession().getAttribute("employee_email");
            var employee = employeeRepo.findByEmail(email.toString());

            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.badRequest().body(null);



        }

    }



    @PostMapping("/register")
    public ResponseEntity<String> saveEmployee(@RequestBody EmployeeDto employeeDto) {
        String name = employeeService.addEmployee(employeeDto);
        return ResponseEntity.ok("Employee " + name + " saved");

    }






}
