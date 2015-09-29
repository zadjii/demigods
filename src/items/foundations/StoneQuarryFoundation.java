package items.foundations;

import org.lwjgl.util.Rectangle;

import entities.buildings.*;

public class StoneQuarryFoundation extends Foundation {

	private static final long serialVersionUID = 1L;

	public StoneQuarryFoundation() {
		super();
		this.name = "Stone Quarry Fdtn.";
		setImageID(15, 0);
	}

	public Building build(int gx, int gy) {
		return new StoneQuarry(gx, gy);
	}

	public Rectangle getPotentialBounds(int gx, int gy) {
		return new Rectangle(gx, gy, 9, 7);
	}

	// ///////
	//public Interior getInterior(int seed, Entrance entrance) {
	//	throw new UncheckedDemigodsException("A Stone Quarry doesn't have an interior!");
	//}

	// //////

}
