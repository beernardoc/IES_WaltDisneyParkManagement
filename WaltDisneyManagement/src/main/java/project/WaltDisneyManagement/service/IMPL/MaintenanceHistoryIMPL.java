package project.WaltDisneyManagement.service.IMPL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.WaltDisneyManagement.Dto.MaintenanceHistoryDto;
import project.WaltDisneyManagement.entity.Attraction;
import project.WaltDisneyManagement.entity.MaintenanceHistory;
import project.WaltDisneyManagement.repository.MaintenanceHistoryRepo;
import project.WaltDisneyManagement.service.MaintenanceHistoryService;

@Service
public class MaintenanceHistoryIMPL implements MaintenanceHistoryService {

    @Autowired
    private MaintenanceHistoryRepo maintenanceHistoryRepo;




    @Override
    public MaintenanceHistory addMaintenanceHistory(MaintenanceHistoryDto maintenanceHistoryDto) {
        System.out.println("Received maintenanceHistoryDto: " + maintenanceHistoryDto);

        MaintenanceHistory maintenanceHistory = new MaintenanceHistory(maintenanceHistoryDto.park(),
                maintenanceHistoryDto.attraction(), maintenanceHistoryDto.Descripton(), maintenanceHistoryDto.date());

        maintenanceHistoryRepo.save(maintenanceHistory);

        System.out.println("maintenanceHistory saved successfully.");

        return maintenanceHistory;
    }
}
