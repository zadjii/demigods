package entities.buildings;

import entities.characters.personas.Persona;
import conditions.etc.Cooldown;

import entities.particles.etc.DroppedItem;
import game.Engine;

import items.materials.*;
import org.lwjgl.util.Point;

import org.newdawn.slick.Graphics;
import util.Images;

public class Field extends OccupyableBuilding {

    private static final long serialVersionUID = 1L;
    Cooldown nextResource = new Cooldown(200);
    private boolean planted = false;

    public Field(int x, int y) {
        super(x, y);
        name = "Field";
        if (Math.random() < .50) planted = true;
        this.progress = 100;
        occupants = new Persona[5];
        this.setWidth(5 * 16);
        this.setHeight(5 * 16);
        this.setGridSize(5);
        this.setEntranceCoordinates(new int[]{this.getGX() + 1, this.getGY() + this.getGridSize()});
        entranceAdjustment = new Point(1, this.getGridSize());
        initiateRequiredSupliesTotal();
    }

    public boolean isPassable() {
        return true;
    }

    public void draw(float screenX, float screenY, float zoom, Graphics g) {
        if (!planted) {
            Images.buildingSheet.draw(
                    (getX() - screenX) * zoom,
                    (getY() - screenY - (0 * 16)) * zoom,
                    (getX() - screenX + (5 * 16)) * zoom,
                    (getY() - screenY + (5 * 16)) * zoom,
                    0 * 16, 10 * 16,
                    5 * 16, 15 * 16);
        } else {
            Images.buildingSheet.draw(
                    (getX() - screenX) * zoom,
                    (getY() - screenY - (0 * 16)) * zoom,
                    (getX() - screenX + (5 * 16)) * zoom,
                    (getY() - screenY + (5 * 16)) * zoom,
                    5 * 16, 10 * 16,
                    10 * 16, 15 * 16);
        }
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

    public boolean drawnAbove() {
        return false;
    }
}
