package entities.buildings;

import org.lwjgl.util.Point;

import org.newdawn.slick.Graphics;
import util.Images;

public class Smithy extends Building {

    private static final long serialVersionUID = 1L;

    public Smithy(int x, int y) {
        super(x, y);
        this.isEnterable = true;
        name = "Smithy";
        requiredSupplies[STONE] = 50;
        this.setWidth(4 * 16);
        this.setHeight(4 * 16);
        this.setGridSize(4);
        this.setEntranceCoordinates(new int[]{this.getGX() + 1, this.getGY() + this.getGridSize()});
        entranceAdjustment = new Point(1, this.getGridSize());
        initiateRequiredSupliesTotal();
    }

    public void draw(float screenX, float screenY, float zoom, Graphics g) {
        if (drawFoundation(screenX, screenY, zoom, this.getWidth(), this.getHeight(), g)) return;
        Images.buildingSheet.draw(
                (getX() - screenX) * zoom,
                (getY() - screenY - 16) * zoom,
                (getX() - screenX + (4 * 16)) * zoom,
                (getY() - screenY + (4 * 16)) * zoom,
                24 * 16, 0 * 16,
                28 * 16, 5 * 16);
    }
}
