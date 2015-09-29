package items.weapons;

import conditions.etc.Cooldown;
import entities.characters.Character;
import items.OffhandItem;
import util.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 9/28/13
 * Time: 10:58 AM
 */
public class TestShield extends OffhandItem {
	private static final long serialVersionUID = 1L;

	private static float OFFHAND_ARMOR = 10;
	public TestShield() {
		super();
//		useTime = new Cooldown(Constants.SPEAR_SPEED);
		this.range = Constants.DAGGER_RANGE;
		this.setDamage(5);
		setWeight(2);
		setIsWeapon(true);
		setImageID(5, 16);
		this.name = "Test Shield";
	}

	@Override
	public void offhandEquip(entities.characters.Character user) {
		user.modifyFlatBonus(Character.ARMOR, OFFHAND_ARMOR);
	}

	@Override
	public void offhandUnequip(Character user) {
		user.modifyFlatBonus(Character.ARMOR, -OFFHAND_ARMOR);
	}
}
