package ru.perminov.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.perminov.game.dto.statistic.UserActivityStatDto;
import ru.perminov.game.model.UserActivity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserActivityRepository extends JpaRepository<UserActivity, UUID> {

    @Query("SELECT new Map(uc.country as country, COUNT(DISTINCT ua.userCredential) as newUsers) " +
            "FROM UserActivity ua " +
            "JOIN UserCredential uc ON ua.userCredential = uc.uuid " +
            "WHERE ua.createActivity BETWEEN :start AND :end " +
            "GROUP BY uc.country")
    Map<String, Long> countNewUsersByCountry(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT new Map('activity' : ua.activity, 'createActivity' : ua.createActivity) " +
            "FROM UserActivity ua " +
            "WHERE ua.userCredential = :userCredential " +
            "AND ua.createActivity BETWEEN :start AND :end " +
            "ORDER BY ua.createActivity")
    List<Map<String, UserActivityStatDto>> findActivityByUserCredentialAndPeriod(
            @Param("userCredential") UUID userCredential,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}
