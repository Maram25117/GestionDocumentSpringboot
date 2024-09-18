package com.example.Gestiondocument.service;

import com.example.Gestiondocument.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    void save(User user);
    User findByUsername(String username);

}
