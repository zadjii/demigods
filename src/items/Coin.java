package items;

public class Coin extends Item {

	private static final long serialVersionUID = 1L;

	public Coin() {
		super();
		setWeight(0);
		recipeValue = 2;
		setImageID(5, 1);
		this.name = "Coins";
	}

}