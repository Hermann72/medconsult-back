package com.hero.medconsult.back.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User login request")
public class LoginRequest {

    @Schema(
            description = "User's email address",
            example = "john.doe@example.com",
            required = true
    )
    @NotBlank(message = "Username field is required")
    private String username;

    @Schema(
            description = "User's password",
            example = "SecurePass123@",
            required = true
    )
    @NotBlank(message = "Password field is required")
    private String password;
}
