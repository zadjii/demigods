package entities.buildings;

import entities.buildings.etc.BuildingStorage;
import entities.buildings.functions.DisplayResourcesFunction;

import org.newdawn.slick.Graphics;
import util.Constants;
import util.Images;

public class ResourcePile extends Building {

    public ResourcePile(int x, int y) {
        super(x, y);
        functions.clear();
        functions.add(new DisplayResourcesFunction(this));
        this.name = "Resource Pile";
        requiredSupplies[WOOD] = 50;
        this.storage = new BuildingStorage(this, 1000);
        this.setHeight(Constants.HOUSE_GRID_SIZE * 16);
        this.setWidth(Constants.HOUSE_GRID_SIZE * 16);
        this.setGridSize(Constants.HOUSE_GRID_SIZE);
        initiateRequiredSupliesTotal();
    }

    public void draw(float screenX, float screenY, float zoom, Graphics g) {
        if (this.progress < 100) {
            Images.buildingSheet.draw((getX() - screenX) * zoom, (getY() - screenY) * zoom, (getX() - screenX + this.getWidth()) * zoom, (getY() - screenY + this.getHeight()) * zoom, 4 * 16, 3 * 16, 6 * 16, 5 * 16);
        } else {
            Images.buildingSheet.draw((getX() - screenX) * zoom, (getY() - screenY) * zoom, (getX() - screenX + this.getWidth()) * zoom, (getY() - screenY + this.getHeight()) * zoom, 2 * 16, 3 * 16, 4 * 16, 5 * 16);
        }
    }
}
