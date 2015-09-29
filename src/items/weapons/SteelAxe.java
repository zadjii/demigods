package items.weapons;

import items.tools.Tool;
import util.Constants;

public class SteelAxe extends Tool {

	private static final long serialVersionUID = 1L;

	public SteelAxe() {
		super();
		this.setHarvestRate(Constants.STEEL_TOOL_UNITS_PER_SECOND);
		setWeight(Constants.STEEL_TOOL_WEIGHT);
		setIsAxe(true);
		this.setDamage(27);
		setImageID(2, 15);
		this.name = "Steel Axe";
	}

}
