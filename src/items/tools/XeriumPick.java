package items.tools;


import util.Constants;

public class XeriumPick extends Tool {

	public XeriumPick() {
		super();
		setWeight(Constants.XERIUM_TOOL_WEIGHT);
		this.setHarvestRate(Constants.XERIUM_TOOL_UNITS_PER_SECOND);
		setIsPick(true);
		this.setDamage(35);
		setImageID(1, 14);
		this.name = "Xerium Pickaxe";
	}


}