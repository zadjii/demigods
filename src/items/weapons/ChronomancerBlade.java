package items.weapons;
import items.Item;
import util.Constants;

public class ChronomancerBlade extends Item {

	public ChronomancerBlade() {
		super();
		setTooltip("");
		this.setDamage(30);
		this.range = 1;
		setWeight(Constants.STEEL_TOOL_WEIGHT);
		setIsWeapon(true);
		setImageID(3, 15);
		this.name = "Chronomancer Blade";
		//this.enchantments.add(Enchantment.newGodlyEnchantment());
	}
}