package org.example.socialnetworkingsite.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
public class UserProfile extends User {
    private String senoirSchool;
    private String college;
    private String university;
    private String workProfile;
    private String highSchool;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String skills;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String interest;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String hobby;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String aboutYou;
}
