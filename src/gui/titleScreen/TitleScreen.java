package gui.titleScreen;

import game.Demigods;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import util.*;
import util.Font;

import javax.swing.*;
import java.awt.event.*;

//The new TitleScreen, with the moving terrain image in the background and generally fancier (and actually working)
public class TitleScreen extends BasicGameState {

    public static class LogoScreen extends BasicGameState {
        private int counter;

        public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
            Images.init();
            Audio.init();
        }

        public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
            Images.companyLogo.draw(0f, -220f, 1056f, 1000f);
        }

        public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
            ++counter;
            if (counter == 100) Demigods.enterMenuState(Demigods.TITLE_SCREEN);
        }

        public int getID() {
            return Demigods.LOGO_SCREEN;
        }
    }

    private Input input;
    private boolean[] hovering;
    private int backgroundX, backgroundY;
    private static final int PLAY = 0, SETTINGS = 1, QUIT = 2;
    private int growthLevel = 0;

    public TitleScreen() {
        resetSelectionBooleans();
    }

    private void resetSelectionBooleans() {
        hovering = new boolean[3];
    }

    public void keyPressed(int key, char c) {
        if (key == Input.KEY_DOWN) {
            for (int i = 0; i < 4; ++i) {
                if (hovering[i]) {
                    hovering[i] = false;
                    hovering[i + 1] = true;
                    break;
                }
            }
            hovering[0] = true;
        } else if (key == Input.KEY_UP) {
            for (int i = 0; i < 4; ++i) {
                if (hovering[i]) {
                    hovering[i] = false;
                    hovering[i - 1] = true;
                    break;
                }
            }
            hovering[0] = true;
        } else if (key == Input.KEY_ENTER) {
            if (hovering[0]) Demigods.enterMenuState(Demigods.PLAYER_SELECT_SCREEN);
            else if (hovering[1]) return; //No settings menu yet
            else if (hovering[2]) System.exit(0);
        }
    }

    public void mouseReleased(int button, int x, int y) {
        if (button == 0) {
            if (x > 455 && x < 600 && y > 260 && y < 300) Demigods.enterMenuState(Demigods.PLAYER_SELECT_SCREEN);
            else if (x > 380 && x < 675 && y > 320 && y < 360) return; //No Settings menu yet
            else if (x > 455 && x < 600 && y > 380 && y < 420) System.exit(0);
        }
    }

    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        input = new Input(600);
        input.addListener(this);
    }

    public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
        Images.terrain.draw(backgroundX, backgroundY);

        Font.drawDkNorthumbriaString("DEMIGODS: Adventures", 200, 75, 50, null, Font.FontType.RENDERED);
        Font.drawMonospacedFontString("PLAY", 455, 250, 60, hovering[0] ? Color.white : Color.black);
        Font.drawMonospacedFontString("SETTINGS", 380, 310, 60, hovering[1] ? Color.white : Color.black);
        Font.drawMonospacedFontString("QUIT", 455, 370, 60, hovering[2] ? Color.white : Color.black);
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        resetSelectionBooleans();

        int x = input.getMouseX(), y = input.getMouseY();
        backgroundX = -419 * x / 528 - 1365;
        backgroundY = -533 * y / 300 - 1365;

        if (x > 455 && x < 600 && y > 260 && y < 300) hovering[0] = true;
        else if (x > 380 && x < 675 && y > 320 && y < 360) hovering[1] = true;
        else if (x > 455 && x < 600 && y > 380 && y < 420) hovering[2] = true;
    }

    public int getID() {
        return Demigods.TITLE_SCREEN;
    }
}
