package gui;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import util.Images;

import java.util.ArrayList;
import java.util.Arrays;

public class ToolTip {
    private Rectangle bounds;
    private String message;
    private ArrayList<String> info;
    private Image image;

    private ToolTip(int x, int y) {
        bounds = new Rectangle(x, y, 0, 0);
    }

    public ToolTip(int x, int y, String... message) {
        this(x, y);
        bounds.setHeight(15);
        if (message.length == 1) {
            bounds.setWidth(12 * message[0].length() + 4);
            this.message = message[0];
        } else {
            info = (ArrayList<String>) Arrays.asList(message);
            bounds.setWidth(12 * getLengthOfLongestString() + 4);
        }
    }

    public ToolTip(int x, int y, Image i) {
        this(x, y);
        bounds.setWidth(4 + i.getWidth());
        bounds.setHeight(4 + i.getHeight());
        this.image = i;
    }

    public void setLocation(int x, int y) {
        bounds.setX(x);
        bounds.setY(y);
    }

    public void draw(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        g.drawRect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
    }

    private int getLengthOfLongestString() {
        int length = 0;
        for (String s : info) {
            if (s.length() > length) length = s.length();
        }
        return length;
    }
}
