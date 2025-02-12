package ru.perminov.game.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.perminov.game.dto.UserDataDto;
import ru.perminov.game.service.UserService;


@Slf4j
@RestController
@RequestMapping("/sync")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @Validated
    @ResponseStatus(HttpStatus.CREATED)
    public void createSync(@Valid @RequestBody UserDataDto userDataDto) {
        log.info("POST request {}", userDataDto);
        userService.create(userDataDto);
    }


}
