package entities.characters;

import items.Item;

import items.Unarmed;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import util.Images;
import util.RotatedBigPicture;
import util.animation.AnimationPoint;

public class GiantHuman extends Humanoid {

	private static final long serialVersionUID = 1L;

	private String name;
	private Color skinColor, torsoColor, pantsColor;

	private static final int handRadius = 10;
	public GiantHuman(int gx, int gy) {
		super(gx, gy);
		init();
	}
	public GiantHuman(){
		super();
		init();
	}
	protected void init(){
		this.setBaseSpeed(2.0f);
		setHeight(64);
		setWidth(64);
		imageYOffset = 32;
		imageXOffset = 32;
		equippedItem = new Unarmed();
		equippedItem.equip(this);
	}
	private static int[][] lhx = new int[][]{
			//____________Moving/Default VV
			{ 20*2,18*2, 21*2},//L
			{ 20*2, 17*2, 21*2},//R
			{ 20*2, 20*2, 20*2},//D
			{7*2,7*2,7*2},//U
			////____________One Handed Swing VV
			//{ 20,19, 21},//L
			//{ 20, 19, 21},//R
			//{ 20, 20, 20},//D
			//{7,7,7},//U
			////____________Two Handed Swing VV
			//{ 4,4, 4},//L
			//{20,20,20},//R
			//{28,16,4},//D
			//{4,16,28},//U
			////____________Stab VV
			//{ 20,18, 21},//L
			//{ 20, 17, 21},//R
			//{ 20, 20, 20},//D
			//{7,7,7},//U
	};
	private static int[][] lhy = new int[][]{
			{20*2,20*2,20*2},//L
			{20*2,20*2,20*2},//R
			{18*2,19*2,16*2},//D
			{18*2,16*2,19*2},//U
			////____________One Handed Swing VV
			//{20,20,20},//L
			//{20,20,20},//R
			//{18,19,17},//D
			//{18,17,19},//U
			////____________Two Handed Swing VV
			//{6,16,26},//L
			//{6,16,26},//R
			//{20,20,20},//D
			//{10,10,10},//U
			////____________Stab VV
			//{20,20,20},//L
			//{20,20,20},//R
			//{18,19,17},//D
			//{18,17,19},//U
	};
	private static int[][] rhx = new int[][]{
			//____________Moving/Default VV
			{ 7*2,10*2, 6*2},//L
			{ 7*2, 9*2, 6*2},//R
			{ 7*2, 7*2, 7*2},//D
			{20*2,20*2,20*2},//U
			////____________One Handed Swing VV
			//{ 2, 2, 2},//L
			//{22,22,22},//R
			//{24,16,6},//D
			//{6,16,24},//U
			////____________Two Handed Swing VV
			//{ 2, 2, 2},//L
			//{22,22,22},//R
			//{28,16,4},//D
			//{4,16,28},//U
			////____________Stab VV
			//{14,6, -2},//L
			//{ 2, 10, 18},//R
			//{ 7, 7, 7},//D
			//{20,20,20},//U
	};
	private static int[][] rhy = new int[][]{
			{20*2,20*2,20*2},//L
			{20*2,20*2,20*2},//R
			{18*2,19*2,16*2},//D
			{18*2,16*2,19*2},//U
			////____________One Handed Swing VV
			//{11,17,25},//L
			//{11,17,25},//R
			//{22,22,22},//D
			//{8,8,8},//U
			////____________Two Handed Swing VV
			//{5,16,26},//L
			//{5,16,26},//R
			//{22,22,22},//D
			//{8,8,8},//U
			////____________Stab VV
			//{20,20,20},//L
			//{20,20,20},//R
			//{18,22,26},//D
			//{18,12,8},//U

	};
	private static int[][] hands = new int[][]{
			{ 0, 0, 0},//L
			{ 1, 1, 1},//R
			{ 2, 0, 1},//D
			{-1, 0, 1},//U
	};

