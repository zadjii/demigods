package util.animation;

import effects.RotatedAttackEffect;
import entities.characters.Character.Direction;
import org.lwjgl.util.Point;
import util.Constants;
import util.Maths;

import java.io.Serializable;
import java.util.ArrayList;

public class Animation implements Serializable {
	protected ArrayList<AnimationPoint> rightHand = new ArrayList<AnimationPoint>();
	protected ArrayList<AnimationPoint> leftHand = new ArrayList<AnimationPoint>();

	/**
	 *
	 * @return whether or not the animation is completed.
	 */
	public boolean tick(){
		try {
			rightHand.remove(0);
			leftHand.remove(0);
			return (rightHand.size()==0)||(leftHand.size()==0);
		} catch (NullPointerException e) {
			return true;
		}
	}

	public AnimationPoint getRightHand(){
		return rightHand.get(0);
	}
	public AnimationPoint getLeftHand(){
		return leftHand.get(0);
	}
	public int getDuration(){
		return rightHand.size();
	}
	protected static void makePoints(Point start, Point end, int frames, float rotation,boolean infront, ArrayList<AnimationPoint> hand){
		double dist = Maths.dist(start, end);
		double totalDX = end.getX() - start.getX();
		double totalDY = end.getY() - start.getY();
		double dx = totalDX/frames;
		double dy = totalDY/frames;
		double absX = start.getX();
		double absY = start.getY();
		int numFrames = 0;
		Point point = new Point((int)absX, (int)absY);
		while(numFrames != frames){
			numFrames++;
			hand.add(new AnimationPoint(point, infront, rotation));
			absX += dx;
			absY += dy;
			point = new Point((int)absX, (int)absY);
		}
	}
	protected static void makePoints(Point start, Point end, int frames, float initRotation, float finalRotation, boolean infront, ArrayList<AnimationPoint> hand){
		double dist = Maths.dist(start, end);
		double totalDX = end.getX() - start.getX();
		double totalDY = end.getY() - start.getY();
		double totalDR = finalRotation - initRotation;

		double dx = totalDX/frames;
		double dy = totalDY/frames;
		double dr = totalDR/frames;

		double absX = start.getX();
		double absY = start.getY();
		float absR = initRotation;
		int numFrames = 0;
		Point point = new Point((int)absX, (int)absY);
		while(numFrames != frames){
			numFrames++;
			hand.add(new AnimationPoint(point, infront, absR));
			absX += dx;
			absY += dy;
			absR += dr;
			point = new Point((int)absX, (int)absY);
		}
	}
	protected static int ONE_HANDED_SWING_DURATION = Constants.ITEM_SPEED - 30;
	protected static int TWO_HANDED_SWING_DURATION = Constants.GREATSWORD_SPEED - 30;
	protected static int PUNCH_DURATION = 20;
	public static Animation getOneHandedSwing(Direction dir){
		Animation swing = new Animation();
		if(dir == Direction.LEFT){
			makePoints(new Point(2,6), new Point(2,26), ONE_HANDED_SWING_DURATION, 180f, false, swing.rightHand);
			makePoints(new Point(20,20), new Point(18,20), ONE_HANDED_SWING_DURATION, 180f, true, swing.leftHand);
		}
		if(dir == Direction.RIGHT){
			makePoints(new Point(22,6), new Point(22,26), ONE_HANDED_SWING_DURATION, 0f, true, swing.rightHand);
			makePoints(new Point(20,20), new Point(18,20), ONE_HANDED_SWING_DURATION, 0f, false, swing.leftHand);
		}
		if(dir == Direction.DOWN){
			makePoints(new Point(6,22), new Point(24,22), ONE_HANDED_SWING_DURATION, 270, true, swing.rightHand);
			makePoints(new Point(20,17), new Point(20,20), ONE_HANDED_SWING_DURATION, 270f, true, swing.leftHand);
		}
		if(dir == Direction.UP){
			makePoints(new Point(6,8), new Point(24,8), ONE_HANDED_SWING_DURATION, 90f, false, swing.rightHand);
			makePoints(new Point(7,17), new Point(7,20), ONE_HANDED_SWING_DURATION, 90f, false, swing.leftHand);
		}
		return swing;
	}
	public static Animation getStab(Direction dir){
		Animation stab = new Animation();
		if(dir == Direction.LEFT){
			makePoints(new Point(14,20), new Point(-16,20), ONE_HANDED_SWING_DURATION, 180f, false, stab.rightHand);
			makePoints(new Point(20,20), new Point(18,20), ONE_HANDED_SWING_DURATION, 180f, true, stab.leftHand);
		}
		if(dir == Direction.RIGHT){
			makePoints(new Point(2,20), new Point(40,20), ONE_HANDED_SWING_DURATION, 0f, true, stab.rightHand);
			makePoints(new Point(20,20), new Point(18,20), ONE_HANDED_SWING_DURATION, 0f, false, stab.leftHand);
		}
		if(dir == Direction.DOWN){
			makePoints(new Point(7,16), new Point(7,40), ONE_HANDED_SWING_DURATION, 270, true, stab.rightHand);
			makePoints(new Point(20,17), new Point(20,20), ONE_HANDED_SWING_DURATION, 270f, true, stab.leftHand);
		}
		if(dir == Direction.UP){
			makePoints(new Point(20,18), new Point(20,0), ONE_HANDED_SWING_DURATION, 90f, false, stab.rightHand);
			makePoints(new Point(7,17), new Point(7,20), ONE_HANDED_SWING_DURATION, 90f, false, stab.leftHand);
		}
		return stab;
	}
	public static Animation getTwoHandedSwing(Direction dir){
		Animation swing = new Animation();
		if(dir == Direction.LEFT){
			makePoints(new Point(2,0), new Point(2,30), TWO_HANDED_SWING_DURATION, 180f, false, swing.rightHand);
			makePoints(new Point(4,0), new Point(4,30), TWO_HANDED_SWING_DURATION, 180f, true, swing.leftHand);
		}
		if(dir == Direction.RIGHT){
			makePoints(new Point(22,0), new Point(22,30), TWO_HANDED_SWING_DURATION, 0f, true, swing.rightHand);
			makePoints(new Point(20,0), new Point(20,30), TWO_HANDED_SWING_DURATION, 0f, false, swing.leftHand);
		}
		if(dir == Direction.DOWN){
			makePoints(new Point(4,22), new Point(28,22), TWO_HANDED_SWING_DURATION, 270, true, swing.rightHand);
			makePoints(new Point(6,22), new Point(30,22), TWO_HANDED_SWING_DURATION, 270f, true, swing.leftHand);
		}
		if(dir == Direction.UP){
			makePoints(new Point(4,8), new Point(28,8), TWO_HANDED_SWING_DURATION, 90f, false, swing.rightHand);
			makePoints(new Point(2,8), new Point(26,8), TWO_HANDED_SWING_DURATION, 90f, false, swing.leftHand);
		}
		return swing;
	}

