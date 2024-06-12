package org.example.socialnetworkingsite.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Photo {
    @Id
    private Long id;
    private String photoUrl;
    private String caption;
    private Date uploadTime;
    private boolean likeStatus;
    private boolean isProfilePhoto;

    @ManyToOne
    @JoinColumn(name = "email_id")
    private User user;
}
