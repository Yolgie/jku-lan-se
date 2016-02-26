package at.jku.oeh.lan.laganizer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import at.jku.oeh.lan.laganizer.steam.SteamGameQuery;

/**
 * Encapsulates a (Steam) Game.
 * @author fuero
 */
@Entity
public class Game {
	/**
	 * The name to be read from Steam
	 */
	@Column
	private String name;
	
	/**
	 * The id to be read from Steam
	 */
	@Id
	private long steamId;
	
	@Transient
	@Autowired
	private SteamGameQuery query;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSteamId() {
		return steamId;
	}
	public void setSteamId(long steamId) {
		this.steamId = steamId;
	}
	
	public Game() {
	}
	
	public Game(long steamId, String name) {
		super();
		this.steamId = steamId;
		this.name = name;
	}
	public Game(long steamId) {
		this();
		Game game = query.getGame(steamId);
		this.name = game.name;
		this.steamId = game.steamId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((query == null) ? 0 : query.hashCode());
		result = prime * result + (int) (steamId ^ (steamId >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
			return false;
		if (steamId != other.steamId)
			return false;
		return true;
	}
}
