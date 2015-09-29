package items.materials;

import items.Item;

public class CopperOre extends Item {

	private static final long serialVersionUID = 1L;

	public CopperOre() {
		super();
		setWeight(2);
		recipeValue = 2;
		setImageID(4, 3);
		this.name = "Copper Ore";
	}

}