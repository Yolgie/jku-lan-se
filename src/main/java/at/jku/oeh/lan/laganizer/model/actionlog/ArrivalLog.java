package at.jku.oeh.lan.laganizer.model.actionlog;

import at.jku.oeh.lan.laganizer.model.base.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class ArrivalLog extends Log<ArrivalAction> implements Serializable {
    @NotNull
    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
