package areas.eventQueue;

import java.io.Serializable;

public class Event implements Serializable {
    protected int duration = 0;//the length in ticks of the event
    protected int progress = 0;//the number of ticks elapsed for this event.
    protected int startTime;

    public Event(double startTime, double duration) {
        this.startTime = (int)startTime;
        this.duration = (int)duration;
    }

    public Event tick(int time) {
        if (time < startTime) return null;
        progress++;
        return progress > duration ? this : null;
    }

    public boolean isCleared() {
        return false;
    }
}
