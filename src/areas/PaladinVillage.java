package areas;

import entities.buildings.Field;
import entities.buildings.House;
import entities.characters.Character;
import entities.characters.GiantHuman;
import entities.characters.Human;
import entities.characters.ZealotChampionCharacter;
import entities.characters.personas.*;
import entities.interactables.InteractableReference;
import entities.tiles.Tiles;
import items.weapons.*;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import util.Constants;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.Random;

/**
 * Static class.
 * one public static method: generateVillage()
 *
 * User: zadjii
 * Date: 6/22/13
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class PaladinVillage {


	public static final int EMPTY      =   -1;
	public static final int NOTHING    =   0;
	public static final int ROAD       =   1;
	public static final int SPAWN      =   2;
	public static final int BUILDING   =   3;
	public static final int FARM       =   4;
	public static final int TOWNCENTER =   5;

	public static void apply(NewGameArea area, int[][] overview){
		int size = overview.length;
		int villageStartX = 0;
		int villageStartY = 0;
		int villageActualSize = 7 * size;
		int areaSize = area.size;

		Boolean hasPlacedBoss = false;

		if(villageActualSize > areaSize){
			System.err.println("00: PaladinVillage too big. Ignoring.");
		}
		if( (areaSize-villageActualSize) < areaSize/4 ){
			System.err.println("01: PaladinVillage too big. Ignoring.");
		}
		villageStartX = area.rand.nextInt(( areaSize-villageActualSize) - (areaSize/4)) + (areaSize/4);
		villageStartY = area.rand.nextInt(( areaSize-villageActualSize) - (areaSize/4)) + (areaSize/4);

		int centerX = villageStartX*16 + (size*16 / 2);
		int centerY = villageStartY*16 + (size*16 / 2);

		((OverworldArea)(area)).setRespawnLocation(new Point(centerX, centerY));

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				hasPlacedBoss |= makeArea(area, overview, x, y, villageStartX, villageStartY, hasPlacedBoss);
			}
		}

	}


	/**
	 * Hey take care to ignore the +3,+7. Remember, gx,gy is the location of the
	 * building grid being placed.
	 */

	public static void villager(int gx, int gy, NewGameArea area){

		Human human = new Human(gx + 3, gy + 7);
		Color shirt = new Color(area.rand.nextInt(255),area.rand.nextInt(255),area.rand.nextInt(255));
		Color pants = new Color(area.rand.nextInt(255),area.rand.nextInt(255),area.rand.nextInt(255));
		Color skin = new Color(214,200,127);

		human.setTorsoColor(shirt);
		human.setPantsColor(pants);
		human.setSkinColor(skin);
		AStarSimpleMob villager = new AStarSimpleMob(human);
//			NewVillager villager = new NewVillager(human);

		StoneSpear dagger = new StoneSpear();
		human.setEquippedItem(dagger);
		dagger.equip(human);

		villager.setTeam(Constants.PLAYERTEAM+1);
		//human.setXY(b.getX()+b.getWidth()/2, b.getY() + b.getHeight() + 16);
		//if(!passable[human.getX()/16][human.getX()/16])continue;
		area.getPersonas().add(villager);
	}
	public static void boss(int gx, int gy, NewGameArea area){

		ZealotChampionCharacter human = new ZealotChampionCharacter(gx + 3, gy + 7);
		Color shirt = new Color(area.rand.nextInt(255),area.rand.nextInt(255),area.rand.nextInt(255));
		Color pants = new Color(area.rand.nextInt(255),area.rand.nextInt(255),area.rand.nextInt(255));
		Color skin = new Color(214,200,127);

		human.setTorsoColor(shirt);
		human.setPantsColor(pants);
		human.setSkinColor(skin);
		ZealotChampionAI villager = new ZealotChampionAI(human);
//			NewVillager villager = new NewVillager(human);

//		GodSword dagger = new GodSword();
//		human.setEquippedItem(dagger);
//		dagger.equip(human);

		villager.setTeam(Constants.PLAYERTEAM+1);
		//human.setXY(b.getX()+b.getWidth()/2, b.getY() + b.getHeight() + 16);
		//if(!passable[human.getX()/16][human.getX()/16])continue;
		area.getPersonas().add(villager);
	}
	private static boolean makeArea(
			NewGameArea area,
			int[][] overview,
			int x,
			int y,
			int villageStartX, // the grid coord of the start of the village
			int villageStartY,  // the grid coord of the start of the village,
			boolean hasPlacedBoss //whether or not the boss has been placed.
	){
		int type = overview[x][y];
		if(type == EMPTY || type == NOTHING){return false;}

		int absx;//these are the absolute locations of the start of the
		int absy;//grid we're working on.

		int gx, gy;//the grid location of the top-L of the grid.

		absx = villageStartX + (x*7);gx = absx;
		absx*=16;

		absy = villageStartY + (y*7);gy = absy;
		absy*=16;

		if(type == BUILDING){
			//Usually more effort would go into this.
			House house = new House(gx+1, gy + 1);
			house.setProgress(100);
			InteractableReference reference = new InteractableReference(gx+1, gy + 1, house);
			for (int r = 0; r < 5; r++) {
				for (int c = 0; c < 5; c++) {
					area.getInteractables()[gx + 1 + r][gy + 1 + c] = reference;
					area.getPassable()[gx + 1 + r][gy + 1 + c] = false;
					area.getMap()[gx + 1 + r][gy + 1 + c] = Tiles.DIRT;
					area.getResources()[gx + 1 + r][gy + 1 + c] = null;

				}
			}
			area.getInteractables()[gx + 5][gy + 5] = house;
			if(!hasPlacedBoss && area.rand.nextDouble() < .75){
				boss(gx, gy, area);
				return true;
			}
			else{
				villager(gx,gy,area);
			}

		}
		else if(type == ROAD){
			PassiveVillage.applyRoads(area,overview,x,y,gx,gy);
		}

		else if(type == SPAWN){
			//Usually more effort would go into this.
			PassiveVillage.applySpawn(area, gx, gy);
		}
		else if(type == FARM){
			PassiveVillage.applyFarm(area, gx, gy);
		}
		return false;
	}

}
