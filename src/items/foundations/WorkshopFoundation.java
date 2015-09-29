package items.foundations;

import org.lwjgl.util.Rectangle;

import util.Constants;

import entities.buildings.*;

public class WorkshopFoundation extends Foundation {

	private static final long serialVersionUID = 1L;

	public WorkshopFoundation() {
		super();
		setImageID(18, 0);
		this.name = "Workshop Fdtn.";
	}

	public Building build(int gx, int gy) {
		return new Workshop(gx, gy);
	}

	public Rectangle getPotentialBounds(int gx, int gy) {
		return new Rectangle(gx, gy, Constants.HOUSE_GRID_SIZE, Constants.HOUSE_GRID_SIZE);
	}

	//public Interior getInterior(int seed, Entrance entrance) {
	//	return new WorkshopInterior(seed, entrance.getLoc(), entrance.getID());
	//}



}
