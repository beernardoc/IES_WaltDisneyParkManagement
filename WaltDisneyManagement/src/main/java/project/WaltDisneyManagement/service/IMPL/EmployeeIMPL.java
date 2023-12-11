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
}
