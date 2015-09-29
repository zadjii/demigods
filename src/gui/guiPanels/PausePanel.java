package gui.guiPanels;

import game.Demigods;
import game.Engine;
import util.Constants;
import util.Font;
import org.newdawn.slick.*;
import org.lwjgl.util.Rectangle;
import util.Images;
import util.SaveManager;

public class PausePanel {

    private static boolean continueSelected = false,
            saveGameSelected = false,
            saveAndQuitSelected = false,
            mainMenuSelected = false,
            settingsSelected = false;
    private static boolean open = false;
    private static Rectangle bounds = new Rectangle(0, 0, 0, 0);

    public static boolean isOpen() {
        return open;
    }

    public static void setOpen(boolean o) {
        open = o;
        if (open) {
            setBounds(new Rectangle(0, 0, 1056, 600));
        } else {
            setBounds(new Rectangle(0, 0, 0, 0));
        }
    }

    public static Rectangle getBounds() {
        return bounds;
    }

    public static void setBounds(Rectangle b) {
        bounds = b;
    }

    public static void draw(Graphics g) {

	    drawBG(g);

	    Font.drawDkNorthumbriaString("PAUSED", 150, 150, 25, Color.white);

	    Font.drawTriniganFgString("CONTINUE", 150, 225, 24, continueSelected ? Color.white : Color.black);

	    Font.drawTriniganFgString("SAVE", 150, 275, 24, saveGameSelected ? Color.white : Color.black);

	    Font.drawTriniganFgString("SAVE AND QUIT TO MENU", 150, 325, 24, saveAndQuitSelected ? Color.white : Color.black);

	    Font.drawTriniganFgString("QUIT TO MENU", 150, 375, 24, mainMenuSelected? Color.white : Color.black);

	    Font.drawTriniganFgString("SETTINGS", 150, 425, 24, settingsSelected ? Color.white : Color.black);

//			Images.dkNorthumbriaSmall.drawString(150, 150, "PAUSED", Color.white);
//			if (!continueSelected) Images.triniganFg.drawString(150, 225, "CONTINUE", Color.lightGray);
//			else Images.triniganFg.drawString(150, 225, "CONTINUE", Color.white);
//			if (!saveGameSelected) Images.triniganFg.drawString(150, 275, "SAVE", Color.lightGray);
//			else Images.triniganFg.drawString(150, 275, "SAVE", Color.white);
//			if (!saveAndQuitSelected) Images.triniganFg.drawString(150, 325, "SAVE AND QUIT TO MENU", Color.lightGray);
//			else Images.triniganFg.drawString(150, 325, "SAVE AND QUIT TO MENU", Color.white);
//			if (!mainMenuSelected) Images.triniganFg.drawString(150, 375, "QUIT TO MENU", Color.lightGray);
//			else Images.triniganFg.drawString(150, 375, "QUIT TO MENU", Color.white);
//			if (!settingsSelected) Images.triniganFg.drawString(150, 425, "SETTINGS", Color.lightGray);
//			else Images.triniganFg.drawString(150, 425, "SETTINGS", Color.white);

    }

	public static void drawBG(Graphics g) {
		g.setColor(Constants.MAIN_MENU_BG_COLOR);
		g.fillRect(0, 0, Demigods.getScreenWidth(), Demigods.getScreenHeight());
	}

	public static void mouseReleased(int button, int x, int y) {
        if (x > 150 && x < 906) {
            if (continueSelected) {
//                setOpen(false);
                Engine.unpause();
            } else if (saveGameSelected) {

				SaveManager.saveGame(Engine.get());

            } else if (saveAndQuitSelected) {


	            Engine.getActiveArea().getPersonas().remove(Engine.getPlayer());
	            SaveManager.saveGame(Engine.get());
	            Demigods.enterMenuState(Demigods.TITLE_SCREEN);

            } else if (mainMenuSelected) {

                Demigods.enterMenuState(Demigods.TITLE_SCREEN);

            } else if (settingsSelected) {

            }
        }
    }

    public static void mouseMoved(int oldx, int oldy, int newx, int newy) {
        if (newx > 150 && newx < 906) {
            if (newy > 225 && newy < 255) continueSelected = true;
            else if (newy > 275 && newy < 305) saveGameSelected = true;
            else if (newy > 325 && newy < 355) saveAndQuitSelected = true;
            else if (newy > 375 && newy < 405) mainMenuSelected = true;
            else if (newy > 425 && newy < 455) settingsSelected = true;
            else {
                continueSelected = false;
                saveGameSelected = false;
                saveAndQuitSelected = false;
                mainMenuSelected = false;
                settingsSelected = false;
            }
        } else {
            continueSelected = false;
            saveGameSelected = false;
            saveAndQuitSelected = false;
            mainMenuSelected = false;
            settingsSelected = false;
        }
    }
}
