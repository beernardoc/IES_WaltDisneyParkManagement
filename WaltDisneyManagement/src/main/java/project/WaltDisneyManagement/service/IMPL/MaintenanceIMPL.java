package project.WaltDisneyManagement.service.IMPL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.WaltDisneyManagement.Dto.MaintenanceDto;
import project.WaltDisneyManagement.entity.Maintenance;
import project.WaltDisneyManagement.repository.MaintenanceRepo;
import project.WaltDisneyManagement.service.MaintenanceService;

import java.util.List;

@Service
public class MaintenanceIMPL implements MaintenanceService {

    @Autowired
    private MaintenanceRepo maintenanceRepo;



    @Override
    public Maintenance addMaintenanceHistory(MaintenanceDto maintenanceDto) {

        Maintenance maintenance = new Maintenance(maintenanceDto.park(),
                maintenanceDto.attraction(), maintenanceDto.Descripton(), maintenanceDto.technician() , maintenanceDto.date());

        maintenanceRepo.save(maintenance);

        return maintenance;
    }

    @Override
    public List<Maintenance> findAll() {
        return maintenanceRepo.findAll();
    }

    @Override
    public List<Maintenance> findByPark(String park) {
        return maintenanceRepo.findByPark(park);
    }

    @Override
    public List<Maintenance> findByAttraction(String attraction) {
        return maintenanceRepo.findByAttraction(attraction);
    }

    @Override
    public Maintenance findById(int id) {
        return maintenanceRepo.findById(id).orElse(null);
    }


}
