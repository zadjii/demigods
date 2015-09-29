package conditions;

import java.io.Serializable;

import conditions.etc.Cooldown;
import entities.characters.Character;
import org.lwjgl.util.Point;
import org.newdawn.slick.Graphics;

public class Condition implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Character src;
    protected Character tgt;
    protected Cooldown duration;
    protected boolean isIndefinite = false;
    protected boolean refreshDuration = true;

    public Condition(Character src, Character tgt, int duration) {
        this.src = src;
        this.tgt = tgt;
        this.duration = new Cooldown(duration);
        this.duration.reset();
    }

    public void tick() {
        this.duration.tick();
        if (src != null && src.getHP() <= 0) src = null;
        if (tgt.getHP() <= 0) tgt = null;
    }

    public boolean isOff() {
        return this.duration.offCooldown();
    }

    public void turnOff() {
    }

    public Point getImageLoc() {
        return new Point(0, 0);
    }

    public boolean isVisible() {
        return true;
    }

    public boolean refreshesDuration() {
        return refreshDuration;
    }

    public void refreshDuration() {
        duration.reset();
    }

    public void addDuration() {
        this.duration.addDuration();
    }

    public boolean isOnHit() {
        return false;
    }

    public int applyOnHit(Character src, Character tgt) {
        return 0;
    }

    public void applyLevelUp(Character src) {
    }

    public void resetDuration() {
        this.duration.reset();
    }

    public String getTooltip() {
        return "Conditions will have descriptive tooltips!";
    }

    public String toString() {
        return "Condition";
    }

    public boolean isDrawn() {
        return false;
    }

    public void draw(float sx, float sy, float zoom, Graphics g) {
    }

    public Character getSrc() {
        return src;
    }

    public int hashCode() {
        return this.toString().hashCode();
    }
}
