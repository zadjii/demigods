package gui.guiPanels;

import game.*;
import gui.GUIButton;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.Font;

import java.io.File;
import java.util.ArrayList;


public class ExplorationPanel extends GUIPanel {

	private static final int DISPLAYED_ITEMS = 9;
	private static final int listItemWidth = 256;
	private static final int listItemHeight = 32;

	private Rectangle listBounds =
			new Rectangle(
					225 + 16 - 2,
					100 + 16 - 2,
					listItemWidth + 4,
					(listItemHeight + 2) * DISPLAYED_ITEMS + 2
			);
	private GUIButton newAreaButton =
			new GUIButton(
					384 + 225,
					250 + 100,
					128,
					24,
					"NEW"
			);
	private GUIButton exploreButton =
			new GUIButton(
					384 + 225,
					300 + 100,
					128,
					24,
					"EXPLORE"
			);
	private GUIButton returnButton =
			new GUIButton(
					384 + 225,
					300 + 100,
					128,
					24,
					"Return to Base"
			);
	private ArrayList<String> areas = new ArrayList<String>();

	/**a marker for where the top of the scroll panel is
	 within the entire list, in pixels*/
	private int scrollListTop = 0;
	/**the index of the selected item in the list of skills, -1 = no item*/
	private int selectedIndex = -1;
	private boolean inBase = true;

	public ExplorationPanel(){
		super();

		this.x = 225;
		this.y = 100;

		this.width = 600;
		this.height = 350;

		this.inBase = Engine.getActiveArea().isBase();

		String[] areasList = new File("sav/players/" + Demigods.getPlayer().getName() + "/worlds/" + Demigods.getWorldName()).list();

		for(String s:areasList){
            if (s.equals("engine.de")) continue;
			s = s.replaceFirst(".da", "");
			int ID = Integer.parseInt(s);
			areas.add(s);
		}
//		levelUpButtonBounds.setX(x + levelUpButtonBounds.getX());
//		levelUpButtonBounds.setY(y + levelUpButtonBounds.getY());

	}

	public void mouseWheelMoved(int change) {
		//if(listBounds.contains(mouse)){
		//	scrollListTop -= (change/10);
		//	if(scrollListTop > areas.size() * listItemHeight){
		//		scrollListTop = (areas.size()-1) * listItemHeight - 1;
		//	}
		//	else if(scrollListTop < 0){
		//		scrollListTop = 0;
		//	}
		//}
	}
	public void mousePressed(int button, int x, int y) {
		//super.mousePressed(button, x, y);
//
//
		//if(listBounds.contains(mouse)){
		//	int scrollListInitIndex = scrollListTop/(listItemHeight);
		//	selectedIndex  = ((mouse.getY()-listBounds.getY())/(listItemHeight+2));
		//	selectedIndex += scrollListInitIndex;
		//}
//		//else selectedIndex = -1;
		//if(selectedIndex >= areas.size())selectedIndex = -1;
	}
	public void draw(Graphics g){

		super.draw(g);
		int startX = 16 + x;
		int startY = 16 + y;



		////////////////////////////////
		//DRAW BACKGROUND
		////////////////////////////////
		g.setColor(Color.darkGray);
		g.fillRect(startX - 2, startY - 2, listItemWidth + 4, (listItemHeight + 2) * DISPLAYED_ITEMS + 2);


		////////////////////////////////
		//DRAW THE SKILL LIST ITEM BOXES:
		////////////////////////////////




		int drawingYPos = startY;

		int startIndex = scrollListTop/listItemHeight;
		int selectedYPos = -1;
		DRAW_ITEMS:
		for(int index = 0; (index < areas.size())&&(index < DISPLAYED_ITEMS); index++){


			g.setColor(Color.white);
			g.fillRect(startX, drawingYPos, listItemWidth, listItemHeight);
			g.setColor(Color.lightGray);
			g.fillRect(startX, drawingYPos, listItemWidth, listItemHeight);

			Font.drawTriniganFgString(areas.get(index), startX + 4, drawingYPos + 8, 16, Color.black );


			if(index + startIndex == selectedIndex){
				selectedYPos = drawingYPos;
			}

			//index++;
			//if(index >= DISPLAYED_ITEMS)break;
			//else
			if(index + startIndex + 1 >= areas.size())break;

			drawingYPos += listItemHeight+2;

		}
		//////////////////////////
		//SELECTED LIST ITEM BOX//
		//////////////////////////
		if(selectedYPos != -1){
			g.setColor(Color.white);
			g.setLineWidth(4);
			g.drawRect(startX - 4, selectedYPos - 4, listItemWidth + 8, listItemHeight + 8);
			g.setLineWidth(1);
		}
		//////////////////////////
		// Buttons (Need to do) //
		//////////////////////////

		if(inBase){
			exploreButton.draw(g);
			newAreaButton.draw(g);
		}
		else{
			returnButton.draw(g);
		}

		//////////////////////////
		//  DEBBUGGING DRAWING  //
		//////////////////////////
		g.setColor(Color.cyan);
//		Images.invFont.drawString(0, 0, "SLT: " + scrollListTop, Color.cyan);
//		Images.invFont.drawString(0, 25, "SelectedIndex: " + selectedIndex, Color.cyan);
//		Images.invFont.drawString(0, 50, "SP: " + character.getSP(), Color.cyan);


	}

	public void tick(){
		this.inBase = Engine.getActiveArea().isBase();


		//LOOK AT ME!!!!!
		//I'm commenting out all these MouseHandler references.
		//The code inside them might need to be replaced.
		//but I'm deleting the mousehandler.

//		if(MouseHandler.LMBDown()){
//		}



		if(!inBase){
//			if(MouseHandler.LMBDown()){
//				//if(returnButton.contains(mouse)){
//				//	if(Engine.getPlayer().getLastBase()!=-1)Engine.returnToBase();
//				//}
//			}

		}
		else{

//			if(MouseHandler.LMBDown()){
//
//				//if(exploreButton.contains(mouse)&&selectedIndex!=-1){
//				//	int areaToLoad = Integer.parseInt(areas.get(selectedIndex));
//				//	Engine.goToArea(areaToLoad);
//				//}
//				//else if(newAreaButton.contains(mouse)){
//				//	Engine.moveOn();
//				//}
//			}

		}

	}


}
