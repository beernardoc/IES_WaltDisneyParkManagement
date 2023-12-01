package project.WaltDisneyManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.WaltDisneyManagement.entity.Attraction;
import project.WaltDisneyManagement.entity.Employee;
import project.WaltDisneyManagement.entity.Park;
import project.WaltDisneyManagement.repository.AttractionRepo;
import project.WaltDisneyManagement.repository.EmployeeRepo;
import project.WaltDisneyManagement.repository.ParkRepo;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ParkRepo parkRepo;

    @Autowired
    private AttractionRepo attractionRepo;



    @Override
    public void run(String... args) throws Exception {
        Employee employee = new Employee("Mariana Dias", "marianadias@ua.pt", "12345", "TECHNICIAN");
        employee.setPassword(this.passwordEncoder.encode(employee.getPassword()));
        employeeRepo.save(employee);

        Park MagicKingdomPark = new Park("Magic Kingdom Park", null);
        parkRepo.save(MagicKingdomPark);

        Attraction WaltDisneyWorldRailroad = new Attraction("Walt Disney World Railroad", MagicKingdomPark);
        attractionRepo.save(WaltDisneyWorldRailroad);



}
}
