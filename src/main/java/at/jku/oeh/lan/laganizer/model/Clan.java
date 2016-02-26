package at.jku.oeh.lan.laganizer.model;

import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;

@Entity
public class Clan implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToOne
    @CollectionTable
    @NotEmpty
    private List<User> members;

    public Clan() {
        members = new LinkedList<User>();
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

    public int countMembers() {
        return members.size();
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public void addUser(User user) {
        if (user != null) {
            members.add(user);
        }
    }

    public void removeUser(User user) {
        if (user != null) {
            members.remove(user);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Group: " + name);
        sb.append("Members: (" + members.size() + ")");
        for (User user : members) {
            sb.append("\n\t" + user);
        }
        return sb.toString();
    }
}