	public static Animation getMassivePunch(Direction dir, Point target){
		Animation stab = new Animation();
		if(dir == Direction.LEFT){
			makePoints(new Point(72,10), new Point(-16,10), PUNCH_DURATION, 180f, false, stab.rightHand);
			makePoints(new Point(-16,10), target, PUNCH_DURATION, 180f, false, stab.rightHand);
			makePoints(new Point(20,20), new Point(18,20), PUNCH_DURATION, 180f, true, stab.leftHand);
			makePoints(new Point(20,20), new Point(18,20), PUNCH_DURATION, 180f, true, stab.leftHand);
		}
		if(dir == Direction.RIGHT){
			makePoints(new Point(0,48), new Point(96,48), PUNCH_DURATION, 0f, true, stab.rightHand);
			makePoints( new Point(96,48), target, PUNCH_DURATION, 0f, true, stab.rightHand);
			makePoints(new Point(20,20), new Point(18,20), PUNCH_DURATION, 0f, false, stab.leftHand);
			makePoints(new Point(20,20), new Point(18,20), PUNCH_DURATION, 0f, false, stab.leftHand);
		}
		if(dir == Direction.DOWN){
			makePoints(new Point(-16,16), new Point(-16,72), PUNCH_DURATION, 270, true, stab.rightHand);
			makePoints(new Point(-16,72), target, PUNCH_DURATION, 270, true, stab.rightHand);
			makePoints(new Point(20,17), new Point(20,20), PUNCH_DURATION, 270f, true, stab.leftHand);
			makePoints(new Point(20,17), new Point(20,20), PUNCH_DURATION, 270f, true, stab.leftHand);
		}
		if(dir == Direction.UP){
			makePoints(new Point(96,72), new Point(96,0), PUNCH_DURATION, 90f, false, stab.rightHand);
			makePoints(new Point(96,0), target, PUNCH_DURATION, 90f, false, stab.rightHand);
			makePoints(new Point(7,17), new Point(7,20), PUNCH_DURATION, 90f, false, stab.leftHand);
			makePoints(new Point(7,17), new Point(7,20), PUNCH_DURATION, 90f, false, stab.leftHand);
		}
		return stab;
	}

