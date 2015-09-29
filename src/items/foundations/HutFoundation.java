package items.foundations;
import org.lwjgl.util.Rectangle;

import util.Constants;

import entities.buildings.*;

public class HutFoundation extends Foundation {

	public HutFoundation() {
		super();
		this.name = "Hut Foundation";
		setImageID(19, 0);
	}

	public Building build(int gx, int gy) {
		return new Hut(gx, gy);
	}

	public Rectangle getPotentialBounds(int gx, int gy) {
		return new Rectangle(gx, gy, Constants.HUT_GRID_SIZE, Constants.HUT_GRID_SIZE);
	}

	// ///////
	//public Interior getInterior(int seed, Entrance entrance) {
	//	return new HutInterior(seed, entrance.getLoc(), entrance.getID());
	//}




}
