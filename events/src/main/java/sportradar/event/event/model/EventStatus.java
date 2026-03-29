package sportradar.event.event.model;

public enum EventStatus {
    PLAYED, SCHEDULED, MOVED, CANCELLED;

    public boolean canTransitionTo(EventStatus nextStatus) {
        if (this == nextStatus) {
            return true;
        }

        return this != PLAYED && this != CANCELLED;
    }
}
