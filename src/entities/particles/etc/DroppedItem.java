package entities.particles.etc;

import entities.Entity;
import items.*;

public class DroppedItem extends Entity {

    public static final float ddz = -.5f;

    public Item item;

    public DroppedItem(Item i) {
        item = i;
        z = 5;
    }

    public void tick() {
        z += dz;
        dz += ddz;
        if (z >= 0) {
            this.setXY(
                    this.getX() + (int)dx,
                    this.getY() + (int)dy
            );
        }
        if (z < 0) z = 0;
    }

    public DroppedItem(int x, int y, Item i) {
        this(i);
        this.setXY(x - 8, y - 8);
    }

    public float getZ() {
        return z;
    }

    public void setDX(float dx) {
        this.dx = (int)dx;
    }

    public void setDY(float dy) {
        this.dy = (int)dy;
    }

    public void setDZ(float dz) {
        this.dz = dz;
    }
}
