package entities.characters;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.Images;

public class Meep extends Character {

    public Meep() {
        init();
    }

    public Meep(int gx, int gy) {
        super(gx, gy);
        init();
    }

    public void init() {
        setHeight(32);
        setWidth(32);
        this.setBaseSpeed(1);
        imageYOffset = 16;
        imageXOffset = 16;
    }

    public static final int STEPRATIO = 6;

    public void updateSpriteXY() {
        if (stepNum < 4 * STEPRATIO - 1 && (getDX() != 0 || getDY() != 0)) {
            stepNum++;
        } else {
            stepNum = 0;
        }
        if ((getDX() == 0 && getDY() == 0)) {
            stepNum = STEPRATIO - 1;
        }
    }

    public void draw(float sx, float sy, float zoom, Graphics g) {
        drawShadow(sx, sy, zoom);
        int step = stepNum / STEPRATIO;
        if (step == 2) step = 0;
        else if (step == 3) step = 2;
        if (this.getKnockback() != null) {
            switch (this.getDirection()) {
                case LEFT:
                    Images.meepSheet.getSprite(step, 1)
                            .draw(
                                    (x - imageXOffset - sx) * zoom,
                                    (y - imageYOffset - sy) * zoom,
                                    (getWidth()) * zoom,
                                    (getHeight()) * zoom,
                                    Color.red
                            );
                    break;
                case RIGHT:
                    Images.meepSheet.getSprite(step, 2)
                            .draw(
                                    (x - imageXOffset - sx) * zoom,
                                    (y - imageYOffset - sy) * zoom,
                                    (getWidth()) * zoom,
                                    (getHeight()) * zoom,
                                    Color.red
                            );
                    break;
                case UP:
                    Images.meepSheet.getSprite(step, 3)
                            .draw(
                                    (x - imageXOffset - sx) * zoom,
                                    (y - imageYOffset - sy) * zoom,
                                    (getWidth()) * zoom,
                                    (getHeight()) * zoom,
                                    Color.red
                            );
                    break;
                case DOWN:
                    Images.meepSheet.getSprite(step, 0)
                            .draw(
                                    (x - imageXOffset - sx) * zoom,
                                    (y - imageYOffset - sy) * zoom,
                                    (getWidth()) * zoom,
                                    (getHeight()) * zoom,
                                    Color.red
                            );
                    break;
            }
        } else {
            switch (this.getDirection()) {
                case LEFT:
                    Images.meepSheet.getSprite(step, 1)
                            .draw(
                                    (x - imageXOffset - sx) * zoom,
                                    (y - imageYOffset - sy) * zoom,
                                    (getWidth()) * zoom,
                                    (getHeight()) * zoom
                            );
                    break;
                case RIGHT:
                    Images.meepSheet.getSprite(step, 2)
                            .draw(
                                    (x - imageXOffset - sx) * zoom,
                                    (y - imageYOffset - sy) * zoom,
                                    (getWidth()) * zoom,
                                    (getHeight()) * zoom
                            );
                    break;
                case UP:
                    Images.meepSheet.getSprite(step, 3)
                            .draw(
                                    (x - imageXOffset - sx) * zoom,
                                    (y - imageYOffset - sy) * zoom,
                                    (getWidth()) * zoom,
                                    (getHeight()) * zoom
                            );
                    break;
                case DOWN:
                    Images.meepSheet.getSprite(step, 0)
                            .draw(
                                    (x - imageXOffset - sx) * zoom,
                                    (y - imageYOffset - sy) * zoom,
                                    (getWidth()) * zoom,
                                    (getHeight()) * zoom
                            );
                    break;
            }
        }
    }
}
