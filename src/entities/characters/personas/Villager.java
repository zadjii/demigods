package entities.characters.personas;

import entities.buildings.Building;
import entities.buildings.etc.BuildingStorage;
import entities.characters.Character;
import entities.characters.etc.Inventory;
import entities.particles.etc.DroppedItem;
import entities.interactables.ResourceTile;
import entities.particles.Particle;
import entities.tiles.Tiles;
import game.*;
import areas.NewGameArea;
import items.*;
import items.materials.Scraps;
import items.tools.Tool;
import items.weapons.*;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Graphics;

import util.Audio;
import util.exceptions.DemigodsException;
import util.Maths;

import java.util.ArrayList;
import java.util.HashMap;

public class Villager extends Persona {

    private static final long serialVersionUID = 1L;

    protected Point target;
    protected Point baseLocation;
    protected Point centralTarget;
    protected Building base;
    protected Building buildingBeingBuilt;

    protected Persona theHunted = null;

    protected ArrayList<Point> path = new ArrayList<Point>();
    private static final double findTargetChance = .0833;

    private boolean harvestingWood = false;
    private boolean pathToBaseSet = false;
    private boolean targetIsBuilding = false;

    private HashMap<String, Integer> neededResources = null;

    public static final short NO_SPECIALTY = 0;
    public static final short WOODCUTTER = 1;
    public static final short HARVESTER = 2;
    public static final short BUILDER = 3;
    public static final short SOLDIER_SWORD = 4;
    public static final short SOLDIER_SPEAR = 5;
    public static final short SOLDIER_GREATSWORD = 6;
    public static final short SOLDIER_ARCHER = 7;
    public static final short FIREMAGE = 8;

    public static final short NO_TOOL = 0;
    public static final short WOOD = 1;
    public static final short STONE = 2;
    public static final short BRONZE = 3;
    public static final short IRON = 4;
    public static final short STEEL = 5;

    private static final double villagerCapacity = .25;

    private short[] pastLives = new short[9];

    private short type = 0;
    private short tier = 0;

    public Villager(Character c) {
        character = c;
    }

    public void setDest(Point p) {
        this.target = new Point(p.getX(), p.getY());
        if (type == WOODCUTTER) {
            this.path = AStarHunter.calculateRoute(this.character, target);
            if (isTargetTree()) {
                centralTarget = target;
                harvestingWood = true;
            } else {
                centralTarget = null;
                harvestingWood = false;
            }
        } else if (type == SOLDIER_SWORD || type == SOLDIER_GREATSWORD || type == SOLDIER_SPEAR) {
            soldierSetDest(p);
        } else if (type == BUILDER) {
            builderSetDest(p);
        } else {
            this.path = AStarHunter.calculateRoute(this.character, target);
        }
        pathToBaseSet = false;
    }

    public void soldierSetDest(Point p) {
        this.centralTarget = new Point(p.getX(), p.getY());
        Rectangle targetingRect = new Rectangle(p.getX() - 8, p.getY() - 8, 16, 16);
        ArrayList<Persona> clicked = Engine.getActiveArea().getPersonas().get(targetingRect);
        if (clicked.size() == 0) {
            this.path = AStarHunter.calculateRoute(this.getCharacter(), target);
            theHunted = null;
        } else if (clicked.size() == 1) {
            if (clicked.get(0).getTeam() != this.team) {
                theHunted = clicked.get(0);
                this.path = AStarHunter.calculateRoute(this.getCharacter(), theHunted.getCharacter());
            } else {
                this.path = AStarHunter.calculateRoute(this.getCharacter(), target);
                theHunted = null;
            }
        } else {
            double minDist = -1;
            Persona tgt = null;
            for (Persona persona : clicked) {
                if (persona.character.getTeam() == this.team) continue;
                double dist = Maths.dist(this, persona);
                if (tgt == null || dist < minDist) {
                    tgt = persona;
                    minDist = dist;
                }
            }
            if (tgt == null) {
                tgt = clicked.get(0);
            }
            theHunted = tgt;
            this.path = AStarHunter.calculateRoute(this.getCharacter(), theHunted.getCharacter());
        }
    }

