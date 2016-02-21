package at.jku.oeh.lan.laganizer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
public class Tournament extends Event implements Serializable{
	@NotNull
	private int teamSize;

    @NotNull
    private String game;

    //ToDo store seed for random teambla

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}
