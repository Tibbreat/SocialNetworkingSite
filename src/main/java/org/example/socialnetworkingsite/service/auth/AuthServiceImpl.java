package org.example.socialnetworkingsite.service.auth;

import org.example.socialnetworkingsite.entites.User;
import org.example.socialnetworkingsite.jwt.JwtTokenProvider;
import org.example.socialnetworkingsite.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;



    @Override
    public String login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateToken(authentication);
    }

}
