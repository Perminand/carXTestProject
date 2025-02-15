package ru.practicum.game.controller.stat;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/stat")
@RequiredArgsConstructor
public class StatController {

    private final StatClient statClient;


    @GetMapping("/top/{country}/{count}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getTopUsersByCountry(@PathVariable @Size(min = 2, max = 10) String country, @PathVariable @Positive int count) {
        log.info("GET request top {} to country {}", count, country);
        return statClient.getTopUsersByCountry(country, count);
    }

    @GetMapping("/new-user/{start}/{end}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getNewUser(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        log.info("GET request new user start: {} end {}", start, end);
        if (start.isAfter(LocalDate.now())) {
            return ResponseEntity.badRequest().body("Start date cannot be in the future.");
        }

        if (end.isBefore(start)) {
            return ResponseEntity.badRequest().body("End date cannot be before start date.");
        }
        return statClient.countNewUsersByCountry(start, end);
    }

    @GetMapping("/activity/{userCredential}/{start}/{end}")
    public ResponseEntity<Object> findActivityByUserCredentialAndPeriod(
            @PathVariable UUID userCredential,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        log.info("GET request activity user date start: {}, end {}", start, end);

        if (start.isAfter(LocalDate.now())) {
            return ResponseEntity.badRequest().body("Start date cannot be in the future.");
        }

        if (end.isBefore(start)) {
            return ResponseEntity.badRequest().body("End date cannot be before start date.");
        }

        return statClient.findActivityByUserAndPeriod(userCredential, start, end);
    }


}
