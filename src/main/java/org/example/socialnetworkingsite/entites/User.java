package org.example.socialnetworkingsite.entites;

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
    private Date dateOfBirth;
    private int contactNo;
    private String city;
    private String state;
    private String country;
    private String accessToken;
}
