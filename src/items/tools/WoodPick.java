package items.tools;


import util.Constants;

public class WoodPick extends Tool {

	public WoodPick() {
		super();
		setWeight(Constants.WOOD_TOOL_WEIGHT);
		this.setHarvestRate(Constants.WOOD_TOOL_UNITS_PER_SECOND);
		setIsPick(true);
		this.setDamage(5);
		setImageID(1, 19);
		this.name = "Wood Pickaxe";
	}


}