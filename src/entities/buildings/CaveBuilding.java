package entities.buildings;

import org.lwjgl.util.Point;

import org.newdawn.slick.Graphics;
import util.Constants;
import util.Images;

public class CaveBuilding extends Building {

    private static final long serialVersionUID = 1L;

    public CaveBuilding(int x, int y) {
        super(x, y);
        name = "Cave Building";
        this.isEnterable = true;
        progress = 100;
        this.setWidth(Constants.BU * 16);
        this.setHeight(4 * 16);
        this.setGridSize(Constants.BU);
        this.setEntranceCoordinates(new int[]{this.getGX() + 1, this.getGY() + this.getGridSize()});
        entranceAdjustment = new Point(2, 4);
        initiateRequiredSupliesTotal();
    }

    public void draw(float screenX, float screenY, float zoom, Graphics g) {
        if (drawFoundation(screenX, screenY, zoom, this.getWidth(), this.getHeight(), g)) return;
        Images.buildingSheet.draw(
                (getX() - screenX) * zoom,
                (getY() - screenY - 16) * zoom,
                (getX() - screenX + (5 * 16)) * zoom,
                (getY() - screenY + (4 * 16)) * zoom,
                20 * 16, 0 * 16,
                23 * 16, 3 * 16);
    }

    public void interact() {
    }
}