	public void draw(float sx, float sy, float zoom, Graphics g) {
		drawShadow(sx, sy, zoom);
		int step = stepNum/Humanoid.STEPRATIO;
		if(step == 2)step = 0;
		else if(step == 3)step = 2;
		if(this.getKnockback() != null){
			if(this.getDirection() == Direction.LEFT){
				coloredDrawTheMan(step, 0, sx, sy, zoom, Color.red, g);
			}
			if(this.getDirection() == Direction.RIGHT){
				coloredDrawTheMan(step, 1, sx, sy, zoom, Color.red, g);
			}
			if(this.getDirection() == Direction.DOWN){
				coloredDrawTheMan(step, 2, sx, sy, zoom, Color.red, g);
			}
			if(this.getDirection() == Direction.UP){
				coloredDrawTheMan(step, 3, sx, sy, zoom, Color.red, g);
			}
		}
		else{
			if(this.getDirection() == Direction.LEFT){
				rotatedDrawTheMan(step, 0, sx, sy, zoom, g);
			}
			if(this.getDirection() == Direction.RIGHT){
				rotatedDrawTheMan(step, 1, sx, sy, zoom, g);
			}
			if(this.getDirection() == Direction.DOWN){
				rotatedDrawTheMan(step, 2, sx, sy, zoom, g);
			}
			if(this.getDirection() == Direction.UP){
				rotatedDrawTheMan(step, 3, sx, sy, zoom, g);
			}
		}
//		if(this.armor[HEAD] != null){
//			Images.itemSheet.getSprite(this.armor[HEAD].getImageID()[0], this.armor[HEAD].getImageID()[1]).draw(getX() - sx, getY() - sy - getImageYOffset());
//		}
	}


	private void rotatedDrawTheMan(int spritex, int spritey, float sx, float sy, float zoom, Graphics g){

		SpriteSheet spritesheet = Images.baldmanSheet;
		SpriteSheet headsheet = Images.humanHeadBWSheet;
		SpriteSheet chestsheet = Images.humanChestBWSheet;
		SpriteSheet legssheet = Images.humanLegsBWSheet;

		int step = stepNum/Humanoid.STEPRATIO;
		Image hand = Images.baldmanHandBW;
		float rotation = 0;//right
		if(spritey == 0)rotation = 180.0f;//left
		if(spritey == 2)rotation = 90.0f;//down
		if(spritey == 3)rotation = -90.0f;//up

		float imageOriginX = getX() - sx - getImageXOffset();//THIS IS CORRECT!
		float imageOriginY =  getY() - sy - getImageYOffset();// It IS supposed to be getX,Y()!
		int handRow = spritey;
		int handCol = spritex;
		AnimationPoint rightHand = null;
		AnimationPoint leftHand = null;
		if(this.animation!=null){
			rightHand = animation.getRightHand();
			leftHand = animation.getLeftHand();
		}
		else{ //Draw the hand normally for walking, bottom layer
			switch (hands[spritey][spritex]){
				case -1:
					//draw the left hand
					hand.draw((imageOriginX + lhx[handRow][handCol])*zoom, (imageOriginY + lhy[handRow][handCol])*zoom, handRadius*zoom, handRadius*zoom, skinColor);
				case 0:
					//draw the right hand
					drawRotatedWeapon(
							imageOriginX + rhx[handRow][handCol] + handRadius/2,
							imageOriginY + rhy[handRow][handCol] + handRadius/2,
							rotation,
							sx, sy, zoom, g);
					hand.draw((imageOriginX + rhx[handRow][handCol])*zoom, (imageOriginY + rhy[handRow][handCol])*zoom, handRadius*zoom, handRadius*zoom, skinColor);


					break;
				case 1:
					//draw the left hand
					hand.draw((imageOriginX + lhx[handRow][handCol])*zoom, (imageOriginY + lhy[handRow][handCol])*zoom, handRadius*zoom, handRadius*zoom, skinColor);
					break;
			}
		}
		if(rightHand!=null && !rightHand.inFront){
			drawRotatedWeapon(imageOriginX + rightHand.x() + handRadius/2, imageOriginY + rightHand.y() + handRadius/2, rightHand.rotation, sx, sy, zoom, g);
			hand.draw((imageOriginX + rightHand.x())*zoom, (imageOriginY + rightHand.y())*zoom, handRadius*zoom, handRadius*zoom, skinColor);
		}
		if(leftHand!=null && !leftHand.inFront){
			hand.draw((imageOriginX + leftHand.x())*zoom, (imageOriginY + leftHand.y())*zoom, handRadius*zoom, handRadius*zoom, skinColor);
		}


		//spritesheet.setCenterOfRotation(spritex*(spritesheet.getWidth()/spritesheet.getHorizontalCount()), spritey);
		//spritesheet.getSprite(spritex, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 32*zoom, 32*zoom);
		if(this.getDirection() == Direction.UP){
			chestsheet.getSprite(spritex, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom, torsoColor);
			if(armor[CHEST] != null){
				armor[CHEST].getImage(CHEST, step, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom);
			}
			legssheet.getSprite(spritex, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom, pantsColor);
			if(armor[LEGS] != null){
				armor[LEGS].getImage(LEGS, step, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom);
			}
			headsheet.getSprite(spritex, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom, skinColor);
			if(armor[HEAD] != null){
				armor[HEAD].getImage(HEAD, step, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom);
			}
		}else{
			headsheet.getSprite(spritex, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom, skinColor);
			if(armor[HEAD] != null){
				armor[HEAD].getImage(HEAD, step, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom);
			}
			legssheet.getSprite(spritex, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom, pantsColor);
			if(armor[LEGS] != null){
				armor[LEGS].getImage(LEGS, step, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom);
			}
			chestsheet.getSprite(spritex, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom, torsoColor);
			if(armor[CHEST] != null){
				armor[CHEST].getImage(CHEST, step, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom);
			}
		}

		if(this.animation!=null){

		}
		else{ //Draw the hand normally for walking, top layer
			switch (hands[spritey][spritex]){
				case 0:
					//draw the left hand
					hand.draw((imageOriginX + lhx[handRow][handCol])*zoom, (imageOriginY + lhy[handRow][handCol])*zoom, handRadius*zoom, handRadius*zoom, skinColor);
					break;
				case 2:
					//draw the left hand
					hand.draw((imageOriginX + lhx[handRow][handCol])*zoom, (imageOriginY + lhy[handRow][handCol])*zoom, handRadius*zoom, handRadius*zoom, skinColor);
				case 1:
					//draw the right hand
					drawRotatedWeapon(
							imageOriginX + rhx[handRow][handCol] + handRadius/2,
							imageOriginY + rhy[handRow][handCol] + handRadius/2,
							rotation,
							sx, sy, zoom, g);
					hand.draw((imageOriginX + rhx[handRow][handCol])*zoom, (imageOriginY + rhy[handRow][handCol])*zoom, handRadius*zoom, handRadius*zoom, skinColor);
					break;
			}
		}
		if(rightHand!=null && rightHand.inFront){
			drawRotatedWeapon(imageOriginX + rightHand.x() + handRadius/2, imageOriginY + rightHand.y() + handRadius/2, rightHand.rotation, sx, sy, zoom, g);
			hand.draw((imageOriginX + rightHand.x())*zoom, (imageOriginY + rightHand.y())*zoom, handRadius*zoom, handRadius*zoom, skinColor);
		}
		if(leftHand!=null && leftHand.inFront){
			hand.draw((imageOriginX + leftHand.x())*zoom, (imageOriginY + leftHand.y())*zoom, handRadius*zoom, handRadius*zoom, skinColor);
		}

	}


