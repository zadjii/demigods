package skills;

import entities.characters.Character;
import org.lwjgl.util.Point;

/**
 * User: zadjii
 * Date: 6/22/13
 * Time: 9:05 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ChanneledSkill implements Skill{

	/**
	 * At the end of the channel animation, this is called on the skill
	 * @param user
	 * @param tgt
	 */
	public abstract void cast(Character user, Point tgt);
}
