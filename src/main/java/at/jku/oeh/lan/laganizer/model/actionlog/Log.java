package at.jku.oeh.lan.laganizer.model.actionlog;

import at.jku.oeh.lan.laganizer.model.base.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class Log<T extends LogAction> implements Serializable {
    @Id
    @GeneratedValue
    protected long id;

    @NotNull
    protected T action;

    @NotNull
    @CreatedDate
    protected Instant createdDate;

    //ToDo change to @CreatedBy
    // http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#auditing
    @NotNull
    @ManyToOne
    protected User createdBy;

    protected String note;

    public long getId() {
        return id;
    }

    public T getAction() {
        return action;
    }

    public void setAction(T action) {
        this.action = action;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
