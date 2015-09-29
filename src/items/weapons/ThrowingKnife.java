package items.weapons;


import effects.Effect;
import effects.ThrowingKnifeEffect;
import game.Engine;
import org.lwjgl.util.Point;

public class ThrowingKnife extends RangedWeapon {

	protected void init(){
		super.init();
		this.maxDamage = 3;
		this.maxCharge = 1;
		setWeight(.1);
		setIsWeapon(true);
		setImageID(1, 1);
		this.name = "Throwing Knife";
	}
	public void fire() {
		Engine.add(new ThrowingKnifeEffect(user, target, (int)maxDamage));
		user.getInventory().removeItem(this);
	}
	public Effect attack(Point point){
		return super.attack(point);
	}
}
