package skills;

import conditions.RallyCondition;
import conditions.etc.Cooldown;
import effects.RotatedAttackEffect;
import entities.characters.Character;
import entities.characters.personas.Persona;
import entities.particles.Particle;
import game.Demigods;
import game.Engine;
import org.lwjgl.util.Point;
import org.newdawn.slick.Graphics;
import util.Constants;
import util.Maths;
import util.OnHitListener;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 8/6/13
 * Time: 7:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class TripleSlash implements Skill{
	public static final int MAX_LVL = 7;
	public static final int SPEED = 2;
	private int level = 1;
	private float damageReductionPercent = .5f;
	private int manaCost = 1;
	private int cooldownDuration = 1;
	private Cooldown cooldown = new Cooldown(cooldownDuration * Constants.TICKS_PER_SECOND);


	@Override
	public boolean use(Character user, Point target) {

		if(!cooldown.offCooldown())return true;
		if(user.getMP() < manaCost)return true;
		if(user.getEquippedItem() == null)return true;

		//Here we're gonna calculate the location of the three attacks.
		//It will be slighlty redundant.
		//get dist and angle to target.
		//put three attacks in, at:
		//  dist, angle
		//  dist, angle - 60
		//  dist, angle + 60
		//in between attacks, take item off CD

		//no on hit.

		float dist = (float)Maths.dist(user.getLoc(), target);
		double distX = target.getX() - user.getX();
		double distY = target.getY() - user.getY();
		float angle = (float)Math.atan2(distY, distX);
		float angleToAdd = 30.0f;
		double angleInRad = Math.toRadians(angleToAdd);
		Point plusTgt = new Point(
				(int)(Math.cos(angle + angleInRad) * dist) + (int)user.getX(),
				(int)(Math.sin(angle + angleInRad) * dist) + (int)user.getY()
		);
		Point minusTgt = new Point(
				(int)(Math.cos(angle - angleInRad) * dist) + (int)user.getX(),
				(int)(Math.sin(angle - angleInRad) * dist) + (int)user.getY()
		);

//		System.out.println(Demigods.getLoc(target) + ": " + (angle));
//		System.out.println(Demigods.getLoc(plusTgt) + ": " + (angle+angleInRad));
//		System.out.println(Demigods.getLoc(minusTgt) + ": " + (angle-angleInRad));

		user.modifyPercentBonus(Character.ATTACK, -damageReductionPercent);
//		RotatedAttackEffect effect = (RotatedAttackEffect)
		user.getEquippedItem().attack(target);
		user.getEquippedItem().getCooldown().clear();
		user.setAnimation(null);

		user.getEquippedItem().attack(plusTgt);
		user.getEquippedItem().getCooldown().clear();
		user.setAnimation(null);

		user.getEquippedItem().attack(minusTgt);

		user.modifyPercentBonus(Character.ATTACK, damageReductionPercent);

//		final Character userReference = user;
//		if(effect != null)effect.setOnHitListener(new OnHitListener() {
//			@Override
//			public void onHit(Character src, Character tgt) {
//				for(Persona other : Engine.getAdjacentPersonas(userReference, 5 * 16)){
//					if(other.getCharacter() != null && other.getTeam() == src.getTeam()){
//						other.getCharacter().addCondition(new RallyCondition(userReference, other.getCharacter(), damage/2 * Constants.TICKS_PER_SECOND));
//						Engine.add(Particle.newRallyParticle(other.getCharacter()));
//					}
//				}
//			}
//		});

		user.drainMana(manaCost);

		cooldown.reset();
		return true;
	}

	@Override
	public void levelUp() {
		if(level + 1 <= MAX_LVL){
			++this.level;
//			if (this.level > 1)damage += 1;
//			if (this.level > 3){damage += 3;}
//			if (this.level > 5){damage += 3;}
			cooldownDuration -= .25;
			cooldown = new Cooldown(cooldownDuration* Constants.TICKS_PER_SECOND);

		}


	}

	@Override
	public void tick() {
		cooldown.tick();
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
	public boolean isReadyable(){return true;}

	public String toString() {
		return "Triple Slash";
	}

	public void readiedDraw(float sx, float sy, float z, Graphics g){
//		SkillsUtils.drawCroshairs(sx, sy, z, g);
	}
	public int getMaxLevel(){return MAX_LVL;}

}
