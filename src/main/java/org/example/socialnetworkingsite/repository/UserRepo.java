package org.example.socialnetworkingsite.repository;

import org.example.socialnetworkingsite.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    User findByEmailId(String username);
    User findByAccessToken(String authToken);
}
