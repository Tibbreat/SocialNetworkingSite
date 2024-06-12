package org.example.socialnetworkingsite.service.user;

import org.example.socialnetworkingsite.entites.User;

public interface UserService {
    User saveUser(User user);

    User findUserByEmailId(String emailId);

    User findByAccessToken(String token);
}
