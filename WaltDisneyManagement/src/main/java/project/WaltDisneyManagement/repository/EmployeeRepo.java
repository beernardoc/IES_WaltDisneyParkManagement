package project.WaltDisneyManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import project.WaltDisneyManagement.entity.Employee;

@EnableJpaRepositories
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    Employee findByName(String employeeName);

    Employee findById(int employeeId);

    Employee findByEmail(String email);



}