    public void builderSetDest(Point p) {
        this.path = AStarHunter.calculateRoute(this.character, target);
        centralTarget = null;
        neededResources = null;
        base = null;
    }

    public void moveAI() {
        if (type == WOODCUTTER) {
            woodcutterAI();
        }
        if (type == BUILDER) {
            builderAI();
        } else if (type == SOLDIER_SWORD || type == SOLDIER_GREATSWORD || type == SOLDIER_SPEAR) {
            soldierAI();
        } else {
            if (!followPath(path, this.character)) {
                if (target != null) {
                    moveTowardsTarget(this.character, target);
                }
            }
        }
    }

    private static boolean depositResources(Inventory inv, BuildingStorage storage) {
        while (!storage.isFull() && inv.getItems().size() > 0) {
            Item item = inv.getItems().get(0);
            if (storage.isAppropriate(item.toString())) {
                storage.addItem(item);
                inv.removeItem(item);
            }
        }
        return (inv.getItems().size() == 0.0);
    }

    public void woodcutterAI() {
        Inventory inv = character.getInventory();
        if (pathToBaseSet) {
            followPath(path, this.character);
            if (base != null) {
                if (Maths.dist(this.character, base) < 5 * 16) {
                    path = null;
                    if (depositResources(inv, base.getStorage())) {
                        pathToBaseSet = false;
                        path = null;
                        base = null;
                        baseLocation = null;
                    } else {
                    }
                }
            } else {
            }
            return;
        }
        if (harvestingWood) {
            if (target != null && Maths.dist(this.character.getLoc(), target) < 2 * 16 && isTargetTree()) {
                int x = target.getX();
                int y = target.getY();
                ResourceTile tile = Engine.getActiveArea().getResources()[x / 16][y / 16];
                NewGameArea activeArea = Engine.getActiveArea();
                if (this.getEquippedItem().isOffCooldown() && tile != null) {
                    Engine.add(Particle.newTreeParticle(x / 16, y / 16, (int)(Math.random() * 4 - 2), (int)(Math.random() * 2) + 1));
                    Audio.playHit();
                    Tool tool = (Tool)this.getEquippedItem();
                    if (tile.isAppropriateTool(tool)) {
                        double chance = Math.random();
                        if (chance < tool.getHarvestRate()) {
                            Item i = tile.harvest();
                            DroppedItem item = new DroppedItem(x / 16 * 16, y / 16 * 16, i);
                            Engine.add(item);
                        }
                    } else {
                        if (tile.getScraps() > 0) {
                            tile.setScraps(tile.getScraps() - 1);
                            Item i = new Scraps();
                            DroppedItem item = new DroppedItem(x / 16 * 16, y / 16 * 16, i);
                            Engine.add(item);
                        }
                    }
                    tool.use();
                    Engine.tileEmptyCheck(activeArea, tile, x, y);
                }
            }
            if (inv.getInventoryWeight() > inv.getMaxWeight() * villagerCapacity) {
                if (!pathToBaseSet) {
                }
                followPath(path, this.character);
            } else if (isTargetTree()) {
                if (followPath(path, this.character)) {
                } else {
                    this.path = AStarHunter.calculateRoute(this.character, target);
                }
            } else {
                for (int radius = 1; radius <= 10; ++radius) {
                    for (int dx = -radius; dx <= radius; ++dx)
                        for (int dy = -radius; dy <= radius; ++dy) {
                            Point next =
                                    new Point(
                                            centralTarget.getX() + (dx * 16),
                                            centralTarget.getY() + (dy * 16));
                            if (isTargetTree(next)) {
                                target = next;
                                path = AStarHunter.calculateRoute(this.character, target);
                                followPath(path, this.character);
                                return;
                            }
                        }
                }
                harvestingWood = false;
                target = null;
                centralTarget = null;
                followPath(path, this.character);
            }
        } else {
            if (!followPath(path, this.character)) {
                if (target != null) {
                    moveTowardsTarget(this.character, target);
                }
            }
        }
    }

