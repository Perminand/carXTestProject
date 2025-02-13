package ru.perminov.game.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.perminov.game.dto.statistic.UserActivityStatDto;
import ru.perminov.game.model.UserData;
import ru.perminov.game.service.StatService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/stat")
@RequiredArgsConstructor
public class StatController {

    private final StatService statService;

    @GetMapping("/money")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<UserData>> getUserMoney() {
        log.info("GET request for money statistic");
        return statService.getMoneyData();
    }

    @GetMapping("/new-user")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Long> getNewUser(@RequestParam LocalDateTime start,
                                        @RequestParam LocalDateTime end) {
        log.info("GET request new user start: {} end {}", start, end);
        return statService.countNewUsersByCountry(start, end);
    }

    @GetMapping("/{uuid}/activity")
    public List<Map<String, UserActivityStatDto>> getActivityForUser(
            @PathVariable UUID uuid,
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return statService.getActivityForUser(uuid, start, end);
    }


}
