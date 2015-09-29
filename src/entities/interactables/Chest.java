package entities.interactables;

import entities.Entity;
import util.StackStorage;

import org.lwjgl.util.Rectangle;

import org.newdawn.slick.Graphics;
import util.Images;

public class Chest extends Entity implements Interactable {
    private static final long serialVersionUID = 1L;

    private StackStorage items = new StackStorage(16);

    public Chest(int absx, int absy) {
        this.setXY(absx, absy);
    }

    public void interact() {
    }

    public void draw(float sx, float sy, float zoom, Graphics g) {
        Images.mapObjectSheet.draw(
                (this.getX() - sx) * zoom,
                (this.getY() - sy) * zoom,
                (this.getX() - sx + 16) * zoom,
                (this.getY() - sy + 16) * zoom,
                0, 0,
                16, 16);
    }

    public Rectangle getAbsHitbox() {
        return new Rectangle(this.getXi(), this.getYi(), 16, 16);
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public Rectangle getImpassableBounds() {
        return new Rectangle(this.getGX(), this.getGY(), 1, 1);
    }

    public boolean drawnAbove() {
        return false;
    }

    public boolean needToRelease() {
        return true;
    }

    public boolean aestheticOnly() {
        return false;
    }
}
