package entities.interactables;

import game.Engine;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Graphics;

public interface Interactable {
    public void interact();

    public void draw(float sx, float sy, float zoom, Graphics g);

    public Rectangle getAbsHitbox();

    public boolean isPassable();

    public Rectangle getImpassableBounds();

    public boolean drawnAbove();

    public boolean needToRelease();

    public boolean aestheticOnly();
}
