package com.hero.medconsult.back.service;

import com.hero.medconsult.back.auth.AuthResponse;
import com.hero.medconsult.back.auth.LoginRequest;
import com.hero.medconsult.back.auth.RegisterRequest;
import com.hero.medconsult.back.jwt.JwtService;
import com.hero.medconsult.back.model.Role;
import com.hero.medconsult.back.model.User;
import com.hero.medconsult.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for user authentication and registration.
 * Provides methods to login and register users.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Authenticates a user with the provided login request details and generates an authentication token.
     *
     * @param request the login request containing the user's username and password.
     * @return an AuthResponse containing the authentication token for the authenticated user.
     */

    public AuthResponse login(LoginRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails userDetails = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(userDetails);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    /**
     * Registers a new user with the provided registration request details.
     *
     * @param request the registration request containing the user's details such as username, password, first name, last name, and country.
     * @return an AuthResponse containing the authentication token for the registered user.
     */
    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .country(request.getCountry())
                .role(Role.USER)
                .build();
        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }
}
