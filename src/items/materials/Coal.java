package items.materials;

import items.Item;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Coal extends Item {

	private static final long serialVersionUID = 1L;

	public Coal() {
		super();
		setWeight(.2);
		recipeValue = 2;
		setImageID(3, 0);
		this.name = "Coal";
	}

}