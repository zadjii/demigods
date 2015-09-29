package skills;

import entities.characters.Character;
import entities.Tickable;
import org.lwjgl.util.Point;

import org.newdawn.slick.Graphics;

import java.io.Serializable;

public interface Skill extends Tickable, Serializable {

	/**Returns true if the skill was successfully used*/
	public boolean use(Character user, Point target);
	public void levelUp();
	public boolean offCooldown();
	public int getLevel();
	public Point getGridCoord();
	public boolean isReadyable();
    public String toString();
	public void readiedDraw(float sx, float sy, float z, Graphics g);
	public int getMaxLevel();
	//public float getRange();
}
