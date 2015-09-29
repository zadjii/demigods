package entities.buildings;

import org.lwjgl.util.Point;

import org.newdawn.slick.Graphics;
import util.Constants;
import util.Images;

public class Hut extends Building {

    private static final long serialVersionUID = 1L;

    public Hut(int x, int y) {
        super(x, y);
        name = "Hut";
        requiredSupplies[WOOD] = 20;
        this.isEnterable = true;
        this.setWidth(Constants.HUT_GRID_SIZE * 16);
        this.setHeight(Constants.HUT_GRID_SIZE * 16);
        this.setGridSize(Constants.HUT_GRID_SIZE);
        this.setEntranceCoordinates(new int[]{this.getGX() + 1, this.getGY() + this.getGridSize()});
        entranceAdjustment = new Point(1, this.getGridSize());
        initiateRequiredSupliesTotal();
    }

    public void draw(float screenX, float screenY, float zoom, Graphics g) {
        if (drawFoundation(screenX, screenY, zoom, this.getWidth(), this.getHeight(), g)) return;
        Images.buildingSheet.draw(
                (getX() - screenX) * zoom,
                (getY() - screenY) * zoom,
                (getX() - screenX + (3 * 16)) * zoom,
                (getY() - screenY + (3 * 16)) * zoom,
                5 * 16, 0 * 16,
                8 * 16, 3 * 16);
    }
}
