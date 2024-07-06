package org.example.socialnetworkingsite.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Entity
@Data
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    private String fEmail;
    private boolean approvalStatus;
    private String reqResDetail;

    @ManyToOne
    @JoinColumn(name = "email_id")
    private User user;
}
