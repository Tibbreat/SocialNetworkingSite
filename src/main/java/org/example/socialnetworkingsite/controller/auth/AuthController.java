package org.example.socialnetworkingsite.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.socialnetworkingsite.dto.LoginDTO;
import org.example.socialnetworkingsite.entites.User;
import org.example.socialnetworkingsite.model.JwtAuthResponseModel;
import org.example.socialnetworkingsite.repository.UserRepo;
import org.example.socialnetworkingsite.service.auth.AuthServiceImpl;
import org.example.socialnetworkingsite.service.user.UserService;
import org.example.socialnetworkingsite.service.user.UserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthServiceImpl authServiceImpl;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseModel> login(@RequestBody LoginDTO loginDTO) {
        String token = authServiceImpl.login(loginDTO.getEmailId(), loginDTO.getPassword());
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            JwtAuthResponseModel jwtAuthResponseModel = new JwtAuthResponseModel();
            jwtAuthResponseModel.setAccessToken(token);
            //Save token in DB
            User user = userServiceImpl.findUserByEmailId(loginDTO.getEmailId());
            if (user != null) {
                user.setLastLogin(new Date());
                user.setAccessToken(token);
                userServiceImpl.saveUser(user);
            }
            return new ResponseEntity<>(jwtAuthResponseModel, HttpStatus.OK);
        }
    }
}
