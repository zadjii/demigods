package areas;

import entities.buildings.Field;
import entities.buildings.House;
import entities.characters.Character;
import entities.characters.GiantHuman;
import entities.characters.Human;
import entities.characters.personas.AStarHunter;
import entities.characters.personas.NewVillager;
import entities.characters.personas.Villager;
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
public class PassiveVillage {


	public static final int EMPTY      =   -1;
	public static final int NOTHING    =   0;
	public static final int ROAD       =   1;
	public static final int SPAWN      =   2;
	public static final int BUILDING   =   3;
	public static final int FARM       =   4;
	public static final int TOWNCENTER =   5;
	/*
		Puts the village into the area.
	 */
	public static int[][] generateVillage(
			//args
			NewGameArea area,
	        int size
	){

		int villageStartX = 0;
		int villageStartY = 0;
		int villageActualSize = 7 * size;
		int areaSize = area.size;
		//need to determine where to place the village
		//each overview index corresponds to a 7x7 area
		if(villageActualSize > areaSize){
			System.err.println("00: Village too big. Ignoring.");
			return null;
		}
		if( (areaSize-villageActualSize) < areaSize/4 ){
			System.err.println("01: Village too big. Ignoring.");
			return null;
		}
		villageStartX = area.rand.nextInt(( areaSize-villageActualSize) - (areaSize/4)) + (areaSize/4);
		villageStartY = area.rand.nextInt(( areaSize-villageActualSize) - (areaSize/4)) + (areaSize/4);
		//vsx, vsy are in grid coords
		int[][] overview = generateOverview(area, size);

		int centerX = villageStartX*16 + (size*16 / 2);
		int centerY = villageStartY*16 + (size*16 / 2);

		((OverworldArea)(area)).setRespawnLocation(new Point(centerX, centerY));

		//Now we add the village to the actual area
		//right now all buildings will just be houses.
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				makeArea(area, overview, x, y, villageStartX, villageStartY, area.rand);
			}
		}

		//Not usually used by the GameArea.
		return overview;
	}


	protected static int[][] generateOverview(NewGameArea area,int size){
		/*
		General outline:

		loop:
			addBuilding
			roads   --->    extends, branches, fills, bends, pick 2
			empty
		 */
		Random rand = area.rand;

		int villageStartX = 0;
		int villageStartY = 0;
		int villageActualSize = 7 * size;
		int areaSize = area.size;
		//need to determine where to place the village
		//each overview index corresponds to a 7x7 area
		if(villageActualSize > areaSize){
			System.err.println("00: Village too big. Ignoring.");
			return null;
		}
		if( (areaSize-villageActualSize) < areaSize/4 ){
			System.err.println("01: Village too big. Ignoring.");
			return null;
		}
		villageStartX = rand.nextInt(( areaSize-villageActualSize) - (areaSize/4)) + (areaSize/4);
		villageStartY = rand.nextInt(( areaSize-villageActualSize) - (areaSize/4)) + (areaSize/4);

		int[][] overview = new int[size][size];

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				overview[x][y] = 0;
			}
		}

		int cx = size/2;
		int cy = size/2;

		try{overview[cx][cy]=SPAWN;}catch (IndexOutOfBoundsException e){/*nothing*/}

		try{overview[cx-1][cy]=ROAD;}catch (IndexOutOfBoundsException e){/*nothing*/}
		try{overview[cx-2][cy]=ROAD;}catch (IndexOutOfBoundsException e){/*nothing*/}

		try{overview[cx+1][cy]=ROAD;}catch (IndexOutOfBoundsException e){/*nothing*/}
		try{overview[cx+2][cy]=ROAD;}catch (IndexOutOfBoundsException e){/*nothing*/}

		try{overview[cx][cy-1]=ROAD;}catch (IndexOutOfBoundsException e){/*nothing*/}
		try{overview[cx][cy-2]=ROAD;}catch (IndexOutOfBoundsException e){/*nothing*/}

		try{overview[cx][cy+1]=ROAD;}catch (IndexOutOfBoundsException e){/*nothing*/}
		try{overview[cx][cy+2]=ROAD;}catch (IndexOutOfBoundsException e){/*nothing*/}


		Boolean hasAddedVillageCenter = false;

		for (int i = 0; i < size + rand.nextInt(size/2); i++) {
			addBuilding(overview, hasAddedVillageCenter, rand);
			roads(overview, rand);
			empty(overview,rand);
		}
		return overview;
	}


	/**
		Adds a Building to a spot where there are at least two adjacent roads.
		In order to make some randomness, adds all the points w/ two adj roads
			to a list, then randomly picks one of those.
		TODO: Add ability to place a VillageCenter
	 */
	private static void addBuilding(int[][] overview, Boolean hasAddedVillageCenter, Random rand){
		int size = overview.length;
		ArrayList<Point> viableSpots = new ArrayList<Point>();
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if(overview[x][y]!= NOTHING)continue;
				int adjRoads = computeAdjacentRoads(overview, x, y);
				if(adjRoads >= 2)viableSpots.add(new Point(x, y));
			}
		}

		if(viableSpots.size()>0){
			Point theChosenPoint = viableSpots.get(rand.nextInt(viableSpots.size()));

			//should check to see if can place a VC here
			overview[theChosenPoint.getX()][theChosenPoint.getY()] = BUILDING;
		}
	}

	private static int computeAdjacentRoads(int[][] overview, int x, int y){
		int adjRoads = 0;
		DX:for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				if(dx == 0 && dy == 0)continue;
				try{
					if(overview[x+dx][y+dy] == ROAD)adjRoads++;
				}catch (IndexOutOfBoundsException e){/*Nothing*/}
			}
		}
		return adjRoads;
	}
	private static int computeDirectAdjacentRoads(int[][] overview, int x, int y){
		int adjRoads = 0;
		DX:for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				if(dx != 0 && dy != 0)continue;
				if(dx == 0 && dy == 0)continue;
				try{
					if(overview[x+dx][y+dy] == ROAD)adjRoads++;
				}catch (IndexOutOfBoundsException e){/*Nothing*/}
			}
		}
		return adjRoads;
	}

	private static void roads(int[][] overview, Random rand){

		switch (rand.nextInt(3)){
			case 0:extendRoads(overview, rand);break;
			case 1:branchRoads(overview, rand);break;
			case 2:bendRoads(overview, rand);break;
			case 3:fillRoads(overview, rand);break;
		}

//		switch (rand.nextInt(4)){
//			case 0:extendRoads(overview, rand);break;
//			case 1:branchRoads(overview, rand);break;
//			case 2:bendRoads(overview, rand);break;
//			case 3:fillRoads(overview, rand);break;
//		}

	}

	/**
	 * looks for two in a row roads, then adds a third to the end.
	 *
	 * finds roads adj to others
	 * looks to see if a third potential road is valid; if so, ads to list.
	 *
	 * picks one.
	 */
	private static void extendRoads(int[][] overview, Random rand){
		int size = overview.length;
		ArrayList<Point> viableSpots = new ArrayList<Point>();
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if(overview[x][y] != ROAD)continue;
				//x,y is definitely a road now.
				//look for _directly_ adj roads.
				DX:for (int dx = -1; dx <= 1; dx++) {
					for (int dy = -1; dy <= 1; dy++) {
						if(dx != 0 && dy != 0)continue;
						if(dx == 0 && dy == 0)continue;

						try{
							if(overview[x+dx][y+dy] == ROAD ){
								if(overview[x-dx][y-dy] == NOTHING){
									Point toBeAdded = new Point(x-dx, y-dy);
									if(!viableSpots.contains(toBeAdded))
										viableSpots.add(toBeAdded);
								}
							}
						}catch (IndexOutOfBoundsException e){/*Nothing*/}
					}
				}
			}
		}
		/* //Decided to not go with the fewest method.
		Point leastAdjRoads = null;
		int fewestNum = 12;//12 > any number of adjacencies
		for (Point p : viableSpots){
			int numOfAdjacencies = computeAdjacentRoads(overview, p.getX(), p.getY());
			if(numOfAdjacencies < fewestNum){
				leastAdjRoads = p;
				fewestNum = numOfAdjacencies;
			}
			else if(numOfAdjacencies == fewestNum){
				leastAdjRoads = (rand.nextBoolean())?leastAdjRoads:p;
			}
		}
		if(leastAdjRoads != null){
			overview[leastAdjRoads.getX()][leastAdjRoads.getY()] = ROAD;
		}//*/
		if(viableSpots.size()>0){
			Point theChosenPoint = viableSpots.get(rand.nextInt(viableSpots.size()));

			//should check to see if can place a VC here
			overview[theChosenPoint.getX()][theChosenPoint.getY()] = ROAD;
		}
	}

	/**
	 * Find a road with a road on both sides of it.
	 * add the other sides to the viable spots
	 *
	 * @param overview
	 * @param rand
	 */
	private static void branchRoads(int[][] overview, Random rand){
		int size = overview.length;
		ArrayList<Point> viableSpots = new ArrayList<Point>();
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if(overview[x][y] != ROAD)continue;
				//x,y is definitely a road now.
				//look for _directly_ adj roads.
				DX:for (int dx = -1; dx <= 1; dx++) {
					for (int dy = -1; dy <= 1; dy++) {
						if(dx != 0 && dy != 0)continue;
						if(dx == 0 && dy == 0)continue;

						try{
							if(overview[x+dx][y+dy] == ROAD && overview[x-dx][y-dy] == ROAD){
								if(overview[x+dy][y-dx] == NOTHING){
									Point toBeAdded = new Point(x+dy, y-dx);
									if(!viableSpots.contains(toBeAdded)
											&& computeDirectAdjacentRoads(overview,toBeAdded.getX(), toBeAdded.getY()) < 3)
										viableSpots.add(toBeAdded);
								}
								if(overview[x-dy][y+dx] == NOTHING){
									Point toBeAdded = new Point(x-dy, y+dx);
									if(!viableSpots.contains(toBeAdded)
										&& computeDirectAdjacentRoads(overview,toBeAdded.getX(), toBeAdded.getY()) < 3)
										viableSpots.add(toBeAdded);
								}
							}
						}catch (IndexOutOfBoundsException e){/*Nothing*/}
					}
				}
			}
		}
		/* //Decided to not go with the fewest method.
		Point leastAdjRoads = null;
		int fewestNum = 12;//12 > any number of adjacencies
		for (Point p : viableSpots){
			int numOfAdjacencies = computeAdjacentRoads(overview, p.getX(), p.getY());
			if(numOfAdjacencies < fewestNum){
				leastAdjRoads = p;
				fewestNum = numOfAdjacencies;
			}
			else if(numOfAdjacencies == fewestNum){
				leastAdjRoads = (rand.nextBoolean())?leastAdjRoads:p;
			}
		}
		if(leastAdjRoads != null){
			overview[leastAdjRoads.getX()][leastAdjRoads.getY()] = ROAD;
		}//*/
		if(viableSpots.size()>0){
			Point theChosenPoint = viableSpots.get(rand.nextInt(viableSpots.size()));

			//should check to see if can place a VC here
			overview[theChosenPoint.getX()][theChosenPoint.getY()] = ROAD;
		}
	}
	private static void bendRoads(int[][] overview, Random rand){
		int size = overview.length;
		ArrayList<Point> viableSpots = new ArrayList<Point>();
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if(overview[x][y] != ROAD)continue;
				//x,y is definitely a road now.
				//look for _directly_ adj roads.
				DX:for (int dx = -1; dx <= 1; dx++) {
					for (int dy = -1; dy <= 1; dy++) {
						if(dx != 0 && dy != 0)continue;
						if(dx == 0 && dy == 0)continue;

						try{
							if(overview[x+dx][y+dy] == ROAD && overview[x-dx][y-dy] != ROAD){
								if(overview[x+dy][y-dx] == NOTHING){
									Point toBeAdded = new Point(x+dy, y-dx);
									if(!viableSpots.contains(toBeAdded)
											&& computeAdjacentRoads(overview, toBeAdded.getX(), toBeAdded.getY()) < 3)
										viableSpots.add(toBeAdded);
								}
								if(overview[x-dy][y+dx] == NOTHING){
									Point toBeAdded = new Point(x-dy, y+dx);
									if(!viableSpots.contains(toBeAdded)
											&& computeAdjacentRoads(overview, toBeAdded.getX(), toBeAdded.getY()) < 3)
										viableSpots.add(toBeAdded);
								}
							}
						}catch (IndexOutOfBoundsException e){/*Nothing*/}
					}
				}
			}
		}
		/* //Decided to not go with the fewest method.
		Point leastAdjRoads = null;
		int fewestNum = 12;//12 > any number of adjacencies
		for (Point p : viableSpots){
			int numOfAdjacencies = computeAdjacentRoads(overview, p.getX(), p.getY());
			if(numOfAdjacencies < fewestNum){
				leastAdjRoads = p;
				fewestNum = numOfAdjacencies;
			}
			else if(numOfAdjacencies == fewestNum){
				leastAdjRoads = (rand.nextBoolean())?leastAdjRoads:p;
			}
		}
		if(leastAdjRoads != null){
			overview[leastAdjRoads.getX()][leastAdjRoads.getY()] = ROAD;
		}//*/
		if(viableSpots.size()>0){
			Point theChosenPoint = viableSpots.get(rand.nextInt(viableSpots.size()));

			//should check to see if can place a VC here
			overview[theChosenPoint.getX()][theChosenPoint.getY()] = ROAD;
		}
	}
	private static void fillRoads(int[][] overview, Random rand){}


	/*
		Adds a Building to a spot where there are at least two adjacent roads.
		In order to make some randomness, adds all the points w/ two adj roads
			to a list, then randomly picks one of those.
		TODO: Add ability to place a VillageCenter
	 */
	private static void empty(int[][] overview, Random rand){
		int size = overview.length;
		ArrayList<Point> viableSpots = new ArrayList<Point>();
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if(overview[x][y] != NOTHING)continue;
				viableSpots.add(new Point(x, y));
			}
		}

		if(viableSpots.size()>0){
			Point theChosenPoint = viableSpots.get(rand.nextInt(viableSpots.size()));

			if(rand.nextBoolean())
				overview[theChosenPoint.getX()][theChosenPoint.getY()] = EMPTY;
			else
				overview[theChosenPoint.getX()][theChosenPoint.getY()] = FARM;

		}
	}

	public static void villager(int gx, int gy, NewGameArea area){
		Human human = new Human(gx + 3, gy + 7);
		Color shirt = new Color(area.rand.nextInt(255),area.rand.nextInt(255),area.rand.nextInt(255));
		Color pants = new Color(area.rand.nextInt(255),area.rand.nextInt(255),area.rand.nextInt(255));
		Color skin = new Color(//fixme replace with better color selection
				214 + (area.rand.nextInt(100)-50),
				175 + (area.rand.nextInt(75)-30),
				127 + (area.rand.nextInt(50)-25));

		human.setTorsoColor(shirt);
		human.setPantsColor(pants);
		human.setSkinColor(skin);
//		AStarHunter villager = new AStarHunter(human);
		NewVillager villager = new NewVillager(human);

//		GodSword dagger = new GodSword();
//		human.setEquippedItem(dagger);
//		dagger.equip(human);

		villager.setTeam(Constants.PLAYERTEAM);
		//human.setXY(b.getX()+b.getWidth()/2, b.getY() + b.getHeight() + 16);
		//if(!passable[human.getX()/16][human.getX()/16])continue;
		area.getPersonas().add(villager);
	}
	private static void makeArea(
			NewGameArea area,
			int[][] overview,
			int x,
			int y,
	        int villageStartX, // the grid coord of the start of the village
	        int villageStartY,  // the grid coord of the start of the village,
	        Random rand
	){
		int type = overview[x][y];
		if(type == EMPTY || type == NOTHING){return;}

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

			villager(gx,gy,area);

		}
		else if(type == ROAD){
			//Usually more effort would go into this.

			applyRoads(area, overview, x, y, gx, gy);

		}

		else if(type == SPAWN){
			//Usually more effort would go into this.

			applySpawn(area, gx, gy);


		}
		else if(type == FARM){
			applyFarm(area, gx, gy);


		}
	}

	public static void applyFarm(NewGameArea area, int gx, int gy) {
		//Usually more effort would go into this.
		Field farm = new Field(gx+1, gy + 1);
		InteractableReference reference = new InteractableReference(gx+1, gy + 1, farm);
		for (int r = 0; r < 5; r++) {
			for (int c = 0; c < 5; c++) {
				area.getInteractables()[gx + 1 + r][gy + 1 + c] = reference;
				area.getPassable()[gx + 1 + r][gy + 1 + c] = false;
				area.getMap()[gx + 1 + r][gy + 1 + c] = Tiles.DIRT;
				area.getResources()[gx + 1 + r][gy + 1 + c] = null;

			}
		}
		area.getInteractables()[gx + 5][gy + 5] = farm;
	}

	public static void applySpawn(NewGameArea area, int gx, int gy) {
		for (int r = 0; r < 7; r++) {
			for (int c = 0; c < 7; c++) {
				area.getInteractables()[gx + r][gy  + c] = null;
				area.getPassable()[gx +  r][gy +  c] = true;
				area.getMap()[gx +  r][gy + c] = Tiles.COBBLEROAD;
				area.getResources()[gx  + r][gy + c] = null;

			}
		}
	}

	public static void applyRoads(NewGameArea area, int[][] overview, int x, int y, int gx, int gy) {
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				area.getInteractables()[gx + 2 + r][gy + 2 + c] = null;
				area.getPassable()[gx + 2 + r][gy + 2 + c] = true;
				area.getMap()[gx + 2 + r][gy + 2 + c] = Tiles.COBBLEROAD;
				area.getResources()[gx + 2 + r][gy + 2 + c] = null;

			}
		}
		try{
		if(overview[x-1][y] == ROAD){
			for (int r = 0; r < 3; r++) {
				for (int c = 0; c < 3; c++) {
					area.getInteractables()[gx  + r][gy + 2 + c] = null;
					area.getPassable()[gx  + r][gy + 2 + c] = true;
					area.getMap()[gx  + r][gy + 2 + c] = Tiles.COBBLEROAD;
					area.getResources()[gx  + r][gy + 2 + c] = null;

				}
			}
		}}catch (IndexOutOfBoundsException e){/*do nothing*/}


		try{
			if(overview[x+1][y] == ROAD){
				for (int r = 0; r < 3; r++) {
					for (int c = 0; c < 3; c++) {
						area.getInteractables()[gx + 4 + r][gy + 2 + c] = null;
						area.getPassable()[gx + 4 + r][gy + 2 + c] = true;
						area.getMap()[gx + 4 + r][gy + 2 + c] = Tiles.COBBLEROAD;
						area.getResources()[gx + 4 + r][gy + 2 + c] = null;

					}
				}
			}}catch (IndexOutOfBoundsException e){/*do nothing*/}

		try{
			if(overview[x][y-1] == ROAD){
				for (int r = 0; r < 3; r++) {
					for (int c = 0; c < 3; c++) {
						area.getInteractables()[gx + 2 + r][gy  + c] = null;
						area.getPassable()[gx + 2 + r][gy  + c] = true;
						area.getMap()[gx + 2 + r][gy + c] = Tiles.COBBLEROAD;
						area.getResources()[gx + 2 + r][gy  + c] = null;

					}
				}
			}}catch (IndexOutOfBoundsException e){/*do nothing*/}

		try{
			if(overview[x][y+1] == ROAD){
				for (int r = 0; r < 3; r++) {
					for (int c = 0; c < 3; c++) {
						area.getInteractables()[gx + 2 + r][gy + 4 + c] = null;
						area.getPassable()[gx + 2 + r][gy + 4 + c] = true;
						area.getMap()[gx + 2 + r][gy + 4 + c] = Tiles.COBBLEROAD;
						area.getResources()[gx + 2 + r][gy + 4 + c] = null;

					}
				}
			}}catch (IndexOutOfBoundsException e){/*do nothing*/}
	}

}
