package items.armor;

import entities.characters.*;
import entities.characters.Character;
import org.newdawn.slick.SpriteSheet;
import util.Images;

public class FarmerHat extends Armor {



	protected void init(){
		super.init();
		this.setHat(true);
		this.name = "Farmer's Hat";
	}

	public void equip(entities.characters.Character user){
		super.equip(user);
	}
	public void unequip(Character user){
		super.unequip(user);
	}

	public SpriteSheet getArmorSheet(){
		return Images.farmerArmorSheet;
	}
}
