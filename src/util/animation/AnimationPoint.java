package util.animation;

import org.lwjgl.util.Point;

import java.io.Serializable;

public class AnimationPoint implements Serializable {
	public Point point;
	public boolean inFront;
	public float rotation;
	public AnimationPoint(Point point, boolean inFront, float rotation){
		this.inFront = inFront;
		this.point = point;
		this.rotation = rotation;
	}
	public int x(){
		return point.getX();
	}
	public int y(){
		return point.getY();
	}
}
