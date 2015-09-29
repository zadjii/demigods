package items.foundations;

import org.lwjgl.util.Rectangle;

import util.Constants;

import entities.buildings.*;

public class VillageCenterFoundation extends Foundation {

	private static final long serialVersionUID = 1L;

	public VillageCenterFoundation() {
		super();
		this.name = "Village Center Fdtn.";
		setImageID(19, 1);
	}

	public Building build(int gx, int gy) {
		return new VillageCenter(gx, gy);
	}

	public Rectangle getPotentialBounds(int gx, int gy) {
		return new Rectangle(gx, gy, Constants.BU*2, Constants.BU*2);
	}

	// ///////
	//public Interior getInterior(int seed, Entrance entrance) {
	//	throw new UncheckedDemigodsException("A Village Center doesn't have an interior! ... Maybe it should...");
	//}

	// //////

}
