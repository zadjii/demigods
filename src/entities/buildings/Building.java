package entities.buildings;

import effects.BuildingBuildAnimation;
import entities.buildings.etc.BuildingStorage;
import entities.buildings.functions.BuildingFunction;
import entities.buildings.functions.NothingFunction;
import entities.characters.etc.Inventory;
import entities.characters.personas.Persona;
import entities.characters.Character;
import entities.interactables.Interactable;
import entities.Entity;
import game.*;
import areas.etc.Entrance;
import items.Item;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.Images;
import util.Maths;
import entities.Tickable;

import java.util.ArrayList;
import java.util.HashMap;

public class Building extends Entity implements Tickable, Interactable {

    private static final long serialVersionUID = 1L;
    protected int progress = 0;

    protected int teamNum;
    protected String name;
    private int gridSize;
    private int[] entranceCoordinates;

    //Entrance adjustment is a GX, GY modifier.
    //the values from it should probably, most likely be
    // multiplied by 16 at some point.
    protected Point entranceAdjustment;
    private Entrance entrance;
    protected float requiredSupliesTotal = 0;
    BuildingStorage storage;

    protected int[] requiredSupplies = new int[]{
            0,//wood
            0,//stone
            0,//food
            0,//iron
            0,//coal
            0,//clay
            0,//crystal
            0,//coins
            0,//gold
            0,//bronze
            0,//steel
    };
    public static final int WOOD = 0;
    public static final int STONE = 1;
    public static final int FOOD = 2;
    public static final int IRON = 3;
    public static final int COAL = 4;
    public static final int CLAY = 5;
    public static final int CRYSTAL = 6;
    public static final int COINS = 7;
    public static final int GOLD = 8;
    public static final int BRONZE = 9;
    public static final int STEEL = 10;

    private String getString(int index) {
        switch (index) {
            case 0:
                return "wood";
            case 1:
                return "stone";
            case 2:
                return "food";
            case 3:
                return "iron bar";
            case 4:
                return "coal";
            case 5:
                return "clay";
            case 6:
                return "crystal";
            case 7:
                return "coin";
            case 8:
                return "gold";
            case 9:
                return "bronze bar";
            case 10:
                return "steel bar";
        }
        return "null item";
    }

    public HashMap<String, Integer> getRemainingMaterials() {
        HashMap<String, Integer> remainingMaterials = new HashMap<String, Integer>();
        remainingMaterials.put("wood", requiredSupplies[WOOD]);
        remainingMaterials.put("stone", requiredSupplies[STONE]);
        remainingMaterials.put("food", requiredSupplies[FOOD]);
        remainingMaterials.put("iron bar", requiredSupplies[IRON]);
        remainingMaterials.put("coal", requiredSupplies[COAL]);
        remainingMaterials.put("clay", requiredSupplies[CLAY]);
        remainingMaterials.put("crystal", requiredSupplies[CRYSTAL]);
        remainingMaterials.put("coin", requiredSupplies[COINS]);
        remainingMaterials.put("gold", requiredSupplies[GOLD]);
        remainingMaterials.put("bronze bar", requiredSupplies[BRONZE]);
        remainingMaterials.put("steel bar", requiredSupplies[STEEL]);
        for (Integer i : remainingMaterials.values()) {
            if (i > 0) return remainingMaterials;
        }
        return null;
    }

    private int team;

    protected int maxHp = 100;

    public int getHp() {
        return hp;
    }

    protected int hp = maxHp;

    protected ArrayList<BuildingFunction> functions = new ArrayList<BuildingFunction>();

    public ArrayList<BuildingFunction> getFunctions() {
        return functions;
    }

    protected boolean isEnterable = false;

    public boolean isEnterable() {
        return isEnterable;
    }

    protected boolean isOccupyable = false;

    public boolean isOccupyable() {
        return isOccupyable;
    }

    public void draw(float screenX, float screenY, float zoom, Graphics g) {
        System.err.println("Somewhere, somehow, a Building was attempted. Fix that. " + this.getLoc());
    }

    public Building(int mouseGridX, int mouseGridY) {
        this.setGXGY(mouseGridX, mouseGridY);
        functions.add(new NothingFunction(this));
        storage = new BuildingStorage(this, 0);
    }

