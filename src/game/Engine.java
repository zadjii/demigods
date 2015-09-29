package game;

import areas.*;
import areas.etc.Entrance;
import areas.eventQueue.EventQueue;
import conditions.Condition;
import effects.Effect;
import enchantments.Enchantment;
import entities.Entity;
import entities.buildings.*;
import entities.characters.etc.*;
import entities.characters.personas.*;
import entities.characters.Character;
import entities.interactables.*;
import entities.particles.Particle;
import entities.particles.etc.DroppedItem;
import entities.tiles.Tiles;
import gui.GUIManager;
import gui.guiPanels.*;
import gui.trading.Trading;
import items.*;
import items.armor.*;
import items.foundations.*;
import items.materials.Scraps;
import items.placements.*;
import items.tools.Tool;
import items.weapons.*;
import org.lwjgl.util.*;
import org.newdawn.slick.Input;
import util.*;
import util.exceptions.*;

import java.io.Serializable;
import java.util.*;

public class Engine implements Serializable {

    private static NewGameArea area;

	private static transient GameScreen gameScreen;//= new GameScreen();
    private static int seed = (int)(Math.random() * 1000000);
    private static Random rand = new Random(seed);
    private static transient Player player;
//    private static Rectangle screen = new Rectangle(0, 0, Demigods.getScreenWidth(), Demigods.getScreenHeight());

	private static float screenX = 0;
	private static float screenY = 0;
	private static float screenW = Demigods.getScreenWidth();
	private static float screenH = Demigods.getScreenHeight();

    private static ArrayList<Persona> toBeRemovedPersonas = new ArrayList<Persona>();
	private static ArrayList<Effect> toBeAddedEffects = new ArrayList<Effect>();
    private static ArrayList<Integer> baseIDs = new ArrayList<Integer>();
    private static String worldName;
    private static World world;
    private static Point mapPos;
    private static int next_ID;
    private static boolean preparedToFastTravel = false; //Indicates that on this tick, we'll be fast travelling somewhere
	private static Point fastTravelTargetLoc;//map coord to FT to. Has priority if both loc&id are set
	private static int fastTravelTargetID;//Interior map id to FT to.
	private static Point entranceLocationOverride = null;//Overrides the default entrance location of a map
														//the default is ALWAYS at the center.
    private static Point lastBase = null;//the maploc of the last base that was visited, for respawning
	private static Point initialSpawn = null;

    private static float zoomFactor = 1.0f;
	private static transient boolean paused;
	private static boolean areaIsCleared;
	private static boolean forceNextArea = false;
	/**
	 * Indicates that the player is near the edge of the map.
	 * Will be replaced by a GUI in the near future.
	 */
	private static boolean atEdgeOfMap = false;
	/**
	 * Indicates that on this tick we should load a different area
	 */
	private static boolean walkOutOfArea = false;
	/**
	 * location of the area to transit to
	 */
	private static Point nextAreaPos = null;
	public static transient Input input;// = new Input(600);
    private static transient Point mouse = new Point(-1,-1);

    //These will be used for saving/loading
    private NewGameArea _area;
    private int _seed;
    private Random _rand;
//    private Rectangle _screen;

	private float _screenX = 0;
	private float _screenY = 0;
	private float _screenW = Demigods.getScreenWidth();
	private float _screenH = Demigods.getScreenHeight();

    private ArrayList<Persona> _toBeRemovedPersonas;
	private ArrayList<Effect> _toBeAddedEffects;
    private ArrayList<Integer> _baseIDs;
    private String _worldName;
    private World _world;
    private Point _mapPos;
    private int _next_ID;
    private float _zoomFactor;
    private boolean _areaIsCleared;
    private boolean _forceNextArea;
    private boolean _atEdgeOfMap;
    private boolean _walkOutOfArea;
    private Point _nextAreaPos;
	private boolean _preparedToFastTravel;
	private Point _fastTravelTargetLoc;//map coord to FT to. Has priority if both loc&id are set
	private int _fastTravelTargetID;//Interior map id to FT to.
	private Point _entranceLocationOverride;
	private Point _lastBase;
	private Point _initialSpawn;


    public static Engine get() {
        Engine engine = new Engine();
        engine._area = area;
        engine._baseIDs = baseIDs;
        engine._mapPos = mapPos;
        engine._next_ID = next_ID;
        engine._rand = rand;
//        engine._screen = screen;
	    engine._screenX = screenX;
	    engine._screenY = screenY;
	    engine._screenW = screenW;
	    engine._screenH = screenH;
        engine._seed = seed;
        engine._toBeRemovedPersonas = toBeRemovedPersonas;
	    engine._toBeAddedEffects = toBeAddedEffects;
        engine._world = world;
        engine._worldName = worldName;
        engine._zoomFactor = zoomFactor;
        engine._atEdgeOfMap = atEdgeOfMap;
        engine._forceNextArea = forceNextArea;
        engine._areaIsCleared = areaIsCleared;
        engine._walkOutOfArea = walkOutOfArea;
        engine._nextAreaPos = nextAreaPos;
        engine._preparedToFastTravel    = preparedToFastTravel;
        engine._fastTravelTargetLoc     = fastTravelTargetLoc ;
        engine._fastTravelTargetID      = fastTravelTargetID  ;
        engine._entranceLocationOverride= entranceLocationOverride  ;
        engine._lastBase                = lastBase  ;
        engine._initialSpawn            = initialSpawn  ;
        return engine;
    }


