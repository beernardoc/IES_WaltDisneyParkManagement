package project.WaltDisneyManagement.service;

import org.springframework.stereotype.Service;
import project.WaltDisneyManagement.Dto.MaintenanceDto;
import project.WaltDisneyManagement.entity.Maintenance;

import java.util.List;

@Service
public interface MaintenanceService {

    Maintenance addMaintenanceHistory(MaintenanceDto maintenanceDto);

    List<Maintenance> findAll();

    List<Maintenance> findByPark(String park);

    List<Maintenance> findByAttraction(String attraction);

    Maintenance findById(int id);


}
