package org.example.socialnetworkingsite.controller.user;


import org.example.socialnetworkingsite.constant.HTTPCode;
import org.example.socialnetworkingsite.constant.RegexConstant;
import org.example.socialnetworkingsite.entites.User;
import org.example.socialnetworkingsite.model.ResponseModel;
import org.example.socialnetworkingsite.service.user.UserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<ResponseModel> register(@RequestBody User user) {
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

    @PostMapping("/edit")
    public ResponseEntity<ResponseModel> edit(@RequestBody User user) {
        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User existingUser = userServiceImpl.findByAccessToken(userDetail.getUsername());
        if (existingUser != null && existingUser.getEmailId().equals(user.getEmailId())) {
            BeanUtils.copyProperties(user, existingUser);
            if (userServiceImpl.saveUser(existingUser) == null) {
                return ResponseEntity.status(HTTPCode.HTTP_NO_CONTENT).body(new ResponseModel(HTTPCode.HTTP_NO_CONTENT, "Update failed"));
            } else {
                return ResponseEntity.status(HTTPCode.HTTP_OK).body(new ResponseModel(HTTPCode.HTTP_OK, "Update succeefully"));
            }
        }else{
            return ResponseEntity.status(HTTPCode.HTTP_NO_CONTENT).body(new ResponseModel(HTTPCode.HTTP_NO_CONTENT, "Update failed"));

        }
    }
}
