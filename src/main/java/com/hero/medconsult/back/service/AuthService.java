package com.hero.medconsult.back.service;

import com.hero.medconsult.back.auth.AuthResponse;
import com.hero.medconsult.back.auth.LoginRequest;
import com.hero.medconsult.back.auth.RegisterRequest;
import com.hero.medconsult.back.exception.UserAlreadyExistsException;
import com.hero.medconsult.back.jwt.JwtService;
import com.hero.medconsult.back.mapper.UserMapper;
import com.hero.medconsult.back.model.Role;
import com.hero.medconsult.back.model.User;
import com.hero.medconsult.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for user authentication and registration.
 * Provides methods to login and register users.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    /**
     * Authenticates a user with the provided login request details and generates an authentication token.
     *
     * @param request the login request containing the user's username and password.
     * @return an AuthResponse containing the authentication token and user information.
     */

    public AuthResponse login(LoginRequest request) {
        log.debug("Login attempt for user: {}", request.getUsername());

        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
            String token = jwtService.generateToken(user);

            log.info("User logged in successfully: {}", request.getUsername());

            return AuthResponse.builder()
                    .token(token)
                    .user(userMapper.toResponseDTO(user))
                    .build();
        } catch (Exception e) {
            log.error("Login failed for user: {}", request.getUsername());
            throw e;
        }
    }

    /**
     * Registers a new user with the provided registration request details.
     *
     * @param request the registration request containing the user's details.
     * @return an AuthResponse containing the authentication token and user information.
     */
    public AuthResponse register(RegisterRequest request) {
        log.debug("Registration attempt for user: {}", request.getUsername());

        // Check if the user already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            log.warn("Registration failed: User already exists - {}", request.getUsername());
            throw new UserAlreadyExistsException("User with email " + request.getUsername() + " already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .country(request.getCountry())
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        log.info("User registered successfully: {} (ID: {})", savedUser.getUsername(), savedUser.getId());

        return AuthResponse.builder()
                .token(jwtService.generateToken(savedUser))
                .user(userMapper.toResponseDTO(savedUser))
                .build();
    }
}
