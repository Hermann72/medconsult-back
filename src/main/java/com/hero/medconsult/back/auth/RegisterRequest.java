package com.hero.medconsult.back.auth;

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
public class RegisterRequest {
    @NotBlank(message = "Username field is required")
    @Email(message = "The email address must be valid")
    private String username;

    @NotBlank(message = "Password field is required")
    @Size(min = 8, message = "The password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
             message = "Password must contain at least one number, one uppercase letter, one lowercase letter, and one special character.")
    private String password;

    @NotBlank(message = "Name field is required")
    private String firstName;

    @NotBlank(message = "Last name field is required")
    private String lastName;

    @NotBlank(message = "Country field is required")
    private String country;
}
