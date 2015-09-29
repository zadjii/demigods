package items.weapons;

import items.tools.Tool;
import util.Constants;

public class StoneAxe extends Tool {

	private static final long serialVersionUID = 1L;

	public StoneAxe() {
		super();
		this.setDamage(12);
		this.setHarvestRate(Constants.STONE_TOOL_UNITS_PER_SECOND);
		setWeight(Constants.STONE_TOOL_WEIGHT);
		setIsAxe(true);
		setImageID(2, 18);
		this.name = "Stone Axe";
	}


}
