package skills;

import conditions.ShiftBlastConditon;
import entities.characters.personas.Persona;
import entities.characters.Character;
import conditions.etc.Cooldown;
import game.Engine;
import org.lwjgl.util.Point;
import org.newdawn.slick.Graphics;
import util.Constants;
import util.SkillsUtils;

public class ShiftBlast implements Skill{
	public static final int MAX_LVL = 3;
	private int level = 1;
	private int manaCost = 10;
	private int cooldownDuration = 10;
	private int delayDuration = 5 * Constants.TICKS_PER_SECOND;
	private Cooldown cooldown = new Cooldown(cooldownDuration * Constants.TICKS_PER_SECOND);

	@Override
	public boolean use(Character user, Point target) {
		if(!cooldown.offCooldown())return true;
		if(user.getMP() < manaCost)return true;
		for(Persona p : Engine.getActiveArea().getPersonas().get(target)){

			if(p.getTeam() != user.getTeam()){
				//if(p.getCharacter().getAbsHitbox().contains(Demigods.getMouseAbsolute())){
					double damage = 0;//user.getStat(Character.ABILITY) + user.getStat(Character.ATTACK);
					damage += user.getEquippedItem().getDamage();
					p.getCharacter().addCondition(new ShiftBlastConditon(user, p.getCharacter(), delayDuration, damage));
					user.drainMana(manaCost);
					cooldown.reset();
					return true;
				//}
			}
		}

		return false;
	}

	@Override
	public void levelUp() {
		if(level + 1 <= MAX_LVL){
			++this.level;
			this.manaCost++;
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
	public boolean isReadyable(){return true;}

	public void readiedDraw(float sx, float sy, float z, Graphics g){
		SkillsUtils.drawCroshairs(sx, sy, z, g);
	}

    public String toString() {
        return "Shift Blast";
    }
	public int getMaxLevel(){return MAX_LVL;}
}
