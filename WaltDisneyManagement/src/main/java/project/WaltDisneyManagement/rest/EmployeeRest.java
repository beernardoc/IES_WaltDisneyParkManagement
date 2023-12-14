package project.WaltDisneyManagement.rest;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import project.WaltDisneyManagement.entity.Employee;
import project.WaltDisneyManagement.service.EmployeeService;

import java.util.List;

@RestController
public class EmployeeRest {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/api/employee")
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeService.findAll();

        if (!employees.isEmpty()) {
            return ResponseEntity.ok(employees);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/api/employee/{id}")
    public ResponseEntity<Employee> getEmployeeData(@PathVariable("id") int id) {

        Employee employee = employeeService.findById(id);

        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
