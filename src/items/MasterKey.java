package items;

import entities.tiles.Tiles;
import game.Demigods;
import game.Engine;
import org.lwjgl.util.Point;

/**
 * Opens any door.
 * User: zadjii
 * Date: 6/16/13
 * Time: 10:24 AM
 */
public class MasterKey extends ConsumableItem {

	public MasterKey() {
		super();
		this.setWeight(1.0);
		recipeValue = 1;
		setImageID(1, 0);
		this.name = "Master Key";
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
		int[][] map = Engine.getActiveArea().getMap();
		int tile = map[tgt.getX()/16][tgt.getY()/16];

		if(tile == Tiles.DUNGEONDOOR){
			map[tgt.getX()/16][tgt.getY()/16] = Tiles.DUNGEONFLOOR;
			return true;
		}


		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
