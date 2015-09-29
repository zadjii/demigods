package entities.characters;

import entities.characters.etc.Knockback;
import items.materials.Bone;

import org.newdawn.slick.*;

import util.exceptions.DemigodsException;
import util.Images;

public class FloatingTorso extends Humanoid {

    private static final long serialVersionUID = 1L;
    private int animStep = 0;

    public FloatingTorso() {
        init();
    }

    public FloatingTorso(int gx, int gy) {
        super(gx, gy);
        init();
    }

    private void init() {
        this.setBaseSpeed(1);
        setHeight(64);
        setWidth(48);
        imageYOffset = 48;
        imageXOffset = 24;
        setSpeed(1);
        this.setBaseStat(HP, 100);
        this.setBaseStat(ARMOR, 10);
        this.setBaseStat(MAGIC_RESIST, 5);
        try {
            inventory.addItem(new Bone());
            inventory.addItem(new Bone());
            inventory.addItem(new Bone());
            inventory.addItem(new Bone());
            inventory.addItem(new Bone());
        } catch (DemigodsException ignored) {
        }
    }

    public void draw(float sx, float sy, float zoom, Graphics g) {
        SpriteSheet spritesheet = Images.floatingTorsoSheet;
        int step = stepNum / Humanoid.STEPRATIO;
        if (step == 2) step = 0;
        else if (step == 3) step = 2;
        if (this.getKnockback() instanceof Knockback) {
            if (this.getDirection() == Direction.LEFT) {
                spritesheet.getSprite(step, 0).draw((getX() - sx - getImageXOffset()) * zoom, (getY() - sy - getImageYOffset()) * zoom, 64 * zoom, 64 * zoom, Color.red);
            }
            if (this.getDirection() == Direction.RIGHT) {
                spritesheet.getSprite(step, 1).draw((getX() - sx - getImageXOffset()) * zoom, (getY() - sy - getImageYOffset()) * zoom, 64 * zoom, 64 * zoom, Color.red);
            }
            if (this.getDirection() == Direction.DOWN) {
                spritesheet.getSprite(step, 2).draw((getX() - sx - getImageXOffset()) * zoom, (getY() - sy - getImageYOffset()) * zoom, 64 * zoom, 64 * zoom, Color.red);
            }
            if (this.getDirection() == Direction.UP) {
                spritesheet.getSprite(step, 3).draw((getX() - sx - getImageXOffset()) * zoom, (getY() - sy - getImageYOffset()) * zoom, 64 * zoom, 64 * zoom, Color.red);
            }
        } else {
            if (this.getDirection() == Direction.LEFT) {
                spritesheet.getSprite(step, 0).draw((getX() - sx - getImageXOffset()) * zoom, (getY() - sy - getImageYOffset()) * zoom, 64 * zoom, 64 * zoom);
            }
            if (this.getDirection() == Direction.RIGHT) {
                spritesheet.getSprite(step, 1).draw((getX() - sx - getImageXOffset()) * zoom, (getY() - sy - getImageYOffset()) * zoom, 64 * zoom, 64 * zoom);
            }
            if (this.getDirection() == Direction.DOWN) {
                spritesheet.getSprite(step, 2).draw((getX() - sx - getImageXOffset()) * zoom, (getY() - sy - getImageYOffset()) * zoom, 64 * zoom, 64 * zoom);
            }
            if (this.getDirection() == Direction.UP) {
                spritesheet.getSprite(step, 3).draw((getX() - sx - getImageXOffset()) * zoom, (getY() - sy - getImageYOffset()) * zoom, 64 * zoom, 64 * zoom);
            }
        }
    }

    public String toString() {
        return "Floating Torso " + this.getLoc();
    }
}
