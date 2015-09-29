package items.materials;

import items.Item;

public class BronzeBar extends Item {

	private static final long serialVersionUID = 1L;

	public BronzeBar() {
		super();
		setWeight(1);
		recipeValue = 2;
		setImageID(4, 5);
		this.name = "Bronze Bar";
	}


}