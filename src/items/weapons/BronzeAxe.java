package items.weapons;

import items.tools.Tool;
import util.Constants;

public class BronzeAxe extends Tool {

	private static final long serialVersionUID = 1L;

	public BronzeAxe() {
		super();
		this.setHarvestRate(Constants.BRONZE_TOOL_UNITS_PER_SECOND);
		setWeight(Constants.BRONZE_TOOL_WEIGHT);
		setIsAxe(true);
		this.setDamage(17);
		setImageID(2, 17);
		this.name = "Bronze Axe";
	}

}
