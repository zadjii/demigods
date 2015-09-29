package entities.characters;

import items.Item;
import items.weapons.IronSword;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import util.Images;
import util.RotatedBigPicture;
import util.animation.AnimationPoint;

public class Squirrel extends Humanoid {

    private static final long serialVersionUID = 1L;

    private static final int handRadius = 2;
    private static final int handSize = 4;
    private static final int W = 16;
    private static final int H = 16;


    public Squirrel() {
        init();
    }

    public Squirrel(int gx, int gy) {
        super(gx, gy);
        init();
    }

    protected void init() {
        setHeight(16);
        setWidth(16);
        this.setBaseSpeed(2);
        this.setBaseStat(EARTH_RESIST, 10);
//        imageYOffset = 16;
//        imageXOffset = 16;
//        equippedItem = new IronSword();
//        equippedItem.equip(this);
    }

    private static int[][] lhx = new int[][]{
            //____________Moving/Default VV
            {20/2, 18/2, 21/2},//L
            {20/2, 17/2, 21/2},//R
            {20/2, 20/2, 20/2},//D
            {7 /2, 7 /2, 7 /2},//U
            //____________One Handed Swing VV
            {20/2, 19/2, 21/2},//L
            {20/2, 19/2, 21/2},//R
            {20/2, 20/2, 20/2},//D
            {7 /2, 7 /2, 7/2},//U
            //____________Two Handed Swing VV
            {4 /2, 4 /2, 4 /2},//L
            {20/2, 20/2, 20/2},//R
            {28/2, 16/2, 4 /2},//D
            {4 /2, 16/2, 28/2},//U
            //____________Stab VV
            {20/2, 18/2, 21/2},//L
            {20/2, 17/2, 21/2},//R
            {20/2, 20/2, 20/2},//D
            {7 /2, 7 /2, 7 /2},//U
    };
    private static int[][] lhy = new int[][]{
            {20/2, 20/2, 20/2},//L
            {20/2, 20/2, 20/2},//R
            {18/2, 19/2, 16/2},//D
            {18/2, 16/2, 19/2},//U
            //_/2____/2____/2___One Handed Swing VV
            {20/2, 20/2, 20/2},//L
            {20/2, 20/2, 20/2},//R
            {18/2, 19/2, 17/2},//D
            {18/2, 17/2, 19/2},//U
            //_/2____/2____/2___Two Handed Swing VV
            {10/2, 18/2, 26/2},//L
            {10/2, 18/2, 26/2},//R
            {20/2, 20/2, 20/2},//D
            {10/2, 10/2, 10/2},//U
            //_/2____/2____/2___Stab VV
            {20/2, 20/2, 20/2},//L
            {20/2, 20/2, 20/2},//R
            {18/2, 19/2, 17/2},//D
            {18/2, 17/2, 19/2},//U
    };
    private static int[][] rhx = new int[][]{
            //____________Moving/Default VV
            {7 /2, 10/2, 6 /2},//L
            {7 /2, 9 /2, 6 /2},//R
            {7 /2, 7 /2, 7 /2},//D
            {20/2, 20/2, 20/2},//U
            //_/2____/2____/2___One Handed Swing VV
            {2 /2, 2 /2, 2 /2},//L
            {22/2, 22/2, 22/2},//R
            {24/2, 16/2, 6 /2},//D
            {6 /2, 16/2, 24/2},//U
            //_/2____/2____/2___Two Handed Swing VV
            {2 /2, 2 /2, 2 /2},//L
            {22/2, 22/2, 22/2},//R
            {28/2, 16/2, 4 /2},//D
            {4 /2, 16/2, 28/2},//U
            //_/2____/2____/2___Stab VV
            {14/2, 6 /2, -2/2},//L
            {2 /2, 10/2, 18/2},//R
            {7 /2, 7 /2, 7 /2},//D
            {20/2, 20/2, 20/2},//U
    };
    private static int[][] rhy = new int[][]{
            {20/2, 20/2, 20/2},//L
            {20/2, 20/2, 20/2},//R
            {18/2, 19/2, 16/2},//D
            {18/2, 16/2, 19/2},//U
            //_/2____/2____/2___One Handed Swing VV
            {11/2, 17/2, 25/2},//L
            {11/2, 17/2, 25/2},//R
            {22/2, 22/2, 22/2},//D
            {8 /2, 8 /2, 8 /2},//U
            //_/2____/2____/2___Two Handed Swing VV
            {10/2, 18/2, 26/2},//L
            {10/2, 18/2, 26/2},//R
            {22/2, 22/2, 22/2},//D
            {8 /2, 8 /2, 8 /2},//U
            //_/2____/2____/2___Stab VV
            {20/2, 20/2, 20/2},//L
            {20/2, 20/2, 20/2},//R
            {18/2, 22/2, 26/2},//D
            {18/2, 12/2, 8 /2},//U
    };
    private static int[][] hands = new int[][]{
            {0, 0, 0},//L
            {1, 1, 1},//R
            {2, 0, 1},//D
            {-1, 0, 1},//U
    };