	private void coloredDrawTheMan(int spritex, int spritey, float sx, float sy, float zoom, Color color,Graphics g){

		SpriteSheet spritesheet = Images.baldmanSheet;
		SpriteSheet headsheet = Images.humanHeadBWSheet;
		SpriteSheet chestsheet = Images.humanChestBWSheet;
		SpriteSheet legssheet = Images.humanLegsBWSheet;

		int step = stepNum/Humanoid.STEPRATIO;
		Image hand = Images.baldmanHandBW;
		float rotation = 0;//right
		if(spritey == 0)rotation = 180.0f;//left
		if(spritey == 2)rotation = 90.0f;//down
		if(spritey == 3)rotation = -90.0f;//up

		float imageOriginX = getX() - sx - getImageXOffset();//THIS IS CORRECT!
		float imageOriginY =  getY() - sy - getImageYOffset();// It IS supposed to be getX,Y()!
		int handRow = spritey;
		int handCol = spritex;
		AnimationPoint rightHand = null;
		AnimationPoint leftHand = null;
		if(this.animation!=null){
			rightHand = animation.getRightHand();
			leftHand = animation.getLeftHand();
		}
		else{ //Draw the hand normally for walking, bottom layer
			switch (hands[spritey][spritex]){
				case -1:
					//draw the left hand
					hand.draw((imageOriginX + lhx[handRow][handCol])*zoom, (imageOriginY + lhy[handRow][handCol])*zoom, handRadius*zoom, handRadius*zoom, color);
				case 0:
					//draw the right hand
					drawRotatedWeapon(
							imageOriginX + rhx[handRow][handCol] + handRadius/2,
							imageOriginY + rhy[handRow][handCol] + handRadius/2,
							rotation,
							sx, sy, zoom, g);
					hand.draw((imageOriginX + rhx[handRow][handCol])*zoom, (imageOriginY + rhy[handRow][handCol])*zoom, handRadius*zoom, handRadius*zoom, color);


					break;
				case 1:
					//draw the left hand
					hand.draw((imageOriginX + lhx[handRow][handCol])*zoom, (imageOriginY + lhy[handRow][handCol])*zoom, handRadius*zoom, handRadius*zoom, color);
					break;
			}
		}
		if(rightHand!=null && !rightHand.inFront){
			drawRotatedWeapon(imageOriginX + rightHand.x() + handRadius/2, imageOriginY + rightHand.y() + handRadius/2, rightHand.rotation, sx, sy, zoom, g);
			hand.draw((imageOriginX + rightHand.x())*zoom, (imageOriginY + rightHand.y())*zoom, handRadius*zoom, handRadius*zoom, color);
		}
		if(leftHand!=null && !leftHand.inFront){
			hand.draw((imageOriginX + leftHand.x())*zoom, (imageOriginY + leftHand.y())*zoom, handRadius*zoom, handRadius*zoom, color);
		}


		//spritesheet.setCenterOfRotation(spritex*(spritesheet.getWidth()/spritesheet.getHorizontalCount()), spritey);
		//spritesheet.getSprite(spritex, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom);
		if(this.getDirection() == Direction.UP){
			chestsheet.getSprite(spritex, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom, color);
			if(armor[CHEST] != null){
				armor[CHEST].getImage(CHEST, step, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom, color);
			}
			legssheet.getSprite(spritex, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom, color);
			if(armor[LEGS] != null){
				armor[LEGS].getImage(LEGS, step, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom, color);
			}
			headsheet.getSprite(spritex, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom, color);
			if(armor[HEAD] != null){
				armor[HEAD].getImage(HEAD, step, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom, color);
			}
		}else{
			headsheet.getSprite(spritex, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom, color);
			if(armor[HEAD] != null){
				armor[HEAD].getImage(HEAD, step, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom, color);
			}
			legssheet.getSprite(spritex, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom, color);
			if(armor[LEGS] != null){
				armor[LEGS].getImage(LEGS, step, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom, color);
			}
			chestsheet.getSprite(spritex, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom, color);
			if(armor[CHEST] != null){
				armor[CHEST].getImage(CHEST, step, spritey).draw(imageOriginX*zoom, imageOriginY*zoom, 64*zoom, 64*zoom, color);
			}
		}

		if(this.animation!=null){

		}
		else{ //Draw the hand normally for walking, top layer
			switch (hands[spritey][spritex]){
				case 0:
					//draw the left hand
					hand.draw((imageOriginX + lhx[handRow][handCol])*zoom, (imageOriginY + lhy[handRow][handCol])*zoom, handRadius*zoom, handRadius*zoom, color);
					break;
				case 2:
					//draw the left hand
					hand.draw((imageOriginX + lhx[handRow][handCol])*zoom, (imageOriginY + lhy[handRow][handCol])*zoom, handRadius*zoom, handRadius*zoom, color);
				case 1:
					//draw the right hand
					drawRotatedWeapon(
							imageOriginX + rhx[handRow][handCol] + handRadius/2,
							imageOriginY + rhy[handRow][handCol] + handRadius/2,
							rotation,
							sx, sy, zoom, g);
					hand.draw((imageOriginX + rhx[handRow][handCol])*zoom, (imageOriginY + rhy[handRow][handCol])*zoom, handRadius*zoom, handRadius*zoom, color);
					break;
			}
		}
		if(rightHand!=null && rightHand.inFront){
			drawRotatedWeapon(imageOriginX + rightHand.x() + handRadius/2, imageOriginY + rightHand.y() + handRadius/2, rightHand.rotation, sx, sy, zoom, g);
			hand.draw((imageOriginX + rightHand.x())*zoom, (imageOriginY + rightHand.y())*zoom, handRadius*zoom, handRadius*zoom, color);
		}
		if(leftHand!=null && leftHand.inFront){
			hand.draw((imageOriginX + leftHand.x())*zoom, (imageOriginY + leftHand.y())*zoom, handRadius*zoom, handRadius*zoom, color);
		}

	}

	private void drawRotatedWeapon(float x, float y, float angle, float sx, float sy, float zoom, Graphics g){
		if(this.getEquippedItem() == null)return;
		RotatedBigPicture image = Item.getRotatedPicture(this.getEquippedItem());
		if(image == null) return;
		image.draw(x, y, angle, sx, sy, zoom, g);
	}

	public void setName(String n) {
		name = n;
	}

	public void setSkinColor(Color sc) {
		skinColor = sc;
	}

	public void setTorsoColor(Color tc) {
		torsoColor = tc;
	}

	public void setPantsColor(Color pc) {
		pantsColor = pc;
	}

	public String toString(){
		return "GiantHuman " + this.getLocString();
	}
}
