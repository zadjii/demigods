package gui.guiPanels;

import entities.characters.personas.NewVillager;
import game.Demigods;
import game.Engine;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.Constants;
import util.Font;
import util.Images;

import java.util.ArrayList;

public class MessagePanel extends GUIPanel {

    private boolean shop;
    private int shopType;
    private ArrayList<String> messages = new ArrayList<String>();
    private int currentIndex = 0;
    private Rectangle nextButtonBounds;// = new Rectangle(50,50,50,50);
    private Rectangle backButtonBounds;// = new Rectangle(550,50,50,50);;
    private NewVillager speaker;

    public MessagePanel(ArrayList<String> messages) {
        if (messages != null) this.messages = messages;
        this.init();
    }

    public MessagePanel() {
        this.init();
    }

    public MessagePanel(String message) {
        messages.add(message);
        this.init();
    }

    private void init() {
        setBounds(Demigods.getScreenWidth() / 2 - 150, 200, 300, 192);
        nextButtonBounds = new Rectangle(x + width - 75, y + height - 50, 50, 25);
        backButtonBounds = new Rectangle(x + 25, y + height - 50, 50, 25);
    }

    private void advanceGUI() {
        currentIndex++;
        if (currentIndex >= messages.size()) {
            if (shop) Engine.displayShop(shopType);
            else Engine.closeGUI();
        }
    }

    private void retreatGUI() {
        currentIndex--;
        if (currentIndex < 0) {
            Engine.closeGUI();
        }
    }

    public void mouseReleased(int button, int x, int y) {
        super.mouseReleased(button, x, y);
        //if(nextButtonBounds.contains(mouse))advanceGUI();
        //else if(backButtonBounds.contains(mouse))retreatGUI();
    }

    public void setMerchant(int shopType) {
        this.shop = true;
        this.shopType = shopType;
    }

    public void tick() {
        this.speaker.stopMoving();
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public void draw(Graphics g) {
        Images.panelBG.draw(x, y, width, height);
        g.setColor(Constants.DARK_BEIGE);
        g.fillRect(nextButtonBounds.getX(), nextButtonBounds.getY(), nextButtonBounds.getWidth(), nextButtonBounds.getHeight());
        g.setColor(Constants.DARK_BEIGE);
        g.fillRect(backButtonBounds.getX(), backButtonBounds.getY(), backButtonBounds.getWidth(), backButtonBounds.getHeight());
        Font.drawTriniganFgString("Next", nextButtonBounds.getX() + 10, nextButtonBounds.getY() + 1, 20, Color.black);
        Font.drawTriniganFgString("Back", backButtonBounds.getX() + 10, backButtonBounds.getY() + 1, 20, Color.black);
        Font.drawTriniganFgString(messages.get(currentIndex), x + 10, y + 32, 20, Color.black);
    }

    public void setSpeaker(NewVillager v) {
        this.speaker = v;
    }
}
