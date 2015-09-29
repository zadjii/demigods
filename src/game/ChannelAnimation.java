package game;

import entities.characters.Character;
import org.lwjgl.util.Point;
import skills.ChanneledSkill;
import skills.Skill;
import util.animation.Animation;
import util.animation.AnimationPoint;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 6/22/13
 * Time: 9:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelAnimation extends Animation {

	private ChanneledSkill skill;
	private Character user;
	private Point tgt;
	private boolean haveCast = false;

	private ChannelAnimation(ChanneledSkill skill, Character user, Point tgt) {
		this.skill = skill;
		this.user = user;
		this.tgt = tgt;
	}

	public boolean tick(){

		if(super.tick() && !haveCast){
			skill.cast(user, tgt);
			haveCast = true;
			return false;
		}else if(haveCast) return true;
		return false;
	}

	public static ChannelAnimation prototype(Character user,Point target, float duration, ChanneledSkill skill){
		ChannelAnimation anim = new ChannelAnimation(skill, user, target);


		makePoints(new Point(24,8), new Point(6,0), (int)duration, 0f, true, anim.rightHand);
		makePoints(new Point(7,17), new Point(7,20), (int)duration, 0f, false, anim.leftHand);


		return anim;
	}
	public static ChannelAnimation getRotatedChanneledSpellcast(Character src,Point tgt,  float duration, ChanneledSkill skill,int imageOffsetX, int imageOffsetY){
		ChannelAnimation anim = new ChannelAnimation(skill, src, tgt);
//		int duration = ONE_HANDED_SWING_DURATION + 10;

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
			anim.rightHand.add(rhPoint);
			anim.leftHand.add(lhPoint);
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
			anim.rightHand.add(rhPoint);
			anim.leftHand.add(lhPoint);
		}
		return anim;
	}
}
