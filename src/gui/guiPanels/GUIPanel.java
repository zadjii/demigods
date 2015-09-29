package gui.guiPanels;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Graphics;
import util.Images;

public abstract class GUIPanel {

    protected int x, y, width, height;

    public GUIPanel() {
        x = y = 0;
        width = height = 100;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void tick() {
    }

    public void draw(Graphics g) {
        Images.panelBG.draw(x, y, width, height);
    }

    public void mousePressed(int button, int x, int y) {

    }

    public void mouseReleased(int button, int x, int y) {

    }

    public void mouseMoved(int oldx, int oldy, int newx, int newy) {

    }

    public void mouseDragged(int oldx, int oldy, int newx, int newy) {

    }

    public void mouseWheelMoved(int change) {
    }
}
