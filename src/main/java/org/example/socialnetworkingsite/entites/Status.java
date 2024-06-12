package org.example.socialnetworkingsite.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Status {
    @Id
    private Long id;
    private Date postingTime;
    private boolean updateStatus;
}
