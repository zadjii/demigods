package items.weapons;

import conditions.etc.Cooldown;
import effects.Effect;
import entities.characters.*;
import items.Item;
import org.lwjgl.util.Point;

public abstract class RangedWeapon extends Item {

	protected int maxCharge;
	protected int charge;
	protected int overcharge = 0;
	protected int maxDamage;
	protected double accuracy;
	protected boolean charging = false;
	protected boolean charged = false;
	protected Point target;
	public RangedWeapon(){
		init();
	}
	protected void init(){
		this.setUseSpeed(0);

//		useTime = new Cooldown(0);
	}
	protected void setUseTime(Cooldown useTime){
		super.setUseTime(new Cooldown(0));
	}
	public void tick(){
		super.tick();
		if(charging){
			if(this.isOffCooldown())charging = false;
			charged = true;
			//if(charge < maxCharge)charge++;
		}
		else{
			if(charged){
				fire();
				charged = false;
			}
			charge = 0;
			overcharge = 0;

		}
	}
	@Override
	public Effect attack(Point point){
		if(charge < maxCharge){
			charge++;
		}
		else{
			overcharge++;
		}
		charging = true;
		charged = true;
		target = point;
		return null;
	}
	public abstract void fire();
}
