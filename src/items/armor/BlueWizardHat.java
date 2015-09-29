package items.armor;

import entities.characters.*;
import entities.characters.Character;
import org.newdawn.slick.SpriteSheet;
import util.Images;

public class BlueWizardHat extends Armor {

	private static final double ABILITY = 15;
	private static final double MANAREGEN = .05;


	protected void init(){
		super.init();
		this.setHat(true);
		this.name = "Blue Wizard's Hat";
	}

	public void equip(entities.characters.Character user){
		super.equip(user);
		user.modifyFlatBonus(Character.ABILITY, ABILITY);
		user.modifyFlatBonus(Character.MPREGEN, MANAREGEN);
	}
	public void unequip(Character user){
		super.unequip(user);
		user.modifyFlatBonus(Character.ABILITY, -ABILITY);
		user.modifyFlatBonus(Character.MPREGEN, -MANAREGEN);
	}

	public SpriteSheet getArmorSheet(){
		return Images.blueWizardArmorSheet;
	}
}
