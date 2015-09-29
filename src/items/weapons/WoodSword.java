package items.weapons;

import items.Item;

import java.awt.Image;

import javax.swing.ImageIcon;

public class WoodSword extends Item {

	public WoodSword() {
		super();
		setTooltip("No sword is more legendary than the wooden sword!");
		this.setDamage(50);
		setWeight(1);
		setIsWeapon(true);
		setImageID(3, 19);

		this.name = "Wood Sword";
	}


}