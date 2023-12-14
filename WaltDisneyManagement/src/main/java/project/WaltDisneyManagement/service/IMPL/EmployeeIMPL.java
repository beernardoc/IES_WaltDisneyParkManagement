package project.WaltDisneyManagement.service.IMPL;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import project.WaltDisneyManagement.Dto.EmployeeDto;
import project.WaltDisneyManagement.Dto.LoginDto;
import project.WaltDisneyManagement.entity.Employee;
import project.WaltDisneyManagement.repository.EmployeeRepo;
import project.WaltDisneyManagement.service.EmployeeService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeIMPL implements EmployeeService {
        
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;

    @Override
    public String addEmployee(EmployeeDto employeeDto) {

        System.out.println("Received employeeDto: " + employeeDto);

        Employee employee = new Employee(
            employeeDto.getName(),
            employeeDto.getEmail(),
            this.passwordEncoder.encode(employeeDto.getPassword()),
            employeeDto.getRole()
            

            );



            employeeRepo.save(employee);


            request.getSession().setAttribute("employee_email", employee.getEmail());

            System.out.println("Employee saved successfully.");

            return employee.getName();


    }

    @Override
    public boolean loginEmployee(LoginDto loginDto){
        Employee employee = employeeRepo.findByEmail(loginDto.getEmail());

        if(employee == null){
            return false;
        }

        if(!this.passwordEncoder.matches(loginDto.getPassword(), employee.getPassword())){
            return false;
        }

        request.getSession().setAttribute("employee_email", employee.getEmail());

        System.out.println("Login successful");

        return true;
        
    }

    @Override
    public Employee findByName(String name) {
        return employeeRepo.findByName(name);
    }

    @Override
    public Employee findByEmail(String email) {
        return employeeRepo.findByEmail(email);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Override
    public String updateEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeRepo.findByEmail(employeeDto.getEmail());

        if(employee == null){
            return null;
        }

        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPassword(this.passwordEncoder.encode(employeeDto.getPassword()));
        employee.setRole(employeeDto.getRole());

        employeeRepo.save(employee);

        return employee.getName();
    }

    @Override
    public String deleteEmployee(String email) {
        Employee employee = employeeRepo.findByEmail(email);

        if(employee == null){
            return null;
        }

        employeeRepo.delete(employee);

        return employee.getName();
    }

    @Override
    public Employee findById(int id) {
        return employeeRepo.findById(id);
    }
}
