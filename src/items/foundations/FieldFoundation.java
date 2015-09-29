package items.foundations;

import org.lwjgl.util.Rectangle;

import entities.buildings.*;

public class FieldFoundation extends Foundation {

	private static final long serialVersionUID = 1L;

	public FieldFoundation() {
		super();
		this.name = "Field";
		setImageID(15, 0);
	}

	public Building build(int gx, int gy) {
		return new Field(gx, gy);
	}

	public Rectangle getPotentialBounds(int gx, int gy) {
		return new Rectangle(gx, gy, 5, 5);
	}
	public boolean buildingIsPassable(){
		return true;
	}
	// ///////
	//public Interior getInterior(int seed, Entrance entrance) {
	//	throw new UncheckedDemigodsException("A Field doesn't have an interior!");
	//}

	// //////

	public String toString() {
		return "Field";
	}


}
