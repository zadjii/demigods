package entities.interactables;

import entities.Entity;
import game.Engine;
import entities.particles.Particle;
import items.Item;
import items.materials.Seeds;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Graphics;
import util.Images;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 9/2/12
 * Time: 1:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class TallGrass extends Entity implements Interactable {

    public TallGrass(int absX, int absY) {
        this.setXY(absX, absY);
    }

    public void interact() {
        int quantity = (int)(Math.random() * 3 + 1);
        for (int i = 0; i < quantity; i++) {
            Item item = new Seeds();
        }
        for (int i = 0; i < 10; i++) {
            Engine.add(Particle.newGreenTreeParticle(this));
        }
    }

    @Override
    public void draw(float sx, float sy, float zoom, Graphics g) {
        Images.mapObjectSheet.draw(
                (this.getX() - sx) * zoom,
                (this.getY() - sy) * zoom,
                (this.getX() - sx + 16) * zoom,
                (this.getY() - sy + 16) * zoom,
                2 * 16, 0 * 16, 3 * 16, 1 * 16);
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
        return "TallGrass " + this.getLocString();
    }

    public boolean aestheticOnly() {
        return true;
    }
}
