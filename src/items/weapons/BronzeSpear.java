package items.weapons;
import conditions.etc.Cooldown;
import items.Item;
import util.Constants;



public class BronzeSpear extends Item {

	private static final long serialVersionUID = 1L;

	public BronzeSpear() {
		super();
		this.setUseSpeed(Constants.SPEAR_SPEED);
		this.range = 3;
		this.setDamage(19);
		setWeight(2);
		setIsWeapon(true);
		setImageID(4, 17);
		this.name = "Bronze Spear";
	}


}