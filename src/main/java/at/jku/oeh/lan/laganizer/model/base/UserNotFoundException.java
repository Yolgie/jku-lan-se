package at.jku.oeh.lan.laganizer.model.base;

import at.jku.oeh.lan.laganizer.model.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
