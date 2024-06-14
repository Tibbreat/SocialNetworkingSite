package org.example.socialnetworkingsite.service.user;

import org.example.socialnetworkingsite.entites.User;
import org.example.socialnetworkingsite.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User findUserByEmailId(String emailId) {
        return userRepo.findByEmailId(emailId);
    }

    @Override
    public User findByAccessToken(String token) {
        return userRepo.findByAccessToken(token);
    }
    @Transactional
    public void updateUserProfile(String emailId, String gender, String firstName, String lastName, int age,
                                  Date dateOfBirth, int contactNo, String city, String state, String country) {
        userRepo.updateUserProfile(emailId, gender, firstName, lastName, age, dateOfBirth, contactNo, city, state, country);
    }
}
