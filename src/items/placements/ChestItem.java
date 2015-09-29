package items.placements;

import entities.interactables.*;

public class ChestItem extends InteractableItem {

	private static final long serialVersionUID = 1L;

	public ChestItem() {
		super();
		setWeight(1);
		this.setImageID(19, 19);
		this.name = "Chest";
	}

	public Interactable getItem(int absx, int absy) {
		return new Chest(absx, absy);
	}


}
