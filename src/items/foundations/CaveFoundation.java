package items.foundations;

import org.lwjgl.util.Rectangle;

import util.Constants;

public class CaveFoundation extends Foundation {

	private static final long serialVersionUID = 1L;

	public CaveFoundation() {
		super();
		this.name = "Cave Fdtn.";
		setImageID(19, 0);
	}


	public Rectangle getPotentialBounds(int gx, int gy) {
		return new Rectangle(gx, gy, Constants.HOUSE_GRID_SIZE, Constants.HOUSE_GRID_SIZE);
	}

	// ///////
	//public Interior getInterior(int seed, Entrance entrance) {
	//	return new Cave(seed, entrance.getLoc(), entrance.getID());
	//}



}
