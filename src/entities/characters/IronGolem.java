package entities.characters;

import util.animation.AnimationPoint;
import areas.NewGameArea;

import items.Unarmed;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import skills.Skill;
import skills.Smash;
import util.Images;

public class IronGolem extends Humanoid {

    private static final long serialVersionUID = 1L;

	public IronGolem() {
		super();
		init();
	}
    public IronGolem(int gx, int gy) {
		super(gx, gy);
		init();
	}
	public void init() {
		this.setBaseSpeed(1);
		setHeight(96);
		setWidth(96);
		imageYOffset = 80;
		imageXOffset = 32;
		equippedItem = new Unarmed();
		equippedItem.equip(this);
		initializeToBossLevel();
	}
    public void initializeToBossLevel() {
        this.modifyBaseStat(HP, 5000);
        this.modifyBaseStat(MP, 30);
        while (this.getLvl() < 15) {
            this.levelUp();
        }
        this.addXP(5000);
        this.setBaseStat(ARMOR, 50);
        this.setBaseStat(MAGIC_RESIST, 50);
        this.modifyBaseStat(MPREGEN, .05);
        this.setBaseStat(ATTACK, 10);
        this.getSkills().add(0, new Smash());
    }

    public void tick(NewGameArea area) {
        if (knockback != null) {
            knockback = null;
        }
        movingTick(area);
        tickHPMP(this);
        tickSkills();
    }

    public void tickSkills() {
        for (Skill skill : this.getSkills()) {
            skill.tick();
        }
    }

    private static int lhWOffset = 64;
    private static int[][] lhx = new int[][]{
            //____________Moving/Default VV
            {64, 60, 68},//L
            {64, 60, 68},//R
            {64, 64, 64},//D
            {7, 7, 7},//U
    };
    private static int[][] lhy = new int[][]{
            {48, 48, 48},//L
            {48, 48, 48},//R
            {48, 52, 44},//D
            {48, 44, 52},//U
    };
    private static int[][] rhx = new int[][]{
            //____________Moving/Default VV
            {7, 10, 6},//L
            {7, 9, 6},//R
            {7, 7, 7},//D
            {64, 64, 64},//U
    };
    private static int[][] rhy = new int[][]{
            {48, 48, 48},//L
            {48, 48, 48},//R
            {48, 52, 44},//D
            {48, 44, 52},//U
    };
    private static int[][] hands = new int[][]{
            {0, 0, 0},//L
            {1, 1, 1},//R
            {2, 0, 1},//D
            {-1, 0, 1},//U
    };

    public void draw(float sx, float sy, float zoom, Graphics g) {
        int step = stepNum / Humanoid.STEPRATIO;
        if (step == 2) step = 0;
        else if (step == 3) step = 2;
        if (this.getKnockback() != null) {
            if (this.getDirection() == Direction.LEFT) {
                coloredDrawTheMan(step, 0, sx, sy, zoom, Color.red);
            }
            if (this.getDirection() == Direction.RIGHT) {
                coloredDrawTheMan(step, 1, sx, sy, zoom, Color.red);
            }
            if (this.getDirection() == Direction.DOWN) {
                coloredDrawTheMan(step, 2, sx, sy, zoom, Color.red);
            }
            if (this.getDirection() == Direction.UP) {
                coloredDrawTheMan(step, 3, sx, sy, zoom, Color.red);
            }
        } else {
            if (this.getDirection() == Direction.LEFT) {
                newDrawTheMan(step, 0, sx, sy, zoom);
            }
            if (this.getDirection() == Direction.RIGHT) {
                newDrawTheMan(step, 1, sx, sy, zoom);
            }
            if (this.getDirection() == Direction.DOWN) {
                newDrawTheMan(step, 2, sx, sy, zoom);
            }
            if (this.getDirection() == Direction.UP) {
                newDrawTheMan(step, 3, sx, sy, zoom);
            }
        }
    }

    public String toString() {
        return "Iron Golem " + this.getLocString();
    }

    private static final int handSize = 32;

