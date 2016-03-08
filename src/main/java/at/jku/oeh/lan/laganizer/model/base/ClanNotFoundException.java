package at.jku.oeh.lan.laganizer.model.base;

import at.jku.oeh.lan.laganizer.model.EntityNotFoundException;

public class ClanNotFoundException extends EntityNotFoundException {
    public ClanNotFoundException(String msg) {
        super(msg);
    }
}
