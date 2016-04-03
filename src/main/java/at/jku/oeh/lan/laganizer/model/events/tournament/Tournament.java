package at.jku.oeh.lan.laganizer.model.events.tournament;

import at.jku.oeh.lan.laganizer.model.events.Event;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@DiscriminatorValue("Tournament")
public class Tournament extends Event implements Serializable {
    @NotNull
    private int maxTeamSize;

    @NotNull
    private String game;

    //ToDo store seed for random teambla

    public int getMaxTeamSize() {
        return maxTeamSize;
    }

    public void setMaxTeamSize(int maxTeamSize) {
        this.maxTeamSize = maxTeamSize;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}
