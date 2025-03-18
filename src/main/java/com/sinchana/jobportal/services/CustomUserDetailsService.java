package com.sinchana.jobportal.services;

import com.sinchana.jobportal.entity.Users;
import com.sinchana.jobportal.repository.UsersRepository;
import com.sinchana.jobportal.util.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Attempting to load user: " + username);
        Users users =usersRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("could not find users"));
        System.out.println("User found: " + users.getEmail());
        System.out.println("Stored password hash: " + users.getPassword());
        return new CustomUserDetails(users);
    }
}
