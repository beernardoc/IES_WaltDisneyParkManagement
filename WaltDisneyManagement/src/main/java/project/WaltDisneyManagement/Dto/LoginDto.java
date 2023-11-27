package project.WaltDisneyManagement.Dto;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Getter
@Setter
public class LoginDto {
    
    private String email;
    private String password;


    public LoginDto() {
    }

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }




    @Override
    public String toString() {
        return "{" +
            " email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
    
}
