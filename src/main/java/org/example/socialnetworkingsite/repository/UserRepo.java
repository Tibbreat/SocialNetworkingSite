package org.example.socialnetworkingsite.repository;

import org.example.socialnetworkingsite.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    User findByEmailId(String username);

    User findByAccessToken(String authToken);

    @Modifying
    @Query("UPDATE User u SET u.gender = :gender, u.firstName = :firstName, u.lastName = :lastName, u.age = :age, " +
            "u.dateOfBirth = :dateOfBirth, u.contactNo = :contactNo, " +
            "u.city = :city, u.state = :state, u.country = :country " +
            "WHERE u.emailId = :emailId")
    void updateUserProfile(String emailId, String gender, String firstName, String lastName, int age, Date dateOfBirth, int contactNo, String city, String state, String country);

    @Modifying
    @Query("UPDATE UserProfile u " +
            "SET u.seniorSchool = :seniorSchool, u.college = :college, " +
            "u.university = :university, u.workProfile = :workProfile , u.highSchool = :highSchool " +
            "WHERE u.emailId = :emailId")
    void setupProfile(String emailId, String seniorSchool, String college, String university, String workProfile, String highSchool);
}
