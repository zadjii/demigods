package items.weapons;

import items.Item;
import util.Constants;

public class LargeSharpStick extends Item {

	private static final long serialVersionUID = 1L;

	public LargeSharpStick() {
		super();

		this.setUseSpeed(Constants.GREATSWORD_SPEED);
		setTooltip("A large, sharp stick.");
		this.setDamage(15);
		range = Constants.GREATSWORD_RANGE;
		setWeight(Constants.WOOD_TOOL_WEIGHT + 2);
		setIsWeapon(true);
		setImageID(6, 18);
		this.name = "Large Sharp Stick";
	}

}