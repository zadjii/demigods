package gui;

import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.Font;

/**
 * A button. Really more of a rectangle that can be drawn pretty and standard.
 */
public class GUIButton {

	private static final Color DARK_BEIGE = new Color(153, 132, 98);
	private static final Color BEIGE = new Color(193, 152, 108);

	private int x, y, w, h;
	private String text;
	private boolean active = true;

	public GUIButton(int x, int y, int w, int h, String text){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.text = text;

	}
	public boolean contains(Point point){
		return new Rectangle(x,y,w,h).contains(point);
	}
	public void draw(Graphics g){
		g.setColor(DARK_BEIGE);
		g.fillRect(x - 1, y - 1, w+2, h+2);

		g.setColor(BEIGE);
		g.fillRect(x + 1, y + 1, w-2, h-2);

		Font.drawTriniganFgString(text,x+3, y+2, 16, Color.black);
	}

}
