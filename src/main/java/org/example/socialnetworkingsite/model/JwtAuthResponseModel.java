package org.example.socialnetworkingsite.model;

import lombok.Data;

@Data
public class JwtAuthResponseModel {
    private String accessToken;
    private String tokenType = "Bearer";
}
