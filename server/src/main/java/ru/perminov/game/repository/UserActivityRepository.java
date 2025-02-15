package ru.perminov.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.perminov.game.model.UserActivityData;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface UserActivityRepository extends JpaRepository<UserActivityData, UUID> {

    @Query(value = "SELECT u.user_uuid FROM user_activity_data u " +
            "WHERE u.create_activity BETWEEN :start AND :end " +
            "AND NOT EXISTS (SELECT 1 FROM user_activity_data u2 " +
            "WHERE u2.user_uuid = u.user_uuid AND u2.create_activity < :start)",
            nativeQuery = true)
    List<UUID> findUserIdsWithActivityInPeriod(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query(value = "SELECT * FROM user_activity_data ua " +
            "WHERE ua.user_uuid = :userCredential " +
            "AND ua.create_activity BETWEEN :start AND :end " +
            "ORDER BY ua.create_activity", nativeQuery = true)
    List<UserActivityData> findActivityByUserAndPeriod(@Param("userCredential") UUID userCredential,
                                                       @Param("start") LocalDate start,
                                                       @Param("end") LocalDate end);
}
