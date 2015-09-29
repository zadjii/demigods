package entities.characters;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.Images;

public class Bee extends Character {

    private static final long serialVersionUID = 1L;
    private int animStep = 0;

    public Bee() {
        super();
        init();
    }

    public Bee(int gx, int gy) {
        super(gx, gy);
        init();
    }

    private void init() {
        this.setBaseSpeed(1);
        setWidth(16);
        setHeight(16);
//        setSpeed(1);
        setBaseStat(HP, 10);
    }

    public void draw(float sx, float sy, float zoom, Graphics g) {
        drawShadow(sx, sy, zoom);
        if (this.getKnockback() != null) {
            Images.batSheet.getSprite(animStep / 3, 0).draw((getX() - sx) * zoom, (getY() - sy) * zoom, getWidth() * zoom, getHeight() * zoom, Color.red);
        } else {
            Images.batSheet.getSprite(animStep / 3, 0).draw((getX() - sx) * zoom, (getY() - sy) * zoom, getWidth() * zoom, getHeight() * zoom);
        }
    }

    public void updateSpriteXY() {
        animStep++;
        if (animStep == 9) animStep = 0;
    }

    public String toString() {
        return "Bee " + this.getLocString();
    }
}
