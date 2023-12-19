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
            employeeDto.Name(),
            employeeDto.Email(),
            this.passwordEncoder.encode(employeeDto.Password()),
            employeeDto.Role()
            

            );



            employeeRepo.save(employee);


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
        Employee employee = employeeRepo.findByEmail(employeeDto.Email());

        if(employee == null){
            return null;
        }

        employee.setName(employeeDto.Name());
        employee.setEmail(employeeDto.Email());
        employee.setPassword(this.passwordEncoder.encode(employeeDto.Password()));
        employee.setRole(employeeDto.Role());

        employeeRepo.save(employee);

        return employee.getName();
    }

    @Override
    public String deleteEmployee(String email) {
        Employee employee = employeeRepo.findByEmail(email);

        System.out.println("Employee: " + employee);

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
