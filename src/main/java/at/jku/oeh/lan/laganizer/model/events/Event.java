package at.jku.oeh.lan.laganizer.model.events;

import at.jku.oeh.lan.laganizer.model.actionlog.EventLog;
import at.jku.oeh.lan.laganizer.model.base.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue
    protected long id;

    @NotNull
    protected String name;

    @NotNull
    @ManyToOne
    protected User eventManager;

    @NotNull
    protected Instant startTime;

    protected Instant endTime;

    protected String description;

    @NotNull
    protected boolean enabled = false;

    @NotNull
    protected boolean started = false;

    @NotNull
    protected boolean finished = false;

    @OneToMany
    @NotNull
    protected List<EventLog> eventLog = new ArrayList<>();

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getEventManager() {
        return eventManager;
    }

    public void setEventManager(User eventManager) {
        this.eventManager = eventManager;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public List<EventLog> getEventLog() {
        return eventLog;
    }

    public void addEventLogEntry(EventLog eventLog) {
        this.eventLog.add(eventLog);
    }
}
