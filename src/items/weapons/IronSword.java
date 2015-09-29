package items.weapons;

import items.Item;
import util.Constants;

public class IronSword extends Item {

	private static final long serialVersionUID = 1L;

	public IronSword() {
		super();
		this.setDamage(23);
		setWeight(Constants.IRON_TOOL_WEIGHT);
		setIsWeapon(true);
		setImageID(3, 16);
		this.name = "Iron Sword";
	}

}