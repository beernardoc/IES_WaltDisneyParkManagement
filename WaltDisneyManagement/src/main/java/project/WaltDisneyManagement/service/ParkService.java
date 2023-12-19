package project.WaltDisneyManagement.service;

import org.springframework.stereotype.Service;
import project.WaltDisneyManagement.Dto.ParkDto;
import project.WaltDisneyManagement.entity.Park;

import java.util.List;


@Service
public interface ParkService {

    Park addPark(ParkDto parkDto);

    public int getTotalVisitors();

    Park findByName(String name);

    List<Park> findAll();

    String deletePark(String parkName);







}
