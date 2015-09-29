package enchantments;

import conditions.Poisoned;
import items.Item;
import entities.characters.Character;
import util.Constants;

import java.io.Serializable;

public class Enchantment implements Serializable {
    protected String prefix = "";
    protected String suffix = "";

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void apply(Item item) {
    }

    public void remove(Item item) {
    }

    public void applyOnHit(Character src, Character tgt) {
    }

    public static void addEnchantment(Item item, String name) {
        Enchantment addedEnchantment = null;
        if (name.equalsIgnoreCase("Poisoned") || name.equalsIgnoreCase("Poison")) {
            addedEnchantment = new PoisonedEnchantment();
        } else if (name.equalsIgnoreCase("Mighty")) {
            addedEnchantment = new MightyEnchantment();
        }
        else if (name.equalsIgnoreCase("Apprentice")) {
	        addedEnchantment = new ApprenticeEnchantment();
        }
        if (addedEnchantment != null) {
            item.getEnchantments().add(addedEnchantment);
            addedEnchantment.apply(item);
        }
    }

    public static Enchantment newGodlyEnchantment() {
        return new GodlyEnchantment();
    }
}

class PoisonedEnchantment extends Enchantment {
    public PoisonedEnchantment() {
        this.prefix = "Poisoned";
    }

    public void applyOnHit(Character src, Character tgt) {
        if (Math.random() <= 0.25) {
            tgt.addCondition(new Poisoned(src, tgt, 10 * Constants.TICKS_PER_SECOND));
        }
    }
}

class MightyEnchantment extends Enchantment {
    public MightyEnchantment() {
        this.prefix = "Mighty";
    }

    public void apply(Item item) {
        item.setDamage(item.getDamage() + 5);
    }
}

class ApprenticeEnchantment extends Enchantment {
	public ApprenticeEnchantment() {
		this.prefix = "Apprentice's";
	}

	public void apply(Item item) {
		item.setDamage(item.getDamage() - 10);
	}
}
class GodlyEnchantment extends Enchantment {
    public GodlyEnchantment() {
    }

    public void applyOnHit(Character src, Character tgt) {
        Character.dealTrueDamage(5, src, tgt, false);
    }
}