    public static void set(Engine engine) {
        area = engine._area;
        baseIDs = engine._baseIDs;
        mapPos = engine._mapPos;
        next_ID = engine._next_ID;
        rand = engine._rand;
        screenX = engine._screenX;
	    screenY = engine._screenY;
	    screenW = engine._screenW;
	    screenH = engine._screenH;
        seed = engine._seed;
        toBeRemovedPersonas = engine._toBeRemovedPersonas;
	    toBeAddedEffects = engine._toBeAddedEffects;
        world = engine._world;
        worldName = engine._worldName;
        zoomFactor = engine._zoomFactor;
        atEdgeOfMap = engine._atEdgeOfMap;
        forceNextArea = engine._forceNextArea;
        areaIsCleared = engine._areaIsCleared;
        walkOutOfArea = engine._walkOutOfArea;
        nextAreaPos =       engine._nextAreaPos;
	    preparedToFastTravel    =       engine._preparedToFastTravel;
	    fastTravelTargetLoc     =       engine._fastTravelTargetLoc ;
	    fastTravelTargetID      =       engine._fastTravelTargetID  ;
	    entranceLocationOverride=       engine._entranceLocationOverride  ;
	    lastBase                =       engine._lastBase  ;
	    initialSpawn            =       engine._initialSpawn  ;
    }

    //shortcut: Initializer
    public static void init(Player p, String name) {
        worldName = name;
        SaveManager.setWorldName(name);
        player = p;
        world = new World(seed, rand, name, player.getName());
        int cx = World.SIZE / 2;
        int cy = World.SIZE / 2;

		/*
        * checks an area starting at the center of the map for valid¡ starts.
		* if there are no valid maps in the center half of the map -> fuck
		*/
        FINDING_START:
        for (int r = 0; r < World.SIZE / 2; r++) {
            for (int x = cx - r; x < cx + r; x++) {
                for (int y = cy - r; y < cy + r; y++) {
                    if (world.isValidStart(x, y)) {
                        mapPos = new Point(x, y);
                        break FINDING_START;
                    }
                }
            }
        }
		/*{
			//test to see if my idea for atacking buildings will work
			House house = new House(5, 5);
			Interactable inter = house;
			if(inter instanceof Building) System.out.println("Success!");
			//apparently always true
		}*/
        //area =  new OverworldArea(
        //				rand,
        //				NewGameArea.DEFAULT_SIZE,
        //				getNextID(),
        //				player.getCharacter().getLvl()
        //				//,
        //				//here's where we'd put the world's area at the initial mapPos
        //		);

	    //makes the mappos we found the location of a starting village.
	    //starting villages are just ever so slightly different.
	    //they always start the same, different from a normal village, and are always the same size, regardless of map size.
	    world.setStartingVillage(mapPos);
		initialSpawn = new Point(mapPos);

	    area = world.createNewArea(mapPos, getNextID(), player, 0);
        //I am going to redo saving.
        //SaveManager.saveArea(area, mapPos);
        area.getPersonas().add(player);
	    player.getCharacter().setBaseMap(mapPos.getX(), mapPos.getY());

	    world.explore(mapPos);

	    world.explore(mapPos.getX()-1, mapPos.getY()-1);
	    world.explore(mapPos.getX()-0, mapPos.getY()-1);
	    world.explore(mapPos.getX()+1, mapPos.getY()-1);

	    world.explore(mapPos.getX()-1, mapPos.getY()+0);
	    world.explore(mapPos.getX()+1, mapPos.getY()+0);

	    world.explore(mapPos.getX()-1, mapPos.getY()+1);
	    world.explore(mapPos.getX()-0, mapPos.getY()+1);
	    world.explore(mapPos.getX()+1, mapPos.getY()+1);

	    lastBase = new Point(mapPos);

	    setZoom(2.0f);
        centerScreen(player);
        //while(player.getCharacter().getLvl() < 10){player.getCharacter().addXP(50);}
        //addAllItems();
//        addSomeItems();
        //addAllWeapons();
        //makeRadiusGrass(player.getCharacter().getAbsX(), player.getCharacter().getAbsY(), 10*16);

    }

    public static void tileEmptyCheck(NewGameArea area, ResourceTile tile, int gx, int gy) {
        if (!tile.hasResources()) {
            if (area.getMap()[gx][gy] == Tiles.TREE) {
                area.getPassable()[gx][gy] = true;
                area.getMap()[gx][gy] = Tiles.STUMP;
                area.getResources()[gx][gy] = null;
            } else if (area.getMap()[gx][gy] == Tiles.CACTUS) {
                area.getPassable()[gx][gy] = true;
                area.getMap()[gx][gy] = Tiles.SAND;
                area.getResources()[gx][gy] = null;
            } else if (area.getMap()[gx][gy] == Tiles.OVERWORLD_STONE) {
                area.getPassable()[gx][gy] = true;
                area.getMap()[gx][gy] = Tiles.DIRT;
                area.getResources()[gx][gy] = null;
            }
        }
    }

    public static void bounce(Character src, Character tgt) {
//        int dx = 0;
//        int dy = 0;
//        if (src.getX() > tgt.getX()) dx = -1;
//        else if (src.getX() == tgt.getX()) dx = 0;
//        else if (src.getX() < tgt.getX()) dx = 1;
//        if (src.getY() > tgt.getY()) dy = -1;
//        else if (src.getY() == tgt.getY()) dy = 0;
//        else if (src.getY() < tgt.getY()) dy = 1;
//        if (dx == 0 && dy == 0) {
//            dx = -2;
//            dy = -2;
//        }
//        tgt.setKnockback(new Knockback(tgt, dx, dy));
	    tgt.setKnockback(new Knockback(tgt, src.getLoc(), 6));
    }

