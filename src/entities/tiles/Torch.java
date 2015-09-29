package entities.tiles;

import entities.tiles.Lightsource;
import util.Images;

public class Torch extends Lightsource {

    private static final long serialVersionUID = 1L;

    public Torch(int gx, int gy) {
        this.setRGBA(1, 1, 1, 1);
        this.lightLevel = 15;
        this.setGXGY(gx, gy);
    }

    public void draw(float sx, float sy, float zoom) {
        Images.mapObjectSheet.draw((this.getGX() * 16 - sx) * zoom, (this.getGY() * 16 - sy) * zoom, (this.getGX() * 16 - sx + 16) * zoom, (this.getGY() * 16 - sy + 16) * zoom, 0 * 16, 1 * 16, 1 * 16, 2 * 16);
    }

    public boolean drawnAbove() {
        return false;
    }
}
