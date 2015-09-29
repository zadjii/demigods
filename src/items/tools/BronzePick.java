package items.tools;


import util.Constants;

public class BronzePick extends Tool {

	public BronzePick() {
		super();
		setWeight(Constants.BRONZE_TOOL_WEIGHT);
		this.setHarvestRate(Constants.BRONZE_TOOL_UNITS_PER_SECOND);
		setIsPick(true);
		this.setDamage(15);
		setImageID(1, 17);
		this.name = "Bronze Pickaxe";
	}


}