package at.jku.oeh.lan.laganizer.model.actionlog;

public enum ArrivalAction implements LogAction {
    USER_ARRIVED,
    USER_DEPARTED,
    USER_CANCELLATION,
    USER_CANCELLATION_BY_THIRD_PARTY
}