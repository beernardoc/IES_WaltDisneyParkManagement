package project.WaltDisneyManagement.service.IMPL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.WaltDisneyManagement.Dto.AttractionDto;
import project.WaltDisneyManagement.entity.Attraction;
import project.WaltDisneyManagement.entity.Employee;
import project.WaltDisneyManagement.entity.Park;
import project.WaltDisneyManagement.repository.AttractionRepo;
import project.WaltDisneyManagement.repository.ParkRepo;
import project.WaltDisneyManagement.service.AttractionService;

import java.util.List;


@Service
public class AttractionIMPL implements AttractionService {

    @Autowired
    private AttractionRepo attractionRepo;

    @Autowired
    private ParkRepo parkRepo;


    @Override
    public Attraction addAttraction(AttractionDto attractionDto) {
        System.out.println("Received attractionDto: " + attractionDto);

        Park park = parkRepo.findById(attractionDto.parkID()).orElse(null);

        Attraction attraction = new Attraction(attractionDto.name(),park, attractionDto.type());

        attractionRepo.save(attraction);

        System.out.println("attraction saved successfully.");

        return attraction;
    }

    @Override
    public Attraction findByName(String name) {
        return attractionRepo.findByName(name);
    }

    @Override
    public List<Attraction> findAll() {
        return attractionRepo.findAll();
    }

    @Override
    public Attraction updateAttraction(AttractionDto attractionDto) {
        Attraction attraction = attractionRepo.findByName(attractionDto.name());

        attraction.setName(attractionDto.name());
        attraction.setType(attractionDto.type());
        attraction.setPark(parkRepo.findById(attractionDto.parkID()).orElse(null));

        attractionRepo.save(attraction);

        return attraction;
    }

    @Override
    public String deleteAttraction(String name) {
        Attraction attraction = attractionRepo.findByName(name);

        if (attraction != null) {
            attractionRepo.delete(attraction);
            return attraction.getName();
        } else {
            return null;
        }
    }


}
