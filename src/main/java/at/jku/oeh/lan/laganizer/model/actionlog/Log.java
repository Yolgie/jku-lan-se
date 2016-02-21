package at.jku.oeh.lan.laganizer.model.actionlog;

import at.jku.oeh.lan.laganizer.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;

@Entity
public class Log <T extends LogAction> implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private T action;

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

    public T getAction() {
        return action;
    }

    public void setAction(T action) {
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
}
