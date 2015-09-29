package items.weapons;

import conditions.etc.Cooldown;
import entities.characters.*;
import entities.characters.Character;
import items.Item;
import items.OffhandItem;
import util.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 12/29/12
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class SteelDagger extends OffhandItem{
	private static final long serialVersionUID = 1L;

	private static float OFFHAND_ATTACK = 10;
	public SteelDagger() {
		super();
		this.setUseSpeed(Constants.DAGGER_SPEED);
		this.range = Constants.DAGGER_RANGE;
		this.setDamage(25);
		setWeight(2);
		setIsWeapon(true);
		setImageID(5, 15);
		this.name = "Steel Dagger";
	}

	@Override
	public void offhandEquip(entities.characters.Character user) {
		user.modifyFlatBonus(Character.ATTACK, OFFHAND_ATTACK);
	}

	@Override
	public void offhandUnequip(Character user) {
		System.out.println("unequiped sd");
		user.modifyFlatBonus(Character.ATTACK, -OFFHAND_ATTACK);
	}
}
