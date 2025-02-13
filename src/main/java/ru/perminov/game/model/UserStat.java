package ru.perminov.game.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class UserStat {

    @Id
    private UUID userId;

    @Column(nullable = false)
    private Integer activity;
}
