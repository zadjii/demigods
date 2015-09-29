package items.weapons;

import entities.characters.Character;
import items.OffhandItem;
import util.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 12/29/12
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmallPointyStick extends OffhandItem{
	private static final long serialVersionUID = 1L;

	private static float OFFHAND_ATTACK = 2;
	private static float OFFHAND_ATTACK_SPEED_GAIN = -0.10f;
	public SmallPointyStick() {
		super();
		this.setUseSpeed(Constants.DAGGER_SPEED);
		this.range = Constants.DAGGER_RANGE;
		this.setDamage(10);
		setWeight(2);
		setIsWeapon(true);
		setImageID(5, 15);
		this.name = "Small Pointy Stick";
	}

	@Override
	public void offhandEquip(Character user) {
		user.modifyFlatBonus(Character.ATTACK, OFFHAND_ATTACK);
		user.modifyPercentBonus(Character.ATTACK_SPEED, OFFHAND_ATTACK_SPEED_GAIN);
	}

	@Override
	public void offhandUnequip(Character user) {

		user.modifyFlatBonus(Character.ATTACK, -OFFHAND_ATTACK);
		user.modifyPercentBonus(Character.ATTACK_SPEED, -OFFHAND_ATTACK_SPEED_GAIN);
	}
}
