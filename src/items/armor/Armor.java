package items.armor;

import items.Item;
import entities.characters.Character;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import util.Images;

public class Armor extends Item {


	public static final int HAT   = 0;
	public static final int CHEST = 1;
	public static final int PANTS = 2;

	public Image getLeft(int armorPiece, int step){
		return getArmorSheet().getSprite(4*armorPiece + step, 0);
	}
	public Image getRight(int armorPiece, int step){
		return getArmorSheet().getSprite(4*armorPiece + step, 1);
	}
	public Image getDown(int armorPiece, int step){
		return getArmorSheet().getSprite(4*armorPiece + step, 2);
	}
	public Image getUp(int armorPiece, int step){
		return getArmorSheet().getSprite(4*armorPiece + step, 3);
	}
	public Image getImage(int armorPiece, int step, int direction){
		return this.getArmorSheet().getSprite(4* armorPiece + step, direction);
	}

	public Armor(){
		init();
	}
	protected void init(){
		this.setArmor(true);
	}
	public void equip(Character user){
		super.equip(user);
		if(this.isHat()){
			if(user.getArmorArray()[HAT] != null)user.getArmorArray()[HAT].unequip(user);
			user.getArmorArray()[HAT] = this;
		}
		else if(this.isChest()){
			if(user.getArmorArray()[CHEST] != null)user.getArmorArray()[CHEST].unequip(user);
			user.getArmorArray()[CHEST] = this;
		}
		else if(this.isLeggings()){
			if(user.getArmorArray()[PANTS] != null)user.getArmorArray()[PANTS].unequip(user);
			user.getArmorArray()[PANTS] = this;
		}
	}
	public void unequip(Character user){
		super.unequip(user);
		if(this.isHat()){
			user.getArmorArray()[HAT] = null;
		}
		else if(this.isChest()){
			user.getArmorArray()[CHEST] = null;
		}
		else if(this.isLeggings()){
			user.getArmorArray()[PANTS] = null;
		}
	}
	public SpriteSheet getArmorSheet(){
		return Images.ironArmorSheet;
	}

}
