package at.jku.oeh.lan.laganizer.model;

public abstract class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
