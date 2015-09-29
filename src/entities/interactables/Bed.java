package entities.interactables;

import entities.Entity;

import game.Engine;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Graphics;

public class Bed extends Entity implements Interactable {
    private static final long serialVersionUID = 1L;

    public void interact() {
    }

    public void draw(float sx, float sy, float zoom, Graphics g) {
    }

    public Rectangle getAbsHitbox() {
        return new Rectangle(this.getXi(), this.getYi(), 16, 32);
    }

    public boolean isPassable() {
        return false;
    }

    public Rectangle getImpassableBounds() {
        return new Rectangle(this.getGX(), this.getGY(), 1, 2);
    }

    public boolean drawnAbove() {
        return true;
    }

    public boolean needToRelease() {
        return true;
    }

    public boolean aestheticOnly() {
        return false;
    }
}
