package gui.titleScreen;

import game.Demigods;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import util.Images;
import util.Font;

public class LoadingScreen extends BasicGameState {

    private int backgroundX, backgroundY;
    private Input input;

    public void init(GameContainer gc, StateBasedGame game) {
        input = new Input(600);
        input.addListener(this);
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        Images.terrain.draw(backgroundX, backgroundY);
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, 1056, 600);

        Font.drawDkNorthumbriaString("LOADING...", 300, 250, 50, null, Font.FontType.RENDERED);
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) {
        int x = input.getMouseX(), y = input.getMouseY();
        backgroundX = -419 * x / 528 - 1365;
        backgroundY = -533 * y / 300 - 1365;
    }

    public int getID() {
        return Demigods.LOADING_SCREEN;
    }
}
