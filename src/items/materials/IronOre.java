package items.materials;

import items.Item;

public class IronOre extends Item {

	private static final long serialVersionUID = 1L;

	public IronOre() {
		super();
		setWeight(2);
		recipeValue = 2;
		setImageID(4, 0);
		this.name = "Iron Ore";
	}


}