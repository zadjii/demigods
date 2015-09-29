package entities.characters;

import items.armor.BlueWizardHat;
import skills.*;

public class IcicleSkeleton extends Skeleton {
    protected void init() {
        super.init();
        this.setBaseSpeed(1);
        this.setBaseStat(ABILITY, -15);
        this.setBaseStat(ARMOR, -20);
        this.setSkill(0, new Icicle());
        BlueWizardHat hat = new BlueWizardHat();
        hat.equip(this);
    }
}
