package items.weapons;


import effects.ArrowEffect;
import effects.Effect;
import util.animation.Animation;
import game.Engine;
import entities.particles.Particle;
import org.lwjgl.util.Point;

public class WoodenBow extends RangedWeapon {

	protected static final double MAX_SPEED = 15;
	protected void init(){
		super.init();
		this.maxDamage = 15;
		this.maxCharge = 60;
		setWeight(1);
		setIsWeapon(true);
		setImageID(0, 1);
		this.name = "Wooden Bow";
	}
	public void fire() {
		if(user.getInventory().hasItem("Arrow")){
			double actualDamage = maxDamage * ((double)charge/(double)maxCharge);
			double actualSpeed = MAX_SPEED * ((double)charge/(double)maxCharge);
			//System.out.println(overcharge);
			if(charge == maxCharge && overcharge <= 10){
				Engine.add(Particle.newCritical(user));
				actualDamage *= 2.0;
			}
			Engine.add(new ArrowEffect(user, target, (actualDamage + Arrow.ARROW_DAMAGE), actualSpeed));
			user.getInventory().removeItem(new Arrow());
			this.user.setAnimation(
					Animation.getRotatedBow(
							user.getLoc(),target, 15, user.getImageXOffset(), user.getImageYOffset()
					)
			);
		}

	}
	public Effect attack(Point point){
		return super.attack(point);

	}
}
