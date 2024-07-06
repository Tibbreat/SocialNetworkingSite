package org.example.socialnetworkingsite.controller.auth;

import jakarta.validation.Valid;
import org.example.socialnetworkingsite.dto.LoginDTO;
import org.example.socialnetworkingsite.entites.User;
import org.example.socialnetworkingsite.model.JwtAuthResponseModel;
import org.example.socialnetworkingsite.service.auth.AuthServiceImpl;
import org.example.socialnetworkingsite.service.user.UserServiceImpl;
import org.example.socialnetworkingsite.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseModel> login(@RequestBody @Valid LoginDTO loginDTO) {
        String token = authServiceImpl.login(loginDTO.getEmailId(), loginDTO.getPassword());
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            JwtAuthResponseModel jwtAuthResponseModel = new JwtAuthResponseModel();
            jwtAuthResponseModel.setAccessToken(token);
            // Save token in DB
            User user = userServiceImpl.findUserByEmailId(loginDTO.getEmailId());
            if (user != null) {
                user.setLastLogin(new Date());
                user.setAccessToken(token);
                userServiceImpl.saveUser(user);
            }
            return new ResponseEntity<>(jwtAuthResponseModel, HttpStatus.OK);
        }
    }

    @GetMapping("/googleAuth")
    public ResponseEntity<JwtAuthResponseModel> loginGoogle(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = principal.getAttribute("email");
        User user = userServiceImpl.findUserByEmailId(email);
        if (user == null) {
            // Register the user if not already in the database
            user = new User();
            user.setEmailId(email);
            user.setPassword(""); // No password for OAuth2 users
            userServiceImpl.saveUser(user);
        }

        String token = jwtTokenProvider.generateTokenWithUserEmail(email);
        JwtAuthResponseModel jwtAuthResponseModel = new JwtAuthResponseModel();
        jwtAuthResponseModel.setAccessToken(token);
        return new ResponseEntity<>(jwtAuthResponseModel, HttpStatus.OK);
    }
}