    //shortcut: update()
    public static void update() {
        if (paused) return;
//	    System.out.println("Engine:0 " + gameScreen);
	    mouse.setLocation(
			    (int) ( (input.getMouseX() / Engine.getZoom()) + Engine.getScreenX() ),
			    (int) ( (input.getMouseY() / Engine.getZoom()) + Engine.getScreenY() )
	    );
	    if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
//		    System.out.println("Engine:1 " + input);
//		    mouse.setLocation(//This line used to be here, I moved it up& out so maybe moving the mose w/o pressing would still work.
//				    (int) (input.getMouseX() / Engine.getZoom()) + Engine.getScreen().getX(),
//				    (int) (input.getMouseY() / Engine.getZoom()) + Engine.getScreen().getY()
//		    );
//		    if(!GUIManager.isGuiPressed())
				mouseInteractionTick();

	    }
//	    System.out.println("Engine:3");
//	    centerScreen(player);
        Character playerCharacter = player.getCharacter();
        playerCharacter.setDY(0);
        playerCharacter.setDX(0);
//	    System.out.println("w: " + input.isKeyDown(Input.KEY_W));
//	    System.out.println("a: " + input.isKeyDown(Input.KEY_A));
//	    System.out.println("s: " + input.isKeyDown(Input.KEY_S));
//	    System.out.println("d: " + input.isKeyDown(Input.KEY_D));
//	    System.out.println("" + playerCharacter.getX() + "," + playerCharacter.getY());
	    if (input.isKeyDown(Input.KEY_W)) {
            playerCharacter.setDY(-playerCharacter.getSpeed());
        }
        if (input.isKeyDown(Input.KEY_S)) {
            playerCharacter.setDY(playerCharacter.getSpeed());
        }
        if (input.isKeyDown(Input.KEY_A)) {
            playerCharacter.setDX(-playerCharacter.getSpeed());
        }
        if (input.isKeyDown(Input.KEY_D)) {
            playerCharacter.setDX(playerCharacter.getSpeed());
        }

        area.getPersonas().reseed();
        for (Persona p : area.getPersonas().getPersonas()) {
            p.moveAI();
        }
        for (Persona p : area.getPersonas().getPersonas()) {
            p.getCharacter().tick(area);
            if (p.getEquippedItem() != null) p.getEquippedItem().tick();
            ArrayList<String> conditionsToBeRemoved = new ArrayList<String>();
            for (ArrayList<Condition> conditions : p.getCharacter().getConditionMap().values()) {
                ArrayList<Condition> tempc = new ArrayList<Condition>();
                for (Condition c : conditions) {
                    c.tick();
                    if (c.isOff()) {
                        c.turnOff();
                        conditionsToBeRemoved.add(c.toString());
                        //tempc.add(c);
                    }
                    if (p.getCharacter().getHP() <= 0) {
                        if (c.getSrc() != null) {
                            Experience.grantXP(p.getCharacter(), c.getSrc());
                        }
                        toBeRemovedPersonas.add(p);
                    }
                }
                //for (Condition c : tempc) {
                //	conditions.remove(c);
                //}
            }
            for (String s : conditionsToBeRemoved) {
                p.getCharacter().removeCondition(s);
            }
        }
        for (DroppedItem i : area.getDroppedItems()) {
            i.tick();
        }
        ArrayList<Effect> tempE = new ArrayList<Effect>();
        for (Effect effect : area.getEffects()) {
            tempE.add(effect.tick());
        }
        for (Effect effect : tempE) {
            area.getEffects().remove(effect);
        }
	    for (Effect effect : toBeAddedEffects) {
		    area.getEffects().add(effect);
	    }
        for (Persona p : toBeRemovedPersonas) {
            area.getPersonas().remove(p);
        }
//	    System.out.println("\t" + playerCharacter.getDX() + "," + playerCharacter.getDY());
	    toBeAddedEffects.clear();
	    toBeRemovedPersonas.clear();

        area.tick();

//        centerScreen(area.getPersonas().getPersonas().get(1));
	    centerScreen(player);


        if (area.isBase()) {
        } else if (area.isCleared()) {
            if (!areaIsCleared) {
//                gameScreen.setGuiPanel(new AreaClearedPanel());
                area.clearArea();
            }
            areaIsCleared = area.isCleared();
        }

