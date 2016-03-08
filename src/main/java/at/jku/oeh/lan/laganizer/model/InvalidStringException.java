package at.jku.oeh.lan.laganizer.model;

public class InvalidStringException extends Exception {
    public final String string;

    public InvalidStringException(String string) {
        this.string = string;
    }
}