    public void build(Persona p) {
        if (Maths.dist(p.getCharacter(), this) > 10 * 16) return;
        Inventory inv = p.getCharacter().getInventory();
        Character c = p.getCharacter();
        Item used = null;
        OUTER_LOOP:
        for (Item item : inv.getItems()) {
            for (int i = 0; i < requiredSupplies.length; ++i) {
                if (requiredSupplies[i] <= 0) continue;
                if (item.toString().equalsIgnoreCase(getString(i))) {
                    used = item;
                    --requiredSupplies[i];
                    break OUTER_LOOP;
                }
            }
        }
        if (used != null) {
            inv.removeItem(used);
            Engine.getActiveArea().getEffects().add(new BuildingBuildAnimation(c.getLoc(), this, used));
        }
        boolean done = true;
        for (int requiredSupply : requiredSupplies) {
            if (requiredSupply == 0) continue;
            done = false;
            break;
        }
        if (done) {
            try {
                this.getEntrance().setActive(true);
            } catch (NullPointerException ignored) {
            }
            this.progress = 100;
        }
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return this.progress;
    }

    protected void initiateRequiredSupliesTotal() {
        for (int requiredSupply : requiredSupplies) {
            requiredSupliesTotal += requiredSupply;
        }
    }

    protected int getRequiredSupliesRemaining() {
        int remaining = 0;
        for (int requiredSupply : requiredSupplies) {
            remaining += requiredSupply;
        }
        return remaining;
    }

    public String toString() {
        return (name + " : " + getLoc());
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setEntranceCoordinates(int[] entranceCoordinates) {
        this.entranceCoordinates = entranceCoordinates;
    }

    public int[] getEntranceCoordinates() {
        return entranceCoordinates;
    }

    public Point getEntranceAdjustment() {
        return entranceAdjustment;
    }

    public void setEntrance(Entrance entrance) {
        this.entrance = entrance;
    }

    public Entrance getEntrance() {
        return entrance;
    }

    public void interact() {
        if (this.progress < 100) {
            build(Demigods.getPlayer());
        }
    }

    public void tick() {
    }

    public Rectangle getAbsHitbox() {
        return new Rectangle(this.getXi(), this.getYi(), (int)this.getWidth(), (int)this.getHeight());
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getTeam() {
        return this.team;
    }

    public boolean isPassable() {
        return false;
    }

    /**
     * The dimensions of this rectangle are defaulted to the size of one that would be used by a house+
     * sized building. anything that would have a differing size collision bounds should overload this method.
     */
    public Rectangle getImpassableBounds() {
        return new Rectangle(this.getXi(), this.getYi(), (int)(getWidth()), (int)(getHeight()));
    }

    public int getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(int teamNum) {
        this.teamNum = teamNum;
    }

    public BuildingStorage getStorage() {
        return storage;
    }

    protected boolean drawFoundation(float screenX, float screenY, float zoom, float width, float height, Graphics g) {
        if (this.progress < 100) {
            Images.buildingSheet.draw(
                    (getX() - screenX) * zoom,
                    (getY() - screenY) * zoom,
                    (getX() - screenX + width) * zoom,
                    (getY() - screenY + height) * zoom,
                    0 * 16, 5 * 16, 5 * 16, 10 * 16);
            g.setColor(Color.darkGray);
            g.fillRect(
                    (getX() - screenX + width) * zoom,
                    (getY() - screenY) * zoom,
                    4 * zoom,
                    height * zoom
            );
            float progressTotal = 0;
            try {
                progressTotal = ((getRequiredSupliesRemaining() - requiredSupliesTotal)) / requiredSupliesTotal;
            } catch (ArithmeticException ignored) {
            }
            progressTotal *= (height - 2);
            g.setColor(Color.white);
            g.fillRect(
                    (getX() - screenX + width + 1) * zoom,
                    (getY() - screenY + height - 1) * zoom,
                    2 * zoom,
                    (progressTotal - 2) * zoom
            );
            return true;
        }
        return false;
    }

    public boolean drawnAbove() {
        return true;
    }

    @Override
    public boolean needToRelease() {
        return true;
    }

    public boolean aestheticOnly() {
        return false;
    }
}
