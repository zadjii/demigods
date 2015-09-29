package items.placements;

import entities.tiles.Lightsource;
import entities.tiles.Torch;

public class TorchItem extends LightItem {

	private static final long serialVersionUID = 1L;

	public TorchItem() {
		super();
		setWeight(1);
		this.name = "Torch";
		this.setImageID(0, 2);
	}

	public Lightsource getItem(int gx, int gy) {
		return new Torch(gx, gy);
	}


}
