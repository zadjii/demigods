package items.materials;

import items.Item;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Wood extends Item {

	public Wood() {
		super();
		this.setWeight(.25);
		recipeValue = 1;
		setImageID(1, 0);
		this.name = "Wood";
	}
}
