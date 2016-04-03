package at.jku.oeh.lan.laganizer.model.base;

public class InvalidEmailException extends Exception {
    public final String email;

    public InvalidEmailException(String email) {
        this.email = email;
    }
}
