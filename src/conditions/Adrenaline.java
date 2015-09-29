package conditions;

import conditions.etc.Cooldown;
import entities.characters.Character;
import util.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 10/15/12
 * Time: 2:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Adrenaline extends IndefiniteCondition {

    private int stacks = 0;
    public static final int STACK_DURATION = 10 * Constants.TICKS_PER_SECOND;

    public Adrenaline(Character src, Character tgt, int duration) {
        super(src, tgt, 0);
    }

    public boolean isOff() {
        return false;
    }

    public boolean isVisible() {
        return stacks > 0;
    }

    public void tick() {
	    super.tick();
        if (this.stacks > 0) {
            this.duration.tick();
            if (duration.offCooldown()) {
                this.stacks = 0;
            }
        }
    }

    public void addDuration() {
        this.duration = new Cooldown(STACK_DURATION);
        duration.reset();
        if (stacks < 25) this.stacks++;
    }

    public boolean isOnHit() {
        return true;
    }

    public int applyOnHit(Character src, Character tgt) {
        return stacks;
    }

    public String toString() {
        return "Adrenaline";
    }
	public int getStacks(){return stacks;}
}
