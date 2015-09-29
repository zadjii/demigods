package items.foundations;
import org.lwjgl.util.Rectangle;

import entities.buildings.*;

public class SmithyFoundation extends Foundation {

	private static final long serialVersionUID = 1L;

	public SmithyFoundation() {
		super();
		this.name = "Smithy Fdtn.";
		setImageID(17, 0);
	}

	public Building build(int gx, int gy) {
		return new Smithy(gx, gy);
	}

	public Rectangle getPotentialBounds(int gx, int gy) {
		return new Rectangle(gx, gy, 4, 4);
	}

	// ///////
	//public Interior getInterior(int seed, Entrance entrance) {
	//	return new SmithyInterior(seed, entrance.getLoc(), entrance.getID());
	//}

	// //////


}
