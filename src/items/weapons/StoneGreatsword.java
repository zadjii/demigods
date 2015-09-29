package items.weapons;

import conditions.etc.Cooldown;
import items.Item;
import util.Constants;

public class StoneGreatsword extends Item {

	private static final long serialVersionUID = 1L;

	public StoneGreatsword() {
		super();

		this.setUseSpeed(Constants.GREATSWORD_SPEED);
		setTooltip("Because having a regular stone sword didn't make sense.");
		this.setDamage(25);
		range = 2;
		setWeight(Constants.STONE_TOOL_WEIGHT + 2);
		setIsWeapon(true);
		setImageID(6, 18);
		this.name = "Stone Greatsword";
	}

}