    private void newDrawTheMan(int spritex, int spritey, float sx, float sy, float zoom) {
        SpriteSheet spritesheet = Images.ironGolemSheet;
        int step = stepNum / Humanoid.STEPRATIO;
        Image hand = Images.ironGolemHandSheet.getSprite(0, spritey);
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
                    hand.draw((imageOriginX + rhx[handRow][handCol]) * zoom, (imageOriginY + rhy[handRow][handCol]) * zoom, handSize * zoom, handSize * zoom);
                    break;
                case 1:
                    //draw the left hand
                    hand.draw((imageOriginX + lhx[handRow][handCol]) * zoom, (imageOriginY + lhy[handRow][handCol]) * zoom, handSize * zoom, handSize * zoom);
                    break;
            }
        }
        if (rightHand != null && !rightHand.inFront) {
            hand.draw((imageOriginX + rightHand.x()) * zoom, (imageOriginY + rightHand.y()) * zoom, handSize * zoom, handSize * zoom);
        }
        if (leftHand != null && !leftHand.inFront) {
            hand.draw((imageOriginX + leftHand.x()) * zoom, (imageOriginY + leftHand.y()) * zoom, handSize * zoom, handSize * zoom);
        }
        spritesheet.getSprite(spritex, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, getWidth() * zoom, getHeight() * zoom);
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
                    hand.draw((imageOriginX + rhx[handRow][handCol]) * zoom, (imageOriginY + rhy[handRow][handCol]) * zoom, handSize * zoom, handSize * zoom);
                    break;
            }
        }
        if (rightHand != null && rightHand.inFront) {
            hand.draw((imageOriginX + rightHand.x()) * zoom, (imageOriginY + rightHand.y()) * zoom, handSize * zoom, handSize * zoom);
        }
        if (leftHand != null && leftHand.inFront) {
            hand.draw((imageOriginX + leftHand.x()) * zoom, (imageOriginY + leftHand.y()) * zoom, handSize * zoom, handSize * zoom);
        }
    }

    private void coloredDrawTheMan(int spritex, int spritey, float sx, float sy, float zoom, Color color) {
        SpriteSheet spritesheet = Images.ironGolemSheet;
        int step = stepNum / Humanoid.STEPRATIO;
        Image hand = Images.ironGolemHandSheet.getSprite(0, spritey);
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
                    hand.draw((imageOriginX + rhx[handRow][handCol]) * zoom, (imageOriginY + rhy[handRow][handCol]) * zoom, handSize * zoom, handSize * zoom, color);
                    break;
                case 1:
                    //draw the left hand
                    hand.draw((imageOriginX + lhx[handRow][handCol]) * zoom, (imageOriginY + lhy[handRow][handCol]) * zoom, handSize * zoom, handSize * zoom, color);
                    break;
            }
        }
        if (rightHand != null && !rightHand.inFront) {
            hand.draw((imageOriginX + rightHand.x()) * zoom, (imageOriginY + rightHand.y()) * zoom, handSize * zoom, handSize * zoom, color);
        }
        if (leftHand != null && !leftHand.inFront) {
            hand.draw((imageOriginX + leftHand.x()) * zoom, (imageOriginY + leftHand.y()) * zoom, handSize * zoom, handSize * zoom, color);
        }
        spritesheet.getSprite(spritex, spritey).draw(imageOriginX * zoom, imageOriginY * zoom, getWidth() * zoom, getHeight() * zoom, color);
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
                    hand.draw((imageOriginX + rhx[handRow][handCol]) * zoom, (imageOriginY + rhy[handRow][handCol]) * zoom, handSize * zoom, handSize * zoom, color);
                    break;
            }
        }
        if (rightHand != null && rightHand.inFront) {
            hand.draw((imageOriginX + rightHand.x()) * zoom, (imageOriginY + rightHand.y()) * zoom, handSize * zoom, handSize * zoom, color);
        }
        if (leftHand != null && leftHand.inFront) {
            hand.draw((imageOriginX + leftHand.x()) * zoom, (imageOriginY + leftHand.y()) * zoom, handSize * zoom, handSize * zoom, color);
        }
    }
}
