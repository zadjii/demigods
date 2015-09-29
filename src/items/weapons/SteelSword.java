package items.weapons;


import java.awt.Image;

import javax.swing.ImageIcon;

import items.Item;
import util.Constants;

public class SteelSword extends Item {

	public SteelSword() {
		super();
		setTooltip("A serious weapon for a serious warrior.");
		this.setDamage(28);
		setWeight(Constants.STEEL_TOOL_WEIGHT);
		setIsWeapon(true);
		setImageID(3, 15);
		this.name = "Steel Sword";
	}



}