package skills;

import conditions.Combo;
import conditions.etc.Cooldown;
import effects.RotatedAttackEffect;
import game.*;
import org.lwjgl.util.Point;

import org.newdawn.slick.Graphics;
import util.Constants;

import entities.characters.Character;

public class QuickStrike implements Skill{
	public static final int MAX_LVL = 5;
	private int level = 1;
	private int manaCost = 5;
	private float cooldownDuration = 2;
	private Cooldown cooldown = new Cooldown(cooldownDuration* Constants.TICKS_PER_SECOND);



	@Override
	public boolean use(Character user, Point target) {
		if(!cooldown.offCooldown())return true;
		if(user.getMP() < manaCost)return true;
		//if(user.getAnimation() != null)return true;
		//user.getConditions().add(new ConjuredBlade(user, duration*20, level));

		double weaponDamage = 0;
		if(user.getEquippedItem()!= null)weaponDamage = user.getEquippedItem().getDamage();
		double damage = (weaponDamage + user.getStat(Character.ATTACK));
		RotatedAttackEffect effect = new RotatedAttackEffect(user, target,damage, 0, 1, RotatedAttackEffect.DEFAULT_ATTACK);

		Engine.add(effect);
		user.drainMana(manaCost);
		Combo.buildCombo(user);

		cooldown.reset();
		return true;
	}

	@Override
	public void levelUp() {
		if(level + 1 <= MAX_LVL)++this.level;
		this.manaCost++;

		cooldownDuration -= 0.5f;
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
		return new Point(1,6);
	}
	public boolean isReadyable(){return false;}

	public String toString() {
		return "Quick Strike";
	}

	public void readiedDraw(float sx, float sy, float z, Graphics g){}
	public int getMaxLevel(){return MAX_LVL;}
}
