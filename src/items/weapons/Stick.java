package items.weapons;

import items.Item;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Stick extends Item {

	private static final long serialVersionUID = 1L;

	public Stick() {
		super();
		setTooltip("Just an ordinary stick, good for hitting things");
		this.setDamage(6);
		setWeight(1);
		setIsWeapon(true);
		recipeValue = 3;
		setImageID(0, 19);
		this.name = "Stick";
	}



}