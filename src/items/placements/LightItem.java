package items.placements;

import entities.tiles.Lightsource;
import items.Item;


public class LightItem extends Item {

	private static final long serialVersionUID = 1L;

	public LightItem() {
		super();
		setWeight(1);
		this.setLight(true);
		this.setImageID(19, 19);
		this.name = "lightsource";
	}

	public Lightsource getItem(int gx, int gy) {
		System.err.println("somehow, a lightsource didn't overload getItem() in items.placements.LightItem");
		return null;
	}



}
