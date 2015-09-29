package items.weapons;
import conditions.etc.Cooldown;
import items.Item;
import util.Constants;



public class BoneSpear extends Item {

	private static final long serialVersionUID = 1L;

	public BoneSpear() {
		super();
		this.setUseSpeed(Constants.SPEAR_SPEED);
		setTooltip("A simple stick with a sharp bone");
		this.range = 3;
		this.setDamage(13);
		setWeight(2);
		setIsWeapon(true);
		setImageID(4, 13);
		this.name = "Bone Spear";
	}

	private void init(){

	}


}