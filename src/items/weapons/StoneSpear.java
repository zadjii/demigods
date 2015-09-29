package items.weapons;

import items.Item;
import util.Constants;
import conditions.etc.Cooldown;


public class StoneSpear extends Item {

	private static final long serialVersionUID = 1L;

	public StoneSpear() {
		super();
		this.setUseSpeed(Constants.SPEAR_SPEED);
		setTooltip("A simple stick with a sharp rock");
		this.range = 3;
		this.setDamage(14);
		setWeight(2);
		setIsWeapon(true);
		setImageID(4, 18);
		this.name = "Stone Spear";
	}



}