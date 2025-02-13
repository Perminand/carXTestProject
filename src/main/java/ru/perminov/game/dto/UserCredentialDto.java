package ru.perminov.game.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import ru.perminov.game.model.Role;

import java.util.UUID;

public record UserCredentialDto(

        UUID id,

        @Column(nullable = false)
        String email,

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        Role role) {
}
