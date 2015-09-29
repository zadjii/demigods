package gui.guiPanels;

import entities.characters.personas.Player;
import game.*;
import entities.characters.Character;
import gui.HUD;
import gui.util.Button;
import gui.util.OnClickListener;
import org.lwjgl.util.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import skills.Skill;
import util.Constants;
import util.Font;
import util.Images;

import java.util.ArrayList;


public class SkillPanel extends GUIPanel {

	private static final int DISPLAYED_ITEMS = 9;
	private static final int listItemWidth = 256;
	private static final int listItemHeight = 32;

	private Rectangle listBounds;//
//			= new Rectangle(
//					225 + 16 - 2,
//					100 + 16 - 2,
//					listItemWidth + 4,
//					(listItemHeight + 2) * DISPLAYED_ITEMS + 2
//			);
//	private Rectangle levelUpButtonBounds;
	private Button levelUpButton;
//			= new Rectangle(
//					384 + 225,
//					300 + 100,
//					128,
//					24
//			);
	private ArrayList<SkillListItem> items = new ArrayList<SkillListItem>();
	private Character character;

	/**a marker for where the top of the scroll panel is
	within the entire list, in pixels*/
	private int scrollListTop = 0;
	/**the index of the selected item in the list of skills, -1 = no item*/
	private int selectedIndex = -1;

	private boolean dragging =false;
	private SkillListItem draggedItem = null;
	private int dragx, dragy;

	public SkillPanel(Character character){
		super();

		this.character = character;
		this.setSkillList(SkillListItem.populateSkillList(character));

		Engine.pause();


        x = 196; y = 100; width = 640; height = 384;

		listBounds
			= new Rectangle(
					x + 16 - 2,
					y + 32 - 2,
					listItemWidth + 4,
					(listItemHeight + 2) * DISPLAYED_ITEMS + 2
			);
//		levelUpButtonBounds
//			= new Rectangle(
//				((width )- listBounds.getWidth() )/2 + (listBounds.getX()+ listBounds.getWidth()) - 128/2,
//					y + height - 100,
//					128,
//					24
//			);
		levelUpButton = new Button();
		levelUpButton.setBounds(
				new Rectangle(
				((width )- listBounds.getWidth() )/2 + (listBounds.getX()+ listBounds.getWidth()) - 128/2,
				y + height - 100,
				128,
				24
		));
		levelUpButton.disable();

//		levelUpButtonBounds.setX(x + levelUpButtonBounds.getX());
//		levelUpButtonBounds.setY(y + levelUpButtonBounds.getY());
	}

	public void setSkillList(ArrayList<SkillListItem> items){
		this.items = items;

	}

