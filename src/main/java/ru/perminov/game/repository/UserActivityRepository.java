package ru.perminov.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.perminov.game.model.UserActivityData;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface UserActivityRepository extends JpaRepository<UserActivityData, UUID> {

    @Query(value = "SELECT u.uuid FROM UserActivity u " +
            "WHERE u.createActivity BETWEEN :start AND :end " +
            "AND NOT EXISTS (SELECT 1 FROM UserActivity u2 " +
            "WHERE u2.uuid = u.uuid AND u2.createActivity < :start)",
            nativeQuery = true)
    List<UUID> findUserIdsWithActivityInPeriod(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query(value = "SELECT ua FROM UserActivity ua " +
            "WHERE ua.uuid = :userCredential " +
            "AND ua.createActivity BETWEEN :start AND :end " +
            "ORDER BY ua.createActivity", nativeQuery = true)
    List<UserActivityData> findActivityByUserAndPeriod(@Param("userCredential") UUID userCredential,
                                                       @Param("start") LocalDate start,
                                                       @Param("end") LocalDate end);
}