    public void soldierAI() {
        if (theHunted != null && theHunted.character.getHP() <= 0) theHunted = null;
        if (theHunted != null) {
            Item item = this.getEquippedItem();
            double targetSize = (theHunted.getCharacter().getWidth() + theHunted.getCharacter().getHeight()) / 2;
            if (Maths.dist(this.character.getLoc(), target) < (targetSize + ((item.getRange() + 2) * 16))) {
                if (item.isOffCooldown()) {
                    item.attack(theHunted.getCharacter().getLoc());
                    item.use();
                    if (theHunted.character.getHP() <= 0) {
                        theHunted = null;
                        path = null;
                    }
                    return;
                }
            }
            if (Math.random() < findTargetChance) {
                this.target = theHunted.getCharacter().getLoc();
                this.path = AStarHunter.calculateRoute(this.character, theHunted.getCharacter());
            }
            if (!followPath(path, this.character)) {
                this.path = AStarHunter.calculateRoute(this.character, theHunted.getCharacter());
                this.target = theHunted.getCharacter().getLoc();
                if (!followPath(path, this.character)) {
                    moveTowardsTarget(this.character, theHunted.getCharacter().getLoc());
                }
            }
        } else {
            if (!followPath(path, this.character)) {
                if (target != null) {
                    moveTowardsTarget(this.character, target);
                }
            }
        }
    }

    private boolean withdrawResources(Inventory inv, BuildingStorage storage) {
        for (String s : neededResources.keySet()) {
            int count = neededResources.get(s);
            if (count == 0) continue;
            while (storage.hasItem(s) && inv.getInventoryWeight() < inv.getMaxWeight() * villagerCapacity && inv.getItemCount(s) < count) {
                try {
                    inv.addItem(storage.getItem(s));
                } catch (DemigodsException ignored) {
                }
            }
            if (inv.getInventoryWeight() < inv.getMaxWeight() * villagerCapacity) return true;
        }
        return false;
    }

    public void builderAI() {
        /**
         * if it's target is a building, then needed resources will be != null.
         * if it isnt being explicitly told to build a building,
         *      it will try and walk towards the target it was given by default.
         *      if there is no target, then it will try and find a building to build on its own.
         * otherwise, it is trying to build a building:
         *      if it has picked up resources, then it will move towards the building, and build the building if it is in range.
         *      if it hasnt picked up resources{
         *          it will look for a storage building nearby, and attempt to pick up the neccessary supplies.
         */
        /**
         *
         * target = where the current path ends
         * central target = the absloc of the building to be built
         * path = either to the building, or storage.
         * base = the storage building
         *
         */
        if (neededResources == null) {
            /*
			if it doesn't "need resources"
			then go towards the target set.
			 */
            if (target != null && path != null) {
                if (!followPath(path, this.character)) {
                    moveTowardsTarget(this.character, target);
                }
            }
        } else {
			/*
			neededResources != null
			needs resources, a building ahs been determined for building
			 */
            if (base != null) {
				/*
				If it has a base (a place to acquire resources) set, then it must be en route to said base
				 */
                followPath(path, this.getCharacter());
                if (Maths.dist(this.character, base) < 5 * 16) {
                    //if it is close
                    //withdraw Materials
                    path = null;
                    withdrawResources(this.getCharacter().getInventory(), base.getStorage());
                    //if the villager has the required materials, pathfind to the central target, and set base == null
                    if (hasNeededResources(false)) {
                        this.path = AStarHunter.calculateRoute(this.getCharacter(), centralTarget);
                        base = null;
                    }
                }
            } else {
				/*
				If it doesn't have a base (a place to acquire resources) set, then it must be en route to building
				 */
                if (!hasNeededResources(false)) {
                    for (String s : neededResources.keySet()) {
                        if (neededResources.get(s) == 0) continue;
                    }
                    beDoneBuilding();
                    return;
                }
                if (!followPath(path, this.getCharacter())) {
                    this.path = AStarHunter.calculateRoute(this.getCharacter(), buildingBeingBuilt.getLoc());
                }
                if (Maths.dist(this.character, buildingBeingBuilt) < 5 * 16) {
                    buildingBeingBuilt.build(this);
                    //build with Materials
                    path = null;
                    this.neededResources = buildingBeingBuilt.getRemainingMaterials();
                    if (neededResources == null) {
                        beDoneBuilding();
                        return;
                    }
                    //if the building is built, be done with it.
                    if (!this.hasNeededResources(false)) {
                        for (String s : neededResources.keySet()) {
                            if (neededResources.get(s) == 0) continue;
                        }
                        beDoneBuilding();
                    }
                    //if the villager can no longer build, then pathfind back to a base
                }
            }
        }
    }

