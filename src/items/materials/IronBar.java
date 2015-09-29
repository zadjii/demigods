package items.materials;

import items.Item;

public class IronBar extends Item {

	private static final long serialVersionUID = 1L;

	public IronBar() {
		super();
		setWeight(1);
		recipeValue = 2;
		setImageID(4, 2);
		this.name = "Iron Bar";
	}

}