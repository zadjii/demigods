package entities.characters;

import org.newdawn.slick.Graphics;

public class Humanoid extends Character {

    private static final long serialVersionUID = 1L;

    public Humanoid() {
        super();
        this.setBaseSpeed(2.0f);
    }

    public Humanoid(int gx, int gy) {
        super(gx, gy);
        this.setBaseSpeed(2.0f);
    }

    protected int handsState = 0;
    protected int attackState = 0;

    protected static final int MOVING = 0;
    protected static final int ONEHANDSWING = 1;
    protected static final int TWOHANDSWING = 2;
    protected static final int STAB = 3;

    public void updateSpriteXY() {
        movingAnimUpdate();
        attackingAnimUpdate();
    }

    public static final int STEPRATIO = 6;

    public void movingAnimUpdate() {
        if (stepNum < 4 * STEPRATIO - 1 && (getDX() != 0 || getDY() != 0)) {
            stepNum++;
        } else {
            stepNum = 0;
        }
        if ((getDX() == 0 && getDY() == 0)) {
            stepNum = STEPRATIO - 1;
        }
    }

    public void updateAttacking() {
        attackState += 1;
        if (attackState >= 3 * STEPRATIO - 1) {
            attackState = 0;
            swinging = false;
        }
    }

    private void attackingAnimUpdate() {
        if (this.getEquippedItem() != null && (swinging)) {
            switch (this.getEquippedItem().getRange()) {
                case 0:
                case 1:
                    //do 1h swing
                    handsState = ONEHANDSWING;
                    break;
                case 2:
                case 4:
                    handsState = TWOHANDSWING;
                    //do 2H swing
                    break;
                case 3:
                    handsState = STAB;
                    //do stab
                    break;
            }
        } else {
            handsState = MOVING;
        }
    }

    public void draw(float sx, float sy, float zoom, Graphics g) {
    }
}
