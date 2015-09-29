package items.foundations;

import org.lwjgl.util.Rectangle;

import util.Constants;

import entities.buildings.*;

public class HouseFoundation extends Foundation {

	private static final long serialVersionUID = 1L;

	public HouseFoundation() {
		super();
		this.name = "House Fdtn.";
		setImageID(19, 0);
	}

	public Building build(int gx, int gy) {
		return new House(gx, gy);
	}

	public Rectangle getPotentialBounds(int gx, int gy) {
		return new Rectangle(gx, gy, Constants.HOUSE_GRID_SIZE, Constants.HOUSE_GRID_SIZE);
	}

	// ///////
	//public Interior getInterior(int seed, Entrance entrance) {
	//	return new HouseInterior(seed, entrance.getLoc(), entrance.getID());
	//}



}
