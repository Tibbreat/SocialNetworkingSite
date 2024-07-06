package org.example.socialnetworkingsite.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.socialnetworkingsite.constant.HTTPCode;
import org.example.socialnetworkingsite.constant.RegexConstant;
import org.example.socialnetworkingsite.entites.User;
import org.example.socialnetworkingsite.entites.UserProfile;
import org.example.socialnetworkingsite.model.ResponseModel;
import org.example.socialnetworkingsite.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<ResponseModel> register(@RequestBody @Valid User user) {
        //Check email existed in BD
        if (userServiceImpl.findUserByEmailId(user.getEmailId()) != null) {
            return ResponseEntity.badRequest().body(new ResponseModel(HTTPCode.HTTP_CONFLICT, "Email was existed in DB"));
        }
        //Register new user
        String passwordHashed = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHashed);
        if (!user.getEmailId().matches(RegexConstant.EMAIL_REGEX)) {
            return ResponseEntity.badRequest().body(new ResponseModel(HTTPCode.HTTP_UNPROCESSABLE_ENTITY, "Email invalid"));
        } else {
            User newUser = userServiceImpl.saveUser(user);
            if (newUser == null) {
                return ResponseEntity.status(HTTPCode.HTTP_UNPROCESSABLE_ENTITY).body(new ResponseModel(HTTPCode.HTTP_UNPROCESSABLE_ENTITY, "User not registered"));
            } else {
                return ResponseEntity.status(HTTPCode.HTTP_CREATED).body(new ResponseModel(HTTPCode.HTTP_CREATED, "User registered successfully"));
            }
        }
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseModel> edit(@RequestBody User userUpdate, HttpServletRequest request) {

        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userUpdate.setEmailId(userDetail.getUsername());
        try {
            // Update user profile
            userServiceImpl.updateUserProfile(userDetail.getUsername(), userUpdate.getGender(), userUpdate.getFirstName(),
                    userUpdate.getLastName(), userUpdate.getAge(), userUpdate.getDateOfBirth(),
                    userUpdate.getContactNo(), userUpdate.getCity(), userUpdate.getState(),
                    userUpdate.getCountry());

            return ResponseEntity.ok(new ResponseModel(HTTPCode.HTTP_OK, "User profile updated successfully"));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HTTPCode.HTTP_INTERNAL_ERROR)
                    .body(new ResponseModel(HTTPCode.HTTP_INTERNAL_ERROR, "Failed to update user profile"));
        }
    }

    @PostMapping("/setupProfile")
    public ResponseEntity<ResponseModel> updateProfile(@RequestBody UserProfile userProfile) {
        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userProfile.setEmailId(userDetail.getUsername());

        try {
            userServiceImpl.setupProfile(userProfile);
            return ResponseEntity.ok(new ResponseModel(HTTPCode.HTTP_OK, "User profile updated successfully"));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HTTPCode.HTTP_INTERNAL_ERROR)
                    .body(new ResponseModel(HTTPCode.HTTP_INTERNAL_ERROR, "Failed to update user profile"));
        }
    }
}
