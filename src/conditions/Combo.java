package conditions;

import entities.characters.Character;
import util.Constants;

public class Combo extends Condition {

    public Combo(Character src, Character tgt, int duration) {
        super(src, tgt, duration);
        refreshDuration = false;//stacks "intensity"
    }

    public boolean isVisible() {
        return true;
    }

    public String toString() {
        return "Combo";
    }

    public static void buildCombo(Character tgt) {
        tgt.addCondition(new Combo(tgt, tgt, 5 * Constants.TICKS_PER_SECOND));
    }

    public void turnOff() {
    }
}
