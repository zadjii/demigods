package skills;

import effects.FireballEffect;
import conditions.etc.Cooldown;
import game.*;
import org.lwjgl.util.Point;

import org.newdawn.slick.Graphics;

import entities.characters.Character;
import util.Constants;
import util.animation.Animation;

public class Fireball implements Skill{
	public static final int MAX_LVL = 7;
	private int level = 1;
	private int damage = 7;
	private int manaCost = 5;
	private int cooldownDuration = 5;
	private Cooldown cooldown = new Cooldown(cooldownDuration* Constants.TICKS_PER_SECOND);;

	@Override
	public boolean use(Character user, Point target) {
		if(!cooldown.offCooldown())return true;
		if(user.getMP() < manaCost)return true;
		Engine.add(
				new FireballEffect(
						user,
						target.getX(),
						target.getY(),
						damage,
						0, 7*16)
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
			this.manaCost++;
			if (this.level > 1)damage += 1;
			if (this.level > 3)damage += 3;
			if (this.level > 5)damage += 4;
			//cooldownDuration += 1;
			cooldown = new Cooldown(cooldownDuration* Constants.TICKS_PER_SECOND);
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
		return new Point(4,0);
	}
	public boolean isReadyable(){return true;}

    public String toString() {
        return "Fireball";
    }

	public void readiedDraw(float sx, float sy, float z, Graphics g){}
	public int getMaxLevel(){return MAX_LVL;}
}
