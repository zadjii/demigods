package items;

import areas.NewGameArea;
import entities.interactables.ResourceTile;
import entities.tiles.Tiles;
import game.Demigods;
import game.Engine;
import org.lwjgl.util.Point;

/**
 * Plants a tree. Not exactly the final implementation.
 * User: zadjii
 * Date: 6/16/13
 * Time: 10:24 AM
 */
public class Sapling extends ConsumableItem {

	public Sapling() {
		super();
		this.setWeight(.1);
		recipeValue = 1;
		setImageID(1, 0);
		this.name = "Sapling";
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
		NewGameArea area = Engine.getActiveArea();
		int[][] map = area.getMap();
		int gx = tgt.getX() / 16;
		int gy = tgt.getY() / 16;
		int tile = map[gx][gy];
		if(!area.getPassable()[gx][gy]
				|| area.getResources()[gx][gy] != null)return false;
		if(tile == Tiles.DIRT
				||Tiles.isGrass(tile)
				){
			map[gx][gy] = Tiles.TREE;
			area.getResources()[gx][gy]=
					new ResourceTile(Tiles.TREE, gx *16, gy * 16);
			area.getPassable()[gx][gy] = false;
			return true;
		}


		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
