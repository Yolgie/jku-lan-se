package at.jku.oeh.lan.laganizer.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Team {
	@Id
	@GeneratedValue
	private long id;
	
	@NotNull
	private List<User> players;
	
	@NotNull
	private Tournament tournament;
}
