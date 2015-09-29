package skills;

import effects.ThunderBallEffect;
import conditions.etc.Cooldown;
import game.*;
import org.lwjgl.util.Point;

import org.newdawn.slick.Graphics;

import entities.characters.Character;
import util.Maths;

public class ThunderStorm implements Skill{
	public static final int MAX_LVL = 5;
	public static final int SPEED = ThunderBallEffect.THUNDERBOLT_SPEED;
	private int level = 1;
	private int damage = 20;
	private int manaCost = 20;
	private int cooldownDuration = 12;
	private Cooldown cooldown = new Cooldown(cooldownDuration*20);

	private int maxUses = 20;
	private int uses = maxUses;

	public boolean use(Character user, Point target) {
		if(!cooldown.offCooldown())return true;
		if(user.getMP() < manaCost && uses==maxUses)return true;

		user.drainMana(manaCost);

		float dx = (float)(-((user.getX()-target.getX()) / Maths.dist(user.getLoc(), target)) * SPEED);
		float dy = (float)(-((user.getY()-target.getY()) / Maths.dist(user.getLoc(), target)) * SPEED);

		Engine.add(
				new ThunderBallEffect(
						user,
						dx,
						dy,
						damage,
						0, 30)
		);
		// != 1 because the first use is above
		int additionalUses = 0;
		additionalUses += (int)(user.getStat(Character.ABILITY)/1.15);
		while(uses != 1 - additionalUses){
			dx = (float)Math.random() * SPEED;
			dy = (float)Math.random() * SPEED;
			if(Engine.getRand().nextBoolean())dx*=-1;
			if(Engine.getRand().nextBoolean())dy*=-1;
			Engine.add(
					new ThunderBallEffect(
							user,
							dx,
							dy,
							damage,
							0, 30)
			);
			--uses;
		}

			cooldown.reset();
			uses = maxUses;
			return true;

	}

	@Override
	public void levelUp() {
		if(level + 1 <= MAX_LVL){
			++this.level;
			//this.manaCost++;
			//this.manaCost++;
			maxUses += 3 + (uses/9);
			uses += 3 + (uses/9);
			if (this.level > 1)damage += 3;
			if (this.level > 4){
				damage += 2;
				maxUses += 10;
				uses += 10;
			}
			cooldownDuration += 2;
			cooldown = new Cooldown(cooldownDuration*20);
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
		return new Point(8,2);
	}
	public boolean isReadyable(){return false;}
	public void readiedDraw(float sx, float sy, float z, Graphics g){}

    public String toString() {
        return "Thunder Storm";
    }
	public int getMaxLevel(){return MAX_LVL;}
}
