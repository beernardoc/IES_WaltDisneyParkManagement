package project.WaltDisneyManagement.service.IMPL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.WaltDisneyManagement.Dto.AttractionDto;
import project.WaltDisneyManagement.entity.Attraction;
import project.WaltDisneyManagement.repository.AttractionRepo;
import project.WaltDisneyManagement.repository.ParkRepo;
import project.WaltDisneyManagement.service.AttractionService;


@Service
public class AttractionIMPL implements AttractionService {

    @Autowired
    private AttractionRepo attractionRepo;


    @Override
    public Attraction addAttraction(AttractionDto attractionDto) {
        System.out.println("Received attractionDto: " + attractionDto);

        Attraction attraction = new Attraction(attractionDto.name(),attractionDto.park(), attractionDto.type());

        attractionRepo.save(attraction);

        System.out.println("attraction saved successfully.");

        return attraction;
    }

    @Override
    public Attraction findByName(String name) {
        return attractionRepo.findByName(name);
    }
}
