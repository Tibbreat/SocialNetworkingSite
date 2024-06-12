package org.example.socialnetworkingsite.entites;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private String imgUrl;
    private Long likes;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "email_id")
    private User user;

    @PrePersist
    protected void onCreate() {
        this.status = false;
        this.likes = 0L;
    }
}
