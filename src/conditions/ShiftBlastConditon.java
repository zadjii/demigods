package conditions;

import entities.characters.Character;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.Constants;

public class ShiftBlastConditon extends Condition {

    private double damage;
    private Character src;

    public ShiftBlastConditon(Character src, Character tgt, int duration, double damage) {
        super(src, tgt, duration);
        this.damage = damage;
        this.src = src;
    }

    public void tick() {
        super.tick();
        if (tgt != null && tgt.getHP() <= 0) {
            this.duration.clear();
            return;
        }
        if (duration.getRemaining() == 1) {
            Character.dealMixedDamage((int)damage, src, tgt, false);
        }
    }

    public boolean isDrawn() {
        return true;
    }

    public Point getImageLoc() {
        return new Point(1, 4);
    }

    Color lightRed = new Color(255, 175, 175);

    public void draw(float sx, float sy, float zoom, Graphics g) {
        if (duration.getRemaining() < 2 * Constants.TICKS_PER_SECOND) {
            float x = tgt.getX() + (tgt.getWidth() / 2) - (tgt.getImageXOffset()) - sx;
            float y = tgt.getY() - sy;
            float radius = (((float)duration.getRemaining()) / (2 * 20));
            System.out.println(duration.getRemaining());
            radius *= 3 * 16;
            g.setColor(Color.red);
            g.drawOval((x - radius / 2) * zoom, (y - radius / 2) * zoom, radius * 2, radius * 2);
            g.setColor(lightRed);
            g.drawOval((x - radius / 2 - 1) * zoom, (y - radius / 2 - 1) * zoom, (radius + 2) * 2, (radius + 2) * 2);
            g.setColor(Color.white);
            g.drawOval((x - radius / 2 + 1) * zoom, (y - radius / 2 + 1) * zoom, (radius - 2) * 2, (radius - 2) * 2);
        }
    }

    public String toString() {
        return "Shift Blast";
    }
}
