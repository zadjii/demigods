package gui.guiPanels;

import entities.characters.Character;
import entities.characters.personas.Player;
import entities.characters.etc.Inventory;
import game.Engine;
import items.Item;
import items.OffhandItem;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import util.Constants;
import util.Font;
import util.Images;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class InventoryPanel extends GUIPanel {

    private Player player;
    private int itemListStart = 0;
    private int listItemHeight = 24;
    private static final Color DARK_BEIGE = Constants.DARK_BEIGE;

    private Rectangle helmSlot = new Rectangle(448, 160, 32, 32);
    private Rectangle torsoSlot = new Rectangle(448, 194, 32, 32);
    private Rectangle pantsSlot = new Rectangle(448, 228, 32, 32);

	private Rectangle offhandSlot = new Rectangle(448, 262, 32, 32);

    private Item draggedItem = null;
    private int dragx, dragy;

    /**
     * a marker for where the top of the scroll panel is
     * within the entire list, in pixels
     */
    private int scrollListTop = 0;

    public InventoryPanel() {
        x = 100; y = 132; width = 396; height = 320;
        player = Engine.getPlayer();
    }

    public boolean isDragging() {
        return draggedItem != null;
    }

    public void draw(Graphics g) {
        Images.panelBG.draw(x, y, width, height);
        g.setColor(DARK_BEIGE);
        g.fillRect(x + 8, y + 14, 196, 292);
        g.setColor(Constants.DARKER_BEIGE);
        g.drawRect(x + 8, y + 14, 196, 292);
        Inventory inv = player.getCharacter().getInventory();
        HashMap<String, Integer> list = inv.getItemCounts();
        int numItemsInInv = list.keySet().size();
        int maxItems = 12;
        if (    numItemsInInv > maxItems &&
		        scrollListTop > (numItemsInInv - maxItems) * listItemHeight) {
            scrollListTop = (numItemsInInv - maxItems) * listItemHeight;
        }
	    else if (numItemsInInv <= maxItems){
	        scrollListTop = 0;
        }
        itemListStart = scrollListTop / listItemHeight;
        int itemY = y + 16;
        Iterator namesIter = list.keySet().iterator();
        for (int j = 0; j < itemListStart; j++) {
            namesIter.next();
        }
        for (int i = itemListStart; i < list.size() && i < (maxItems + itemListStart) && namesIter.hasNext(); i++) {
            String string = (String)namesIter.next();
            Item item = inv.itemFromString(string);
            Images.itemSheet.getSprite(item.getImageID()[0], item.getImageID()[1]).draw(x + 10, itemY + 2);
            Font.drawTriniganFgString(list.get(string) + "x: " + string, x + 32, itemY, 20, Color.black);
            itemY += 24;
        }
        player.getCharacter().draw(
                player.getCharacter().getX() - ((x / 2.0f) + 150),
                player.getCharacter().getY() - ((y / 2.0f) + 25),
                2.0f,
                g
        );
	    g.setColor(DARK_BEIGE);
	    g.fill(helmSlot);
	    g.fill(torsoSlot);
	    g.fill(pantsSlot);
	    g.fill(offhandSlot);

        if (draggedItem != null) Images.itemSheet.getSprite(
                draggedItem.getImageID()[0], draggedItem.getImageID()[1]
        ).draw(
                dragx - 20, dragy - 20,
                40, 40
        );

        Item hat = player.getCharacter().getArmorArray()[Character.HEAD];
        Item chest = player.getCharacter().getArmorArray()[Character.CHEST];
        Item pants = player.getCharacter().getArmorArray()[Character.LEGS];
	    OffhandItem offhandOffhand = player.getCharacter().getOffhandItem();
        if (hat != null) {
            Images.itemSheet.getSprite(
                    hat.getImageID()[0], hat.getImageID()[1]
            ).draw(helmSlot.getX(), helmSlot.getY(), 32, 32);
        }
        if (chest != null) {
            Images.itemSheet.getSprite(
                    chest.getImageID()[0], chest.getImageID()[1]
            ).draw(torsoSlot.getX(), torsoSlot.getY(), 32, 32);
        }
	    if (pants != null) {
		    Images.itemSheet.getSprite(
				    pants.getImageID()[0], pants.getImageID()[1]
		    ).draw(pantsSlot.getX(), pantsSlot.getY(), 32, 32);
	    }
	    if (offhandOffhand != null) {
		    Images.itemSheet.getSprite(
				    offhandOffhand.getImageID()[0], offhandOffhand.getImageID()[1]
		    ).draw(offhandSlot.getX(), offhandSlot.getY(), 32, 32);
	    }
        Font.drawTriniganFgString(
                "ATTACK:",
                316, 336,
                20, Color.black);
        Font.drawTriniganFgString(
                "ARMOR:",
                316, 360,
                20, Color.black);
        Font.drawTriniganFgString(
                "ABILITY:",
                316, 384,
                20, Color.black);
        Font.drawTriniganFgString(
                "MAGIC RESIST:",
                316, 408,
                20, Color.black);
        Font.drawTriniganFgString(
                "" + player.getCharacter().getStat(Character.ATTACK),
                448, 336,
                20, Color.black);
        Font.drawTriniganFgString(
                "" + player.getCharacter().getStat(Character.ARMOR),
                448, 360,
                20, Color.black);
        Font.drawTriniganFgString(
                "" + player.getCharacter().getStat(Character.ABILITY),
                448, 384,
                20, Color.black);
        Font.drawTriniganFgString(
                "" + player.getCharacter().getStat(Character.MAGIC_RESIST),
                448, 408,
                20, Color.black);
    }

    public void mousePressed(int button, int x, int y) {
        if (button == Input.MOUSE_LEFT_BUTTON && draggedItem == null) {

	        if (x < this.x + 8 || x > this.x + 8 + 196) return;
	        if (x < this.y + 14 || y > this.y + 14 + 292) return;

	        Inventory inv = player.getCharacter().getInventory();

	        HashMap<String, Integer> list = inv.getItemCounts();
	        Iterator namesIter = list.keySet().iterator();

	        int listIndex = (y - (this.y + 14)) / listItemHeight;

	        System.out.println("pressed on inventory: listIndex: " + listIndex + ", listStart: " + itemListStart );

	        try {
		        for (int j = 0; j < itemListStart + listIndex; j++) {
	                namesIter.next();
	            }
		        if (namesIter.hasNext()) draggedItem = inv.itemFromString((String)namesIter.next());
		        dragx = x;
		        dragy = y;
	        } catch (NoSuchElementException e) {

	        }
	        System.out.println("draggedItem = " + draggedItem);

        }

    }

    public void mouseReleased(int button, int x, int y) {
        if (button == 0 && draggedItem != null) {
            int itemBarStartX = 25;
            int itemBarStartY = 20;
            int itemBarSlotSize = 40;
	        System.out.println("at start of mouseReleased: " + player.getCharacter().getOffhandItem());
	        if (
                    (dragx < itemBarStartX + (5 * (itemBarSlotSize + 10)) && dragx > itemBarStartX) &&
                            (dragy < itemBarStartY + (itemBarSlotSize) && dragy > itemBarStartY)
                    ) {

                int itemSlot = (dragx - itemBarStartX) / (itemBarSlotSize + 10);

//	            if(draggedItem instanceof OffhandItem && player.getCharacter().getOffhandItem() != null){
//			            player.getCharacter().getOffhandItem().offhandUnequip(player.getCharacter());
//
//	            }

	            player.equipItem(draggedItem, itemSlot);

            } else if (draggedItem.isArmor()) {
                if (draggedItem.isChest() && torsoSlot.contains(dragx, dragy)) {
                    draggedItem.equip(player.getCharacter());
	                System.out.println("released in chest " + draggedItem);
                } else if (draggedItem.isHat() && helmSlot.contains(dragx, dragy)) {
                    draggedItem.equip(player.getCharacter());
	                System.out.println("released in hat " + draggedItem);
                } else if (draggedItem.isLeggings() && pantsSlot.contains(dragx, dragy)) {
                    draggedItem.equip(player.getCharacter());
	                System.out.println("released in legs " + draggedItem);
                }
            }else if (draggedItem instanceof OffhandItem && offhandSlot.contains(dragx, dragy)) {
	            //if the player has the OH item equipped, unequip it.
	            player.unequipItem(draggedItem);

	            player.getCharacter().equipOffhand((OffhandItem)draggedItem);

	            System.out.println("released in OH " + draggedItem);
            }
	        System.out.println("player offhand: " + player.getCharacter().getOffhandItem());
	        draggedItem = null;
        }
    }

    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        if (draggedItem != null) {
            dragx = newx;
            dragy = newy;
        }
    }

    public void mouseWheelMoved(int change) {
        scrollListTop -= (change / 10);
        if (scrollListTop < 0) scrollListTop = 0;
    }
}
