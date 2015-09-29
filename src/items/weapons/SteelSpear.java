package items.weapons;

import items.Item;
import util.Constants;
import conditions.etc.Cooldown;


public class SteelSpear extends Item {

	private static final long serialVersionUID = 1L;

	public SteelSpear() {
		super();
		this.setUseSpeed(Constants.SPEAR_SPEED);
		this.range = 3;
		this.setDamage(29);
		setWeight(2);
		setIsWeapon(true);
		setImageID(4, 15);
		this.name = "Steel Spear";
	}


}