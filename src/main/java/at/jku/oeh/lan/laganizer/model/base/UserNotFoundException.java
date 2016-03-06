package at.jku.oeh.lan.laganizer.model.base;

public class UserNotFoundException extends Exception {
    public final String msg;

    public UserNotFoundException(String msg) {
        this.msg = msg;
    }
}
