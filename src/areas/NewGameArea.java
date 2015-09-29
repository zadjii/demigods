package areas;

import effects.Effect;
import entities.interactables.Interactable;
import entities.interactables.ResourceTile;
import entities.particles.etc.DroppedItem;
import entities.tiles.Tiles;
import areas.etc.Entrance;
import areas.eventQueue.EventQueue;
import game.Engine;
import util.data.PersonaMap;
import util.SaveManager;
import entities.Tickable;
import org.lwjgl.util.Point;
import util.data.EntityMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class NewGameArea implements Serializable {
    // Public static Constants
    public static final int WATER_LEVEL = 12;
    public static final int DEFAULT_SIZE = 128;



    // instance variables

    protected Random rand;

    protected int size = 128;

    /**
     * an ID is assigned by the Engine when a new Area is made.
     */
    private int ID;

    /**
     * The Biome value is what Biome the area is. Easy enough. Assigned currently when generated.
     */
    protected int biome;
    /**
     * The uEntrance value is what type of entrance is in the area. 0 = nothing
     */
//    protected int uEntrance = World.NOTHING;
	//uEntrance was abandoned in favor of the more correct "feature" term
	/**
	 * feature is the type of feature (listed in World) that exists in this area.
	 */
	protected int feature;
	protected int state = World.NOT_CLEARED;

    /**
     * A variable stating if the area has been cleared at least once.
     * If there is a uEntrance, then the first time the area is cleared, the
     * corresponding underworld area will be generated and placed in the
     * area list.
     * If the engine determines it wants to put a random event in the area,
     * like a village, stronghold, horde, etc, then it can set hasBeenCleared
     * to false. NOTE: That should never happen if the area is ALSO a uEntrance.
     */
    protected boolean hasBeenCleared = false;

    protected int[][] map;
    protected boolean[][] passable;
    protected Entrance[][] entrances;
    protected ResourceTile[][] resources;
    protected Interactable[][] interactables;

    private ArrayList<Effect> effects = new ArrayList<Effect>();
    private ArrayList<DroppedItem> droppedItems = new ArrayList<DroppedItem>();
    private ArrayList<Tickable> tickables = new ArrayList<Tickable>();
    private EntityMap items = new EntityMap();

    private PersonaMap personas = new PersonaMap();

    private String name = "-blank new game area-";

    private boolean isCleared = false;
    private boolean isBase = false;

    protected EventQueue events;


	/**The default location for the player to enter an area*/
	protected Point entranceLocation;



    protected void initializeToSize(int size) {
        this.size = size;
        map = new int[size][size];
        passable = new boolean[size][size];
        entrances = new Entrance[size][size];
        resources = new ResourceTile[size][size];
        interactables = new Interactable[size][size];
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                map[x][y] = 0;
                passable[x][y] = true;
            }
        }
	    if(this.entranceLocation == null)
		    this.entranceLocation = new Point((size/2) * 16, (size/2) * 16);//*16, b/c size is in grid coords
    }

    public NewGameArea(Random rand, int size, int ID) {
        this.rand = rand;
        this.initializeToSize(size);
        this.ID = ID;
    }

    public static void refreshPassable(NewGameArea map) {
        for (int r = 0; r < map.getSize(); r++) {
            for (int c = 0; c < map.getSize(); c++) {
                try {
                    map.getPassable()[r][c] = Tiles.passable[map.getMap()[r][c]];
	                if(map.getPassable()[r][c]){//if it's still passable, check the interactable
		                if(map.getInteractables()[r][c] != null)map.getPassable()[r][c] = map.getInteractables()[r][c].isPassable();
	                }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }

            }
        }
    }

    public void tick() {
        if (events != null) events.tick();
    }

    public boolean isCleared() {
	    if(events == null)return false;
        return events.isCleared();
    }

    public void setIsBase(boolean base) {
        this.isBase = base;
    }

    public void setEventQueue(EventQueue events) {
        this.events = events;
    }

    protected static ArrayList<Point> getCircle(int x0, int y0, int radius) {
        ArrayList<Point> list = new ArrayList<Point>();
        int f = 1 - radius;
        int ddF_x = 1;
        int ddF_y = -2 * radius;
        int x = 0;
        int y = radius;
        list.add(new Point(x0, y0 + radius));
        list.add(new Point(x0, y0 - radius));
        list.add(new Point(x0 + radius, y0));
        list.add(new Point(x0 - radius, y0));
        while (x < y) {
            if (f >= 0) {
                y--;
                ddF_y += 2;
                f += ddF_y;
            }
            x++;
            ddF_x += 2;
            f += ddF_x;
            list.add(new Point(x0 + x, y0 + y));
            list.add(new Point(x0 - x, y0 + y));
            list.add(new Point(x0 + x, y0 - y));
            list.add(new Point(x0 - x, y0 - y));
            list.add(new Point(x0 + y, y0 + x));
            list.add(new Point(x0 - y, y0 + x));
            list.add(new Point(x0 + y, y0 - x));
            list.add(new Point(x0 - y, y0 - x));
        }
        return list;
    }

    /**
     * Anything special that should be done when the area is initially cleared
     */
    public void clearArea() {
        if (!hasBeenCleared) {
//            if (this.uEntrance == World.CAVE) {
//                //Cave's size parameter is ignored, btw
//                Cave cave = new Cave(rand, 512, Engine.getNextID());
//                SaveManager.saveArea(cave);
//                hasBeenCleared = true;
//            }
        }
	    hasBeenCleared = true;
    }

    protected void setMap(int[][] newMap) {
        this.map = newMap;
    }

    public EntityMap getItems() {
        return this.items;
    }

	public boolean isInUnderworld(){
		return this.state == World.IN_UNDERWORLD;
	}

	public boolean isUnderworldEntrance(){
		return this.state == World.UNDERWORLD_ENTRANCE_STATE;//seems appropriate
	}
    /**
     * A development hack.
     *
     * Allows the programmer to specify a hardcoded area to set the next area a
     * player walks into.
     *
     * Will be deleted for release versions.
     */

    public static NewGameArea forcedArea() {
        //TODO



        return null;


    }

	// Accessor Methods // KEEP AT BOTTOM
	public int getSize() {
		return size;
	}

	public int getID() {
		return ID;
	}

	public int getBiome() {
		return biome;
	}

	public int[][] getMap() {
		return map;
	}

	public boolean[][] getPassable() {
		return passable;
	}

	public Entrance[][] getEntrances() {
		return entrances;
	}

	public ResourceTile[][] getResources() {
		return resources;
	}

	public Interactable[][] getInteractables() {
		return interactables;
	}

	public ArrayList<Effect> getEffects() {
		return effects;
	}

	public ArrayList<DroppedItem> getDroppedItems() {
		return droppedItems;
	}

	public ArrayList<Tickable> getTickables() {
		return tickables;
	}

	public PersonaMap getPersonas() {
		return personas;
	}

	public String getName() {
		return name;
	}

	public boolean isBase() {
		return isBase;
	}

	public Point getEntranceLocation() {
		return entranceLocation;
	}

	public void setEntranceLocation(Point entranceLocation) {
		this.entranceLocation = entranceLocation;
	}
}
