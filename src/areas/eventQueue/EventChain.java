package areas.eventQueue;

import entities.characters.GreenSlime;
import entities.characters.Orc;
import entities.characters.RedSlime;
import entities.characters.personas.AStarSimpleMob;
import util.Constants;

import static areas.eventQueue.EventQueue.TPM;

import java.util.ArrayList;

/**
 * A series of waves that are dependent on the completion of the previous event.
 */
public class EventChain extends Event {

	//package access yo VVVVVVVV
    ArrayList<Event> subEvents = new ArrayList<Event>();

    private int internalTime = 0;

    public EventChain(double startTime, double duration) {
        super(startTime, duration);
    }

    public Event tick(int time) {
        if (time < startTime) return null;
        progress++;
        Event event = subEvents.get(0).tick(internalTime);
        internalTime++;
        if (event != null) {
            internalTime = 0;
            subEvents.remove(0);
        }
        return subEvents.size() == 0 ? this : null;
    }

    public static EventChain newTimingTest() {
        EventChain chain = new EventChain(0, 0);
        chain.subEvents.add(new WaveEvent(0, 1, new AStarSimpleMob(), new GreenSlime(), 1, 1));
        chain.subEvents.add(new TimingBufferEvent(0, 10 * Constants.TICKS_PER_SECOND));
        chain.subEvents.add(new WaveEvent(0, 1, new AStarSimpleMob(), new RedSlime(), 1, 1));
        chain.subEvents.add(new TimingBufferEvent(0, 10 * Constants.TICKS_PER_SECOND));
        chain.subEvents.add(new WaveEvent(0, 1, new AStarSimpleMob(), new GreenSlime(), 1, 1));
        chain.subEvents.add(new TimingBufferEvent(0, 10 * Constants.TICKS_PER_SECOND));
        chain.subEvents.add(new WaveEvent(0, 1, new AStarSimpleMob(), new Orc(), 1, 1));
        return chain;
    }

    public static EventChain newPlainsTest() {
        EventChain chain = new EventChain(0, 0);
        chain.subEvents.add(new WaveEvent(0, .5 * TPM, new AStarSimpleMob(), new GreenSlime(), 20, 1));
        chain.subEvents.add(new TimingBufferEvent(0, 5 * Constants.TICKS_PER_SECOND));
        chain.subEvents.add(new WaveEvent(0, .5 * TPM, new AStarSimpleMob(), new RedSlime(), 10, 1));
        chain.subEvents.add(new TimingBufferEvent(0, 5 * Constants.TICKS_PER_SECOND));
        chain.subEvents.add(new WaveEvent(0, .5 * TPM, new AStarSimpleMob(), new GreenSlime(), 15, 1));
        chain.subEvents.add(new TimingBufferEvent(0, 5 * Constants.TICKS_PER_SECOND));
        chain.subEvents.add(new WaveEvent(0, .1 * TPM, new AStarSimpleMob(), new Orc(), 1, 1));
        return chain;
    }
}
