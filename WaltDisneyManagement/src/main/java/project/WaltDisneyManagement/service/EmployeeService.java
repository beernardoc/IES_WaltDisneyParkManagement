package project.WaltDisneyManagement.service;


import project.WaltDisneyManagement.entity.Employee;

import java.util.List;

public interface EmployeeService {

    Employee createEmployee(Employee employee);

    Employee getEmployeeById(Long id);

    List<Employee> findByEmail(String email);

    Employee updateEmployee(Employee employee);

    List<Employee> getAllEmployees();


    void deleteEmployee(Long id);
}
