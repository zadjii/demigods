package skills;

import conditions.etc.Cooldown;
import effects.LungeEffect;
import game.*;
import org.lwjgl.util.Point;

import org.newdawn.slick.Graphics;
import util.Constants;

import entities.characters.Character;
import util.animation.ControlAnimation;

public class Lunge implements Skill{
	public static final int MAX_LVL = 7;
	private int level = 1;
	private int manaCost = 5;
	private int cooldownDuration = 10;
	private Cooldown cooldown = new Cooldown(cooldownDuration* Constants.TICKS_PER_SECOND);



	@Override
	public boolean use(Character user, Point target) {
		if(!cooldown.offCooldown())return true;
		if(user.getMP() < manaCost)return true;
		if(user.getAnimation() != null)return true;
		//user.getConditions().add(new ConjuredBlade(user, duration*20, level));

		ControlAnimation lunge = ControlAnimation.getLungeAnimation(user, target);
		double weaponDamage = 0;
		if(user.getEquippedItem()!= null)weaponDamage = user.getEquippedItem().getDamage();
		double damage = (weaponDamage + user.getStat(Character.ATTACK)*1.5);
		LungeEffect lungeEffect = new LungeEffect(user, target, damage, lunge.getDX(), lunge.getDY(), lunge.getDuration());
		user.setAnimation(lunge);
		Engine.add(lungeEffect);
		user.drainMana(manaCost);
		cooldown.reset();
		return true;
	}

	@Override
	public void levelUp() {
		if(level + 1 <= MAX_LVL)++this.level;
		this.manaCost++;

		cooldownDuration -= 1;
		cooldown = new Cooldown(cooldownDuration* Constants.TICKS_PER_SECOND);
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
		return new Point(1,0);
	}
	public boolean isReadyable(){return true;}

	public String toString() {
		return "Lunge";
	}

	public void readiedDraw(float sx, float sy, float z, Graphics g){}
	public int getMaxLevel(){return MAX_LVL;}
}
