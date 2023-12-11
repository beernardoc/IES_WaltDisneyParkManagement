package project.WaltDisneyManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import project.WaltDisneyManagement.entity.Park;

@EnableJpaRepositories
@Repository
public interface ParkRepo extends JpaRepository<Park, Integer> {

    Park findByName(String parkName);

    @Query("SELECT SUM(p.visitors) FROM Park p")
    int getTotalVisitors();



}
