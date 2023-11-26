package project.WaltDisneyManagement.service;

import org.springframework.stereotype.Service;

import project.WaltDisneyManagement.Dto.EmployeeDto;
import project.WaltDisneyManagement.Dto.LoginDto;

@Service
public interface EmployeeService {


    String addEmployee(EmployeeDto employeeDto);

    boolean loginEmployee(LoginDto loginDto);  

}
