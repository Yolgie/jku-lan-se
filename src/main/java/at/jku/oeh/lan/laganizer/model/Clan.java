package at.jku.oeh.lan.laganizer.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Clan implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @UniqueConstraint()
    private String name;

    private Set<User> members;

    public Clan() {
        members = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(HashSet<User> members) {
        this.members = members;
    }
}
