package project.WaltDisneyManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.WaltDisneyManagement.entity.*;
import project.WaltDisneyManagement.repository.*;

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

    @Autowired
    private ParkCarsRepo parkCarsRepo;





    @Override
    public void run(String... args) throws Exception {
        Employee employee = new Employee("Mariana Dias", "marianadias@ua.pt", "12345", "TECHNICIAN");
        employee.setPassword(this.passwordEncoder.encode(employee.getPassword()));
        employeeRepo.save(employee);

        Park magicKingdomPark = new Park("Magic Kingdom", null);
        parkRepo.save(magicKingdomPark);

        Attraction WaltDisneyWorldRailroad = new Attraction("Walt Disney World Railroad", magicKingdomPark, "RollerCoaster");
        attractionRepo.save(WaltDisneyWorldRailroad);

        Attraction PiratesOfTheCaribbean = new Attraction("Pirates of the Caribbean", magicKingdomPark, "DarkRide");
        attractionRepo.save(PiratesOfTheCaribbean);

        Attraction HauntedMansion = new Attraction("Haunted Mansion", magicKingdomPark, "DarkRide");
        attractionRepo.save(HauntedMansion);

        Attraction SevenDwarfsMineTrain = new Attraction("Seven Dwarfs Mine Train", magicKingdomPark, "RollerCoaster");
        attractionRepo.save(SevenDwarfsMineTrain);

        Attraction TomorrowLandSpeedway = new Attraction("Tomorrowland Speedway", magicKingdomPark, "RollerCoaster");
        attractionRepo.save(TomorrowLandSpeedway);


        Park Epcot = new Park("Epcot", null);
        parkRepo.save(Epcot);

        Attraction SpaceshipEarth = new Attraction("Spaceship Earth", Epcot, "DarkRide");
        attractionRepo.save(SpaceshipEarth);

        Attraction GuardiansoftheGalaxy = new Attraction("Guardians of the Galaxy: Cosmic Rewind", Epcot, "Darkride");
        attractionRepo.save(GuardiansoftheGalaxy);

        Attraction TestTrack = new Attraction("Test Track", Epcot, "Darkride");
        attractionRepo.save(TestTrack);

        
        ParkCars ParkingLot1 = new ParkCars("ParkingLot1", 700, 30);
        parkCarsRepo.save(ParkingLot1);






    }
}
