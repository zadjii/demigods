package entities.characters;

import items.materials.Wood;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.Images;
import util.exceptions.DemigodsException;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 1/21/13
 * Time: 11:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class SandDervish extends Character {

    private static final long serialVersionUID = 1L;
    private int animStep = 0;
    private Color SAND = new Color(255, 218, 156);

    public SandDervish() {
        super();
        init();
    }

    private void init() {
        setWidth(16);
        setHeight(24);
        this.setBaseStat(HP, 50);
        try {
            inventory.addItem(new Wood());
        } catch (DemigodsException ignored) {
        }
        setBaseSpeed(2.5f);
        this.setDX((float)(Math.random() * 4 - 2));
        this.setDY((float)(Math.random() * 4 - 2));
    }
	public void levelUp(){
		super.levelUp();
		this.modifyBaseStat(Character.ATTACK, 1);
	}
    public void draw(float sx, float sy, float zoom, Graphics g) {
        drawShadow(sx, sy, zoom);
        if (this.getKnockback() != null) {
            g.setColor(Color.red);
            g.fillRect(
                    (getX() - sx) * zoom,
                    (getY() - sy) * zoom,
                    getWidth() * zoom,
                    getHeight() * zoom
            );
        } else {
            g.setColor(SAND);
            g.fillRect(
                    (getX() - sx) * zoom,
                    (getY() - sy) * zoom,
                    getWidth() * zoom,
                    getHeight() * zoom
            );
        }
//	    Images.greenSlimeSheet.getSprite(0, 0)
//			    .draw(
//					    (getX() - sx) * zoom,
//					    (getY() - sy) * zoom,
//					    getWidth() * zoom,
//					    getHeight() * zoom
//			    );
    }

    public void updateSpriteXY() {
    }

    public String toString() {
        return "SandDervish " + this.getLocString();
    }
}
