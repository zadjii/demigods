package entities.buildings;

import org.lwjgl.util.Point;

import org.newdawn.slick.Graphics;
import util.Constants;
import util.Images;

public class DungeonBuilding extends Building {

    private static final long serialVersionUID = 1L;

    public DungeonBuilding(int x, int y) {
        super(x, y);
        name = "Dungeon Building";
        this.isEnterable = true;
        progress = 100;
        this.setWidth(Constants.BU * 16);
        this.setHeight(Constants.BU * 16);
        this.setGridSize(Constants.BU);
        this.setEntranceCoordinates(new int[]{this.getGX() + 1, this.getGY() + this.getGridSize()});
        entranceAdjustment = new Point(2, this.getGridSize());
        initiateRequiredSupliesTotal();
    }

    public void draw(float screenX, float screenY, float zoom, Graphics g) {
        if (drawFoundation(screenX, screenY, zoom, this.getWidth(), this.getHeight(), g)) return;
        Images.buildingSheet.draw(
                (getX() - screenX) * zoom,
                (getY() - screenY) * zoom,
                (getX() - screenX + (5 * 16)) * zoom,
                (getY() - screenY + (5 * 16)) * zoom,
                11 * 16, 0 * 16,
                14 * 16, 3 * 16);
    }

    public void interact() {
    }
}
