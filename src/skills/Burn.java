package skills;

import effects.FireballEffect;
import conditions.etc.Cooldown;
import game.*;
import org.lwjgl.util.Point;

import org.newdawn.slick.Graphics;

import entities.characters.Character;
import util.Constants;
import util.animation.Animation;

public class Burn implements Skill{
	public static final int MAX_LVL = 7;
	public static final int SPEED = 2;
	private int level = 1;
	private int damage = 1;
	private int manaCost = 5;
	private int cooldownDuration = 3;
	private int maxUses = 5;
	private int uses = 5;
	private Cooldown cooldown = new Cooldown(cooldownDuration* Constants.TICKS_PER_SECOND);

	@Override
	public boolean use(Character user, Point target) {
		if(!cooldown.offCooldown())return true;
		if(user.getMP() < manaCost)return true;


		float dx = 0;
		float dy = 0;
		while(uses != 0){
			dx = (float)Math.random() * SPEED;
			dy = (float)Math.random() * SPEED;
			if(Engine.getRand().nextBoolean())dx*=-1;
			if(Engine.getRand().nextBoolean())dy*=-1;
			Engine.add(
					new FireballEffect(
							user,
							target.getX(),
							target.getY(),
							dx,
							dy,
							damage,
							3, 15*((level+1)/2))
			);
			--uses;
		}

		uses = maxUses;

		user.drainMana(manaCost);
		cooldown.reset();
		user.setAnimation(Animation.getRotatedSpellcast(user.getLoc(), target, user.getImageXOffset(), user.getImageYOffset()));
		return true;
	}

	@Override
	public void levelUp() {
		if(level + 1 <= MAX_LVL){
			++this.level;
			if (this.level > 1)damage += 1;
			if (this.level > 3){damage += 1;maxUses++;}
			if (this.level > 5){damage += 2;maxUses+=2;}
			cooldownDuration -= .25;
			cooldown = new Cooldown(cooldownDuration* Constants.TICKS_PER_SECOND);
			uses = maxUses;
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
		return new Point(3,0);
	}
	public boolean isReadyable(){return false;}

	public String toString() {
		return "Burn";
	}

	public void readiedDraw(float sx, float sy, float z, Graphics g){}
	public int getMaxLevel(){return MAX_LVL;}
}
