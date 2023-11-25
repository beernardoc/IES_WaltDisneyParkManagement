package project.WaltDisneyManagement.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.WaltDisneyManagement.entity.Employee;
import project.WaltDisneyManagement.repository.EmployeeRepository;
import project.WaltDisneyManagement.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;


    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);

    }

    @Override
    public Employee getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.get();
    }


    public List<Employee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        Employee existingEmployee = employeeRepository.findById(employee.getId()).get();
        existingEmployee.setName(employee.getName());
        existingEmployee.setEmail(employee.getEmail());
        return employeeRepository.save(existingEmployee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);

    }
}
