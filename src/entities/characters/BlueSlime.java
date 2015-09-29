package entities.characters;

import areas.NewGameArea;
import entities.particles.Particle;
import game.Engine;
import items.materials.Bone;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.Images;
import util.exceptions.DemigodsException;

public class BlueSlime extends Slime {

    private static final long serialVersionUID = 1L;
    private int animStep = 0;
	private float previousHP;
    public BlueSlime() {
        super();
        init();
    }

    protected void init() {
	    super.init();
	    this.blobletColor = new Color(100, 100, 200);
        setWidth(8);
        setHeight(8);
        this.setBaseStat(HP, 25);
        this.setBaseStat(ARMOR, 0);
        try {
            inventory.addItem(new Bone());
        } catch (DemigodsException ignored) {
        }
        setBaseSpeed(1.5f);
	    this.previousHP = this.getHP();
    }


    public void draw(float sx, float sy, float zoom, Graphics g) {
        drawShadow(sx, sy, zoom);
        if (this.getKnockback() != null) {
            Images.blueSlimeSheet.getSprite(animStep / 3, 0).draw((getX() - sx) * zoom, (getY() - sy) * zoom, getWidth() * zoom, getHeight() * zoom, Color.red);
        } else {
            Images.blueSlimeSheet.getSprite(animStep / 3, 0).draw((getX() - sx) * zoom, (getY() - sy) * zoom, getWidth() * zoom, getHeight() * zoom);
        }
//	    System.out.println(animStep / 3 + ", " + Images.blueSlimeSheet.getHorizontalCount());
    }

    public String toString() {
        return "BlueSlime " + this.getLocString();
    }
}
