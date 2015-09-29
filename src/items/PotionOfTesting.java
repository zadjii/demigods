package items;

import conditions.TestPotionHeal;
import entities.interactables.ResourceTile;
import entities.characters.Character;
import game.Demigods;
import areas.NewGameArea;
import org.lwjgl.util.Point;
import util.Constants;

/**
 * A test potion implementation. gives a hp5&mp5 regen boost.
 * User: zadjii
 * Date: 6/16/13
 * Time: 10:24 AM
 */
public class PotionOfTesting extends ConsumableItem {

	public PotionOfTesting() {
		super();
		this.setWeight(.1);
		recipeValue = 1;
		setImageID(1, 0);
		this.name = "PotionOfTesting";
	}
	/**
	 * Code for consuming the item. Returns true if the item should be removed
	 * from inventory. I'm not really sure when it wouldn't be, but who knows.
	 *
	 * @param tgt the location clicked.
	 * @return true if the item should be removed from inventory.
	 */
	@Override
	public boolean consume(Point tgt) {

		if(this.user == null){
			System.out.println("Potion wasn't equipped");
			return false;
		}
		if(user.getConditionMap().containsKey("Test Potion!"))return false;
		this.user.addCondition(new TestPotionHeal(user, user, 10 * Constants.TICKS_PER_SECOND));


		return true;
	}
}
