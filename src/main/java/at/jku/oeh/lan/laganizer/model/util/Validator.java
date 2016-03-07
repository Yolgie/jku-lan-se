package at.jku.oeh.lan.laganizer.model.util;

public class Validator {
    public static boolean isValidUserName(String name) {
        //TODO implement constraints (RegEx?)
        return name.length() >= 3;
    }

    public static boolean isValidEmail(String email) {
        //TODO implement constraints (RegEx?)
        return email.length() >= 6 && email.contains("@") && email.contains(".");
    }

    public static boolean isValidTeamName(String name) {
        //TODO constraints on team name
        return name.length() >= 3;
    }
}
