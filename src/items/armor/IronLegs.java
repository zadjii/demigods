package items.armor;

import entities.characters.Character;
import org.newdawn.slick.SpriteSheet;
import util.Images;

public class IronLegs extends Armor {

	private static final double ARMOR = 10;

	protected void init(){
		super.init();
		this.setLeggings(true);
		this.name = "Iron Legs";
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
		return Images.ironArmorSheet;
	}
}
