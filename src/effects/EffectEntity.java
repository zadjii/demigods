package effects;

import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

import java.io.Serializable;

public class EffectEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //bounds is the conventional xy coordinates and dimensions
    //gBounds is the grid-based (gxgy) coordinates and dimensions
    //speed is what speed was in the traditional sense
    //delta is a Vector, a replacement for speedX and speedY
    //anything with an s in front is the scaled version for screen-based (zooming) purposes

    protected float x;
    protected float y;
    protected float w;
    protected float h;

    protected int gx;
    protected int gy;

    protected Point baseMap = new Point(0, 0);
    protected float dx = 0, dy = 0;
    protected float speed = 0;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getHeight() {
        return h;
    }

    public float getWidth() {
        return w;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, (int)w, (int)h);
    }

    public Rectangle getAbsBounds() {
        return new Rectangle((int)getAbsX(), (int)getAbsY(), (int)getWidth(), (int)getHeight());
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

    public void setX(float x) {
        this.x = x;
        gx = (floor(x / 16));
    }

    public void setY(float y) {
        this.y = y;
        gy = (floor(y / 16));
    }

    public void setHeight(float h) {
        this.h = h;
    }

    public void setWidth(float w) {
        this.w = w;
    }

    public void setWidthHeight(float w, float h) {
        this.w = w;
        this.h = h;
    }

    public int getGX() {
        return gx;
    }

    public int getGY() {
        return gy;
    }

    public void setGX(int gx) {
        this.gx = gx;
        this.x = gx * 16;
    }

    public void setGY(int gy) {
        this.gy = gy;
        this.y = gy * 16;
    }

    public float getDX() {
        return dx;
    }

    public float getDY() {
        return dy;
    }

    public void setDX(float dx) {
        this.dx = dx;
    }

    public void setDY(float dy) {
        this.dy = dy;
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

    public void setLoc(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    protected static int floor(float f) {
        return (int)Math.floor(f);
    }

    public Point getAbsoluteLoc() {
        int mapX = baseMap.getX() * 64 * 16;
        int mapY = baseMap.getY() * 64 * 16;
        return new Point((int)this.getX() + mapX, (int)this.getY() + mapY);
    }

    public float getAbsX() {
        int mapX = baseMap.getX() * 64 * 16;
        return this.getX() + mapX;
    }

    public float getAbsY() {
        int mapY = baseMap.getY() * 64 * 16;
        return this.getY() + mapY;
    }

    public void setAbsoluteLoc(Point p) {
        this.setAbsoluteLoc(p.getX(), p.getY());
    }

    public void setAbsoluteLoc(float x, float y) {
        float nx = x % (64 * 16);
        float ny = y % (64 * 16);
        int mapX = (int)x / (64 * 16);
        int mapY = (int)y / (64 * 16);
        if (x < 0) {
            mapX -= 1;
            nx += (64 * 16);
        }
        if (y < 0) {
            mapY -= 1;
            ny += (64 * 16);
        }
        this.setXY(nx, ny);
        this.setBaseMap(mapX, mapY);
    }
}
