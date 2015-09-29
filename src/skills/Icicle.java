package skills;

import conditions.etc.Cooldown;
import effects.IcicleEffect;
import entities.characters.Character;
import game.*;
import org.lwjgl.util.Point;
import org.newdawn.slick.Graphics;
import util.Constants;
import util.animation.Animation;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 8/26/12
 * Time: 3:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Icicle implements Skill{
	public static final int MAX_LVL = 7;
	private int level = 1;
	private int damage = 5;
	private int manaCost = 5;
	private int cooldownDuration = 3 * Constants.TICKS_PER_SECOND;
	private Cooldown cooldown = new Cooldown(cooldownDuration);
	private Character user;
	private Point target;
	@Override
	public boolean use(Character user, Point target) {
		if(!cooldown.offCooldown())return true;
		if(user.getMP() < manaCost)return true;
		this.user = user;
		this.target = target;
		//user.setAnimation(Animation.getSmash(user.getDirection()));
		Engine.add(
				new IcicleEffect(
						user,
						target,
						damage,
						5, 7*16)
		);
		user.setAnimation(Animation.getRotatedSpellcast(user.getLoc(), target, user.getImageXOffset(), user.getImageYOffset()));
		user.drainMana(manaCost);
		cooldown.reset();

		return true;
	}

	@Override
	public void levelUp() {
		if(level + 1 <= MAX_LVL){
			++this.level;
			this.manaCost++;
			if (this.level > 5)this.manaCost++;
			if (this.level > 1)damage += 1;
			if (this.level > 3)damage += 1;
			if (this.level > 5)damage += 2;
			//cooldownDuration += 1;
			//cooldown = new Cooldown(cooldownDuration);
		}


	}

	@Override
	public void tick() {
		cooldown.tick();

		//To change body of implemented methods use File | Settings | File Templates.
	}
	public boolean offCooldown(){
		return cooldown.offCooldown();
	}
	public int getLevel(){
		return this.level;
	}

	public Point getGridCoord(){
		return new Point(7,0);
	}
	public boolean isReadyable(){return true;}

	public String toString() {
		return "Icicle";
	}

	public void readiedDraw(float sx, float sy, float z, Graphics g){}
	public int getMaxLevel(){return MAX_LVL;}

}
