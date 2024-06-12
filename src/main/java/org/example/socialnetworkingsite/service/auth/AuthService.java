package org.example.socialnetworkingsite.service.auth;

import org.example.socialnetworkingsite.entites.User;

public interface AuthService {
    String login(String email, String password);
}
