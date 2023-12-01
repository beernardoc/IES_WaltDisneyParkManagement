package project.WaltDisneyManagement.service;



import org.springframework.stereotype.Service;
import project.WaltDisneyManagement.Dto.AttractionDto;
import project.WaltDisneyManagement.entity.Attraction;


@Service
public interface AttractionService {
    Attraction addAttraction(AttractionDto attractionDto);
}