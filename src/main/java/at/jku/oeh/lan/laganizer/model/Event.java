package at.jku.oeh.lan.laganizer.model;

import at.jku.oeh.lan.laganizer.model.actionlog.EventLog;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String name;

    @NotNull
    private User eventManager;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Calendar startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar endTime;

    private String description;

    @NotNull
    private boolean enabled = false;

    @NotNull
    private boolean started = false;

    @NotNull
    private boolean finished = false;

    @OneToMany
    @NotNull
    private List<EventLog> eventLog = new ArrayList<>();

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

    public User getEventManager() {
        return eventManager;
    }

    public void setEventManager(User eventManager) {
        this.eventManager = eventManager;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
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

    public void setEventLog(List<EventLog> eventLog) {
        this.eventLog = eventLog;
    }

    public void addEventLogEntry(EventLog eventLog) {
        this.eventLog.add(eventLog);
    }
}
