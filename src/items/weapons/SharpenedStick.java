package items.weapons;

import items.Item;
import util.Constants;

public class SharpenedStick extends Item {

	private static final long serialVersionUID = 1L;

	public SharpenedStick() {
		super();
		this.setDamage(13);
		setWeight(Constants.WOOD_TOOL_WEIGHT);
		setIsWeapon(true);
		setImageID(3, 17);
		this.name = "Sharpened Stick";
	}


}