package items.foundations;

import org.lwjgl.util.Rectangle;

import util.Constants;

import entities.buildings.*;

public class CraftingHutFoundation extends Foundation {

	public CraftingHutFoundation() {
		super();
		this.name = "Crafting Hut Foundation";
		setImageID(18, 0);
	}

	public Building build(int gx, int gy) {
		return new CraftingHut(gx, gy);
	}

	public Rectangle getPotentialBounds(int gx, int gy) {
		return new Rectangle(gx, gy, Constants.HUT_GRID_SIZE, Constants.HUT_GRID_SIZE);
	}

	// ///////
	//public Interior getInterior(int seed, Entrance entrance) {
	//	return new CraftingHutInterior(seed, entrance.getLoc(), entrance.getID());
	//}




}
