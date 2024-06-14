package org.example.socialnetworkingsite.entites;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class User {
    @Id
    private String emailId;
    private String gender;
    private String firstName;
    private String lastName;
    private String password;
    private int age;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private int contactNo;
    private String city;
    private String state;
    private String country;
    private String accessToken;
    private Date lastLogin;
}
