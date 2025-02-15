package ru.perminov.game.exception.error;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String massage) {
        super(massage);
    }
}
