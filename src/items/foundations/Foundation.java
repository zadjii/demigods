package items.foundations;

import items.Item;
import org.lwjgl.util.Rectangle;

import util.Constants;

import entities.buildings.*;

public class Foundation extends Item {
	private static final long serialVersionUID = 1L;
	protected boolean isEnterableBuilding;

	public Foundation() {
		super();
		setWeight(1);
		setFoundation(true);
		this.name = "Foundation";
	}

	public Building build(int gx, int gy) {
		return new Building(gx, gy);
	}

	public Rectangle getPotentialBounds(int gx, int gy) {
		return new Rectangle(gx, gy, Constants.HOUSE_GRID_SIZE, Constants.HOUSE_GRID_SIZE);
	}
	public boolean buildingIsPassable(){
		return false;
	}
	// ///////
	//public Interior getInterior(int seed, Entrance entrance) {
	//	return new Interior(seed, entrance.getLoc(), entrance.getID());
	//}
//



}
