package util;

import game.Engine;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 7/19/12
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class SkillsUtils {

	public static void drawCroshairs(float sx, float sy, float z, Graphics g){
		g.setColor(new Color(150, 0, 0));
		g.setLineWidth(3);
		float x = Engine.getMouse().getX() - sx;
		float y = Engine.getMouse().getY() - sy;
		x *= z;
		y *= z;
		g.drawOval(x - 10, y - 10, 20, 20);

		g.drawLine(x, y - 12, x, y - 8);
		g.drawLine(x, y + 12, x, y + 8);
		g.drawLine(x - 12, y, x - 8, y);
		g.drawLine(x + 12, y, x + 8, y);

//		g.setLineWidth(2);
//		g.setColor(new Color(255, 0, 0));
//
//		g.drawOval(x - 9, y - 9, 18, 18);
//
//		g.drawLine(x, y - 11, x, y - 9);
//		g.drawLine(x, y + 11, x, y + 9);
//		g.drawLine(x - 11, y, x - 9, y);
//		g.drawLine(x + 11, y, x + 9, y);

		g.setLineWidth(1);

	}


}
