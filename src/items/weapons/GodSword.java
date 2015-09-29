package items.weapons;
import enchantments.Enchantment;
import items.Item;
import util.Constants;

public class GodSword extends Item {

	public GodSword() {
		super();
		setTooltip("A Magical Weapon");
		this.setDamage(30);
		this.range = 2;
		setWeight(Constants.STEEL_TOOL_WEIGHT);
		setIsWeapon(true);
		setImageID(3, 15);
		this.name = "God Sword";
		this.enchantments.add(Enchantment.newGodlyEnchantment());
	}
}