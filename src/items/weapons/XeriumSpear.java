package items.weapons;

import items.Item;
import util.Constants;
import conditions.etc.Cooldown;


public class XeriumSpear extends Item {

	private static final long serialVersionUID = 1L;

	public XeriumSpear() {
		super();
		this.setUseSpeed(Constants.SPEAR_SPEED);
		this.range = 3;
		this.setDamage(39);
		setWeight(2);
		setIsWeapon(true);
		setImageID(4, 14);
		this.name = "Xerium Spear";
	}



}