package gui.guiPanels;

import entities.characters.personas.Player;
import game.Demigods;
import game.Engine;
import gui.GUIButton;
import gui.GUIManager;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.Font;

public class AreaClearedPanel extends GUIPanel {

    GUIButton establishBase;
    GUIButton moveOnReturn;
    GUIButton decideLater;
    private boolean returnToBase = true;

    public AreaClearedPanel() {
        setBounds((Demigods.getScreenWidth() - 348) / 2, Demigods.getScreenHeight() / 2 - 128 - 48, 348, 128);
        Player player = Engine.getPlayer();
	    //todo fuck this shit up
	    //also a fixme
//        if (player.getLastBase() == -1) {
//            moveOnReturn = new GUIButton(x + 16, y + 64, 80, 32, "Move On");
//            returnToBase = false;
//        } else {
//            moveOnReturn = new GUIButton(x + 16, y + 64, 80, 32, "Return to Base");
//        }
        establishBase = new GUIButton(x + 100, y + 64, 80, 32, "Establish Base");
        decideLater = new GUIButton(x + 184, y + 64, 80, 32, "Decide Later");
    }

    public void draw(Graphics g) {
        super.draw(g);
        Font.drawTriniganFgString("Area Cleared!", x + 20, y + 20, 24, Color.black);
        establishBase.draw(g);
        moveOnReturn.draw(g);
        decideLater.draw(g);
    }

    public void mousePressed(int button, int x, int y) {
        if (establishBase.contains(new Point(x, y))) {
//            Engine.establishBase();
	        System.err.println("Cannot Establish a Base like this anymore");
        } else if (moveOnReturn.contains(new Point(x, y))) {
            if (returnToBase){
//	            Engine.returnToBase();
	            System.err.println("Cannot Return to Base like this anymore");
            }
            else {
//	            Engine.moveOn();
	            System.err.println("Cannot Move On like this anymore");
            }
        } else if (decideLater.contains(new Point(x, y))) {
            GUIManager.setActivePanel(null);
        }
    }
}
