package items.weapons;


import items.tools.Tool;
import util.Constants;

public class WoodAxe extends Tool {

	public WoodAxe() {
		super();
		this.setHarvestRate(Constants.WOOD_TOOL_UNITS_PER_SECOND);
		setWeight(Constants.WOOD_TOOL_WEIGHT);
		setIsAxe(true);
		this.setDamage(7);
		setImageID(2, 19);
		this.name = "Wood Axe";
	}
}
