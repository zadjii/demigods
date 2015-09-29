package areas.eventQueue;

import entities.characters.Character;
import entities.characters.personas.Persona;
import game.Engine;
import areas.NewGameArea;
import org.lwjgl.util.Point;

import java.util.ArrayList;

/**
 * A wave of a single type of enemies.
 * <p/>
 * Specialization of this type of event could be made to only spawn
 * in a specific area of the area of the area, but that would be done in a
 * sub class.
 */
public class WaveEvent extends Event {
    protected Character mob = null;
    protected Persona ai = null;
    protected int level = 1;
    protected ArrayList<Persona> mobs = new ArrayList<Persona>();//all the mobs this wave has spawned
    protected int quantity = 0;
    protected int limit;

    public WaveEvent(double startTime, double duration, Persona persona, Character character, int limit, int level) {
        super(startTime, duration);
        this.ai = persona;
        this.mob = character;
        if (level > 1) this.level = level;
        this.limit = limit;
    }

    /**
     * @param time The current time of the eventqueue calling this
     * @return the event if it is all done. Doesn't return till all of it's spawned mobs are dead
     */
    public Event tick(int time) {
        //if the event hasn't started, do nothing.
        if (time < startTime) return null;
        super.tick(time);
        //Remove all of the mobs that are dead from this list.
        ArrayList<Persona> temp = new ArrayList<Persona>();
        for (Persona p : mobs) {
            if (p.getCharacter() != null) {
                if (p.getCharacter().getHP() <= 0) temp.add(p);
            }
        }
        for (Persona p : temp) {
            mobs.remove(p);
        }
        //determine if spawn, and spawn enemies
        if ((progress <= duration) && (progress % (duration / limit) == 0)) {
            Persona spawned = spawnMobRandomly(ai, mob, level);
            if (spawned != null) {
                quantity++;
                mobs.add(spawned);
            }
        }
        if (time > startTime + duration && mobs.size() == 0) return this;
        return null;
    }

    public static Persona spawnMobRandomly(Persona ai, Character mob, int level) {
        try {
            Persona persona = ai.getClass().newInstance();
            Character character = mob.getClass().newInstance();
            persona.setCharacter(character);
            Point point = getSpawnLoc(Engine.getActiveArea());
            if (point != null) {
                character.setLoc(point);
                while (character.getLvl() < level) {
                    character.addXP(10 * level);
                }
                Engine.getActiveArea().getPersonas().add(persona);
                return persona;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Point getSpawnLoc(NewGameArea area) {
        int attempts = area.getSize() * area.getSize();
        attempts /= 8;
        int radius = area.getSize() / 4;
        for (int i = attempts; i > 0; i--) {
            int gx = (int)(Math.random() * radius * 2 )+ (area.getSize() / 4);
            int gy = (int)(Math.random() * radius * 2 )+ (area.getSize() / 4);
            if (area.getPassable()[gx][gy]) {
                return new Point(gx * 16, gy * 16);
            }
        }
        return null;
    }

    public boolean isCleared() {
        return false;
    }
}



