package org.example.socialnetworkingsite.entites;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fEmail;
    private boolean approvalStatus;
    private String reqResDetail;

    @ManyToOne
    @JoinColumn(name = "email_id")
    private User user;
}
