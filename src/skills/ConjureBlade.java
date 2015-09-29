package skills;

import conditions.ConjuredBlade;
import conditions.etc.Cooldown;
import org.lwjgl.util.Point;

import org.newdawn.slick.Graphics;
import util.Constants;

import entities.characters.Character;

public class ConjureBlade implements Skill{
	public static final int MAX_LVL = 7;
	private int level = 1;
	private int duration = 10;
	private int manaCost = 5;
	private int cooldownDuration = 10;
	private Cooldown cooldown = new Cooldown(cooldownDuration* Constants.TICKS_PER_SECOND);

	@Override
	public boolean use(Character user, Point target) {
		if(!cooldown.offCooldown())return true;
		if(user.getMP() < manaCost)return true;
		user.addCondition(new ConjuredBlade(user,user, duration*Constants.TICKS_PER_SECOND, level));

		user.drainMana(manaCost);
		cooldown.reset();
		return true;
	}

	@Override
	public void levelUp() {
		if(level < MAX_LVL)++this.level;
		this.manaCost++;
		duration++;
		if (this.level > 3)duration++;
		//cooldownDuration -= 2;
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
	public boolean isReadyable(){return false;}

    public String toString() {
        return "Conjure Blade";
    }

	public void readiedDraw(float sx, float sy, float z, Graphics g){}
	public int getMaxLevel(){return MAX_LVL;}

}
