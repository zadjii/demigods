package entities.characters;

import items.weapons.BoneSpear;

public class BoneSpearGoblin extends Goblin {
    protected void init() {
        super.init();
        this.setBaseSpeed(0.5f);
//        this.setBaseStat(ABILITY, -15);

	    equippedItem = new BoneSpear();
	    equippedItem.equip(this);
    }
}
