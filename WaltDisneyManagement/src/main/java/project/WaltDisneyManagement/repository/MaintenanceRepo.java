package project.WaltDisneyManagement.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import project.WaltDisneyManagement.entity.Maintenance;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface MaintenanceRepo extends JpaRepository<Maintenance, Integer> {

    List<Maintenance> findByAttraction(String attraction);

    List<Maintenance> findByPark(String park);


}
