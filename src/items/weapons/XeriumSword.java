package items.weapons;


import java.awt.Image;

import javax.swing.ImageIcon;

import items.Item;
import util.Constants;
import util.Images;

public class XeriumSword extends Item {

	public XeriumSword() {
		super();
		setTooltip("The highest quality weapon around.");
		this.setDamage(38);
		setWeight(Constants.XERIUM_TOOL_WEIGHT);
		setIsWeapon(true);
		setImageID(3, 14);
		this.name = "Xerium Sword";
	}



}