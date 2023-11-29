package project.WaltDisneyManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.WaltDisneyManagement.entity.Employee;
import project.WaltDisneyManagement.repository.EmployeeRepo;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Employee employee = new Employee("Mariana Dias", "marianadias@ua.pt", "12345", "TECHNICIAN");

        employee.setPassword(this.passwordEncoder.encode(employee.getPassword()));

        employeeRepo.save(employee);
}
}
