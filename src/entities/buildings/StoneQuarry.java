package entities.buildings;

import entities.characters.personas.Persona;
import conditions.etc.Cooldown;

import entities.particles.etc.DroppedItem;
import game.Engine;

import items.materials.*;
import org.lwjgl.util.Point;

import org.newdawn.slick.Graphics;
import util.Images;

public class StoneQuarry extends OccupyableBuilding {

    private static final long serialVersionUID = 1L;
    Cooldown nextResource = new Cooldown(200);

    public StoneQuarry(int x, int y) {
        super(x, y);
        name = "Stone Quarry";
        requiredSupplies[WOOD] = 50;
        requiredSupplies[STONE] = 50;
        occupants = new Persona[5];
        this.setWidth(9 * 16);
        this.setHeight(7 * 16);
        this.setGridSize(9);
        this.setEntranceCoordinates(new int[]{this.getGX() + 1, this.getGY() + this.getGridSize()});
        entranceAdjustment = new Point(1, this.getGridSize());
        initiateRequiredSupliesTotal();
    }

    public void draw(float screenX, float screenY, float zoom, Graphics g) {
        if (drawFoundation(screenX, screenY, zoom, this.getWidth(), this.getHeight(), g)) return;
        Images.buildingSheet.draw(
                (getX() - screenX) * zoom,
                (getY() - screenY - (2 * 16)) * zoom,
                (getX() - screenX + (9 * 16)) * zoom,
                (getY() - screenY + (7 * 16)) * zoom,
                15 * 16, 4 * 16,
                24 * 16, 13 * 16);
    }

    public void tick() {
        nextResource.tick();
        if (nextResource.offCooldown()) {
            for (int i = 0; i < occupants.length; ++i) {
                if (occupants[i] != null) {
                    short rand = (short)(Math.random() * 20);
                    int x = getXi() + (i * 16);
                    int y = getYi() + (int)getHeight() + 16;
                    switch (rand) {
                        case 6:
                            Engine.add(new DroppedItem(x, y, new Coal()));
                            break;
                        case 5:
                        case 4:
                            Engine.add(new DroppedItem(x, y, new TinOre()));
                            break;
                        case 3:
                        case 2:
                            Engine.add(new DroppedItem(x, y, new CopperOre()));
                            break;
                        case 1:
                            Engine.add(new DroppedItem(x, y, new Coal()));
                            break;
                        case 0:
                            Engine.add(new DroppedItem(x, y, new IronOre()));
                            break;
                        default:
                            Engine.add(new DroppedItem(x, y, new Stone()));
                            break;
                    }
                }
            }
            nextResource.reset();
        }
    }
}
