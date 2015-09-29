package conditions;

import entities.characters.Character;
import game.Engine;
import org.lwjgl.util.Point;

public class Poisoned extends Condition {
    private static final long serialVersionUID = 1L;

    private double healthRegenMod = -0.05;

    public Poisoned(Character src, Character tgt, int duration) {
        super(src, tgt, duration);
        tgt.modifyFlatBonus(Character.HPREGEN, healthRegenMod);
    }

    public void turnOff() {
        if (tgt != null) tgt.modifyFlatBonus(Character.HPREGEN, -healthRegenMod);
    }

    public void tick() {
        super.tick();
    }

    public String getTooltip() {
        return "Inflicted with poison, causing " +
                -this.healthRegenMod + " health degeneration";
    }

    public String toString() {
        return "Poisoned";
    }

    public Point getImageLoc() {
        return new Point(8, 0);
    }
}
