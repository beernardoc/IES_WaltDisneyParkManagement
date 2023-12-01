package project.WaltDisneyManagement.service;

import org.springframework.stereotype.Service;
import project.WaltDisneyManagement.Dto.ParkDto;
import project.WaltDisneyManagement.entity.Park;


@Service
public interface ParkService {

    Park addPark(ParkDto parkDto);



}
