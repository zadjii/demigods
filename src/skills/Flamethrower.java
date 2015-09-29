package skills;

import effects.FireballEffect;
import conditions.etc.Cooldown;
import game.*;
import org.lwjgl.util.Point;

import org.newdawn.slick.Graphics;

import entities.characters.Character;

public class Flamethrower implements Skill{
	public static final int MAX_LVL = 7;
	private int level = 1;
	private int damage = 1;
	private int manaCost = 10;
	private int cooldownDuration = 12;
	private Cooldown cooldown = new Cooldown(cooldownDuration*20);

	private int maxUses = 50;
	private int uses = maxUses;
	@Override
	public boolean use(Character user, Point target) {
		if(!cooldown.offCooldown())return true;
		if(user.getMP() < manaCost && uses==maxUses)return true;
		if(uses == maxUses){
			user.drainMana(manaCost);
		}
		Engine.add(
				new FireballEffect(
						user,
						target.getX(),
						target.getY(),
						damage,
						1, 100)
		);
		--uses;
		if(uses == 0){
			cooldown.reset();
			uses = maxUses;
			return true;
		}
		return false;
	}

	@Override
	public void levelUp() {
		if(level + 1 <= MAX_LVL){
			++this.level;
			//this.manaCost++;
			//this.manaCost++;
			maxUses += 10;
			uses += 10;
			if (this.level > 1)damage += 1;
			if (this.level > 5){
				damage += 1;
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
		return new Point(4,1);
	}
	public boolean isReadyable(){return true;}

    public String toString() {
        return "Flamethrower";
    }

	public void readiedDraw(float sx, float sy, float z, Graphics g){}
	public int getMaxLevel(){return MAX_LVL;}
}
