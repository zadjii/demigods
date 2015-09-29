package gui.titleScreen;

import game.Demigods;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import util.Constants;
import util.Font;
import util.Images;
import util.SaveManager;

public class NewWorldScreen extends BasicGameState {

    private int backgroundX, backgroundY;
    private Input input;
    private boolean[] hovering;
    private String name = "";
    private int nameFieldCursorCount;
    private boolean nameFieldSelected = true, nameFieldCursorOn;
    private int nameErrorCount;

    public NewWorldScreen() {
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

        Font.drawDkNorthumbriaString("NEW WORLD", 360, 75, 50, null, util.Font.FontType.RENDERED);
        Font.drawDkNorthumbriaString("BACK", 20, 545, 40, hovering[0] ? Color.white : null, hovering[0] ? null : util.Font.FontType.RENDERED);
        Font.drawDkNorthumbriaString("CREATE WORLD", 690, 545, 40, hovering[1] ? Color.white : null, hovering[1] ? null : util.Font.FontType.RENDERED);

        Font.drawTriniganFgString("Name:", 250, 175, 30, (nameErrorCount > 0) ? Color.red : Color.black);
        Font.drawMonospacedFontString(name, 370, 185, 30, Color.black);
        if (nameFieldSelected && nameFieldCursorOn) {
            g.setColor(Color.black);
            g.setLineWidth(3);
            g.drawLine(370 + name.length() * 18.5f, 210, 390 + name.length() * 18.5f, 210);
        }
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) {
        resetSelectionBooleans();

        int x = input.getMouseX(), y = input.getMouseY();
        backgroundX = -419 * x / 528 - 1365;
        backgroundY = -533 * y / 300 - 1365;

        if (x > 20 && x < 140 && y > 545 && y < 580) hovering[0] = true;
        else if (x > 687 && x < 1030 && y > 547 && y < 583) hovering[1] = true;

        nameFieldCursorCount++;
        nameFieldCursorOn = (nameFieldCursorCount < 20);
        if (nameFieldCursorCount == 40) nameFieldCursorCount = 0;

        if (nameErrorCount > 0) nameErrorCount--;
    }

    public void mousePressed(int button, int x, int y) {
        if (button == 0) {
            if (x > 20 && x < 140 && y > 545 && y < 580) {
                Demigods.enterMenuState(Demigods.WORLD_SELECT_SCREEN);
            } else if (x > 687 && x < 1030 && y > 547 && y < 583) {
                createWorld();
            }
            nameFieldSelected = (x > 320 && y > 195 && y < 215 || nameErrorCount == 20);
        }
    }

    public void keyPressed(int key, char c) {
        if (nameFieldSelected) {
            if (Constants.isAcceptedCharacter(c) && name.length() <= 24) {
                name += c;
            } else if (key == Input.KEY_BACK && name.length() > 0) {
                name = name.substring(0, name.length() - 1);
            }
        }
        if (key == Input.KEY_ENTER) {
            createWorld();
        }
    }

    public int getID() {
        return Demigods.NEW_WORLD_SCREEN;
    }

    private void resetSelectionBooleans() {
        hovering = new boolean[2];
    }
	private void createWorld(){
		if (name.length() > 0) {
			SaveManager.saveNewWorldDir(Demigods.getPlayer(),name);
			Demigods.enterMenuState(Demigods.LOADING_SCREEN);
			Demigods.startGameThread(name, true);
		}
		else {
			nameFieldSelected = true;
			nameErrorCount = 20;
		}
	}
}
