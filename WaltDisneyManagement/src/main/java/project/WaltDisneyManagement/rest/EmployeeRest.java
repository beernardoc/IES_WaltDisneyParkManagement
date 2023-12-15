package project.WaltDisneyManagement.rest;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.WaltDisneyManagement.Dto.EmployeeDto;
import project.WaltDisneyManagement.entity.Employee;
import project.WaltDisneyManagement.service.EmployeeService;

import java.util.List;

@RestController
public class EmployeeRest {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/api/employee")
    public ResponseEntity<String> saveEmployee(@RequestBody EmployeeDto employeeDto) {
        String name = employeeService.addEmployee(employeeDto);
        return ResponseEntity.ok("Employee " + name + " saved");

    }

    @GetMapping("/api/employee")
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeService.findAll();

        if (!employees.isEmpty()) {
            return ResponseEntity.ok(employees);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/api/employee")
    public ResponseEntity<String> updateEmployee(@RequestBody EmployeeDto employeeDto) {
        String name = employeeService.updateEmployee(employeeDto);

        if (name == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Employee " + name + " updated");
    }

    @DeleteMapping("/api/employee/{email}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("email") String email) {
        String name = employeeService.deleteEmployee(email);
        if (name != null) {
            return ResponseEntity.ok("Employee " + name + " deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/employee/{email}")
    public ResponseEntity<Employee> getEmployeeData(@PathVariable("email") String email) {

        Employee employee = employeeService.findByEmail(email);

        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