    private void beDoneBuilding() {
        path = null;
        buildingBeingBuilt = null;
        target = null;
        centralTarget = null;
        neededResources = null;
    }

    public void makeWoodcutter(int tier) {
        makeWoodcutter((short)tier);
    }

    public void makeWoodcutter(short tier) {
        changeClass(WOODCUTTER, tier);
        if (this.tier == WOOD) equipItem(new WoodAxe());
        else if (this.tier == STONE) equipItem(new StoneAxe());
        else if (this.tier == BRONZE) equipItem(new BronzeAxe());
        else if (this.tier == IRON) equipItem(new IronAxe());
        else if (this.tier == STEEL) equipItem(new SteelAxe());
    }

    public void makeHarvester(int tier) {
        makeHarvester((short)tier);
    }

    public void makeHarvester(short tier) {
        changeClass(HARVESTER, tier);
        equipItem(new Unarmed());
    }

    public void makeBuilder(int tier) {
        makeBuilder((short)tier);
    }

    public void makeBuilder(short tier) {
        changeClass(BUILDER, tier);
        equipItem(new Unarmed());
    }

    public void makeSwordsman(int tier) {
        makeSwordsman((short)tier);
    }

    public void makeSwordsman(short tier) {
        changeClass(SOLDIER_SWORD, tier);
        if (this.tier == BRONZE) equipItem(new BronzeSword());
        else if (this.tier == IRON) equipItem(new IronSword());
        else if (this.tier == STEEL) equipItem(new SteelSword());
        else equipItem(new Stick());
    }

    private void equipItem(Item i) {
        this.getCharacter().setEquippedItem(i);
    }

    private void changeClass(int type, int tier) {
        changeClass((short)type, (short)tier);
    }

    private void changeClass(short type, short tier) {
        this.pastLives[this.type] = this.tier;
        if (pastLives[type] < tier) pastLives[type] = tier;
        this.type = type;
        this.tier = pastLives[type];
    }

    private boolean isTargetTree() {
        return isTargetTree(target);
    }

    private boolean isTargetTree(Point point) {
        return (Engine.getActiveArea().getMap()[point.getX() / 16][point.getY() / 16] == Tiles.TREE ||
                Engine.getActiveArea().getMap()[point.getX() / 16][point.getY() / 16] == Tiles.CACTUS);
    }

    private boolean hasNeededResources(boolean needToFillUp) {
        /**
         * Determine that the villager's inventory contains at least one of the required supplies
         * if(need to fill up)AND that with the requirement of the inventory being over 25% full
         *
         */
        boolean returnValue = false;
        if (neededResources != null) {
            ITEM_SEARCH:
            for (Item i : character.getInventory().getItems()) {
                for (String s : neededResources.keySet()) {
                    if (i.toString().equalsIgnoreCase(s)) {
                        returnValue = true;
                        break ITEM_SEARCH;
                    }
                }
            }
            if (needToFillUp) {
                return (character.getInventory().getInventoryWeight() > character.getInventory().getMaxWeight() * villagerCapacity) &&
                        returnValue;
            } else return returnValue;
        }
        //this  VVV  value may need to be changed.
        return false;
    }

    public String toString() {
        if (type == WOODCUTTER) return "Woodcutter-" + character;
        else if (type == HARVESTER) return "Harvester-" + character;
        else if (type == BUILDER) return "Builder-" + character;
        else if (type == SOLDIER_SWORD) return "Swordsman-" + character;
        else if (type == SOLDIER_SPEAR) return "Spearman-" + character;
        else if (type == SOLDIER_GREATSWORD) return "Greatswordsman-" + character;
        else if (type == SOLDIER_ARCHER) return "Archer-" + character;
        else if (type == FIREMAGE) return "Firemage-" + character;
        return "Villager-" + character;
    }

    public void draw(float sx, float sy, float zoom, Graphics g) {
        super.draw(sx, sy, zoom, g);
    }
}
