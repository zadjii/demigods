package items.materials;

import items.Item;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Stone extends Item {

	private static final long serialVersionUID = 1L;

	public Stone() {
		super();
		setWeight(.5);
		recipeValue = 2;
		setImageID(2, 0);
		this.name = "Stone";
	}

}