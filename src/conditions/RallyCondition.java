package conditions;

import entities.characters.Character;
import entities.particles.Particle;
import game.Engine;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 10/15/12
 * Time: 2:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class RallyCondition extends Condition {



    public RallyCondition(Character src, Character tgt, int duration) {
        super(src, tgt, duration);
    }

    public boolean isOff() {
        return duration.offCooldown();
    }

    public boolean isVisible() {
        return true;
    }

    public void tick() {
//	    System.out.println("Rally: " + duration.getRemaining());

	    if (src != null && src.getHP() <= 0) src = null;
        if (tgt.getHP() <= 0) tgt = null;
	    this.duration.tick();
    }

    public void addDuration() {
        this.duration.addDuration();
        duration.reset();
    }

    public boolean isOnHit() {
        return true;
    }

    public int applyOnHit(Character src, Character tgt) {

	    if(src != null){
		    src.heal(5.0);
//		    System.out.println("HEALED from Rally " + src.getHP());
		    Engine.add(Particle.newHealTextParticle(src, 5));
	    }
	    return 0;
    }

    public String toString() {
        return "Rally";
    }
}
