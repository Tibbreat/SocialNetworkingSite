package org.example.socialnetworkingsite.service.auth;

import org.example.socialnetworkingsite.entites.User;
import org.example.socialnetworkingsite.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class CustomerUserDetailService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmailId(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        Set<GrantedAuthority> grantedAuthorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(user.getEmailId(), user.getPassword(), grantedAuthorities);
    }
}
