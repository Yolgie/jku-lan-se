package at.jku.oeh.lan.laganizer.model.actionlog;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class EventLog extends Log<EventAction> implements Serializable{

}
