package items.weapons;

import items.Item;
import util.Constants;
import conditions.etc.Cooldown;


public class IronSpear extends Item {

	private static final long serialVersionUID = 1L;

	public IronSpear() {
		super();
		this.setUseSpeed(Constants.SPEAR_SPEED);
		setTooltip("A simple stick with a sharp rock");
		this.range = 3;
		this.setDamage(24);
		setWeight(2);
		setIsWeapon(true);
		setImageID(4, 16);
		this.name = "Iron Spear";
	}



}