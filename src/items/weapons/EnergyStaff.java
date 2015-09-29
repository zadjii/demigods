package items.weapons;


import effects.ArrowEffect;
import effects.Effect;
import effects.EnergyBoltEffect;
import effects.ThrowingKnifeEffect;
import entities.particles.Particle;
import game.Engine;
import org.lwjgl.util.Point;
import util.animation.Animation;

public class EnergyStaff extends RangedWeapon {

	protected static final double MAX_SPEED = 15;
	protected void init(){
		super.init();
		this.damage = 5;
		this.maxCharge = 1;
		setWeight(1);
		setIsWeapon(true);
		setImageID(0, 1);
		this.name = "Energy Staff";
	}
	public void fire() {
		Engine.add(new EnergyBoltEffect(user, target, getDamage(), 5, 200));
//EnergyBoltEffect(Character caster, Point target, double damage, double speed, double range)
		this.user.setAnimation(
				Animation.getRotatedBow(
						user.getLoc(),target, 15, user.getImageXOffset(), user.getImageYOffset()
				)
		);


	}
	public Effect attack(Point point){
		return super.attack(point);

	}
}
