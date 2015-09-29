package gui;

import game.Engine;
import gui.guiPanels.AreaClearedPanel;
import gui.guiPanels.GUIPanel;
import gui.guiPanels.InventoryPanel;
import gui.guiPanels.PausePanel;
import org.lwjgl.util.Point;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;//why

import java.util.ArrayList;

public class GUIManager {

    private static HUD hud = new HUD();
    private static GUIPanel activePanel;
	private static PausePanel pausePanel;


	public static void setGuiPressed(boolean guiPressed) {
		GUIManager.guiPressed = guiPressed;
	}

	private static boolean guiPressed = false;

	private static org.lwjgl.util.Rectangle notificationZone = new org.lwjgl.util.Rectangle(800, 10, 32, 32);
	private static boolean areaClearedNotification = false;

    public static void draw(Graphics g) {
        hud.draw(g);
        if (activePanel != null) activePanel.draw(g);
    }

    public static void tick() {
        hud.tick();
        if (activePanel != null) activePanel.tick();
    }

    public static void mousePressed(int button, int x, int y) {
//	    if(Engine.isPaused()){PausePanel.mousePressed(button, x, y);return;}
	    if(areaClearedNotification&&notificationZone.contains(x, y)){
		    activePanel = new AreaClearedPanel();
		    return;
	    }
	    for (Rectangle r : hud.getBounds()) {
            if (r.contains(x, y)) {
                hud.mousePressed(button, x, y);
//	            guiPressed = true;
	            return;
            }
        }
	    if(activePanel != null){
		    if(activePanel.getBounds().contains(x, y)){
			    guiPressed = true;
			    activePanel.mousePressed(button, x, y);
			    return;
		    }
		    else{
			    activePanel = null;
			    if(Engine.isPaused())Engine.unpause();
		    }
	    }
	    guiPressed = false;
    }

    public static void mouseReleased(int button, int x, int y) {
	    if (activePanel != null) {
//	        guiPressed = activePanel.getBounds().contains(x, y);
		    activePanel.mouseReleased(button, x, y);
	    }
	    if(activePanel == null && Engine.isPaused()){PausePanel.mouseReleased(button, x, y);return;}
        guiPressed = false;
    }

    public static void mouseMoved(int oldx, int oldy, int newx, int newy) {
	    if(Engine.isPaused()){PausePanel.mouseMoved(oldx, oldy, newx, newy);return;}
        hud.mouseMoved(oldx, oldy, newx, newy);
	    if(activePanel != null){
//		    guiPressed = activePanel.getBounds().contains(newx, newy);
		    activePanel.mouseMoved(oldx, oldy, newx, newy);
	    }
	    else guiPressed = false;
    }

    public static void mouseDragged(int oldx, int oldy, int newx, int newy) {
        hud.mouseDragged(oldx, oldy, newx, newy);
        if (activePanel != null) {
//	        guiPressed = activePanel.getBounds().contains(newx, newy);
	        activePanel.mouseDragged(oldx, oldy, newx, newy);

        }
        else guiPressed = false;
    }

    public static void mouseWheelMoved(int change) {
        if (activePanel != null) {
            activePanel.mouseWheelMoved(change);
        }
	    else guiPressed = false;
    }

    public static void setActivePanel(GUIPanel p) {
        activePanel = p;
    }

    public static GUIPanel getActivePanel() {
        return activePanel;
    }

	public static boolean isGuiPressed() {
		return guiPressed;
	}
}
