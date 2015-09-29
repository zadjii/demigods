package entities.buildings;

import org.newdawn.slick.Graphics;
import util.Images;

public class WoodenBarricade extends Building {
    private static final long serialVersionUID = 1L;

    private boolean drawHorizontal = false;
    private boolean drawVertical = false;

    private int refreshCounter = 0;
    private final int refreshCap = 10;

    public WoodenBarricade(int x, int y) {
        super(x, y);
        maxHp = 250;
        hp = maxHp;
        name = "Wooden Barricade";
        requiredSupplies[WOOD] = 20;
        this.setWidth(2 * 16);
        this.setHeight(2 * 16);
        this.setGridSize(2);
        initiateRequiredSupliesTotal();
    }

    public void draw(float screenX, float screenY, float zoom, Graphics g) {
        if (drawFoundation(screenX, screenY, zoom, this.getWidth(), this.getHeight(), g)) return;
        refreshCounter++;
        if (refreshCounter == refreshCap) refreshCounter = 0;
        if (drawHorizontal && !drawVertical) {
            Images.buildingSheet.draw(
                    (getX() - screenX) * zoom,
                    (getY() - screenY - (2 * 16)) * zoom,
                    (getX() - screenX + (2 * 16)) * zoom,
                    (getY() - screenY + (2 * 16)) * zoom,
                    16 * 16, 0 * 16,
                    18 * 16, 4 * 16);
        } else if (!drawHorizontal && drawVertical) {
            Images.buildingSheet.draw(
                    (getX() - screenX) * zoom,
                    (getY() - screenY - (2 * 16)) * zoom,
                    (getX() - screenX + (2 * 16)) * zoom,
                    (getY() - screenY + (2 * 16)) * zoom,
                    18 * 16, 0 * 16,
                    20 * 16, 4 * 16);
        } else {
            Images.buildingSheet.draw(
                    (getX() - screenX) * zoom,
                    (getY() - screenY - (2 * 16)) * zoom,
                    (getX() - screenX + (2 * 16)) * zoom,
                    (getY() - screenY + (2 * 16)) * zoom,
                    14 * 16, 0 * 16,
                    16 * 16, 4 * 16);
        }
    }
}
