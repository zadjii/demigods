package util;

import org.lwjgl.util.Point;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 10/12/12
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShieldRotatedBigPicture extends RotatedBigPicture{

//	RotatedBigPicture(Image image, int x, int y){
//		this.image = image;
//		this.handLoc = new Point(x, y);
//	}
	ShieldRotatedBigPicture(String src, int x, int y){
		super(src, x, y);
	}
	public void draw(float x, float y, float angle, float sx, float sy, float zoom, Graphics g){
		//g.rotate((x+ handLoc.getX())*zoom, (y+ handLoc.getY())*zoom, angle);
//		g.rotate((x)*zoom, (y)*zoom, angle);
//		image.draw(
//				(x)*zoom,
//				(y)*zoom,
//				(image.getWidth())*zoom,
//				(image.getHeight())*zoom);

//		float dx = (float)(handLoc.getX() * Math.cos(Math.toRadians(angle)));
//		float dy = (float)(handLoc.getY() * Math.cos(Math.toRadians(angle)));
//		dx += (float)(handLoc.getY() * Math.sin(Math.toRadians(angle)));
//		dy += (float)(handLoc.getX() * Math.cos(Math.toRadians(angle)));
		float dx = (float)(handLoc.getX());
		float dy = (float)(handLoc.getY());


		image.draw(
				(x - dx)*zoom,
				(y - dy)*zoom,
				(image.getWidth())*zoom,
				(image.getHeight())*zoom);
//		g.rotate((x)*zoom, (y)*zoom, -angle);
	}
}
