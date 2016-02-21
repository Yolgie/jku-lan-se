package at.jku.oeh.lan.laganizer.services;

import at.jku.oeh.lan.laganizer.model.Event;
import at.jku.oeh.lan.laganizer.model.EventDAO;
import at.jku.oeh.lan.laganizer.model.User;
import at.jku.oeh.lan.laganizer.model.actionlog.EventAction;
import at.jku.oeh.lan.laganizer.model.actionlog.EventLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class EventService {

    @Autowired
    private EventDAO eventDAO;

    public void startEvent(Event event, User reporter) {
        EventLog eventLog = new EventLog();
        eventLog.setReporter(reporter);
        eventLog.setAction(EventAction.STARTED);
        eventLog.setTimestamp(Calendar.getInstance());
        event.addEventLogEntry(eventLog);
        eventDAO.save(event);
    }

    public void finishEvent(Event event, User reporter) {}
    public void enableEvent(Event event, User reporter) {}
    public void disableEvent(Event event, User reporter) {}
    public void toggleEvent(Event event, User reporter) {}
    public void createEvent(Event event, User reporter) {}
}
