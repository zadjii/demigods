package skills;

import effects.SparkEffect;
import entities.characters.personas.Persona;
import conditions.etc.Cooldown;
import game.*;
import org.lwjgl.util.Point;

import org.newdawn.slick.Graphics;

import entities.characters.Character;
import util.Constants;
import util.Maths;

public class Spark implements Skill{
	public static final int MAX_LVL = 5;
	public static final int SPEED = 5;
	private int level = 1;
	private int damage = 5;
	private int manaCost = 5;
	private int cooldownDuration = 5;
	private int maxUses = 1;
	private int uses = 1;
	private Cooldown cooldown = new Cooldown(cooldownDuration* Constants.TICKS_PER_SECOND);

	@Override
	public boolean use(Character user, Point target) {
		if(!cooldown.offCooldown())return true;
		if(user.getMP() < manaCost)return true;


		Persona targetEnemy =
				Engine.getActiveArea().getPersonas()
						.getClosestEnemy(target, user.getTeam());
		if(targetEnemy == null)return true;
		if(Maths.dist(target, targetEnemy.getCharacter().getLoc()) > 10*16)return true;

		float dx = 0;
		float dy = 0;
		while(uses != 0){
			dx = (float)Math.random() * 25;
			dy = (float)Math.random() * 25;
			if(Engine.getRand().nextBoolean())dx*=-1;
			if(Engine.getRand().nextBoolean())dy*=-1;
			Point start = new Point(target.getX() + (int)dx, target.getY()  + (int)dy);
			targetEnemy =
					Engine.getActiveArea().getPersonas()
							.getClosestEnemy(start, user.getTeam());
			Engine.add(
					new SparkEffect(
							user,
							start,
							targetEnemy,
							damage,
							SPEED, 100)
			);
			--uses;
		}

		uses = maxUses;

		user.drainMana(manaCost);
		cooldown.reset();
		return true;
	}

	@Override
	public void levelUp() {
		if(level + 1 <= MAX_LVL){
			++this.level;
			if (this.level > 1)damage += 1;
			if (this.level > 3){damage += 2;maxUses+=2;}
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
		return new Point(7,2);
	}
	public boolean isReadyable(){return true;}

	public String toString() {
		return "Spark";
	}

	public void readiedDraw(float sx, float sy, float z, Graphics g){}
	public int getMaxLevel(){return MAX_LVL;}
}
