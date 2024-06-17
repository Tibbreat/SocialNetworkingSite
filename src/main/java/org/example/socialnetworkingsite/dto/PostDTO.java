package org.example.socialnetworkingsite.dto;


import lombok.Data;
@Data
public class PostDTO {
    private Long id;
    private String description;
    private String imgUrl;
    private Long likes;
    private boolean status;
}
