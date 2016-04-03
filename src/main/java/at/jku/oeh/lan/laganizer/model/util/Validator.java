package at.jku.oeh.lan.laganizer.model.util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Validator {
    public static boolean isValidUserName(String name) {
        //TODO implement constraints (RegEx?)
        return name.length() >= 3;
    }

    public static boolean isValidEmail(String email) {
        try{
            InternetAddress mailAddr = new InternetAddress(email);
            mailAddr.validate();
        } catch(AddressException e) {
            return false;
        }
        return true;
    }

    public static boolean isValidTeamName(String name) {
        //TODO constraints on team name
        return name.length() >= 3;
    }
}
