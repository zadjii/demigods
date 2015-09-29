package entities;

import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 1L;
    protected Point baseMap = new Point(0, 0);
    protected float x = 0;
    protected float y = 0;
    protected float z = 0;
    protected float w = 0;
    protected float h = 0;

    protected float dx = 0, dy = 0, dz = 0;
    public static final float ddz = -0.25f;
	//If you're trying and failing to change this value for a Character, it's because
	//Character uses BASESPEED as the speed basis. Use setBaseSpeed(). plz
    protected float speed = 0;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getXi() {
        return (int)x;
    }

    public int getYi() {
        return (int)y;
    }

    public Point getBaseMap() {
        return baseMap;
    }

    public void setBaseMap(Point point) {
        this.baseMap = point;
    }

    public void setBaseMap(int x, int y) {
        this.baseMap = new Point(x, y);
    }

    public float getHeight() {
        return h;
    }

    public float getWidth() {
        return w;
    }

    public Rectangle getAbsBounds() {
        return new Rectangle((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
    }
	public void translate(float x, float y){
		this.x += x;
		this.y += y;
	}
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setHeight(float h) {
        this.h = h;
    }

    public void setWidth(float w) {
        this.w = w;
    }

    public int getGX() {
        return (int)x / 16;
    }

    public int getGY() {
        return (int)y / 16;
    }

    public void setGX(int gx) {
        this.x = (gx * 16);
    }

    public void setGY(int gy) {
        this.y = (gy * 16);
    }

    public float getDX() {
        return dx;
    }

    public float getDY() {
        return dy;
    }

    public int getDZ() {
        return (int)dz;
    }

    public void setDX(float dx) {
        this.dx = dx;
    }

    public void setDY(float dy) {
        this.dy = dy;
    }

    public void setDZ(float dz) {
        this.dz = dz;
    }

    public void setDXDYDZ(float dx, float dy, float dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float s) {
        speed = s;
    }

    public void setXY(float x, float y) {
        setX(x);
        setY(y);
    }

    public void setGXGY(int gx, int gy) {
        setGX(gx);
        setGY(gy);
    }

    public Point getLoc() {
        return new Point((int)getX(), (int)getY());
    }

    public String getLocString() {
        return this.getX() + ", " + this.getY();
    }

    public void setLoc(Point p) {
        this.setXY(p.getX(), p.getY());
    }

    public void tickDZ() {
        if (this.z > 0) {
            dz += ddz;
        }
        if (this.z < 0) this.z = 0;
    }
}
