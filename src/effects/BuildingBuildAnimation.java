package effects;

import entities.buildings.Building;
import items.Item;
import org.lwjgl.util.Point;
import org.newdawn.slick.Graphics;
import util.Images;
import util.Maths;

public class BuildingBuildAnimation extends Effect {

    private Item item;
    private Point tgt;
    private static final int SPEED = 5;

    public BuildingBuildAnimation(Point src, Building tgt, Item item) {
        this.item = item;
        this.setLoc(src);
        int initialX = tgt.getXi();
        int initialY = tgt.getYi();
        int xRange = (int)tgt.getWidth();
        int yRange = (int)tgt.getHeight();
        int targetX = (int)(Math.random() * xRange) + initialX;
        int targetY = (int)(Math.random() * yRange) + initialY;
        this.tgt = new Point(targetX, targetY);
        dx = (float)(-((this.getAbsX() - targetX) / Maths.dist(this.getAbsoluteLoc(), new Point(targetX, targetY))) * SPEED);
        dy = (float)(-((this.getAbsY() - targetY) / Maths.dist(this.getAbsoluteLoc(), new Point(targetX, targetY))) * SPEED);
    }

    @Override
    public void draw(float sx, float sy, Graphics g, float zoom) {
        int imgX = item.getImageID()[0];
        int imgY = item.getImageID()[1];
        Images.itemSheet.getSprite(imgX, imgY).draw(
                (this.getAbsX() - sx) * zoom, (this.getAbsY() - sy) * zoom, 16 * zoom, 16 * zoom
        );
    }

    @Override
    public Effect tick() {
        this.setX(this.getX() + this.getDX());
        this.setY(this.getY() + this.getDY());
        if (Maths.dist(this.getAbsoluteLoc(), tgt) < 10) return this;
        return null;
    }
}
