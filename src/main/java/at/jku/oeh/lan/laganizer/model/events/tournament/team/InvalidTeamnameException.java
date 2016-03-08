package at.jku.oeh.lan.laganizer.model.events.tournament.team;

import at.jku.oeh.lan.laganizer.model.InvalidStringException;

public class InvalidTeamNameException extends InvalidStringException {

    public InvalidTeamNameException(String teamname) {
        super(teamname);
    }
}
