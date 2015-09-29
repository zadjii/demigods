package items.weapons;

import conditions.etc.Cooldown;
import items.Item;
import util.Constants;

public class IronGreatsword extends Item {

	private static final long serialVersionUID = 1L;

	public IronGreatsword() {
		super();

//		useTime = new Cooldown(Constants.GREATSWORD_SPEED);
		this.setUseSpeed(Constants.GREATSWORD_SPEED);

		this.setDamage(25);
		range = 2;
		setWeight(Constants.IRON_TOOL_WEIGHT + 2);
		setIsWeapon(true);
		setImageID(6, 16);
		this.name = "Iron Greatsword";
	}

}