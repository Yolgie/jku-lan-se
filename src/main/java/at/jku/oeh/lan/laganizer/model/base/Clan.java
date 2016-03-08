package at.jku.oeh.lan.laganizer.model.base;

import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Clan implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany
    @CollectionTable
    @NotEmpty
    private Set<User> users = new HashSet<>();

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<User> getUsers() {
        return users;
    }


    void setName(String name) {
        this.name = name;
    }

    void addUser(User member) {
        this.users.add(member);
    }

    void removeUser(User member) {
        this.users.remove(member);
    }

    void setUsers(Set<User> users) {
        this.users = users;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Clan '");
        sb.append(name);
        sb.append("' (ID: ");
        sb.append(id);
        sb.append(") with ");
        sb.append(users.size());
        sb.append(" Users: {");
        boolean first = true;
        for (User user : users) {
            sb.append(user.toString());
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
