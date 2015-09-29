package items.weapons;

import items.Item;
import util.Constants;

public class BoneSword extends Item {

	private static final long serialVersionUID = 1L;

	public BoneSword() {
		super();
		setTooltip("Better than a stick!");
		this.setDamage(14);
		setWeight(Constants.IRON_TOOL_WEIGHT);
		setIsWeapon(true);
		setImageID(3, 13);
		this.name = "Bone Sword";
	}


}