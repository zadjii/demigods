package entities.buildings;

import org.lwjgl.util.Point;
import org.newdawn.slick.*;

import util.Constants;
import util.Images;

public class Workshop extends Building {

    public Workshop(int x, int y) {
        super(x, y);
        this.isEnterable = true;
        this.name = "Workshop";
        requiredSupplies[WOOD] = 50;
        requiredSupplies[STONE] = 50;
        this.setWidth(Constants.HOUSE_GRID_SIZE * 16);
        this.setHeight(Constants.HOUSE_GRID_SIZE * 16);
        this.setGridSize(Constants.HOUSE_GRID_SIZE);
        this.setEntranceCoordinates(new int[]{this.getGX() + 2, this.getGY() + this.getGridSize()});
        entranceAdjustment = new Point(2, this.getGridSize());
        initiateRequiredSupliesTotal();
    }

    public void draw(float screenX, float screenY, float zoom, Graphics g) {
        if (drawFoundation(screenX, screenY, zoom, this.getWidth(), this.getHeight(), g)) return;
        Images.buildingSheet.draw(
                (getX() - screenX) * zoom,
                (getY() - screenY - 16) * zoom,
                (getX() - screenX + (5 * 16)) * zoom,
                (getY() - screenY + (5 * 16)) * zoom,
                10 * 16, 4 * 16,
                15 * 16, 10 * 16);
    }
}
