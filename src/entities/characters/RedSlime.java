package entities.characters;

import areas.NewGameArea;
import entities.particles.Particle;
import game.Engine;
import items.materials.Bone;

import org.newdawn.slick.*;

import util.exceptions.DemigodsException;
import util.Images;

public class RedSlime extends Slime {

    private static final long serialVersionUID = 1L;
    private int animStep = 0;
	private float previousHP;

    public RedSlime() {
        init();
    }

    protected void init() {
	    super.init();
	    this.blobletColor = new Color(200, 100, 100);
        this.setBaseSpeed(1);
        setWidth(32);
        setHeight(32);
        setSpeed(1);
        this.setBaseStat(HP, 80);
        this.setBaseStat(ARMOR, 2);
        this.setBaseStat(FIRE_RESIST, 2);
	    this.setBaseStat(ATTACK, 5);
        imageYOffset = 16;
        imageXOffset = 16;
        try {
            inventory.addItem(new Bone());
        } catch (DemigodsException ignored) {
        }
	    previousHP = this.getHP();
    }

    public void draw(float sx, float sy, float zoom, Graphics g) {
        drawShadow(sx, sy, zoom);
        if (this.getKnockback() != null) {
            Images.redSlimeSheet.getSprite(animStep / 3, 0).draw((getX() - sx - getImageXOffset()) * zoom, (getY() - sy - getImageYOffset()) * zoom, getWidth() * zoom, getHeight() * zoom, Color.red);
        } else {
            Images.redSlimeSheet.getSprite(animStep / 3, 0).draw((getX() - sx - getImageXOffset()) * zoom, (getY() - sy - getImageYOffset()) * zoom, getWidth() * zoom, getHeight() * zoom);
        }
    }


    public String toString() {
        return "RedSlime (" + this.getLoc() + ")";
    }
}