    public void draw(float sx, float sy, float zoom, Graphics g) {
        drawShadow(sx, sy, zoom);
        int step = stepNum / Humanoid.STEPRATIO;
        if (step == 2) step = 0;
        else if (step == 3) step = 2;
        if (this.getKnockback() != null) {
            if (this.getDirection() == Direction.LEFT) {
                coloredDrawTheMan(step, 0, sx, sy, zoom, Color.red, g);
            }
            if (this.getDirection() == Direction.RIGHT) {
                coloredDrawTheMan(step, 1, sx, sy, zoom, Color.red, g);
            }
            if (this.getDirection() == Direction.DOWN) {
                coloredDrawTheMan(step, 2, sx, sy, zoom, Color.red, g);
            }
            if (this.getDirection() == Direction.UP) {
                coloredDrawTheMan(step, 3, sx, sy, zoom, Color.red, g);
            }
        } else {
            if (this.getDirection() == Direction.LEFT) {
                rotatedDrawTheMan(step, 0, sx, sy, zoom, g);
            }
            if (this.getDirection() == Direction.RIGHT) {
                rotatedDrawTheMan(step, 1, sx, sy, zoom, g);
            }
            if (this.getDirection() == Direction.DOWN) {
                rotatedDrawTheMan(step, 2, sx, sy, zoom, g);
            }
            if (this.getDirection() == Direction.UP) {
                rotatedDrawTheMan(step, 3, sx, sy, zoom, g);
            }
        }
    }

