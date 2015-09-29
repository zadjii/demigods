package conditions;

import entities.characters.Character;
import game.Engine;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 10/3/12
 * Time: 9:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndefiniteCondition extends Condition {

    public IndefiniteCondition(Character src, Character tgt, int duration) {
        super(src, tgt, 0);
        this.isIndefinite = true;
    }

    public boolean isOff() {
        return false;
    }

    public void tick() {
        if (src != null && src.getHP() <= 0) src = null;
        if (tgt.getHP() <= 0) tgt = null;
    }
}
