package at.jku.oeh.lan.laganizer.model.events;

public class InvalidTeamnameException extends Exception {
    public final String name;

    public InvalidTeamnameException(String name) {
        this.name = name;
    }
}