		/*//TODO:Add the ability to move to world based maps
		* Check if the player is near the edge. (<3 tiles)
		* if so, prompt them to leave. -> display a new panel
		*       initialize the new panel with the position of the new map
		*
		* upon clicking yes on the panel,
		*       toggle a bool in Engine indicating time to leave
		*       set next map pos to the position stored in the panel
		*       closeGUI
		*
		* if walkingOutOfArea
		*       make sure nextMapPos is != null
		*       remove the player from the map
		*       save the current area
		*       attempt to load the area at the given pos
		*       if == null
		*           world -> generate new overworld
		*       area = newArea
		*       add player to area
		*       clear walkingOutOfArea,nextMapPos
		*
		*/
        if (playerCharacter.getX() < 3 * 16) {//left
            atEdgeOfMap = true;
            nextAreaPos = new Point(mapPos.getX() - 1, mapPos.getY());
        } else if (playerCharacter.getX() > (area.getSize() - 3) * 16) {//right
            atEdgeOfMap = true;
            nextAreaPos = new Point(mapPos.getX() + 1, mapPos.getY());
        } else if (playerCharacter.getY() < 3 * 16) {//top
            atEdgeOfMap = true;
            nextAreaPos = new Point(mapPos.getX(), mapPos.getY() - 1);
        } else if (playerCharacter.getY() > (area.getSize() - 3) * 16) {//bottom
            atEdgeOfMap = true;
            nextAreaPos = new Point(mapPos.getX(), mapPos.getY() + 1);
        } else {
            atEdgeOfMap = false;
            nextAreaPos = null;
        }
        if (walkOutOfArea) {
            System.out.println("Engine:walking out of area (walkOutOfArea == true)");
            NewGameArea area = SaveManager.loadArea(nextAreaPos);
            if (area == null) {
	            System.out.println("\t:area was null");
	            area = world.createNewArea(nextAreaPos, getNextID(), player, Maths.dist(initialSpawn, mapPos));
                SaveManager.saveArea(area, nextAreaPos);
            }
	        System.out.println("\t:at this point, area shouldnt be null= " + area);

	        //remove player from curr area
	        getActiveArea().getPersonas().remove(player);
			//todo may want to remove everyone from area...

	        //save current area
            SaveManager.saveArea(getActiveArea(), mapPos);
			//todo: forced next area debug?
//            if (forceNextArea) {
//                area = NewGameArea.forcedArea();
//                forceNextArea = false;
//            }

	        mapPos = nextAreaPos;
	        world.explore(mapPos);
	        Engine.area = area;
	        updateUponEntering();
	        area.getPersonas().add(player);
//            area = area;//DAMMIT JAKE. DAMMIT DAMMIT DAMMIT
	        player.getCharacter().setBaseMap(mapPos.getX(), mapPos.getY());
            nextAreaPos = null;
            walkOutOfArea = false;
            atEdgeOfMap = false;
        }
	    if(preparedToFastTravel && (fastTravelTargetLoc != null || fastTravelTargetID != OverworldArea.NO_UNDERWORLD)){
		    performFastTravel();
	    }
    }

    public static void walkOutOfArea() {
        System.out.println("Engine:attempting to walk out of area (newline was pressed)");
        if (atEdgeOfMap) {
            walkOutOfArea = true;
        }
    }

	/**
	 * Prepare the engine to move to a new location in the overworld.
	 * This is called by the fast travel button. It could, <i>could</i> be used
	 * to implement Entrances back to the overworld.
	 *
	 * Like holy shit it really could.
	 */
	public static void fastTravelToLoc(Point overworldLoc){
		if(overworldLoc != null){
			preparedToFastTravel = true;
			fastTravelTargetLoc = new Point(overworldLoc.getX(), overworldLoc.getY());
		}
	}
	/**
	 * Prepare the engine to move to a new location in the overworld.
	 * This is called by the enter underworld button.
	 *
	 * Also, <i>MIGHT</i> be able to be used for entrances to other interiors.
	 * Like, It probably should... Really, the hardest part of entrances is
	 * making sure they are set up properly
	 */
	public static void fastTravelToID(int mapID){
		if(mapID != OverworldArea.NO_UNDERWORLD){
			preparedToFastTravel = true;
			fastTravelTargetID = mapID;
		}
	}

	/**
	 * Prepare the engine to move to a new location in the overworld.
	 * This is called by the enter underworld button.
	 *
	 * Also, <i>MIGHT</i> be able to be used for entrances to other interiors.
	 * Like, It probably should... Really, the hardest part of entrances is
	 * making sure they are set up properly
	 * @param targetEntranceLoc the location that the player will arrive in the new area.
	 *                          Passed from the Entrance calling this.
	 *                          overrides the default entrance location when putting the player in the world again.
	 */
	public static void fastTravelToID(int mapID, Point targetEntranceLoc){
		if(mapID != OverworldArea.NO_UNDERWORLD){
			preparedToFastTravel = true;
			fastTravelTargetID = mapID;
			entranceLocationOverride = targetEntranceLoc;
		}
	}

	/**
	 * Actually excecutes the travel. swaps out the current area for the loaded one.
	 * Assumes the target map already exists. This might be seriously problematic if it didn't, actually.
	 */
	private static void performFastTravel(){
		if(
			!(preparedToFastTravel
				&& (fastTravelTargetLoc != null || fastTravelTargetID != OverworldArea.NO_UNDERWORLD))
			){
			//If the conditions used to call this function actually aren't
			// true(not sure how), then clear everything and return
			preparedToFastTravel = false;
			fastTravelTargetLoc = null;
			fastTravelTargetID = OverworldArea.NO_UNDERWORLD;
			return;
		}
		NewGameArea areaToLoad = null;
		if(fastTravelTargetLoc != null){

			areaToLoad = SaveManager.loadArea(fastTravelTargetLoc);

			//This code is here, but it shouldn't be needed. We shouldn't be
			// able to travel to an area that hasn't already been loaded before.
			//FIXME: remove dis shit
//			if (areaToLoad == null) {
//				System.out.println("\t:areaToLoad was null");
//				areaToLoad = world.createNewArea(fastTravelTargetLoc, getNextID(), player);
//				SaveManager.saveArea(areaToLoad, fastTravelTargetLoc);
//			}

		}else if(fastTravelTargetID != OverworldArea.NO_UNDERWORLD){
			areaToLoad = SaveManager.loadArea(fastTravelTargetID);
		}
		if(areaToLoad != null){

			area.getPersonas().remove(player);

			if(area instanceof OverworldArea)SaveManager.saveArea(area, mapPos);
			else SaveManager.saveArea(area);

			area = areaToLoad;

			mapPos = fastTravelTargetLoc;//this is null if the map is an interior/uw (inuw)
			updateUponEntering();
			area.getPersonas().add(player);
			player.getCharacter().setBaseMap(mapPos.getX(), mapPos.getY());
			nextAreaPos = null;
			walkOutOfArea = false;
			atEdgeOfMap = false;

			if(entranceLocationOverride != null){
				player.getCharacter().setLoc(
						new Point(entranceLocationOverride.getX(), entranceLocationOverride.getY())
				);
				entranceLocationOverride = null;
			}
			else{
				player.getCharacter().setLoc(
						new Point(area.getEntranceLocation().getX(), area.getEntranceLocation().getY())
				);
			}


		}
		else{
			System.err.println("DID NOT SUCCESSFULLY LOAD THE TARGET AREA: "
					+ ( (fastTravelTargetLoc!=null)?
							(fastTravelTargetLoc.getX() +","+ fastTravelTargetLoc.getY())
							:(fastTravelTargetID)
					)
			);
		}
		fastTravelTargetID = OverworldArea.NO_UNDERWORLD;
		fastTravelTargetLoc = null;
		preparedToFastTravel = false;
	}

	public static void setEntranceLocationOverride(Point overridePoint){
		entranceLocationOverride = overridePoint;
	}

	/**
	 * Respawns the player.
	 * Uses the previously set values of lastBaseVisited & respawnLocation
	 *      - lastBaseVisited: map loc to load
	 *      - respawnLocation: location in map to respawn to.
	 */
	public static void respawn(){
		if(player.getCharacter().getHP() > 0)return;
		if(area.getPersonas().contains(player))area.getPersonas().remove(player);
		area.getPersonas().add(player);
		System.out.println("respawning from: " + mapPos.getX() + "," + mapPos.getY());
		fastTravelTargetLoc = lastBase;
		preparedToFastTravel = true;
		performFastTravel();
		System.out.println("arrived? in: " + mapPos.getX() +","+ mapPos.getY());
		player.getCharacter().setLoc(((OverworldArea)(area)).getRespawnLocation());

		player.getCharacter().respawn();
		for (int i = 0; i < 240; i++) {
			add(Particle.newRespawnParticle(player.getCharacter()));
		}
	}

	/**
	 * Update the state of things that might need updating,
	 * regarding the state of the area we're loading
	 *
	 * Performed BEFORE the player is added to the personamap
	 */
	private static void updateUponEntering(){
		if(mapPos != null){ // it would be null if the entered an underworld
			int mapX = mapPos.getX();
			int mapY = mapPos.getY();

			if(world.isRespawnable(mapPos)){
				lastBase = mapPos;
			}
		}
		if(area instanceof OverworldArea){
			OverworldArea oArea = (OverworldArea)area;
			oArea.rebootEventQueue(world.getState(mapPos));
		}

	}

    public static ArrayList<Persona> getAdjacentPersonas() {
		return getAdjacentPersonas(16);
    }

	public static ArrayList<Persona> getAdjacentPersonas(int radius) {
		return getAdjacentPersonas(player, radius);
	}


	public static ArrayList<Persona> getAdjacentPersonas(Persona tgt, int radius) {
		return getAdjacentPersonas(tgt.getCharacter(), radius);
	}

	/**
	 * Also returns the source of the query.
	 */
	public static ArrayList<Persona> getAdjacentPersonas(Character tgt, int radius) {

		ArrayList<Persona> temp = area.getPersonas().get(
				new Rectangle(
						tgt.getXi() - radius,
						tgt.getYi() - radius,
						radius * 2,
						radius * 2
				)
		);

		return temp;
	}

    public static void add(Particle p) {
        gameScreen.getParticles().add(p);
    }

    public static void add(Item item, Entity entity, float dx, float dy, float dz) {
        DroppedItem newItem = new DroppedItem(item);
        newItem.setDX(dx);
        newItem.setDY(dy);
        newItem.setDZ(dz);
        newItem.setXY(entity.getX(), entity.getY());
        area.getDroppedItems().add(newItem);
    }

    public static void add(Effect effect) {
	    toBeAddedEffects.add(effect);
//        area.getEffects().add(effect);
    }

    public static void add(DroppedItem item) {
        area.getDroppedItems().add(item);
    }

    public static void centerScreen(Persona p) {
        centerScreen(p.getCharacter().getX(), p.getCharacter().getY());
    }

    public static void centerScreen(float x, float y) {
//	    if(screen == null)screen = new Rectangle();

	    screenW = (   (Demigods.getScreenWidth() / zoomFactor));
	    screenH = (   (Demigods.getScreenHeight() / zoomFactor));

		screenX = (x - (screenW / 2));
	    screenY = (y - (screenH / 2));
//	    System.out.println(screen.getX() + ", " + screen.getY() + ";\t" + screen.getWidth() + ", " + screen.getHeight());
//	    System.out.println(Demigods.getScreenWidth() + ", " + Demigods.getScreenHeight() + ": " + zoomFactor);
    }

    public static void pause() {
        paused = true;
    }

    public static void unpause() {
        paused = false;
    }

    public static void toggleInventory() {
        if (gameScreen.getGuiPanel() instanceof InventoryPanel) {
            gameScreen.closeGUIPanel();
//			this.unpause();
        } else {
            gameScreen.setGuiPanel(new InventoryPanel());
//			this.pause();
        }
    }
	public static void toggleMap() {
		if (gameScreen.getGuiPanel() instanceof MapPanel) {
			gameScreen.closeGUIPanel();
//			unpause();
		} else {
			gameScreen.setGuiPanel(new MapPanel());
//			pause();
		}
	}
    public static boolean closeGUI() {
        boolean returnValue = false;
        if (gameScreen.getGuiPanel() != null) returnValue = true;
        gameScreen.setGuiPanel(null);
        return returnValue;
    }

    public static void togglePause() {
        paused = !paused;
    }

    public static void addToBeRemoved(Persona p) {
        toBeRemovedPersonas.add(p);
    }

    public static void toggleSkillLevelUpPanel(Character character) {
        if (gameScreen.getGuiPanel() == null) {
            SkillPanel panel = new SkillPanel(character);
            gameScreen.setGuiPanel(panel);
        } else {
            gameScreen.closeGUIPanel();
        }
    }

