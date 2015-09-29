package items.armor;

import entities.characters.*;
import entities.characters.Character;
import org.newdawn.slick.SpriteSheet;
import util.Images;

public class ChronomancerChest extends Armor {

	private static final double ARMOR = 50;
	private static final double ATTACK = 5;
	private static final double ABILITY = 5;
	private static final double MANAREGEN = .05;


	protected void init(){
		super.init();
		this.setChest(true);
		this.name = "Chronomancer Armor";
	}

	public void equip(entities.characters.Character user){
		super.equip(user);
		user.modifyFlatBonus(Character.ARMOR, ARMOR);
		user.modifyFlatBonus(Character.ATTACK, ATTACK);
		user.modifyFlatBonus(Character.ABILITY, ABILITY);
		user.modifyFlatBonus(Character.MPREGEN, MANAREGEN);
	}
	public void unequip(Character user){
		super.unequip(user);
		user.modifyFlatBonus(Character.ARMOR, -ARMOR);
		user.modifyFlatBonus(Character.ATTACK, -ATTACK);
		user.modifyFlatBonus(Character.ABILITY, -ABILITY);
		user.modifyFlatBonus(Character.MPREGEN, -MANAREGEN);
	}

	public SpriteSheet getArmorSheet(){
		return Images.chronomancerArmorSheet;
	}
}