    private void rotatedDrawTheMan(int spritex, int spritey, float sx, float sy, float zoom, Graphics g) {

        SpriteSheet headsheet = Images.squirrelHeadSheet;
        SpriteSheet chestsheet = Images.squirrelChestSheet;
        SpriteSheet legssheet = Images.squirrelLegsSheet;
	    Image hand = Images.squirrelHand;
	    int step = stepNum / Humanoid.STEPRATIO;
        float rotation = 0;//right
        if (spritey == 0) rotation = 180.0f;//left
        if (spritey == 2) rotation = 90.0f;//down
        if (spritey == 3) rotation = -90.0f;//up
        float imageOriginX = getX() - sx - getImageXOffset();
        float imageOriginY = getY() - sy - getImageYOffset();
        int handRow = spritey;
        int handCol = spritex;
        AnimationPoint rightHand = null;
        AnimationPoint leftHand = null;
        if (this.animation != null) {
            rightHand = animation.getRightHand();
            leftHand = animation.getLeftHand();
        } else { //Draw the hand normally for walking, bottom layer
            switch (hands[spritey][spritex]) {
                case -1:
                    //draw the left hand
                    hand.draw((imageOriginX + lhx[handRow][handCol]) * zoom, (imageOriginY + lhy[handRow][handCol]) * zoom, handSize * zoom, handSize * zoom);
                case 0:
                    //draw the right hand
                    drawRotatedWeapon(
                            imageOriginX + rhx[handRow][handCol] + handRadius,
                            imageOriginY + rhy[handRow][handCol] + handRadius,
                            rotation,
                            sx, sy, zoom, g);
                    hand.draw((imageOriginX + rhx[handRow][handCol]) * zoom, (imageOriginY + rhy[handRow][handCol]) * zoom, handSize * zoom, handSize * zoom);
                    break;
                case 1:
                    //draw the left hand
                    hand.draw((imageOriginX + lhx[handRow][handCol]) * zoom, (imageOriginY + lhy[handRow][handCol]) * zoom, handSize * zoom, handSize * zoom);
                    break;
            }
        }
        if (rightHand != null && !rightHand.inFront) {
            drawRotatedWeapon(imageOriginX + rightHand.x() + handRadius, imageOriginY + rightHand.y() + handRadius, rightHand.rotation, sx, sy, zoom, g);
            hand.draw((imageOriginX + rightHand.x()) * zoom, (imageOriginY + rightHand.y()) * zoom, handSize * zoom, handSize * zoom);
        }
        if (leftHand != null && !leftHand.inFront) {
            hand.draw((imageOriginX + leftHand.x()) * zoom, (imageOriginY + leftHand.y()) * zoom, handSize * zoom, handSize * zoom);
        }
        if (this.getDirection() != Direction.UP) {
            chestsheet.getSprite(spritex, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom);
            if (armor[CHEST] != null) {
                armor[CHEST].getImage(CHEST, step, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom);
            }
            legssheet.getSprite(spritex, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom);
            if (armor[LEGS] != null) {
                armor[LEGS].getImage(LEGS, step, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom);
            }
            headsheet.getSprite(spritex, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom);
            if (armor[HEAD] != null) {
                armor[HEAD].getImage(HEAD, step, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom);
            }
        } else {
            headsheet.getSprite(spritex, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom);
            if (armor[HEAD] != null) {
                armor[HEAD].getImage(HEAD, step, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom);
            }
            legssheet.getSprite(spritex, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom);
            if (armor[LEGS] != null) {
                armor[LEGS].getImage(LEGS, step, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom);
            }
            chestsheet.getSprite(spritex, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom);
            if (armor[CHEST] != null) {
                armor[CHEST].getImage(CHEST, step, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom);
            }
        }
        if (this.animation != null) {
        } else { //Draw the hand normally for walking, top layer
            switch (hands[spritey][spritex]) {
                case 0:
                    //draw the left hand
                    hand.draw((imageOriginX + lhx[handRow][handCol]) * zoom, (imageOriginY + lhy[handRow][handCol]) * zoom, handSize * zoom, handSize * zoom);
                    break;
                case 2:
                    //draw the left hand
                    hand.draw((imageOriginX + lhx[handRow][handCol]) * zoom, (imageOriginY + lhy[handRow][handCol]) * zoom, handSize * zoom, handSize * zoom);
                case 1:
                    //draw the right hand
                    drawRotatedWeapon(
                            imageOriginX + rhx[handRow][handCol] + handRadius,
                            imageOriginY + rhy[handRow][handCol] + handRadius,
                            rotation,
                            sx, sy, zoom, g);
                    hand.draw((imageOriginX + rhx[handRow][handCol]) * zoom, (imageOriginY + rhy[handRow][handCol]) * zoom, handSize * zoom, handSize * zoom);
                    break;
            }
        }
        if (rightHand != null && rightHand.inFront) {
            drawRotatedWeapon(imageOriginX + rightHand.x() + handRadius, imageOriginY + rightHand.y() + handRadius, rightHand.rotation, sx, sy, zoom, g);
            hand.draw((imageOriginX + rightHand.x()) * zoom, (imageOriginY + rightHand.y()) * zoom, handSize * zoom, handSize * zoom);
        }
        if (leftHand != null && leftHand.inFront) {
            hand.draw((imageOriginX + leftHand.x()) * zoom, (imageOriginY + leftHand.y()) * zoom, handSize * zoom, handSize * zoom);
        }
    }

