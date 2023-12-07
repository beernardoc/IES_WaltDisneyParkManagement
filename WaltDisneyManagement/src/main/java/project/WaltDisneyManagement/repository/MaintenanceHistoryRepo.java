package project.WaltDisneyManagement.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import project.WaltDisneyManagement.entity.MaintenanceHistory;

@EnableJpaRepositories
@Repository
public interface MaintenanceHistoryRepo extends JpaRepository<MaintenanceHistory, Integer> {


}
