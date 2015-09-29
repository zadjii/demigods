package entities.tiles;

import util.Images;
import entities.Entity;

public class Lightsource extends Entity {

    private static final long serialVersionUID = 1L;
    protected float red = 1.0f;
    protected float green = 1.0f;
    protected float blue = 1.0f;
    protected float alpha = 1.0f;
    protected int lightLevel = 10;

    public float r() {
        return red;
    }

    public float g() {
        return green;
    }

    public float b() {
        return blue;
    }

    public float a() {
        return alpha;
    }

    public int getLight() {
        return this.lightLevel;
    }

    protected void setRGBA(float r, float g, float b, float a) {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.alpha = a;
    }

    public void draw(float sx, float sy, float zoom) {
        Images.mapObjectSheet.draw((this.getGX() * 16 - sx) * zoom, (this.getGY() * 16 - sy) * zoom, (this.getGX() * 16 - sx + 16) * zoom, (this.getGY() * 16 - sy + 16) * zoom, 0, 0, 16, 16);
    }
}
