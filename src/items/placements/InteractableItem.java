package items.placements;

import entities.interactables.*;
import entities.interactables._test.InteracterTester;
import items.Item;

public class InteractableItem extends Item {

	private static final long serialVersionUID = 1L;

	public InteractableItem() {
		super();
		setWeight(1);
		this.setInteractableItem(true);
		this.setImageID(19, 19);
		this.name = "interactable";
	}

	public Interactable getItem(int gx, int gy) {
		return new InteracterTester(gx, gy);
	}



}