	public static Animation getSmash(Direction dir){
		Animation smash = new Animation();
		if(dir == Direction.LEFT){
			makePoints(new Point(-16,16), new Point(-16,72), PUNCH_DURATION, 180f, false, smash.rightHand);
			makePoints(new Point(20,16), new Point(20,72), PUNCH_DURATION, 180f, true, smash.leftHand);
		}
		if(dir == Direction.RIGHT){
			makePoints(new Point(-16,16), new Point(-16,72), PUNCH_DURATION, 0f, true, smash.rightHand);
			makePoints(new Point(20,16), new Point(20,72), PUNCH_DURATION, 0f, false, smash.leftHand);
		}
		if(dir == Direction.DOWN){
			makePoints(new Point(-16,16), new Point(-16,72), PUNCH_DURATION, 270, true, smash.rightHand);
			makePoints(new Point(20,16), new Point(20,72), PUNCH_DURATION, 270f, true, smash.leftHand);
		}
		if(dir == Direction.UP){
			makePoints(new Point(-16,16), new Point(-16,72), PUNCH_DURATION, 90f, false, smash.rightHand);
			makePoints(new Point(20,16), new Point(20,72), PUNCH_DURATION, 90f, false, smash.leftHand);
		}
		return smash;
	}

	public static Animation getRotatedSwing(Point src, Point tgt, float radius, int type, int imageOffsetX, int imageOffsetY){
		Animation animation = new Animation();
		int duration = ONE_HANDED_SWING_DURATION;
		if(type == RotatedAttackEffect.GREATSWORD_ATTACK
				||type == RotatedAttackEffect.POLEARM_ATTACK) duration = TWO_HANDED_SWING_DURATION;

		double distX = tgt.getX() - src.getX();
		double distY = tgt.getY() - src.getY();
		float theta = (float)(Math.toDegrees(Math.atan2(distY, distX)));
		float alpha = 55;//1/2 * one handed swing arc
		if(type == RotatedAttackEffect.GREATSWORD_ATTACK
				||type == RotatedAttackEffect.POLEARM_ATTACK) alpha = 80;
		float increment = (2*alpha)/duration;

		if(Math.random() < 1.1){//if(true)normal swing direction()
			for(float angle = theta - alpha; angle < theta + alpha; angle += increment){

				int rx = (int)(radius * Math.cos(Math.toRadians(angle)));
				int ry = (int)(radius * Math.sin(Math.toRadians(angle)));
				int lx = (int)((radius-2) * Math.cos(Math.toRadians(angle)));
				int ly = (int)((radius-2) * Math.sin(Math.toRadians(angle)));

				rx+=imageOffsetX;
				ry+=imageOffsetY;
				lx+=imageOffsetX;
				ly+=imageOffsetY;

				AnimationPoint rhPoint = new AnimationPoint(new Point(rx, ry),true, angle );
				AnimationPoint lhPoint = new AnimationPoint(new Point(lx, ly),true, angle );
				animation.rightHand.add(rhPoint);
				animation.leftHand.add(lhPoint);
			}
		}
//		else{//this is the opposite swing direction, if I can get the animation to be flipped.
//			for(float angle = theta + alpha; angle > theta - alpha; angle -= increment){
//
//				int rx = (int)(radius * Math.cos(Math.toRadians(angle)));
//				int ry = (int)(radius * Math.sin(Math.toRadians(angle)));
//				int lx = (int)((radius-2) * Math.cos(Math.toRadians(angle)));
//				int ly = (int)((radius-2) * Math.sin(Math.toRadians(angle)));
//
//				rx+=imageOffsetX;
//				ry+=imageOffsetY;
//				lx+=imageOffsetX;
//				ly+=imageOffsetY;
//
//				AnimationPoint rhPoint = new AnimationPoint(new Point(rx, ry),true, angle );
//				AnimationPoint lhPoint = new AnimationPoint(new Point(lx, ly),true, angle );
//				animation.rightHand.add(rhPoint);
//				animation.leftHand.add(lhPoint);
//			}
//		}
		return animation;
	}
	public static Animation getRotatedStab(Point src, Point tgt, float radius, int imageOffsetX, int imageOffsetY){
		Animation animation = new Animation();
		int duration = ONE_HANDED_SWING_DURATION + 5;

		double distX = tgt.getX() - src.getX();
		double distY = tgt.getY() - src.getY();
		float theta = (float)(Math.toDegrees(Math.atan2(distY, distX)));

		float changeInRadius = 20;

		float increment = (2*changeInRadius)/duration;
		for(float r = radius - (changeInRadius/2); r > radius - changeInRadius; r -= increment){

			int rx = (int)(r * Math.cos(Math.toRadians(theta)));
			int ry = (int)(r * Math.sin(Math.toRadians(theta)));
			int lx = (int)((r-2) * Math.cos(Math.toRadians(theta)));
			int ly = (int)((r-2) * Math.sin(Math.toRadians(theta)));

			rx+=imageOffsetX;
			ry+=imageOffsetY;
			lx+=imageOffsetX;
			ly+=imageOffsetY;

			AnimationPoint rhPoint = new AnimationPoint(new Point(rx, ry),true, theta );
			AnimationPoint lhPoint = new AnimationPoint(new Point(lx, ly),true, theta );
			animation.rightHand.add(rhPoint);
			animation.leftHand.add(lhPoint);
		}
		for(float r = radius - changeInRadius; r < radius + changeInRadius; r += increment*2){

			int rx = (int)(r * Math.cos(Math.toRadians(theta)));
			int ry = (int)(r * Math.sin(Math.toRadians(theta)));
			int lx = (int)((r-2) * Math.cos(Math.toRadians(theta)));
			int ly = (int)((r-2) * Math.sin(Math.toRadians(theta)));

			rx+=imageOffsetX;
			ry+=imageOffsetY;
			lx+=imageOffsetX;
			ly+=imageOffsetY;

			AnimationPoint rhPoint = new AnimationPoint(new Point(rx, ry),true, theta );
			AnimationPoint lhPoint = new AnimationPoint(new Point(lx, ly),true, theta );
			animation.rightHand.add(rhPoint);
			animation.leftHand.add(lhPoint);
		}
		return animation;
	}
	public static Animation getRotatedBow(Point src, Point tgt, float radius, int imageOffsetX, int imageOffsetY){
		Animation animation = new Animation();
		int duration = 20;

		double distX = tgt.getX() - src.getX();
		double distY = tgt.getY() - src.getY();
		float theta = (float)(Math.toDegrees(Math.atan2(distY, distX)));




		int rx = (int)(radius * Math.cos(Math.toRadians(theta)));
		int ry = (int)(radius * Math.sin(Math.toRadians(theta)));
		int lx = (int)((radius-2) * Math.cos(Math.toRadians(theta)));
		int ly = (int)((radius-2) * Math.sin(Math.toRadians(theta)));

		rx+=imageOffsetX;
		ry+=imageOffsetY;
		lx+=imageOffsetX;
		ly+=imageOffsetY;

		for(int i = 0; i <= duration; i++){
			AnimationPoint rhPoint = new AnimationPoint(new Point(rx, ry),true, theta-90 );
			AnimationPoint lhPoint = new AnimationPoint(new Point(lx, ly),true, theta-90 );
			animation.rightHand.add(rhPoint);
			animation.leftHand.add(lhPoint);
		}
		return animation;
	}