//    public static void establishBase() {
//        area.setIsBase(true);
//        player.setLastBase(area.getID());
//        baseIDs.add(area.getID());
//        for (int x = 0; x < area.getSize(); x++) {
//            for (int y = 0; y < area.getSize(); y++) {
//                area.getMap()[x][y] = Tiles.DIRT;
//            }
//        }
//        moveOn();
//    }

//    public static void returnToBase() {
//        System.out.println(
//                "Return to base!"
//        );
//        int baseToLoad = player.getLastBase();
//        area.getPersonas().remove(player);
//        SaveManager.saveArea(area);
//        //try {
//        //    this.area = SaveManager.loadArea(baseToLoad);
//        //} catch (NoSavedMapException e) {
//        //    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        //    System.out.println(
//        //            "Engine: Base wasn't loaded. Sorry, it's gone.[0]"
//        //    );
//        //}
//        enterNewArea();
//    }

//    public static void moveOn() {
//        area.getPersonas().remove(player);
//        //SaveManager.saveArea(this.area);
//        area =
//                new OverworldArea(
//                        rand,
//                        NewGameArea.DEFAULT_SIZE,
//                        getNextID(),
//                        player.getCharacter().getLvl()
//                );
//        enterNewArea();
//    }

    public static void enterNewArea() {
        player.getCharacter().setXY((area.getSize() / 2) * 16, (area.getSize() / 2) * 16);
        area.getPersonas().add(player);
        closeGUI();
        areaIsCleared = area.isCleared();
    }

    public static void goToArea(int id) {
        NewGameArea tempArea = null;
        tempArea = SaveManager.loadArea(id);
        if (tempArea != null) {
            area.getPersonas().remove(player);
            //SaveManager.saveArea(this.area);
            area = tempArea;
            if (!area.isBase()) area.setEventQueue(EventQueue.newDynamicEvents());
            enterNewArea();
        }
    }

    public static void displayShop(int shopType) {
        closeGUI();
        gameScreen.setGuiPanel(Trading.get(shopType));
    }

    public static void talk(MessagePanel gui) {
        closeGUI();
        gameScreen.setGuiPanel(gui);
    }

    public static void exploreMenu() {
        if (gameScreen.getGuiPanel() == null) {
            ExplorationPanel panel = new ExplorationPanel();
            gameScreen.setGuiPanel(panel);
        } else {
            gameScreen.closeGUIPanel();
        }
    }

    private static void mouseInteractionTick() {
        boolean lmbPressed = input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON),
                rmbPressed = input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON);
        if (!rmbPressed && !lmbPressed) return;
