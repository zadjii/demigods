package entities.characters;

import items.armor.BlueWizardHat;
import skills.Icicle;
import skills.ThrowAcorn;

public class ThrowAcornSquirrel extends Squirrel {
    protected void init() {
        super.init();
        this.setBaseSpeed(1);
//        this.setBaseStat(ABILITY, -15);
//        this.setBaseStat(ARMOR, -20);
        this.setSkill(0, new ThrowAcorn());
//        BlueWizardHat hat = new BlueWizardHat();
//        hat.equip(this);
    }
}
