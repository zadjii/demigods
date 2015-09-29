package entities.characters.personas;

import entities.characters.Human;
import items.*;
import org.newdawn.slick.Color;
import skills.Skill;
import util.Constants;
import util.exceptions.DemigodsException;

public class Player extends Persona {

    private String name;

    private static final long serialVersionUID = 1L;

    public Player() {
        this.team = Constants.PLAYERTEAM;
        unarmed = new Unarmed();
        equippedItems = new Item[]{unarmed, unarmed, unarmed, unarmed, unarmed};
    }

    private Unarmed unarmed;

    private Item[] equippedItems;

    public Item[] getEquippedItems() {
        return equippedItems;
    }

    public Skill[] getEquippedSkills() {
        return this.getCharacter().getEquippedSkills();
    }


    private int selectedItemNum = 0;

    public Item getEquippedItem() {
        if (equippedItems[selectedItemNum] == null) equippedItems[selectedItemNum] = unarmed;
        Item equippedItem = equippedItems[selectedItemNum];
        if (!equippedItem.isEquipped()) equippedItem.equip(this.getCharacter());
        return equippedItem;
    }

    public int getSelectedItemNum() {
        return selectedItemNum;
    }

    public void setSelectedItemNum(int s) {
        equippedItems[selectedItemNum].unequip(this.getCharacter());
        selectedItemNum = s;
        equippedItems[selectedItemNum].equip(this.getCharacter());
    }

    /**
     * when the mouse is scrolled, this is called to change equipped weapon
     */
    public void changeSelectedItemNum(int change) {
        if (change == 0) return;
        else if (change > 0) change = -1;
        else change = 1;
        int newIndex = selectedItemNum + change;
        if (newIndex >= equippedItems.length) newIndex = 0;
        else if (newIndex < 0) newIndex = equippedItems.length - 1;
        equippedItems[selectedItemNum].unequip(this.getCharacter());
        selectedItemNum = newIndex;
        equippedItems[selectedItemNum].equip(this.getCharacter());
    }

    public void equipItem(Item i) throws DemigodsException {
        int place = getFirstEmptySlot();
        if (place == -1) throw new DemigodsException("No open toolbelt slots!");
        if (itemEquipped(i)) throw new DemigodsException("That item is already equipped!");
        equippedItems[place] = i;
    }

    public void equipItem(Item i, int place) {
        unequipItem(place);
        if (itemEquipped(i)) {
            unequipItem(i);
            equipItem(i, place);
        }
        equippedItems[place] = i;
	    if(this.getCharacter().getOffhandItem()!= null && this.getCharacter().getOffhandItem().equals(i)){
		    this.getCharacter().equipOffhand(null);
	    }
    }

    public Item unequipItem(int place) {
        Item unequipped = equippedItems[place];
        equippedItems[place] = unarmed;
        return unequipped;
    }

    public void unequipItem(Item item) {
        for (int i = 0; i < equippedItems.length; ++i) {
            if (equippedItems[i].getClass().equals(item.getClass())) {
                unequipItem(i);
                return;
            }
        }
    }

    public boolean equipmentSlotIsEmpty(int place) {
        return equippedItems[place] == null || equippedItems[place] instanceof Unarmed;
    }

    private int getFirstEmptySlot() {
        int i = -1;
        for (int j = 0; j < 5; ++j) {
            if (equipmentSlotIsEmpty(j)) {
                i = j;
                break;
            }
        }
        return i;
    }

	public void equipSkill(Skill skill, int place) {
		for (int i = 0; i < 5; ++i) {
			try {
				if (getEquippedSkills()[i].equals(skill)) {
					getEquippedSkills()[i] = null;
				}
			} catch (NullPointerException ignored) {
			}
		}
		getEquippedSkills()[place] = skill;
	}

    private boolean itemEquipped(Item i) {
        boolean itemEquipped = false;
        for (Item item : equippedItems) {
            if (item != null && i.getClass().equals(item.getClass())) itemEquipped = true;
        }
        return itemEquipped;
    }

    public void removeItem(int place) {
        if (!equipmentSlotIsEmpty(place)) {
            Item buffer = equippedItems[place];
            this.character.getInventory().removeItem(equippedItems[place]);
            unequipItem(place);
            for (Item i : this.character.getInventory().getItems()) {
                if (i.toString().equalsIgnoreCase(buffer.toString())) {
                    try {
                        this.equipItem(i);
                    } catch (DemigodsException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
        }
    }

    public void moveAI() {
        for (int i = 0; i < equippedItems.length; i++) {
            if (!(equippedItems[i] instanceof Unarmed) && equippedItems[i] != null)
                if (!this.getCharacter().getInventory().hasItem(equippedItems[i]))
                    this.equippedItems[i] = null;
        }
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }


    public void setCharacter(entities.characters.Character c) {

        character = c;
	    if(character instanceof Human) ((Human)character).setName(name);

//        ((Human)character).setSkinColor(skinColor);
//        ((Human)character).setTorsoColor(torsoColor);
//        ((Human)character).setPantsColor(pantsColor);
    }

    public String toString() {
        return "Player - " + character;
    }
}