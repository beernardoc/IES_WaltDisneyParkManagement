package project.WaltDisneyManagement.service.IMPL;

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


    @Override
    public String addEmployee(EmployeeDto employeeDto) {

        System.out.println("Received employeeDto: " + employeeDto);

        Employee employee = new Employee(
            employeeDto.getEmployeeId(),
            employeeDto.getEmployeeName(),
            employeeDto.getEmail(),
            
            this.passwordEncoder.encode(employeeDto.getPassword())
            );

            System.out.println("Saving employee: " + employee);

            employeeRepo.save(employee);

            System.out.println("Employee saved successfully.");

            return employee.getEmployeeName();


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

        return true;
        
    }
}
