package project.WaltDisneyManagement.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {
   

    private String name;
    private String email;
    private String password;
    private String role;


    public EmployeeDto() {
    }

    public EmployeeDto(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }




    @Override
    public String toString() {
        return "{" +
            " employeeName='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
    
}
