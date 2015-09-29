package areas.eventQueue;

/**
 * An event that exists for a specific duration, then returns itself.
 * actually, just a plain old event will do this, but whatever.
 */
public class TimingBufferEvent extends Event {

    public TimingBufferEvent(double startTime, double duration) {
        super(startTime, duration);
    }
}
