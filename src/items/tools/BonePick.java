package items.tools;


import util.Constants;

public class BonePick extends Tool {

	public BonePick() {
		super();
		setWeight(Constants.BONE_TOOL_WEIGHT);
		this.setHarvestRate(Constants.BONE_TOOL_UNITS_PER_SECOND);
		setIsPick(true);
		this.setDamage(9);
		setImageID(1, 13);
		this.name = "Bone Pickaxe";
	}

}