//	    System.out.println("Engine:4 " + gameScreen.haveToRelease);
//	    System.out.println("mouse tick");
        Character pc = player.getCharacter();
        FIRST_HALF:
        {
            if (lmbPressed && !gameScreen.haveToRelease) {
                if (pc.getAnimation() == null) {
                    if (pc.getSkillReadied()) {
                        if (pc.getReadiedSkill().use(player.getCharacter(), mouse)) {
                            pc.setSkillReadied(false);
                            pc.setReadiedSkill(null);
                        }
                        break FIRST_HALF;
                    }
                    if (player.getEquippedItem().isOffCooldown()) {
                        playerAttack(mouse);
                        playerHarvest(mouse);
                        playerBuild(mouse);
                        player.getEquippedItem().use();
                        player.getCharacter().setSwinging(true);
                    }
                }
            }
        }
	    //something in here is buggy.
	    //Not sure what, but I just lost a half hour to locating this.
	    //Todo this general area
//	    SECOND_HALF:{
//		    if (lmbPressed) {
//			    if(!gameScreen.haveToRelease){
//			    	playerInteract();
//			    }
//		    }
//	    }
    }

    private static void playerHarvest(Point mouse) {
        int mx = mouse.getX();
        int my = mouse.getY();
        int gx = mx / 16, gy = my / 16;
        ResourceTile tile;
        try {
            tile = area.getResources()[mx / 16][my / 16];
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        if (tile != null && Maths.dist(mouse, player.getCharacter().getLoc()) < (5 * 16)) {
            if (area.getMap()[gx][gy] == Tiles.TREE ||
                    area.getMap()[gx][gy] == Tiles.CACTUS) {
                gameScreen.getParticles().add(Particle.newTreeParticle(mx / 16, my / 16, (int)(Math.random() * 4 - 2), (int)(Math.random() * 2) + 1));
            }
            Audio.playHit();
            if (tile.isAppropriateTool(player.getEquippedItem())) {
                Tool tool = (Tool)player.getEquippedItem();
                Item i = tile.harvest(tool.getHarvestRate());
                if (i != null) {
                    DroppedItem item = new DroppedItem(mx, my, i);
                    item.setDXDYDZ((float)((Math.random() < .5 ? 1 : -1) * (Math.random() * 1.5 + 1)), (float)((Math.random() < .5 ? 1 : -1) * (Math.random() * 1.5 + 1)), (float)(Math.random() * 3 + 3));
                    area.getDroppedItems().add(item);
                }
            } else {
                if (tile.getScraps() > 0) {
                    tile.setScraps(tile.getScraps() - 1);
                    Item i = new Scraps();
                    DroppedItem item = new DroppedItem(mx, my, i);
                    item.setDXDYDZ((float)((Math.random() < .5 ? 1 : -1) * (Math.random() * 1.5 + 1)), (float)((Math.random() < .5 ? 1 : -1) * (Math.random() * 1.5 + 1)), (float)(Math.random() * 3 + 3));
                    //item.setAbsoluteLoc(Demigods.getMouseAbsolute());
                    area.getDroppedItems().add(item);
                }
            }
            tileEmptyCheck(area, tile, mx / 16, my / 16);
        }
    }

    private static void playerBuild(Point mouse) {
        Point playerPoint = player.getCharacter().getLoc();
        int mAX = mouse.getX();
        int mAY = mouse.getY();
        int mgx = mAX / 16;
        int mgy = mAY / 16;
        //if (Maths.dist(playerPoint.getX(), mousePoint.getX(), playerPoint.getY(), mousePoint.getY()) < (10*16)) {
        if (player.getEquippedItem().isFoundation()) {
            Foundation i = (Foundation)player.getEquippedItem();
            boolean placeable = true;
            Rectangle potentialBounds = i.getPotentialBounds(mgx, mgy);
            int potentialGWidth = potentialBounds.getWidth();
            int potentialGHeight = potentialBounds.getHeight();
            potentialBounds = new Rectangle(
                    potentialBounds.getX() * 16,
                    potentialBounds.getY() * 16,
                    potentialBounds.getWidth() * 16,
                    potentialBounds.getHeight() * 16
            );
            if (area.getPersonas().get(potentialBounds).size() > 0) {
                placeable = false;
            }
            for (int r = 0; r < potentialGWidth; ++r) {
                for (int c = 0; c < potentialGHeight; ++c) {
                    if (area.getEntrances()[mgx + r][mgy + c] != null) {
                        placeable = false;
                        break;
                    }
                    if (!area.getPassable()[mgx + r][mgy + c]) {
                        placeable = false;
                        break;
                    }
                }
            }
            if (placeable) {
                Building b = i.build(mgx, mgy);
                b.setTeam(player.getTeam());
                InteractableReference reference = new InteractableReference(b.getXi(), b.getYi(), b);
                if (!i.buildingIsPassable()) {
                    for (int r = 0; r < potentialGWidth; ++r) {
                        for (int c = 0; c < potentialGHeight; ++c) {
                            area.getPassable()[mgx + r][mgy + c] = false;
                            area.getInteractables()[mgx + r][mgy + c] = reference;
                        }
                    }
                }
                // activeArea.getCollidables().add(b);
                area.getTickables().add(b);
                area.getInteractables()[mgx + potentialGWidth - 1][mgy + potentialGHeight - 1] = b;
                player.removeItem(player.getSelectedItemNum());
                if (b.isEnterable()) {
                    Point entranceAdjustment = b.getEntranceAdjustment();
                    int entranceAbsX = b.getXi() + (entranceAdjustment.getX() * 16);
                    int entranceAbsY = b.getYi() + (entranceAdjustment.getY() * 16);
//						Entrance entrance = new Entrance(b.getEntranceCoordinates()[0], b.getEntranceCoordinates()[1], true, getNextInteriorID());
//						int entranceAbsGX = b.getEntranceCoordinates()[0];
//						entranceAbsGX += (b.getBaseMap().getX() * Map.SIZE);
//						int entranceAbsGY= b.getEntranceCoordinates()[1];
//						entranceAbsGY += (b.getBaseMap().getY() * Map.SIZE);
//						if(b.getBaseMap().getX() < 0)entranceAbsGX +=1;
//						if(b.getBaseMap().getY() < 0)entranceAbsGY +=1;
                    Entrance entrance = new Entrance(0, 0, true, getNextID());
                    entrance.setXY(entranceAbsX, entranceAbsY);
                    //entrance.setAbsoluteLoc(entranceAbsGX*16, entranceAbsGY*16);
//						activeArea.setEntrance(entranceAbsX, entranceAbsY, entrance);
                    area.getEntrances()[entranceAbsX / 16][entranceAbsY / 16] = entrance;
                    //mouseMap.getEntrances()[entrance.getGX()][entrance.getGY()] = entrance;
                    //Interior interior = i.getInterior(seed, entrance);
                    //b.setEntrance(entrance);
                    //entrance.setTargetAbsLoc(interior.getEntrancePoint());
                    //SaveManager.saveInterior(interior);
                }
                gameScreen.haveToRelease = true;
            }
        } else if (player.getEquippedItem().isRoad() && area.isBase()) {
            //System.out.println("    Engine.playerBuild - Road");
            Road road = (Road)player.getEquippedItem();
            int roadID = road.getID();
            if (area.getMap()[mgx][mgy] != roadID) {
                makeRoad(mAX - 16, mAY - 16, roadID);
                makeRoad(mAX - 16, mAY, roadID);
                makeRoad(mAX - 16, mAY + 16, roadID);
                makeRoad(mAX, mAY - 16, roadID);
                makeRoad(mAX, mAY, roadID);
                makeRoad(mAX, mAY + 16, roadID);
                makeRoad(mAX + 16, mAY - 16, road.getID());
                makeRoad(mAX + 16, mAY, road.getID());
                makeRoad(mAX + 16, mAY + 16, road.getID());
                player.removeItem(player.getSelectedItemNum());
            }
        } else if (player.getEquippedItem().isInteractableItem()) {
//					&& !activeArea.toString().equalsIgnoreCase("overworld")
//					&& !activeArea.toString().equalsIgnoreCase("dungeon")
//					&& !activeArea.toString().equalsIgnoreCase("Crafting hut")
//					&& !activeArea.toString().equalsIgnoreCase("Workshop")
//					&& !activeArea.toString().equalsIgnoreCase("smithy")) {
            InteractableItem item = (InteractableItem)player.getEquippedItem();
            System.out.println(
                    item
            );
            if (area.getInteractables()[mgx][mgy] == null) {
                if (area.getPassable()[mgx][mgy]) {
                    Interactable interactable = item.getItem(mAX, mAY);
                    area.getInteractables()[mgx][mgy] = interactable;
                    player.removeItem(player.getSelectedItemNum());
                    gameScreen.haveToRelease = true;
                }
            }
        }
    }

    private static void playerInteract() {
        int max = mouse.getX();
        int may = mouse.getY();
        int mgx = max / 16;
        int mgy = may / 16;
        boolean interacted = false;
	    try{
		    Interactable interactable = area.getInteractables()[mgx][mgy];
		    if (interactable != null) {
			    interactable.interact();
			    interacted = true;
			    if (interactable.needToRelease()) gameScreen.haveToRelease = true;
		    }
	    }catch (IndexOutOfBoundsException e){}
    }

    private static void playerAttack(Point mouse) {
        player.getCharacter().getEquippedItem().attack(mouse);
    }

    private static void makeRoad(int x, int y, int type) {
        try {
            if (area.getPassable()[x / 16][y / 16]) {
                area.getMap()[x / 16][y / 16] = type;
            }
        } catch (NullPointerException e) {
            System.out.println("Engine.makeRoad() caught a NPE");
        }
    }

    /////////////////////
    //GETTERS & SETTERS//
    /////////////////////
    public static Random getRand() {
        return rand;
    }

    public static Point getMouse() {
        return mouse;
    }

    public static float getZoom() {
        return zoomFactor;
    }

    public static void setZoom(float z) {
        zoomFactor = z;
//        screen.setWidth((int)(screen.getWidth() / zoomFactor));
//        screen.setHeight((int)(screen.getHeight() / zoomFactor));
    }

    public static NewGameArea getActiveArea() {
        return area;
    }

    public static float[] getScreen() {
	    return new float[]{screenX, screenY, screenW, screenH};
//        return screen;
    }
	public static float getScreenX() {return screenX;}
	public static float getScreenY() {return screenY;}
	public static float getScreenW() {return screenW;}
	public static float getScreenH() {return screenH;}

    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player p) {
        player = p;
//	    if(area != null){
//		    Persona personToBeRemoved = null;
//		    for(Persona person: area.getPersonas().getPersonas()){
//			    if(person instanceof Player){
//				    personToBeRemoved = person;
//				    break;
//			    }
//		    }
//		    if(personToBeRemoved != null){
//			    area.getPersonas().remove(personToBeRemoved);
//		    }
//		    if(area.getPersonas().contains(p)){
//			    area.getPersonas().remove(p);
//			    area.getPersonas().add(p);
//		    }
//	    }
    }

    public static boolean isPaused() {
        return paused;
    }

    public static int getNextID() {
        return next_ID++;
    }

    public static String getWorldName() {
        return worldName;
    }

    public static World getWorld() {
        return world;
    }

	public static boolean isInUnderworld(){
		return area.isInUnderworld();
	}
    ///////////
    //TESTING//
    ///////////

    private static void addAllItems() {
        player.getCharacter().setInventory(new Inventory(10000));
        // *
        try {
            for (int i = 0; i < Constants.allTheItems.length; i++) {
                String name = Constants.allTheItems[i].toString();
                if (!name.equalsIgnoreCase("nullitem") &&
                        !name.equalsIgnoreCase("Foundation") &&
                        !name.equalsIgnoreCase("Empty Hands") &&
                        !name.equalsIgnoreCase("lightsource") &&
                        !name.equalsIgnoreCase("road"))
                    player.getCharacter().getInventory().addItem(Constants.allTheItems[i]);
            }
        } catch (DemigodsException ignored) {
        }
        // */
    }

    private static void addAllWeapons() {
        // *
        try {
            for (int i = 0; i < Constants.allTheItems.length; i++) {
                String name = Constants.allTheItems[i].toString();
                if (!name.equalsIgnoreCase("nullitem") && !name.equalsIgnoreCase("Foundation") && !name.equalsIgnoreCase("Empty Hands") && !name.equalsIgnoreCase("lightsource") && !name.equalsIgnoreCase("interactable") && !name.equalsIgnoreCase("road")) {
                    if (!name.contains("Foundation") && !name.contains("Bar") && !name.contains("Ore") && !name.contains("ore"))
                        player.getCharacter().getInventory().addItem(Constants.allTheItems[i]);
                }
            }
        } catch (DemigodsException ignored) {
        }
        // */
    }

    private static void addSomeItems() {
        player.getCharacter().setInventory(new Inventory(10000));
        try {
//
            player.getCharacter().getInventory().addItem(new WoodenBow());
            addItem(new ChestItem(), 2);
            addItem(new WoodenBow(), 1);
            addItem(new PotionOfTesting(), 4000);
            addItem(new Arrow(), 4000);
//			addItem(new Stone(), 4000);
            addItem(new WoodenBarricadeFoundation(), 400);
            addItem(new HouseFoundation(), 10);
            addItem(new StoneGreatsword(), 1);
            addItem(new GodSword(), 1);
            addItem(new ChronomancerBlade(), 1);
            addItem(new BronzeChest(), 1);
            addItem(new BronzeHat(), 1);
            addItem(new BronzeLegs(), 1);
//			addItem(new SteelAxe(), 1);
            SteelAxe axe = new SteelAxe();
            Enchantment.addEnchantment(axe, "Poisoned");
            //Enchantment.addEnchantment(axe, "Mighty");
            player.getCharacter().getInventory().addItem(axe);
        } catch (DemigodsException ignored) {
        }
    }

    private static void addItem(Item item, int quantity) {
        try {
            for (int i = 0; i < quantity; ++i) {
                player.getCharacter().getInventory().addItem(Constants.getItemFromString(item.toString()));
            }
        } catch (DemigodsException ignored) {
        }
    }

	public static void setGameScreen(GameScreen gameScreen) {
		Engine.gameScreen = gameScreen;
		input.addListener(gameScreen);
	}

	public static Input getInput() {
		return input;
	}
	public static void saveArea(){
		if(area instanceof OverworldArea) SaveManager.saveArea(area, mapPos);
		else SaveManager.saveArea(area);
	}

}
