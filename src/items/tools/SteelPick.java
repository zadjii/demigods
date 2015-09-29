package items.tools;



import util.Constants;

public class SteelPick extends Tool {

	public SteelPick() {
		super();
		setWeight(Constants.STEEL_TOOL_WEIGHT);
		this.setHarvestRate(Constants.STEEL_TOOL_UNITS_PER_SECOND);
		setIsPick(true);
		setImageID(1, 15);
		this.name = "Steel Pickaxe";
	}


}