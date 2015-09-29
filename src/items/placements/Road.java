package items.placements;

import items.Item;

import java.awt.*;
import javax.swing.ImageIcon;

public class Road extends Item {

	private static final long serialVersionUID = 1L;
	private static final int TILE_ID = 16;

	public Road() {
		super();
		setWeight(1);
		this.setRoad(true);
		this.setImageID(17, 1);
		this.name = "road";
	}


	public int getID() {
		return TILE_ID;
	}


}
