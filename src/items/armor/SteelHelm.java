package items.armor;


import items.Item;

public class SteelHelm extends Item {

	private static final long serialVersionUID = 1L;

	public SteelHelm() {
		super();
		this.setWeight(.1);
		recipeValue = 1;
		setImageID(5, 16);
		this.setHat(true);
		this.name = "Steel Helm";
	}
}
