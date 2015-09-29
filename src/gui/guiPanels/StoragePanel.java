package gui.guiPanels;

import game.*;
import util.StackStorage;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


public class StoragePanel extends GUIPanel {

	StackStorage storage;

	public StoragePanel(StackStorage storage){
		super();

		this.storage = storage;
		Engine.pause();

		this.x = 225;
		this.y = 100;

		this.width = 600;
		this.height = 350;
	}



	public void draw(Graphics g){
		super.draw(g);

		int startX = 12;
		int startY = 12;
		int boxSize = 36;

		g.setColor(Color.white);
		int index = 0;
		int size = storage.getSize();
		DRAW_BOXES:
		for(int y = 0; y < 10; y++){
			for(int x = 0; x < 10; x++){

				g.fillRect(
						this.x + startX + x*boxSize,
						this.y + startY + y*boxSize,
						boxSize - 2,
						boxSize - 2
				);

				index++;
				if(index>=size)break DRAW_BOXES;

			}
		}
	}



}
