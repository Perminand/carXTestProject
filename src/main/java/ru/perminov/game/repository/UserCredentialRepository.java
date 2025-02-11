package ru.perminov.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.perminov.game.model.UserCredential;

import java.util.UUID;

public interface UserCredentialRepository extends JpaRepository<UserCredential, UUID> {
}
