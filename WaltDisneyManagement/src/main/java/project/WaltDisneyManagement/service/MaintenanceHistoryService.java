package project.WaltDisneyManagement.service;

import org.springframework.stereotype.Service;
import project.WaltDisneyManagement.Dto.MaintenanceHistoryDto;
import project.WaltDisneyManagement.entity.MaintenanceHistory;

@Service
public interface MaintenanceHistoryService {

    MaintenanceHistory addMaintenanceHistory(MaintenanceHistoryDto maintenanceHistoryDto);
}
