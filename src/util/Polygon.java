package util;

import org.lwjgl.util.Point;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 2/21/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Polygon implements Serializable {
	private float minX;

	public float getMinX() {
		return minX;
	}

	public float getMaxX() {
		return maxX;
	}

	public float getMinY() {
		return minY;
	}

	public float getMaxY() {
		return maxY;
	}

	public ArrayList<Point> getPath() {
		return path;
	}
	public int getVertices(){return path.size();}

	private float maxX;
	private float minY;
	private float maxY;
	ArrayList<Point> path = new ArrayList<Point>();
	public Polygon(){
		minX = minY = -1;
		maxX = maxY = 0;
	}
	public void addPoint(Point point){
		path.add(point);


		if(point.getX()>maxX)maxX = point.getX();
		else if(point.getX()<minX)minX = point.getX();
		if(minX == -1)minX = point.getX();

		if(point.getY()>maxY)maxY = point.getY();
		else if(point.getY()<minY)minY = point.getY();
		if(minY == -1)minY = point.getY();
	}
	public void addPoint(Point point, int index){
		path.add(index, point);


		if(point.getX()>maxX)maxX = point.getX();
		else if(point.getX()<minX)minX = point.getX();
		if(minX == -1)minX = point.getX();

		if(point.getY()>maxY)maxY = point.getY();
		else if(point.getY()<minY)minY = point.getY();
		if(minY == -1)minY = point.getY();
	}
	/**
	 Checks to see if the point is inside the bounds
	 of the polygon.
	 */
	public boolean contains(Point point){
		int i, j=path.size()-1;
		boolean oddNodes= false;

		float x = point.getX();
		float y = point.getY();

		for (i=0; i<path.size(); i++) {
			if ((path.get(i).getY()< y && path.get(j).getY()>=y
					||   path.get(j).getY()< y && path.get(i).getY()>=y)
					&&  (path.get(i).getX()<=x || path.get(j).getX()<=x)) {

				oddNodes ^= (path.get(i).getX()+(y-path.get(i).getY())/(path.get(j).getY()-path.get(i).getY())*(path.get(j).getX()-path.get(i).getX())<x);
			}
			j=i;
		}
		return oddNodes;
	}
	public double distFromVertex(Point point){
		double minDist = Maths.dist(point, path.get(0));
        for (Point p : path) {
            double newDist = Maths.dist(point, p);
            if (newDist < minDist) minDist = newDist;
        }
		return minDist;

	}
	public boolean intersects(Polygon other){
        for (Point point : path) {
            if (other.contains(point)) return true;
        }
		return false;
	}
}
