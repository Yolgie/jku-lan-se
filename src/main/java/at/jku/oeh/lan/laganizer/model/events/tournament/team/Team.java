package at.jku.oeh.lan.laganizer.model.events.tournament.team;

import at.jku.oeh.lan.laganizer.model.base.User;
import at.jku.oeh.lan.laganizer.model.events.tournament.Tournament;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Team implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @ElementCollection
    @ManyToMany(mappedBy = "Team.players")
    private Set<User> players;

    @NotNull
    @ManyToOne
    private Tournament tournament;

    @NotNull
    private String name;

    public Set<User> getPlayers() {
        return players;
    }

    public void addPlayer(User player) {
        if (players == null) {
            players = new HashSet<>();
        }
        players.add(player);
    }

    public void removePlayer(User player) {
        players.remove(player);
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setPlayers(Set<User> players) {
        this.players = players;
    }
}
