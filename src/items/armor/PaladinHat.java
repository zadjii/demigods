package items.armor;

import entities.characters.Character;
import org.newdawn.slick.SpriteSheet;
import util.Images;

public class PaladinHat extends Armor {

	private static final double ARMOR = 10;

	protected void init(){
		super.init();
		this.setHat(true);
		this.name = "Paladin Helmet";
	}

	public void equip(Character user){
		super.equip(user);
		user.modifyFlatBonus(Character.ARMOR, ARMOR);
	}
	public void unequip(Character user){
		super.unequip(user);
		user.modifyFlatBonus(Character.ARMOR, -ARMOR);
	}

	public SpriteSheet getArmorSheet(){
		return Images.paladinArmorSheet;
	}
}
