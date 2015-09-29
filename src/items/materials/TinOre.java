package items.materials;

import items.Item;

public class TinOre extends Item {

	private static final long serialVersionUID = 1L;

	public TinOre() {
		super();
		setWeight(2);
		recipeValue = 2;
		setImageID(6, 0);
		this.name = "Tin Ore";
	}

}