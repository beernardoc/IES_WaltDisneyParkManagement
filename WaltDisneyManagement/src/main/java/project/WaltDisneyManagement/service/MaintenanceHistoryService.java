package project.WaltDisneyManagement.service;

import com.sun.tools.javac.Main;
import org.springframework.stereotype.Service;
import project.WaltDisneyManagement.Dto.MaintenanceHistoryDto;
import project.WaltDisneyManagement.entity.MaintenanceHistory;
import java.util.List;

@Service
public interface MaintenanceHistoryService {

    MaintenanceHistory addMaintenanceHistory(MaintenanceHistoryDto maintenanceHistoryDto);

    List<MaintenanceHistory> findAll();

    List<MaintenanceHistory> findByPark(String park);

    List<MaintenanceHistory> findByAttraction(String attraction);

    MaintenanceHistory findById(int id);


}
