package items.foundations;

import org.lwjgl.util.Rectangle;

import util.Constants;

import entities.buildings.*;

public class ResourcePileFoundation extends Foundation {

	private static final long serialVersionUID = 1L;

	public ResourcePileFoundation() {
		super();
		this.name = "Resource Pile Fdtn.";
		setImageID(19, 0);
	}

	public Building build(int gx, int gy) {
		return new ResourcePile(gx, gy);
	}

	public Rectangle getPotentialBounds(int gx, int gy) {
		return new Rectangle(gx, gy, Constants.HOUSE_GRID_SIZE, Constants.HOUSE_GRID_SIZE);
	}




}
