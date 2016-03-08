package at.jku.oeh.lan.laganizer.model.base;

import at.jku.oeh.lan.laganizer.model.InvalidStringException;

public class InvalidUsernameException extends InvalidStringException {

    public InvalidUsernameException(String username) {
        super(username);
    }
}
