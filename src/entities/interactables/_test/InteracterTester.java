package entities.interactables._test;

import entities.Entity;

import entities.interactables.Interactable;
import game.Engine;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Graphics;

public class InteracterTester extends Entity implements Interactable {
    private static final long serialVersionUID = 1L;

    public InteracterTester(int gx, int gy) {
        this.setGXGY(gx, gy);
    }

    public void interact() {
        System.out.println("you did it!");
    }

    public void draw(float sx, float sy, float zoom, Graphics g) {
    }

    public Rectangle getAbsHitbox() {
        return new Rectangle(this.getXi(), this.getYi(), 16, 16);
    }

    public boolean isPassable() {
        return false;
    }

    public Rectangle getImpassableBounds() {
        return new Rectangle(this.getGX(), this.getGY(), 1, 1);
    }

    public boolean drawnAbove() {
        return true;
    }

    public boolean needToRelease() {
        return true;
    }

    public boolean aestheticOnly() {
        return true;
    }
}
