package project.WaltDisneyManagement.service;



import org.springframework.stereotype.Service;
import project.WaltDisneyManagement.Dto.AttractionDto;
import project.WaltDisneyManagement.entity.Attraction;

import java.util.List;


@Service
public interface AttractionService {
    Attraction addAttraction(AttractionDto attractionDto);

    Attraction findByName(String name);

    List<Attraction> findAll();

}