package items.foundations;

import org.lwjgl.util.Rectangle;

import entities.buildings.*;

public class WoodenBarricadeFoundation extends Foundation {

	private static final long serialVersionUID = 1L;

	public WoodenBarricadeFoundation() {
		super();
		this.name = "Wooden Barricade Fdtn.";
		setImageID(16, 0);
	}

	public Building build(int gx, int gy) {
		return new WoodenBarricade(gx, gy);
	}

	public Rectangle getPotentialBounds(int gx, int gy) {
		return new Rectangle(gx, gy, 2, 2);
	}

	// ///////
	//public Interior getInterior(int seed, Entrance entrance) {
	//	throw new UncheckedDemigodsException("A Wooden Barricade doesn't have an interior!");
	//}


}
