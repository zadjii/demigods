package entities.interactables;

import entities.Entity;
import game.Engine;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Graphics;
import util.Images;

public class AestheticTile extends Entity implements Interactable {

    public static final int NOTHING = -1;
    public static final int GRASS_ROCKS = 0;
    public static final int PEBBLES = 1;
    public static final int ROCK = 2;

    private int type = -1;

    public AestheticTile(int X, int Y) {
        this.setXY(X, Y);
    }

    public void setType(int type) {
        this.type = type;
    }

    public void interact() {
    }

    @Override
    public void draw(float sx, float sy, float zoom, Graphics g) {
        if (this.type == ROCK) {
            Images.tileSheet.draw(
                    (this.getX() - sx) * zoom,
                    (this.getY() - sy) * zoom,
                    (this.getX() - sx + 16) * zoom,
                    (this.getY() - sy + 16) * zoom,
                    5 * 16, 5 * 16, 6 * 16, 6 * 16);
        } else if (this.type == GRASS_ROCKS) {
            Images.tileSheet.draw(
                    (this.getX() - sx) * zoom,
                    (this.getY() - sy) * zoom,
                    (this.getX() - sx + 16) * zoom,
                    (this.getY() - sy + 16) * zoom,
                    5 * 16, 6 * 16, 6 * 16, 7 * 16);
        } else if (this.type == PEBBLES) {
            Images.tileSheet.draw(
                    (this.getX() - sx) * zoom,
                    (this.getY() - sy) * zoom,
                    (this.getX() - sx + 16) * zoom,
                    (this.getY() - sy + 16) * zoom,
                    5 * 16, 7 * 16, 6 * 16, 8 * 16);
        }
    }

    public Rectangle getAbsHitbox() {
        return new Rectangle(this.getXi(), this.getYi(), 16, 16);
    }

    @Override
    public boolean isPassable() {
        return true;
    }

    public Rectangle getImpassableBounds() {
        return new Rectangle(this.getGX(), this.getGY(), 1, 1);
    }

    @Override
    public boolean drawnAbove() {
        return false;
    }

    public boolean needToRelease() {
        return false;
    }

    public String toString() {
        return "Aesthetic " + this.getLocString();
    }

    public boolean aestheticOnly() {
        return true;
    }
}
