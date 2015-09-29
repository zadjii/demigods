package entities.characters;

import areas.NewGameArea;
import entities.particles.Particle;
import game.Engine;
import items.materials.Bone;

import org.newdawn.slick.*;

import util.exceptions.DemigodsException;
import util.Images;

public class GreenSlime extends Slime {

    private static final long serialVersionUID = 1L;


    public GreenSlime() {
        super();
        init();
    }

    protected void init() {
	    super.init();
	    this.blobletColor = new Color(100, 200, 100);
        setWidth(16);
        setHeight(16);
        this.setBaseStat(HP, 35);
        this.setBaseStat(ARMOR, 2);
	    this.setBaseStat(ATTACK, 3);
        try {
            inventory.addItem(new Bone());
        } catch (DemigodsException ignored) {
        }
        setBaseSpeed(1);
    }

    public void draw(float sx, float sy, float zoom, Graphics g) {
        drawShadow(sx, sy, zoom);
        if (this.getKnockback() != null) {
            Images.greenSlimeSheet.getSprite(animStep / 3, 0).draw((getX() - sx) * zoom, (getY() - sy) * zoom, getWidth() * zoom, getHeight() * zoom, Color.red);
        } else {
            Images.greenSlimeSheet.getSprite(animStep / 3, 0).draw((getX() - sx) * zoom, (getY() - sy) * zoom, getWidth() * zoom, getHeight() * zoom);
        }
    }

    public String toString() {
        return "GreenSlime " + this.getLocString();
    }
}
