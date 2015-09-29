package skills;

import conditions.etc.Cooldown;
import effects.SmiteEffect;
import entities.characters.personas.Persona;
import game.*;
import org.lwjgl.util.Point;

import org.newdawn.slick.Graphics;

import entities.characters.Character;
import util.Constants;
import util.SkillsUtils;
import util.animation.Animation;

import java.util.ArrayList;

public class Smite extends ChanneledSkill {
	public static final int MAX_LVL = 7;
	public static final int SPEED = 2;
	private int level = 1;
	private int damage = 10;
	private int manaCost = 5;
	private int cooldownDuration = 10;
	private Cooldown cooldown = new Cooldown(cooldownDuration * Constants.TICKS_PER_SECOND);

	private Persona tgtPersona;

	@Override
	public boolean use(Character user, Point target) {
		//TODO: make deal true damage
		if(!cooldown.offCooldown())return true;
		if(user.getMP() < manaCost)return true;

		ArrayList<Persona> hitPersonas = Engine.getActiveArea().getPersonas().get(target);
		if(hitPersonas!=null && hitPersonas.size() >=1){
			tgtPersona = hitPersonas.get(0);


		}else return true;


		user.drainMana(manaCost);
		user.setAnimation(ChannelAnimation.prototype(user, target, 60, this));
		cooldown.reset();
		return true;
	}
	/**
	 * At the end of the channel animation, this is called on the skill
	 *
	 * @param user
	 * @param tgt
	 */
	@Override
	public void cast(Character user, Point tgt) {

		if(tgtPersona != null && tgtPersona.getCharacter() != null && tgtPersona.getCharacter().getHP() > 0){
            Engine.add(
					new SmiteEffect(
							user,tgtPersona,damage
					)
			);

		}
		tgtPersona = null;
		user.setAnimation(null);
		user.setAnimation(Animation.getRotatedSpellcast(user.getLoc(), tgt, user.getImageXOffset(), user.getImageYOffset()));
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
	public boolean isReadyable(){return true;}

	public String toString() {
		return "Smite";
	}

	public void readiedDraw(float sx, float sy, float z, Graphics g){
		SkillsUtils.drawCroshairs(sx, sy, z, g);
	}
	public int getMaxLevel(){return MAX_LVL;}


}