    private void coloredDrawTheMan(int spritex, int spritey, float sx, float sy, float zoom, Color color, Graphics g) {

        SpriteSheet headsheet = Images.squirrelHeadSheet;
        SpriteSheet chestsheet = Images.squirrelChestSheet;
        SpriteSheet legssheet = Images.squirrelLegsSheet;
	    Image hand = Images.squirrelHand;
	    int step = stepNum / Humanoid.STEPRATIO;
        float rotation = 0;//right
        if (spritey == 0) rotation = 180.0f;//left
        if (spritey == 2) rotation = 90.0f;//down
        if (spritey == 3) rotation = -90.0f;//up
        float imageOriginX = getX() - sx - getImageXOffset();
        float imageOriginY = getY() - sy - getImageYOffset();
        int handRow = spritey;
        int handCol = spritex;
        AnimationPoint rightHand = null;
        AnimationPoint leftHand = null;
        if (this.animation != null) {
            rightHand = animation.getRightHand();
            leftHand = animation.getLeftHand();
        } else { //Draw the hand normally for walking, bottom layer
            switch (hands[spritey][spritex]) {
                case -1:
                    //draw the left hand
                    hand.draw((imageOriginX + lhx[handRow][handCol]) * zoom, (imageOriginY + lhy[handRow][handCol]) * zoom, handSize * zoom, handSize * zoom, color);
                case 0:
                    //draw the right hand
                    drawRotatedWeapon(
                            imageOriginX + rhx[handRow][handCol] + handRadius,
                            imageOriginY + rhy[handRow][handCol] + handRadius,
                            rotation,
                            sx, sy, zoom, g);
                    hand.draw((imageOriginX + rhx[handRow][handCol]) * zoom, (imageOriginY + rhy[handRow][handCol]) * zoom, handSize * zoom, handSize * zoom, color);
                    break;
                case 1:
                    //draw the left hand
                    hand.draw((imageOriginX + lhx[handRow][handCol]) * zoom, (imageOriginY + lhy[handRow][handCol]) * zoom, handSize * zoom, handSize * zoom, color);
                    break;
            }
        }
        if (rightHand != null && !rightHand.inFront) {
            drawRotatedWeapon(imageOriginX + rightHand.x() + handRadius, imageOriginY + rightHand.y() + handRadius, rightHand.rotation, sx, sy, zoom, g);
            hand.draw((imageOriginX + rightHand.x()) * zoom, (imageOriginY + rightHand.y()) * zoom, handSize * zoom, handSize * zoom, color);
        }
        if (leftHand != null && !leftHand.inFront) {
            hand.draw((imageOriginX + leftHand.x()) * zoom, (imageOriginY + leftHand.y()) * zoom, handSize * zoom, handSize * zoom, color);
        }
        if (this.getDirection() != Direction.UP) {
            chestsheet.getSprite(spritex, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom, color);
            if (armor[CHEST] != null) {
                armor[CHEST].getImage(CHEST, step, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom, color);
            }
            legssheet.getSprite(spritex, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom, color);
            if (armor[LEGS] != null) {
                armor[LEGS].getImage(LEGS, step, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom, color);
            }
            headsheet.getSprite(spritex, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom, color);
            if (armor[HEAD] != null) {
                armor[HEAD].getImage(HEAD, step, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom, color);
            }
        } else {
            headsheet.getSprite(spritex, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom, color);
            if (armor[HEAD] != null) {
                armor[HEAD].getImage(HEAD, step, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom, color);
            }
            legssheet.getSprite(spritex, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom, color);
            if (armor[LEGS] != null) {
                armor[LEGS].getImage(LEGS, step, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom, color);
            }
            chestsheet.getSprite(spritex, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom, color);
            if (armor[CHEST] != null) {
                armor[CHEST].getImage(CHEST, step, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, W * zoom, H * zoom, color);
            }
        }
        if (this.animation != null) {
        } else { //Draw the hand normally for walking, top layer
            switch (hands[spritey][spritex]) {
                case 0:
                    //draw the left hand
                    hand.draw((imageOriginX + lhx[handRow][handCol]) * zoom, (imageOriginY + lhy[handRow][handCol]) * zoom, handSize * zoom, handSize * zoom, color);
                    break;
                case 2:
                    //draw the left hand
                    hand.draw((imageOriginX + lhx[handRow][handCol]) * zoom, (imageOriginY + lhy[handRow][handCol]) * zoom, handSize * zoom, handSize * zoom, color);
                case 1:
                    //draw the right hand
                    drawRotatedWeapon(
                            imageOriginX + rhx[handRow][handCol] + handRadius,
                            imageOriginY + rhy[handRow][handCol] + handRadius,
                            rotation,
                            sx, sy, zoom, g);
                    hand.draw((imageOriginX + rhx[handRow][handCol]) * zoom, (imageOriginY + rhy[handRow][handCol]) * zoom, handSize * zoom, handSize * zoom, color);
                    break;
            }
        }
        if (rightHand != null && rightHand.inFront) {
            drawRotatedWeapon(imageOriginX + rightHand.x() + handRadius, imageOriginY + rightHand.y() + handRadius, rightHand.rotation, sx, sy, zoom, g);
            hand.draw((imageOriginX + rightHand.x()) * zoom, (imageOriginY + rightHand.y()) * zoom, handSize * zoom, handSize * zoom, color);
        }
        if (leftHand != null && leftHand.inFront) {
            hand.draw((imageOriginX + leftHand.x()) * zoom, (imageOriginY + leftHand.y()) * zoom, handSize * zoom, handSize * zoom, color);
        }
    }

    private void drawRotatedWeapon(float x, float y, float angle, float sx, float sy, float zoom, Graphics g) {
        if (this.getEquippedItem() == null) return;
        RotatedBigPicture image = Item.getRotatedPicture(this.getEquippedItem());
        if (image == null) return;
        image.draw(x, y, angle, sx, sy, zoom, g);
    }

    public String toString() {
        return "Squirrel (" + this.getLoc() + ")";
    }
}
