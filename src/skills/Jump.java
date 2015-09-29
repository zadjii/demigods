package skills;

import conditions.etc.Cooldown;
import entities.characters.Character;
import org.lwjgl.util.Point;
import org.newdawn.slick.Graphics;
import util.SkillsUtils;

public class Jump implements Skill{

	public static final int MAX_LVL = 5;
	private int level = 1;
	private int manaCost = 5;
	private int cooldownDuration = 1;
	private Cooldown cooldown = new Cooldown(cooldownDuration*20);

	@Override
	public boolean use(Character user, Point target) {

		if(!cooldown.offCooldown())return true;
		if(user.getMP() < manaCost)return true;
		user.setXY(
				target.getX(),
				target.getY()
		);
		user.drainMana(manaCost);
		cooldown.reset();
		return true;
	}

	@Override
	public void levelUp() {
		if(level + 1 <= MAX_LVL)++this.level;
		this.manaCost++;
		cooldownDuration -= 2;
		cooldown = new Cooldown(cooldownDuration*20);
	}

	@Override
	public boolean offCooldown() {
		return cooldown.offCooldown();
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public Point getGridCoord() {
		return new Point(2, 0);
	}

	@Override
	public boolean isReadyable() {
		return true;
	}

	@Override
	public void tick() {
		cooldown.tick();
	}

    public String toString() {
        return "Jump";
    }
	public void readiedDraw(float sx, float sy, float z, Graphics g){
		SkillsUtils.drawCroshairs(sx, sy, z, g);
	}
	public int getMaxLevel(){return MAX_LVL;}
}

