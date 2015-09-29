package entities.buildings.etc;

import entities.buildings.Building;
import items.Item;
import items.materials.Wood;
import util.exceptions.UncheckedDemigodsException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class BuildingStorage implements Serializable {

    protected Building host;

    protected int storageCapacity;

    protected ArrayList<Item> items = new ArrayList<Item>();

    public static final short ALL = 0;
    public static final short WOOD = 1;
    public static final short STONE = 2;
    public static final short ORE = 3;
    public static final short METAL = 4;
    public static final short FOOD = 5;
    public static final short GOLD = 6;

    protected short type;

    private static final int DEFAULT_CAPACITY = 100;

    private int scrapsCount = 0;

    public BuildingStorage(Building host) {
        this.storageCapacity = DEFAULT_CAPACITY;
        this.host = host;
        this.type = ALL;
    }

    public BuildingStorage(Building host, int capacity) {
        this.storageCapacity = capacity;
        this.host = host;
        this.type = ALL;
    }

    public BuildingStorage(Building host, int capacity, int type) {
        this.storageCapacity = capacity;
        this.host = host;
        this.type = (short)type;
    }

    public boolean addItem(Item item) {
        if (items.size() < storageCapacity) {
            if (isAppropriateItem(this.type, item)) {
                items.add(item);
                if (item.toString().equalsIgnoreCase("scraps")) {
                    scrapsCount++;
                    if (scrapsCount % 3 == 0 && scrapsCount > 0) {
                        int scrapsDeletionCounter = 0;
                        ArrayList<Item> scrapsToDelete = new ArrayList<Item>();
                        for (Item iteratedItem : items) {
                            if (iteratedItem.toString().equalsIgnoreCase("scraps")) {
                                scrapsToDelete.add(iteratedItem);
                                scrapsDeletionCounter++;
                                if (scrapsDeletionCounter == 3) break;
                            }
                        }
                        if (scrapsDeletionCounter == 3) {
                            for (Item iteratedItem : scrapsToDelete) {
                                items.remove(iteratedItem);
                            }
                            items.add(new Wood());
                            scrapsCount -= 3;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Item getItem(String itemName) {
        if (hasItem(itemName)) {
            for (Item item : items) {
                if (item.toString().equalsIgnoreCase(itemName)) {
                    items.remove(item);
                    return item;
                }
            }
        }
        throw new UncheckedDemigodsException("No such item in BuildingStorage " + host);
    }

    public boolean isFull() {
        return host.getProgress() < 100 || items.size() >= storageCapacity;
    }

    public boolean isEmpty() {
        return (items.size() == 0);
    }

    public boolean isAppropriate(String itemName) {
        return isAppropriateItem(this.type, itemName);
    }

    public boolean hasItem(String itemName) {
        if (isAppropriate(itemName)) {
            for (Item i : items) {
                if (i.toString().equalsIgnoreCase(itemName)) return true;
            }
        }
        return false;
    }

    public static boolean isAppropriateItem(int type, String itemName) {
        switch (type) {
            case WOOD:
                return isWood(itemName);
            case STONE:
                return isStone(itemName);
            case ORE:
                return isOre(itemName);
            case METAL:
                return isMetal(itemName);
            case FOOD:
                return isFood(itemName);
            case GOLD:
                return isGold(itemName);
            case ALL:
                if (isWood(itemName) ||
                        isStone(itemName) ||
                        isOre(itemName) ||
                        isMetal(itemName) ||
                        isFood(itemName) ||
                        isGold(itemName)) {
                    return true;
                }
        }
        return false;
    }

    public static boolean isAppropriateItem(int type, Item item) {
        String itemName = item.toString();
        return isAppropriateItem(type, itemName);
    }

    private static boolean isWood(String itemName) {
        if (itemName.equalsIgnoreCase("wood")) return true;
        else if (itemName.equalsIgnoreCase("scraps")) return true;
        return false;
    }

    private static boolean isStone(String itemName) {
        if (itemName.equalsIgnoreCase("stone")) return true;
        else if (itemName.equalsIgnoreCase("clay")) return true;
        else if (itemName.equalsIgnoreCase("brick")) return true;
        return false;
    }

    private static boolean isOre(String itemName) {
        if (itemName.contains("Ore")) return true;
        else if (itemName.contains("ore")) return true;
        else if (itemName.equalsIgnoreCase("coal")) return true;
        else if (itemName.equalsIgnoreCase("crystal")) return true;
        return false;
    }

    private static boolean isMetal(String itemName) {
        if (itemName.contains("Bar")) return true;
        else if (itemName.contains("bar")) return true;
        return false;
    }

    private static boolean isFood(String itemName) {
        if (itemName.equalsIgnoreCase("food")) return true;
        else if (itemName.equalsIgnoreCase("meat")) return true;
        else if (itemName.equalsIgnoreCase("grain")) return true;
        return false;
    }

    private static boolean isGold(String itemName) {
        if (itemName.contains("Coin")) return true;
        else if (itemName.contains("coin")) return true;
        else if (itemName.equalsIgnoreCase("gold bar")) return true;
        return false;
    }

    public String toString() {
        String returnString = "";
        HashMap<String, Integer> list = new HashMap<String, Integer>();
        for (Item item : items) {
            if (list.containsKey(item.toString())) {
                Integer i = list.remove(item.toString());
                i++;
                list.put(item.toString(), i);
            } else {
                list.put(item.toString(), 1);
            }
        }
        for (String item : list.keySet()) {
            returnString += item + ": " + list.get(item);
            returnString += "\n";
        }
        return returnString;
    }
}
