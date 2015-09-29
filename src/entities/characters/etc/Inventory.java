package entities.characters.etc;

import items.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import util.exceptions.DemigodsException;
import util.exceptions.UncheckedDemigodsException;

public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;

    private double maxWeight = 0;
    private ArrayList<Item> items = new ArrayList<Item>();

    public ArrayList<Item> getItems() {
        return this.items;
    }

    public Inventory(double mw) {
        maxWeight = mw;
    }

    public void setMaxWeight(double mw) {
        maxWeight = mw;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public boolean isFull() {
        return getInventoryWeight() >= maxWeight;
    }

    public int getItemCount(String item) throws UncheckedDemigodsException {
        int count = 0;
        for (Item i : items) {
            if (i.toString().equalsIgnoreCase(item)) ++count;
        }
        return count;
    }

    public int getItemCount(Item item) throws UncheckedDemigodsException {
        return getItemCount(item.toString());
    }

    public double getInventoryWeight() {
        double weight = 0;
        for (Item i : items) {
            weight += i.getWeight();
        }
        return ((double)((int)(weight * 100))) / 100;
    }

    public void addItem(Item i) throws DemigodsException {
        if (!itemCanFit(i)) {
            throw new DemigodsException();
        } else {
            items.add(i);
        }
    }

    private boolean itemCanFit(Item i) {
        return i == null || i.getWeight() + getInventoryWeight() <= this.maxWeight;
    }

    public Item findItem(Item i) {
        if (hasItem(i)) return items.get(items.indexOf(i));
        else return null;
    }

    public Item itemFromString(String name) {
        for (Item item : items) {
            if (item.toString().equalsIgnoreCase(name)) return item;
        }
        return null;
    }

    public boolean hasItem(Item i) {
        return items.contains(i);
    }

    public boolean hasItem(String itemName) {
        for (Item i : items) {
            if (i.toString().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    public void removeItem(Item i) {
        removeItem(i.toString());
    }

    public void removeItem(String item) {
        for (Item i : items) {
            if (i.toString().equals(item)) {
                items.remove(i);
                break;
            }
        }
    }

    public ArrayList<String> toStringArray() {
        ArrayList<String> itemList = new ArrayList<String>();
        for (Item i : items) {
            if (!i.getIsCounted()) {
                int count = 0;
                for (Item j : items) {
                    if (j.toString().equals(i.toString())) {
                        ++count;
                        j.setIsCounted(true);
                    }
                }
                itemList.add(i.toString() + " x" + count);
                i.setIsCounted(true);
            }
        }
        for (Item i : items) {
            i.setIsCounted(false);
        }
        return itemList;
    }

    public HashMap<String, Integer> getItemCounts() {
        HashMap<String, Integer> itemCounts = new HashMap<String, Integer>();
        for (Item i : items) {
            if (itemCounts.containsKey(i.toString())) {
                int newCount = itemCounts.get(i.toString()) + 1;
                itemCounts.put(i.toString(), newCount);
            } else {
                itemCounts.put(i.toString(), 1);
            }
        }
        return itemCounts;
    }

    public Item removeFirstItem() {
        if (items.size() > 0) {
            return items.remove(0);
        } else return null;
    }
}
