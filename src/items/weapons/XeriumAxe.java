package items.weapons;


import items.tools.Tool;
import util.Constants;

public class XeriumAxe extends Tool {

	public XeriumAxe() {
		super();
		this.setHarvestRate(Constants.XERIUM_TOOL_UNITS_PER_SECOND);
		setWeight(Constants.XERIUM_TOOL_WEIGHT);
		setIsAxe(true);
		this.setDamage(37);
		setImageID(2, 14);
		this.name = "Xerium Axe";
	}


}
