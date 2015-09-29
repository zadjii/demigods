package items.weapons;

import items.Item;

public class Arrow extends Item {
	private static final long serialVersionUID = 1L;
	public static final int ARROW_DAMAGE = 5;
	public Arrow() {
		super();
		this.setDamage(ARROW_DAMAGE);
		setWeight(.1);
		setImageID(2, 1);
		this.name = "Arrow";
	}

}
