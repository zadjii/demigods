package items.tools;

import util.Constants;

public class IronPick extends Tool {

	public IronPick() {
		super();
		setWeight(Constants.IRON_TOOL_WEIGHT);
		this.setHarvestRate(Constants.IRON_TOOL_UNITS_PER_SECOND);

		this.setDamage(20);
		setIsPick(true);
		setImageID(1, 16);
		this.name = "Iron Pickaxe";
	}
}