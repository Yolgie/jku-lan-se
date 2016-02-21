package at.jku.oeh.lan.laganizer.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue
    protected long id;

    @NotNull
    @CreatedDate
    protected Instant createdDate;

    @NotNull
    @LastModifiedDate
    protected Instant lastModifiedDate;

    public long getId() {
        return id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }
}