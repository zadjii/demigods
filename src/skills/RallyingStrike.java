package skills;

import conditions.RallyCondition;
import conditions.etc.Cooldown;
import effects.RotatedAttackEffect;
import effects.SmiteEffect;
import entities.characters.*;
import entities.characters.Character;
import entities.characters.personas.Persona;
import entities.particles.Particle;
import game.ChannelAnimation;
import game.Engine;
import org.lwjgl.util.Point;
import org.newdawn.slick.Graphics;
import util.Constants;
import util.OnHitListener;
import util.SkillsUtils;
import util.animation.Animation;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 8/6/13
 * Time: 7:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class RallyingStrike implements Skill{
	public static final int MAX_LVL = 7;
	public static final int SPEED = 2;
	private int level = 1;
	private int damage = 10;
	private int manaCost = 5;
	private int cooldownDuration = 10;
	private Cooldown cooldown = new Cooldown(cooldownDuration * Constants.TICKS_PER_SECOND);


	@Override
	public boolean use(Character user, Point target) {

		if(!cooldown.offCooldown())return true;
		if(user.getMP() < manaCost)return true;
		if(user.getEquippedItem() == null)return true;

		user.modifyFlatBonus(Character.ATTACK, damage);
		RotatedAttackEffect effect = (RotatedAttackEffect)user.getEquippedItem().attack(target);
		user.modifyFlatBonus(Character.ATTACK, -damage);

		final Character userReference = user;
		if(effect != null)effect.setOnHitListener(new OnHitListener() {
			@Override
			public void onHit(Character src, Character tgt) {
				for(Persona other : Engine.getAdjacentPersonas(userReference, 5 * 16)){
					if(other.getCharacter() != null && other.getTeam() == src.getTeam()){
						other.getCharacter().addCondition(new RallyCondition(userReference, other.getCharacter(), damage/2 * Constants.TICKS_PER_SECOND));
						Engine.add(Particle.newRallyParticle(other.getCharacter()));
					}
				}
			}
		});

		user.drainMana(manaCost);

		cooldown.reset();
		return true;
	}

	@Override
	public void levelUp() {
		if(level + 1 <= MAX_LVL){
			++this.level;
			if (this.level > 1)damage += 1;
			if (this.level > 3){damage += 3;}
			if (this.level > 5){damage += 3;}
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
		return "Rallying Strike";
	}

	public void readiedDraw(float sx, float sy, float z, Graphics g){
//		SkillsUtils.drawCroshairs(sx, sy, z, g);
	}
	public int getMaxLevel(){return MAX_LVL;}

}
