package jkulan.software.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

@Entity
public class ArrivalLog implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    private ArrivalAction action;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timestamp;

    @NotNull
    @ManyToOne
    private User reporter;

    private String note;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrivalAction getAction() {
        return action;
    }

    public void setAction(ArrivalAction action) {
        this.action = action;
    }

    public Calendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String toString() {
        String result = getUser().getName() +
                "@" + DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(getTimestamp().toInstant()) +
                " -> " + getAction().name();
        return result;
    }
}
