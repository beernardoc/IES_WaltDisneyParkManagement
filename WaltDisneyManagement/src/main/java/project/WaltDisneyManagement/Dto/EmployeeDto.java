package project.WaltDisneyManagement.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


public record EmployeeDto(String Name, String Email, String Password, String Role) {
}
