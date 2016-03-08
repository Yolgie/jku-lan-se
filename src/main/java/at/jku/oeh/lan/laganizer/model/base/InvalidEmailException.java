package at.jku.oeh.lan.laganizer.model.base;

import at.jku.oeh.lan.laganizer.model.InvalidStringException;

public class InvalidEmailException extends InvalidStringException {

    public InvalidEmailException(String email) {
        super(email);
    }
}
