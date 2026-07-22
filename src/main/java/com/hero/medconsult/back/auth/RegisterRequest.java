package com.hero.medconsult.back.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that represents a user´s registration request.
 * Contains basic information such as username, password,
 * first name, last name and country.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User registration request")
public class RegisterRequest {

    @Schema(
            description = "User's email address (used as username)",
            example = "john.doe@example.com",
            required = true
    )
    @NotBlank(message = "Username field is required")
    @Email(message = "The email address must be valid")
    private String username;

    @Schema(
            description = "User's password (min 8 characters, must contain uppercase, lowercase, number and special character)",
            example = "SecurePass123@",
            required = true
    )
    @NotBlank(message = "Password field is required")
    @Size(min = 8, message = "The password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
             message = "Password must contain at least one number, one uppercase letter, one lowercase letter, and one special character.")
    private String password;

    @Schema(
            description = "User's first name",
            example = "John",
            required = true
    )
    @NotBlank(message = "Name field is required")
    private String firstName;

    @Schema(
            description = "User's last name",
            example = "Doe",
            required = true
    )
    @NotBlank(message = "Last name field is required")
    private String lastName;

    @Schema(
            description = "User's country",
            example = "Colombia",
            required = true
    )
    @NotBlank(message = "Country field is required")
    private String country;
}
