package com.hero.medconsult.back.auth;

import com.hero.medconsult.back.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for user authentication.
 * Provides endpoints for user login and registration.
 *
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint for user login.
     *
     * @param request Object containing the user's login data.
     * @return ResponseEntity with the authentication response.
     */
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Endpoint for registering new users.
     *
     * @param request Object containing the user's registration data.
     * @return ResponseEntity with the authentication response.
     */
    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

}
