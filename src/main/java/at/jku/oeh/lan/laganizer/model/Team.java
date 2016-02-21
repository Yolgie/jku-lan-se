package at.jku.oeh.lan.laganizer.model;

import java.io.Serializable;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Team implements Serializable{
	@Id
	@GeneratedValue
	private long id;
	
	@NotNull
	@ElementCollection
	@CollectionTable(name = "team_players", joinColumns = @JoinColumn(name = "id"))
	private List<User> players;
	
	@NotNull
	@ManyToOne
	private Tournament tournament;
	
	@NotNull
	private String name;

	public List<User> getPlayers() {
		return players;
	}

	public void setPlayers(List<User> players) {
		this.players = players;
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
}
