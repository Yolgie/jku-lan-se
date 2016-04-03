package at.jku.oeh.lan.laganizer.model.base;

public class InvalidUsernameException extends RuntimeException {
    public final String name;

    public InvalidUsernameException(String name) {
        this.name = name;
    }
}