	public void mouseWheelMoved(int change) {
//		if(listBounds.contains(mouse)){
			scrollListTop -= (change/10);
			if(scrollListTop > items.size() * listItemHeight){
				scrollListTop = (items.size()-1) * listItemHeight - 1;
			}
			else if(scrollListTop < 0){
				scrollListTop = 0;
			}
//		}
	}
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		super.mouseDragged(oldx, oldy, newx, newy);
		System.out.println("mouseDragged");
		if(!dragging){//beginning of a drag
			System.out.println("!dragging");
			//get the index clicked on
			//if char has it, drag it
			SELECT_LIST_ITEM:
			if(listBounds.contains(newx, newy)){
				System.out.println("listBounds.contains(newx, newy)");
				int scrollListInitIndex = scrollListTop/(listItemHeight);
				int draggedIndex;
				draggedIndex  = (int)((newy-listBounds.getY())/(listItemHeight+2));
				draggedIndex += scrollListInitIndex;
				System.out.println("draggedIndex = " + draggedIndex);

				if(draggedIndex >= items.size()){break SELECT_LIST_ITEM;}
				if(draggedIndex <= -1){break SELECT_LIST_ITEM;}

				SkillListItem item = items.get(draggedIndex);
				System.out.println("SkillListItem.skillListContains(character, item.getName()) = " + SkillListItem.skillListContains(character, item.getName()));
				if(item != null && SkillListItem.skillListContains(character, item.getName())){
					System.out.println("startedDrag");
					draggedItem = item;
					dragx = newx;
					dragy = newy;
				}
				System.out.println("draggedItem = " + draggedItem);

			}
		}
		dragging = true;
		if (draggedItem != null) {
			dragx = newx;
			dragy = newy;
			System.out.println("draggedItem, x, y = " + draggedItem + "," +dragx +","+dragy);
		}
	}
	@Override
	public void mouseReleased(int button, int x, int y) {
		super.mouseReleased(button, x, y);
		SELECT_LIST_ITEM:
		if(listBounds.contains(x, y)){
			int scrollListInitIndex = scrollListTop/(listItemHeight);
			selectedIndex  = (int)((y-listBounds.getY())/(listItemHeight+2));
			selectedIndex += scrollListInitIndex;

			if(selectedIndex >= items.size()){selectedIndex = -1;break SELECT_LIST_ITEM;}

			SkillListItem item = items.get(selectedIndex);
			if(item != null && item.satisfiesPreReqs(character) && character.getSP() >= item.getSPCost()){
				levelUpButton.enable();
				levelUpButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick() {
						SkillListItem item = items.get(selectedIndex);
						item.unlock(character);
					}
				});
			}else levelUpButton.disable();

		}
		else if(dragging && draggedItem != null && HUD.getSkillBarBounds().contains(x, y)){
			Rectangle sbb = HUD.getSkillBarBounds();
			Player player = Engine.getPlayer();
			int skillBarIndex = (int)((x - sbb.getX())/(sbb.getWidth()/player.getEquippedSkills().length));
			if(skillBarIndex >=0 && skillBarIndex < player.getEquippedSkills().length){
				Skill skill = SkillListItem.getSkillFromName(player.getCharacter(), draggedItem.getName());
				player.equipSkill(skill, skillBarIndex);

			}

		}
		else if(!dragging){
			levelUpButton.click(new Point(x, y));
		}
		else selectedIndex = -1;
		if(selectedIndex >= items.size())selectedIndex = -1;
		if(selectedIndex == -1)levelUpButton.disable();
		dragging = false;
		draggedItem = null;
	}
	public void draw(Graphics g){

		super.draw(g);
//		int startX = listBounds.getX();
//		int startY = 16 + y;

		////////////////////////////////
		//DRAW BACKGROUND
		////////////////////////////////
		g.setColor(Color.darkGray);
		g.fillRect(listBounds.getX() - 2, listBounds.getY() - 2, listItemWidth + 4, (listItemHeight + 2) * DISPLAYED_ITEMS + 2);


		////////////////////////////////
		//DRAW THE SKILL LIST ITEM BOXES:
		////////////////////////////////




		int drawingYPos = (int)listBounds.getY();

		int startIndex = scrollListTop/listItemHeight;
		int selectedYPos = -1;
		DRAW_ITEMS:
		for(int index = 0; (index < items.size())&&(index < DISPLAYED_ITEMS); index++){

			SkillListItem item = items.get(index+startIndex);
			g.setColor(Color.white);
			g.fillRect(listBounds.getX(), drawingYPos, listItemWidth, listItemHeight);
			g.setColor(item.getBackgroundColor());
			g.fillRect(listBounds.getX(), drawingYPos, listItemWidth, listItemHeight);

			if(
					!(!item.satisfiesPreReqs(character)|| character.getSP() < item.getSPCost())
					&& SkillListItem.skillListContains(character, item.getName())
					){
				Images.skillSheet.getSprite(item.getImageLoc().getX(), item.getImageLoc().getY())
						.draw(listBounds.getX() + 4, drawingYPos + 4, 24, 24);
			}
			else{
				g.setColor(new Color(0.25f, 0.25f, 0.25f));
				g.fillOval(listBounds.getX() + 4, drawingYPos + 4, 24, 24);
			}


			Font.drawTriniganFgString(item.getName(), listBounds.getX() + 32, drawingYPos + 8, 16, Color.black );

			Font.drawTriniganFgString("sp: " + item.getSPCost(), listBounds.getX() + 200, drawingYPos + 8, 16, Color.black );

			if(!item.satisfiesPreReqs(character)
					|| character.getSP() < item.getSPCost()){
				g.setColor(new Color(0.25f, 0.25f, 0.25f, 0.75f));
				g.fillRect(listBounds.getX(), drawingYPos, listItemWidth, listItemHeight);
			}

			if(index + startIndex == selectedIndex){
				selectedYPos = drawingYPos;
			}

			//index++;
			//if(index >= DISPLAYED_ITEMS)break;
			//else
			if(index + startIndex + 1 >= items.size())break;

			drawingYPos += listItemHeight+2;

		}
		//////////////////////////////
		//SELECTED LIST ITEM DETAILS//
		//////////////////////////////
		if(selectedYPos != -1){

			SkillListItem item = items.get(selectedIndex);

			Images.skillSheet.getSprite(item.getImageLoc().getX(), item.getImageLoc().getY())
					.draw(
							levelUpButton.getBounds().getX() + (levelUpButton.getBounds().getWidth()/2) - 24,
							listBounds.getY() + 32,
							48,
							48
					);
		}
		//////////////////////////
		//SELECTED LIST ITEM BOX//
		//////////////////////////
		if(selectedYPos != -1){
			g.setColor(Color.white);
			g.setLineWidth(4);
			g.drawRect(listBounds.getX() - 4, selectedYPos - 4, listItemWidth + 8, listItemHeight + 8);
			g.setLineWidth(1);
		}
		//////////////////////////
		//LEVEL UP BUTTON//
		//////////////////////////
		g.setColor(Constants.XP_COLOR);
		levelUpButton.draw(g);

		Font.drawTriniganFgString("UNLOCK", levelUpButton.getBounds().getX() + 8,
				levelUpButton.getBounds().getY() + 4, 16, levelUpButton.isEnabled()?Color.black:Color.white );
		//////////////////////////
		//Player's SP//
		//////////////////////////
		Font.drawTriniganFgString("Remaining SP: " + character.getSP(),
				listBounds.getX()+ listBounds.getWidth() - 100, listBounds.getY() + listBounds.getHeight(), 16, Color.black );

		//////////////////////////
		//Dragged Item          //
		//////////////////////////
		if (draggedItem != null) {
			Images.skillSheet.getSprite(draggedItem.getImageLoc().getX(), draggedItem.getImageLoc().getY())
			.draw(
					dragx - 20, dragy - 20,
					40, 40
			);
		}

		//////////////////////////
		//DEBBUGGING DRAWING    //
		//////////////////////////
		g.setColor(Color.cyan);
//		Images.invFont.drawString(0, 0, "SLT: " + scrollListTop, Color.cyan);
//		Images.invFont.drawString(0, 25, "SelectedIndex: " + selectedIndex, Color.cyan);
//		Images.invFont.drawString(0, 50, "SP: " + character.getSP(), Color.cyan);


	}

}
