package entities.particles;

import org.newdawn.slick.Color;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 2/15/13
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class DialogParticle extends Particle {
    public DialogParticle(int x, int y, int dx, int dy, int time, Color color, boolean isAString, boolean isASquare, boolean isACircle, float size, String text) {
        super(x, y, dx, dy, time, color, isAString, isASquare, isACircle, size, text);
    }
}
