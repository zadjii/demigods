package skills;

import conditions.ShiftBlastConditon;
import conditions.etc.Cooldown;
import effects.EnergyRuneEffect;
import entities.characters.Character;
import entities.characters.personas.Persona;
import game.Engine;
import org.lwjgl.util.Point;
import org.newdawn.slick.Graphics;
import util.Constants;
import util.SkillsUtils;

public class EnergyRune implements Skill{
	public static final int MAX_LVL = 3;
	private int level = 1;
	private int manaCost = 10;
	private int cooldownDuration = 10;
	private double damage = 15;
	private int delayDuration = 5 * Constants.TICKS_PER_SECOND;
	private Cooldown cooldown = new Cooldown(cooldownDuration * Constants.TICKS_PER_SECOND);

	@Override
	public boolean use(Character user, Point target) {
		if(!cooldown.offCooldown())return true;
		if(user.getMP() < manaCost)return true;

		Engine.add(
				new EnergyRuneEffect(user, user.getLoc(), damage)
		);
		user.drainMana(manaCost);
		cooldown.reset();
		return true;
	}

	@Override
	public void levelUp() {
		if(level + 1 <= MAX_LVL){
			++this.level;
			this.manaCost++;
			this.damage += 2;
			cooldownDuration -= 1;
			cooldownDuration -= 1;
			cooldown = new Cooldown(cooldownDuration * Constants.TICKS_PER_SECOND);
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
		return new Point(1,4);
	}
	public boolean isReadyable(){return false;}

	public void readiedDraw(float sx, float sy, float z, Graphics g){
//		SkillsUtils.drawCroshairs(sx, sy, z, g);
	}

    public String toString() {
        return "Energy Rune";
    }
	public int getMaxLevel(){return MAX_LVL;}
}
