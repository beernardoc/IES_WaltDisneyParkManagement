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
        Employee technician = new Employee("Mariana Dias", "marianadias@ua.pt", "12345", "TECHNICIAN");
        technician.setPassword(this.passwordEncoder.encode(technician.getPassword()));
        employeeRepo.save(technician);

        Employee admin = new Employee("João Pinto", "joao@ua.pt", "12345", "ADMIN");
        admin.setPassword(this.passwordEncoder.encode(admin.getPassword()));
        employeeRepo.save(admin);

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

        Attraction GuardiansoftheGalaxy = new Attraction("Guardians of the Galaxy: Cosmic Rewind", Epcot, "DarkRide");
        attractionRepo.save(GuardiansoftheGalaxy);

        Attraction TestTrack = new Attraction("Test Track", Epcot, "DarkRide");
        attractionRepo.save(TestTrack);

        Park HollywoodStudios = new Park("Hollywood Studios", null);
        parkRepo.save(HollywoodStudios);

        Attraction MickeyMinnieRunawayRailway = new Attraction("Mickey & Minnie's Runaway Railway", HollywoodStudios, "RollerCoaster");
        attractionRepo.save(MickeyMinnieRunawayRailway);

        Attraction RocknRollerCoaster = new Attraction("Rock n Roller Coaster", HollywoodStudios, "RollerCoaster");
        attractionRepo.save(RocknRollerCoaster);

        Park AnimalKingdom = new Park("Animal Kingdom", null);
        parkRepo.save(AnimalKingdom);

        Attraction TriceratopSpin = new Attraction("Triceratop Spin", AnimalKingdom, "Carousel");
        attractionRepo.save(TriceratopSpin);

        Attraction KaliRiverRapids = new Attraction("Kali River Rapids", AnimalKingdom, "WaterRide");
        attractionRepo.save(KaliRiverRapids);

        Attraction Dinosaur = new Attraction("Dinosaur", AnimalKingdom, "DarkRide");
        attractionRepo.save(Dinosaur);

        Park DisneySprings = new Park("Disney Springs", null);
        parkRepo.save(DisneySprings);

        Attraction MarketplaceCarousel = new Attraction("Marketplace Train Express", DisneySprings, "RollerCoaster");
        attractionRepo.save(MarketplaceCarousel);

        Attraction ClassicCarousel = new Attraction("Classic Carousel", DisneySprings, "Carousel");
        attractionRepo.save(ClassicCarousel);

        Park blizzardbeach = new Park("Blizzard Beach", null);
        parkRepo.save(blizzardbeach);

        Attraction SummitPlummet = new Attraction("Summit Plummet", blizzardbeach, "WaterRide");
        attractionRepo.save(SummitPlummet);

        Attraction SlushGusher = new Attraction("Slush Gusher", blizzardbeach, "WaterRide");
        attractionRepo.save(SlushGusher);

        Park typhoonlagoon = new Park("Typhoon Lagoon", null);
        parkRepo.save(typhoonlagoon);

        Attraction GangplankFalls = new Attraction("Gangplank Falls", typhoonlagoon, "WaterRide");
        attractionRepo.save(GangplankFalls);

        Attraction HumungaKowabunga = new Attraction("Humunga Kowabunga", typhoonlagoon, "WaterRide");
        attractionRepo.save(HumungaKowabunga);

        ParkCars ParkingLot1 = new ParkCars("ParkingLot1", 700, 699, "Open");
        parkCarsRepo.save(ParkingLot1);

        ParkCars ParkingLot2 = new ParkCars("ParkingLot2", 700, 600, "Open");
        parkCarsRepo.save(ParkingLot2);








    }
}
