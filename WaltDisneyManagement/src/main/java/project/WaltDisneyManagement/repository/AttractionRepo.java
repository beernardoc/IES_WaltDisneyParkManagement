package project.WaltDisneyManagement.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import project.WaltDisneyManagement.entity.Attraction;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface AttractionRepo extends JpaRepository<Attraction, Integer> {

    Attraction findByName(String attraction_name);

    List<Attraction> findByPark_ParkId(int parkId);

}
