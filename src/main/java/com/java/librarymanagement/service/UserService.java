package com.java.librarymanagement.service;

import com.java.librarymanagement.DTO.LoginDto;
import com.java.librarymanagement.DTO.LoginResponseDto;
import com.java.librarymanagement.DTO.SignupDtoRequest;
import com.java.librarymanagement.DTO.SignupDtoResponse;
import com.java.librarymanagement.Jwt.JwtService;
import com.java.librarymanagement.entity.enums.Role;
import com.java.librarymanagement.entity.Users;
import com.java.librarymanagement.repo.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;



    public SignupDtoResponse signup(SignupDtoRequest request) {


        if (usersRepository.existsByEmail(request.email())) {
            throw new RuntimeException("User already exists");
        }

        Users newUser = new Users();
        newUser.setName(request.name());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password())); // make sure this is hashed - see note below
        newUser.setRole(Role.USER);

        usersRepository.save(newUser);

        return new SignupDtoResponse(request.name(), request.email());
    }

    public Users getUserById(String userId) {
        return usersRepository.findByEmail(userId);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByEmail(username);
    }
}
