package conditions;

import conditions.etc.Cooldown;
import effects.SandPileEffect;
import entities.characters.Character;
import entities.particles.Particle;
import game.Engine;
import org.lwjgl.util.Point;
import util.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 10/15/12
 * Time: 2:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class SandAttackCondition extends IndefiniteCondition {
	public static final String NAME = "Sand Attack";
    private int stacks = 0;
    public static final int STACK_DURATION = 10 * Constants.TICKS_PER_SECOND;

    public SandAttackCondition(Character src, Character tgt, int duration) {
        super(src, tgt, 0);
    }

    public boolean isOff() {
        return false;
    }

    public boolean isVisible() {
        return true;
    }

    public void tick() {
        super.tick();
    }

    public void addDuration() {
//        this.duration = new Cooldown(STACK_DURATION);
//        duration.reset();
//        if (stacks < 25) this.stacks++;
    }

    public boolean isOnHit() {
        return true;
    }

    public int applyOnHit(Character src, Character tgt) {
		if(src.getMP() < 1)return 0;
	    src.drainMana(1);
		float dx = (float)Math.random() * 32 + 16;
	    float dy = (float)Math.random() * 32 + 16;

	    dx *= (Math.random() > .5 ? -1 : 1);
	    dy *= (Math.random() > .5 ? -1 : 1);
	    Point newPile = new Point(
			    (int)(tgt.getX() + dx),
			    (int)(tgt.getY() + dy)
	    );

	    Engine.add(new SandPileEffect(src, newPile, 5));
	    for (int i = 0; i < 12; i++) {
		    Engine.add(Particle.newSandPileEffectDestruction(tgt.getLoc()));
	    }
        return 5;
    }

    public String toString() {
        return NAME;
    }

}
