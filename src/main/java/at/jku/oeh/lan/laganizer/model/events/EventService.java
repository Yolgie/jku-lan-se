package at.jku.oeh.lan.laganizer.model.events;

import at.jku.oeh.lan.laganizer.model.events.Event;
import at.jku.oeh.lan.laganizer.model.events.EventDAO;
import at.jku.oeh.lan.laganizer.model.base.User;
import at.jku.oeh.lan.laganizer.model.actionlog.EventAction;
import at.jku.oeh.lan.laganizer.model.actionlog.EventLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventDAO eventDAO;

    public void startEvent(Event event, User reporter) {
        EventLog eventLog = new EventLog();
        eventLog.setCreatedBy(reporter);
        eventLog.setAction(EventAction.STARTED);
        event.addEventLogEntry(eventLog);
        eventDAO.save(event);
    }

    public void finishEvent(Event event, User reporter) {
    }

    public void enableEvent(Event event, User reporter) {
    }

    public void disableEvent(Event event, User reporter) {
    }

    public void toggleEvent(Event event, User reporter) {
    }

    public void createEvent(Event event, User reporter) {
    }
}
