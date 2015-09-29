package skills;

import effects.DarkTendrilEffect;
import conditions.etc.Cooldown;
import game.*;
import org.lwjgl.util.Point;

import org.newdawn.slick.Graphics;
import util.Constants;

import entities.characters.Character;
import util.animation.Animation;

public class DarkTendril implements Skill{
	public static final int MAX_LVL = 5;
	private int level = 1;
	private int damage = 5;
	private int manaCost = 5;
	private float cooldownDuration = 5;
	private Cooldown cooldown = new Cooldown(cooldownDuration * Constants.TICKS_PER_SECOND);

	private int maxTargets = 3;

	public boolean use(Character user, Point target) {
		if(!cooldown.offCooldown())return true;
		if(user.getMP() < manaCost)return true;

		user.drainMana(manaCost);
		double damage = this.damage + user.getStat(Character.DARK_POWER) + user.getStat(Character.ABILITY);
		DarkTendrilEffect tendril = new DarkTendrilEffect((int)damage, user, maxTargets);
		Engine.add(tendril);
		cooldown.reset();
		user.setAnimation(Animation.getRotatedSpellcast(user.getLoc(), target, user.getImageXOffset(), user.getImageYOffset()));
		return true;

	}

	@Override
	public void levelUp() {
		if(level + 1 <= MAX_LVL){
			++this.level;
			this.manaCost += 2;

			maxTargets++;
			if (this.level > 1){
				damage += 1;
			}
			if (this.level > 3){
				damage += 2;
				maxTargets++;
			}
			cooldownDuration -= .75;
			cooldown = new Cooldown(cooldownDuration * Constants.TICKS_PER_SECOND);
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
		return new Point(11,0);
	}
	public boolean isReadyable(){return false;}
	public void readiedDraw(float sx, float sy, float z, Graphics g){}

    public String toString() {
        return "Dark Tendril";
    }

	public int getMaxLevel(){return MAX_LVL;}
}
