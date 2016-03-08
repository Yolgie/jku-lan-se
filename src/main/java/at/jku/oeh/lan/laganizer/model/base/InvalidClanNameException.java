package at.jku.oeh.lan.laganizer.model.base;

import at.jku.oeh.lan.laganizer.model.InvalidStringException;


public class InvalidClanNameException extends InvalidStringException {

    public InvalidClanNameException(String name) {
        super(name);
    }
}
