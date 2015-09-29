package items.weapons;

import items.tools.Tool;
import util.Constants;

public class BoneAxe extends Tool {

	private static final long serialVersionUID = 1L;

	public BoneAxe() {
		super();
		this.setHarvestRate(Constants.BONE_TOOL_UNITS_PER_SECOND);
		setWeight(Constants.BONE_TOOL_WEIGHT);
		setIsAxe(true);
		this.setDamage(11);
		setImageID(2, 13);

		this.name = "Bone Axe";
	}


}
