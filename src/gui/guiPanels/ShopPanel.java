package gui.guiPanels;

import game.Demigods;
import entities.characters.etc.Inventory;
import gui.trading.Trade;
import items.Item;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.*;
import util.exceptions.DemigodsException;
import util.exceptions.UncheckedDemigodsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 2/15/13
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShopPanel extends GUIPanel{


	ArrayList<Trade> trades = new ArrayList<Trade>();

	private static final Color DARK_BEIGE = new Color(153, 132, 98);

	private int maxItems = 12;
	private int numItemsInInv = 0;
	private int itemListStart = 0;
	private int tradeListStart = 0;

	private static final int listItemWidth = 196;
	private static final int listItemHeight = 24;
	private static Rectangle invListBounds =
			new Rectangle(
					225 + 20 - 2,
					100 + 20 - 2,
					listItemWidth + 4,
					292
			);
	private static Rectangle tradeListBounds =
			new Rectangle(
					225 + 16 - 2,
					100 + 16 - 2,
					listItemWidth + 4,
					292
			);
	/**a marker for where the top of the scroll panel is
	 within the entire list, in pixels*/
	private int tradeScrollListTop = 0;
	private int invScrollListTop = 0;
	/**the index of the selected item in the list of skills, -1 = no item*/
	private int selectedIndex = -1;
	private boolean tradeSelected = false;


	private Rectangle tradeButton;
	//private Rectangle backButtonBounds;
	public ShopPanel(ArrayList<Trade> trades){
		this.width  = (int)(396*1.5);
		this.height = 384;
		this.x = Demigods.getScreenWidth()/2 - width/2;
		this.y = 100;
		this.trades = trades;
		invListBounds = new Rectangle(x+8, y+14, 196, 292);
		tradeListBounds = new Rectangle(x+8+196+8, y+14, 196, 292);

		//positioning the trade button properly.
		float dist = (((x+width)-(tradeListBounds.getX()+tradeListBounds.getWidth()))/2);
		dist += (tradeListBounds.getX()+tradeListBounds.getWidth());
		dist = dist - (75/2);
		tradeButton =
				new Rectangle(
						(int)dist,
						y+height-150,
						75,32
				);
		//backButtonBounds = new Rectangle(x+25,y+height-50,50,25);
	}

	public void mouseWheelMoved(int change) {
		//if(tradeListBounds.contains(mouse)){
		//	tradeScrollListTop -= (change/10);
		//	if(tradeScrollListTop < 0)tradeScrollListTop = 0;
		//}
		//if(invListBounds.contains(mouse)){
		//	invScrollListTop -= (change/10);
		//	if(invScrollListTop < 0)invScrollListTop = 0;
		//}
	}
	public void mouseReleased(int button, int x, int y) {
		super.mouseReleased(button, x, y);
		//if(tradeListBounds.contains(mouse)){
		//	//System.out.println(tradeScrollListTop);
		//	int scrollListInitIndex = tradeScrollListTop/(listItemHeight);
		//	selectedIndex  = ((mouse.getY()-tradeListBounds.getY())/(listItemHeight+2));
		//	//System.out.println(selectedIndex);
		//	selectedIndex += scrollListInitIndex;
		//	tradeSelected = true;
		//	//System.out.println(selectedIndex);
		//	//System.out.println(trades.size());
		//	if(selectedIndex >= trades.size())selectedIndex = -1;
		//	System.out.println(selectedIndex);
		//}
		//if(invListBounds.contains(mouse)){
		//	int scrollListInitIndex = invScrollListTop/(listItemHeight);
		//	selectedIndex  = ((mouse.getY()-invListBounds.getY())/(listItemHeight+2));
		//	selectedIndex += scrollListInitIndex;
		//	tradeSelected = false;
		//	if(selectedIndex >= numItemsInInv)selectedIndex = -1;
		//}
		//if(tradeButton.contains(mouse)
		//		&&tradeSelected
		//		&&selectedIndex!=-1){
		//	try {
		//		int status = trades.get(selectedIndex).trade(Demigods.getPlayer().getCharacter());
		//	} catch (DemigodsException e) {
		//		System.err.println(e.getMessage());
		//	}
		//}
		//else selectedIndex = -1;

	}
	public void draw(Graphics g){

		super.draw(g);
		int startX = 16 + x;
		int startY = 16 + y;



		////////////////////////////////
		//DRAW BACKGROUND
		////////////////////////////////
		g.setColor(DARK_BEIGE);
		g.fillRect(invListBounds.getX(), invListBounds.getY(), invListBounds.getWidth(), invListBounds.getHeight());
		g.setColor(Constants.DARKER_BEIGE);
		g.drawRect(invListBounds.getX(), invListBounds.getY(), invListBounds.getWidth(), invListBounds.getHeight());


		g.setColor(Color.darkGray);
		g.fillRect(tradeListBounds.getX(), tradeListBounds.getY(), tradeListBounds.getWidth(), tradeListBounds.getHeight());
		g.setColor(Color.black);
		g.drawRect(tradeListBounds.getX(), tradeListBounds.getY(), tradeListBounds.getWidth(), tradeListBounds.getHeight());


		Inventory inv = Demigods.getPlayer().getCharacter().getInventory();
		HashMap<String, Integer> list = inv.getItemCounts();


		numItemsInInv = list.keySet().size();
		if(invScrollListTop > (numItemsInInv-maxItems)*listItemHeight ){
			invScrollListTop = (numItemsInInv-maxItems)*listItemHeight;
		}
		int value = maxItems>trades.size()?(0):(trades.size()-maxItems);
		if(tradeScrollListTop > (value)*listItemHeight ){
			tradeScrollListTop = (value)*listItemHeight;
		}
		g.setLineWidth(1.0f);

		itemListStart = invScrollListTop/listItemHeight;
		int itemY = y + 16;
		Iterator namesIter = list.keySet().iterator();
		for(int j = 0; j < itemListStart; j++){
			namesIter.next();
		}
		for(int i = itemListStart; i < list.size() && i< (maxItems+itemListStart) && namesIter.hasNext(); i++){
			String string = (String)namesIter.next();
			Item item = inv.itemFromString(string);
			Images.itemSheet.getSprite(item.getImageID()[0], item.getImageID()[1]).draw(x + 10, itemY + 2);
			Font.drawTriniganFgString(list.get(string) + "x: " +string, x+32, itemY, 20, Color.black);
			if(!tradeSelected && selectedIndex == i){
				g.setColor(Color.white);
				g.drawRect(invListBounds.getX()+1, itemY, invListBounds.getWidth()-3, 24);
			}
			itemY+=24;
		}

		tradeListStart = tradeScrollListTop/listItemHeight;
		itemY = y + 16;
		for(int i = tradeListStart; i < trades.size(); i++){

			try{
				String itemName = trades.get(i).getItemName();
				if(Character.isLowerCase(itemName.charAt(0)))
					itemName = Character.toUpperCase(itemName.charAt(0))+itemName.substring(1);
				int quantity = trades.get(i).getItemCount();

				String buySell = (trades.get(i).isBuy())?"Buy ":"Sell ";
				Font.drawTriniganFgString(buySell + " " +quantity + "x: " + itemName, tradeListBounds.getX() + 8, itemY, 20, Color.black);
				if(tradeSelected && selectedIndex == i){
					g.setColor(Color.white);
					g.drawRect(tradeListBounds.getX()+1, itemY, tradeListBounds.getWidth()-3, 24);
				}
				itemY+=24;
			}catch(IndexOutOfBoundsException e){
				//System.out.println("index: " + i);
			}

		}

		g.setColor(Constants.DARK_BEIGE);
		g.fillRect(tradeButton.getX(), tradeButton.getY(), tradeButton.getWidth(), tradeButton.getHeight());
		Font.drawTriniganFgString("Trade", tradeButton.getX() + 14, tradeButton.getY() + 1, 20, Color.black);
		g.setColor(Constants.DARKER_BEIGE);
		g.drawRect(tradeButton.getX(), tradeButton.getY(), tradeButton.getWidth(), tradeButton.getHeight());


		if(selectedIndex != -1 && tradeSelected){
			Trade trade = trades.get(selectedIndex);

			int quantity = trade.getItemCount();
			int price = trade.getPrice();
			boolean buy = trade.isBuy();
			String buySell = (buy)?"Buy ":"Sell ";
			String itemName = trade.getItemName();
			if(Character.isLowerCase(itemName.charAt(0)))
				itemName = Character.toUpperCase(itemName.charAt(0))+itemName.substring(1);
			Font.drawTriniganFgString(
					buySell + quantity + "\n  " + itemName
							+ "\nfor " + price + " coins?",
					tradeButton.getX() ,
					tradeButton.getY() - 125,
					20,
					Color.black);

		}

		//Font.drawTriniganFgString("Trade", tradeButton.getX() + 14, tradeButton.getY() + 1, 20, Color.black);



		//////////////////////////
		//DEBBUGGING DRAWING    //
		//////////////////////////
		//g.setColor(Color.cyan);
		//Font.drawTriniganFgString("selectedindex = " + selectedIndex, 0, 0, 20, Color.black);
		//Font.drawTriniganFgString("tradeselected = "+tradeSelected ,0, 24, 20, Color.black);
		//Font.drawTriniganFgString("tbboundshovered = "+ tradeButton.contains(mouse) ,0, 48, 20, Color.black);

//		Images.invFont.drawString(0, 0, "SLT: " + scrollListTop, Color.cyan);
//		Images.invFont.drawString(0, 25, "SelectedIndex: " + selectedIndex, Color.cyan);
//		Images.invFont.drawString(0, 50, "SP: " + character.getSP(), Color.cyan);


	}

	private int getCoins(int index){
		if(index > trades.size()){
			throw new UncheckedDemigodsException("tradelist index is greater than length of trades list.");
		}if(index <0){
			throw new UncheckedDemigodsException("tradelist index is less than 0");
		}
		Trade trade =trades.get(index);
		return trade.getPrice();
	}



}
