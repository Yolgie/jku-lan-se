package at.jku.oeh.lan.laganizer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
public class Tournament implements Serializable{
	@Id
	@GeneratedValue
	private long id;
	
	@NotNull
	private int teamSize;
}
