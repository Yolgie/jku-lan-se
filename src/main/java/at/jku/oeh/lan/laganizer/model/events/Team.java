package at.jku.oeh.lan.laganizer.model.events;

import at.jku.oeh.lan.laganizer.model.base.User;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Team implements Serializable{
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
		if(players == null){
			players = new HashSet<>();
		}
		players.add(player);
	}
	
	public void delPlayer(User player) {
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