    /**
     * Just like a stab, but the left hand does nothing?
     * @param src
     * @param tgt
     * @param imageOffsetX
     * @param imageOffsetY
     * @return
     */
    public static Animation getRotatedSpellcast(Point src, Point tgt, int imageOffsetX, int imageOffsetY){
        Animation animation = new Animation();
        int duration = ONE_HANDED_SWING_DURATION + 10;

        double distX = tgt.getX() - src.getX();
        double distY = tgt.getY() - src.getY();
        float theta = (float)(Math.toDegrees(Math.atan2(distY, distX)));

        float changeInRadius = 16;

        float increment = (2*changeInRadius)/duration;
        for(float r = 0 - (changeInRadius/2); r > 0 - changeInRadius; r -= increment/1.0f){

            int rx = (int)(r * Math.cos(Math.toRadians(theta)));
            int ry = (int)(r * Math.sin(Math.toRadians(theta))* Math.sin(Math.toRadians(theta)));
            int lx = 0;
            int ly = 0;

            rx+=imageOffsetX;
            ry+=imageOffsetY;
            lx+=imageOffsetX;
            ly+=imageOffsetY;

            AnimationPoint rhPoint = new AnimationPoint(new Point(rx, ry),true, theta );
            AnimationPoint lhPoint = new AnimationPoint(new Point(lx, ly),false, theta );
            animation.rightHand.add(rhPoint);
            animation.leftHand.add(lhPoint);
        }

        for(float r = 0 - changeInRadius; r < 0 + changeInRadius; r += increment*2){

            int rx = (int)(r * Math.cos(Math.toRadians(theta)));
            int ry = (int)(r * Math.sin(Math.toRadians(theta)));
            int lx = 0;
            int ly = 0;

            rx+=imageOffsetX;
            ry+=imageOffsetY;
            lx+=imageOffsetX;
            ly+=imageOffsetY;

            AnimationPoint rhPoint = new AnimationPoint(new Point(rx, ry),true, theta );
            AnimationPoint lhPoint = new AnimationPoint(new Point(lx, ly),false, theta );
            animation.rightHand.add(rhPoint);
            animation.leftHand.add(lhPoint);
        }
//		System.out.println('\t' + animation.leftHand.size());
//		System.out.println('\t' + animation.rightHand.size());
        return animation;
    }
}



