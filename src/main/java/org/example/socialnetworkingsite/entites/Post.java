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
    @Column(columnDefinition = "TEXT")
    private String description;

    private String imgUrl;
    private Long likes;
    
}
