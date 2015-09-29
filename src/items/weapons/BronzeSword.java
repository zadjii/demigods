package items.weapons;

import items.Item;
import util.Constants;

public class BronzeSword extends Item {

	private static final long serialVersionUID = 1L;

	public BronzeSword() {
		super();
		this.setDamage(18);
		setWeight(Constants.BRONZE_TOOL_WEIGHT);
		setIsWeapon(true);
		setImageID(3, 17);
		this.name = "Bronze Sword";
	}


}