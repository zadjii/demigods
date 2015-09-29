package items.weapons;

import items.tools.Tool;
import util.Constants;

public class IronAxe extends Tool {

	private static final long serialVersionUID = 1L;

	public IronAxe() {
		super();
		this.setHarvestRate(Constants.IRON_TOOL_UNITS_PER_SECOND);
		setWeight(Constants.IRON_TOOL_WEIGHT);

		this.setDamage(22);
		setIsAxe(true);
		setImageID(2, 16);
		this.name = "Iron Axe";
	}

}
