package gui.titleScreen;

import game.Demigods;
import game.Engine;
import util.SaveManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import util.*;

import java.io.File;

public class WorldSelectScreen extends BasicGameState {

    private Input input;
    private int backgroundX, backgroundY;
    private boolean[] hovering;
    private int scrollLocation;
    private File[] savedWorlds;
    private int selectedFile;

    public WorldSelectScreen() {
        resetSelectionBooleans();
    }

    public void init(GameContainer gc, StateBasedGame game) {
        input = new Input(600);
        input.addListener(this);
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        Images.terrain.draw(backgroundX, backgroundY);
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, 1056, 600);

        util.Font.drawDkNorthumbriaString("SELECT WORLD", 335, 75, 50, null, util.Font.FontType.RENDERED);
        util.Font.drawDkNorthumbriaString("BACK", 20, 545, 40, hovering[0] ? Color.white : null, hovering[0] ? null : util.Font.FontType.RENDERED);
        util.Font.drawDkNorthumbriaString("NEW WORLD", 760, 545, 40, hovering[1] ? Color.white : null, hovering[1] ? null : util.Font.FontType.RENDERED);
        util.Font.drawTriniganFgString("name", 10, 150, 30, Color.black);
        util.Font.drawTriniganFgString("progress", 600, 150, 30, Color.black);
        util.Font.drawTriniganFgString("date created", 750, 150, 30, Color.black);
        util.Font.drawTriniganFgString("hours played", 900, 150, 30, Color.black);
        for (int i = 0; i < savedWorlds.length; ++i) {
            if (5 + scrollLocation + i * 40 > 0 && scrollLocation + i * 40 < 300) {
                util.Font.drawTriniganFgString(savedWorlds[i].getName(), 10, 195 + scrollLocation + i * 40, 24, i == selectedFile ? Color.white : Color.black);
            }
        }
        g.setColor(Color.black);
        g.setLineWidth(2);
        g.drawLine(0, 190, 1056, 190);
        g.drawLine(0, 525, 1056, 525);
        g.drawLine(590, 150, 590, 525);
        g.drawLine(740, 150, 740, 525);
        g.drawLine(890, 150, 890, 525);
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) {
//        if (savedWorlds == null)
	        savedWorlds = SaveManager.listWorlds();
        resetSelectionBooleans();

        int x = input.getMouseX(), y = input.getMouseY();
        backgroundX = -419 * x / 528 - 1365;
        backgroundY = -533 * y / 300 - 1365;

        if (x > 20 && x < 140 && y > 545 && y < 580) hovering[0] = true;
        else if (x > 755 && x < 1030 && y > 545 && y < 580) hovering[1] = true;
        else if (y > 190 && y < 525) {
            selectedFile = (y - 190 - scrollLocation) / 40;
        }
    }

    public void mouseReleased(int button, int x, int y) {
        if (x > 20 && x < 140 && y > 545 && y < 580) Demigods.enterMenuState(Demigods.PLAYER_SELECT_SCREEN);
        else if (x > 755 && x < 1030 && y > 545 && y < 580) Demigods.enterMenuState(Demigods.NEW_WORLD_SCREEN);
        else if (y > 190 && y < 525) {
	        try{
	            Engine.set(SaveManager.loadEngine(savedWorlds[selectedFile].getName()));
	            SaveManager.setWorldName(Engine.getWorldName());
	            Demigods.enterMenuState(Demigods.LOADING_SCREEN);
                Demigods.startGameThread(savedWorlds[selectedFile].getName(), false);
	        }catch (IndexOutOfBoundsException e){}
        }
    }

    public void mouseWheelMoved(int change) {
        if (savedWorlds.length * 40 > 525 - 190) {
            scrollLocation += (int)(0.01D * change);
            if (scrollLocation > 0) scrollLocation = 0;
            else if (scrollLocation < -(savedWorlds.length * 40 - 335))
                scrollLocation = -(savedWorlds.length * 40 - 335);
        }
    }

    public int getID() {
        return Demigods.WORLD_SELECT_SCREEN;
    }

    private void resetSelectionBooleans() {
        hovering = new boolean[2];
        selectedFile = -1;
    }
}
