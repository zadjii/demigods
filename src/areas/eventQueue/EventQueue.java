package areas.eventQueue;

import entities.characters.*;
import entities.characters.personas.*;
import util.Constants;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The EQ is a type of object that handles the order of events in an area when
 * a player enters the area. The primary function of the EQ is to spawn
 * enemies for the player. Actually, that is about all of it's function.
 * <p/>
 * Actually not really a queue...
 */
public class EventQueue implements Serializable {
    /**
     * Ticks per Minute.
     */
    public static final int TPM = Constants.TICKS_PER_SECOND * 60;

    private int time = 0;
    private boolean clearable = true;

	//package access yo VVVVVVVV
    ArrayList<Event> events = new ArrayList<Event>();

    private ArrayList<Event> temp = new ArrayList<Event>();

    public void tick() {
        //This ensures there is no overflow, which shouldn't happen for 1.8e26 millennia, but just in case!
        //In retrospect (after doing the math) even a short could be used to represent 828 days of values.
        if (time < Float.MAX_VALUE - 1) time++;
        for (Event event : events) {
            temp.add(event.tick(time));
        }
        for (Event event : temp) events.remove(event);
        temp.clear();
    }

    public static EventQueue newLvlZeroPlains() {
        EventQueue events = new EventQueue();
        events.events.add(new WaveEvent(.2 * TPM, .5 * TPM, new SimpleMob(), new GreenSlime(), 15, 1));
        events.events.add(new WaveEvent(1.2 * TPM, .5 * TPM, new SimpleMob(), new RedSlime(), 15, 2));
        events.events.add(new WaveEvent(2.2 * TPM, .5 * TPM, new SimpleMob(), new FloatingTorso(), 15, 3));
        return events;
    }

    public static EventQueue newClearingTester() {
        EventQueue events = new EventQueue();
        events.events.add(new WaveEvent(.01 * TPM, .2 * TPM, new DervishAI(), new SandDervish(), 1, 1));
        return events;
    }

    public static EventQueue newChainedEventTester() {
        EventQueue events = new EventQueue();
        events.events.add(EventChain.newTimingTest());
        return events;
    }

    public static EventQueue newChainedPlainsTester() {
        EventQueue events = new EventQueue();
        events.events.add(EventChain.newPlainsTest());
//	    events.events.add(new WaveEvent(.5*TPM, .5 * TPM, new AStarSimpleMob(), new Orc(), 10, 5));
	    events.events.add(new WaveEvent(.5*TPM, .5 * TPM, new SimpleMob(), new FloatingTorso(), 10, 3));
	    events.events.add(new WaveEvent(.5*TPM, .5 * TPM, new SimpleCasterAI(), new ThrowAcornSquirrel(), 120, 5));
        return events;
    }

    public static EventQueue newDynamicEvents() {
        EventQueue events = new EventQueue();
        events.events.add(EventChain.newPlainsTest());
        return events;
    }

    public boolean isCleared() {
        if (!clearable) return false;
        for (Event event : events) {
            if (!event.isCleared()) return false;
        }
        return true;
    }
}
