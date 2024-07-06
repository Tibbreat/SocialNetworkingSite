package org.example.socialnetworkingsite.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginDTO {
    @Email(message = "Email not valid")
    private String emailId;
    private String password;
}
