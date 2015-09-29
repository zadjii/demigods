package items;

import effects.Effect;
import org.lwjgl.util.Point;
import util.exceptions.UncheckedDemigodsException;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 6/16/13
 * Time: 10:00 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ConsumableItem extends Item{

	public Effect attack(Point tgt){
		if(user.getAnimation()!=null)return null;
		if(user == null)throw new UncheckedDemigodsException("A Item without" +
				" a user cannot possibly be used, how did that happen?");
		if(consume(tgt)){
			//Remove the item from inventory.
			user.getInventory().removeItem(this.toString());
		}
		else{
			//don't
			this.use();
		}
		return null;
	}

	/**
	 * Code for consuming the item. Returns true if the item should be removed
	 * from inventory. I'm not really sure when it wouldn't be, but who knows.
	 *
	 * @param tgt the location clicked.
	 * @return true if the item should be removed from inventory.
	 */
	public abstract boolean consume(Point tgt);
}
