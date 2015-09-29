package items.materials;

import items.Item;

public class XeriumBar extends Item {

	private static final long serialVersionUID = 1L;

	public XeriumBar() {
		super();
		setWeight(1);
		recipeValue = 2;
		setImageID(5, 5);
		this.name = "Xerium Bar";
	}


}