package entities.buildings;

import entities.buildings.functions.BuildVillagerFunction;

import org.newdawn.slick.Graphics;
import util.Constants;
import util.Images;

import conditions.etc.Cooldown;

public class VillageCenter extends Building {

    private static final long serialVersionUID = 1L;
    private Cooldown villager = new Cooldown(10);

    public VillageCenter(int x, int y) {
        super(x, y);
        name = "Village Center";
        requiredSupplies[WOOD] = 200;
        requiredSupplies[STONE] = 100;
        this.setHeight(Constants.BU * 2 * 16);
        this.setWidth(Constants.BU * 2 * 16);
        this.setGridSize(Constants.BU * 2);
        this.setEntranceCoordinates(new int[]{(int)(getGX() + getWidth() / 32), this.getGY() + this.getGridSize()});
        this.villager.reset();
        functions.clear();
        functions.add(new BuildVillagerFunction(this));
        initiateRequiredSupliesTotal();
    }

    public void draw(float screenX, float screenY, float zoom, Graphics g) {
        if (this.progress < 100) {
            Images.buildingSheet.draw(
                    (getX() - screenX) * zoom,
                    (getY() - screenY) * zoom,
                    (getX() - screenX + this.getWidth()) * zoom,
                    (getY() - screenY + this.getHeight()) * zoom, 4 * 16, 3 * 16, 6 * 16, 5 * 16);
        } else {
            Images.buildingSheet.draw(
                    (getX() - screenX) * zoom,
                    (getY() - screenY) * zoom,
                    (getX() - screenX + this.getWidth()) * zoom,
                    (getY() - screenY + this.getHeight()) * zoom,
                    6 * 16, 0, 11 * 16, 5 * 16);
        }
    }

    public void addOnePercent() {
        if (this.getProgress() <= 100) {
            ++this.progress;
        } else {
            this.progress = 100;
            if (this.getEntrance() != null) {
                this.getEntrance().setActive(true);
            }
            villager.reset();
        }
        if (this.getProgress() >= 100) if (this.getEntrance() != null) this.getEntrance().setActive(true);
    }

    public void tick() {
        villager.tick();
    }
}
