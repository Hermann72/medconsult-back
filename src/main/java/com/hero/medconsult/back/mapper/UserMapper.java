package com.hero.medconsult.back.mapper;

import com.hero.medconsult.back.dto.UserResponseDTO;
import com.hero.medconsult.back.model.User;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between User entities and DTOs.
 */
@Component
public class UserMapper {

    /**
     * Converts a User entity to a UserResponseDTO.
     *
     * @param user the User entity to convert
     * @return UserResponseDTO containing safe user information
     */
    public UserResponseDTO toResponseDTO(User user) {
        if (user == null) {
            return null;
        }

        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .country(user.getCountry())
                .role(user.getRole())
                .build();
    }

    /**
     * Converts a UserResponseDTO to a User entity (useful for updates).
     * Note: This does not include password or sensitive data.
     *
     * @param dto the UserResponseDTO to convert
     * @return User entity
     */
    public User toEntity(UserResponseDTO dto) {
        if (dto == null) {
            return null;
        }

        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .country(dto.getCountry())
                .role(dto.getRole())
                .build();
    }
}
