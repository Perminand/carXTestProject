package ru.perminov.game.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.perminov.game.dto.statistic.UserActivityStatDto;
import ru.perminov.game.model.UserSyncData;
import ru.perminov.game.service.StatService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/stat")
@RequiredArgsConstructor
public class StatController {

    private final StatService statService;


    @GetMapping("/top/{country}/{count}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserSyncData> getTopUsersByCountry(@PathVariable @Size(min = 2, max = 10) String country, @PathVariable @Positive int count) {
        log.info("GET request top {} to country {}", count, country);
        return statService.getTopUsersByCountry(country, count);
    }

    @GetMapping("/new-user/{start}/{end}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Long> getNewUser(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        log.info("GET request new user start: {} end {}", start, end);
        return statService.countNewUsersByCountry(start, end);
    }

    @GetMapping("/activity/{userCredential}/{start}/{end}")
    public List<UserActivityStatDto> findActivityByUserCredentialAndPeriod(
            @PathVariable UUID userCredential,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return statService.findActivityByUserAndPeriod(userCredential, start, end);
    }


}
