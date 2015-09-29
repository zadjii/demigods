package effects;

import game.Engine;
import org.newdawn.slick.Graphics;

public abstract class Effect extends EffectEntity {

    public abstract void draw(float sx, float sy, Graphics g, float zoom);
	public void drawBelow(float sx, float sy, Graphics g, float zoom){};

    public abstract Effect tick();

    public boolean hitImpassable() {
        try {
            return !Engine.getActiveArea().getPassable()[(int)(this.getX() / 16)][(int)(this.getY() / 16)];
        } catch (IndexOutOfBoundsException e1) {
            return true;
        }
    }
}
