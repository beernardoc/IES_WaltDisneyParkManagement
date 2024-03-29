package project.WaltDisneyManagement.service;

import org.springframework.stereotype.Service;

import project.WaltDisneyManagement.Dto.EmployeeDto;
import project.WaltDisneyManagement.Dto.LoginDto;
import project.WaltDisneyManagement.entity.Employee;

import java.util.List;

@Service
public interface EmployeeService {


    String addEmployee(EmployeeDto employeeDto);

    boolean loginEmployee(LoginDto loginDto);


    Employee findById(int id);

    Employee findByName(String name);

    Employee findByEmail(String email);

    List<Employee> findAll();

    String updateEmployee(EmployeeDto employeeDto);

    String deleteEmployee(String email);


}
