package entities.characters;

import items.armor.BlueWizardHat;
import items.weapons.EnergyStaff;
import skills.Icicle;

public class EnergyStaffGoblin extends Goblin {
    protected void init() {
        super.init();
        this.setBaseSpeed(0.75f);
//        this.setBaseStat(ABILITY, -15);
        this.setBaseStat(ARMOR, -20);
//        this.setSkill(0, new Icicle());
//        BlueWizardHat hat = new BlueWizardHat();
//        hat.equip(this);
	    equippedItem = new EnergyStaff();
	    equippedItem.equip(this);
    }
}
