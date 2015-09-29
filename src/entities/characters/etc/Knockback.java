package entities.characters.etc;

import java.io.Serializable;

import conditions.etc.Cooldown;
import entities.characters.Character;
import org.lwjgl.util.Point;
import util.Maths;

public class Knockback implements Serializable {

    private static final long serialVersionUID = 1L;
    private Character character;
    private Cooldown time = new Cooldown(14);
    private float dx;
    private float dy;
    private float ddx;
    private float ddy;
    private static final float DEFAULT_DD = -.50f;
    private static final float DEFAULT_D = 7.0f;
    private float initialDX;
    private float initialDY;

//    public Knockback(Character src, Character tgt) {
//        dx = (float)(((src.getX() - tgt.getX()) / Maths.dist(src.getLoc(), tgt.getLoc())));
//        dy = (float)(((src.getY() - tgt.getY()) / Maths.dist(src.getLoc(), tgt.getLoc())));
//        dx /= Math.abs(dx);
//        dy /= Math.abs(dy);
//        this.character = tgt;
//        time.reset();
//        this.dx = dx * DEFAULT_D;
//        this.dy = dy * DEFAULT_D;
//        this.ddx = dx * DEFAULT_DD;
//        this.ddy = dy * DEFAULT_DD;
//        this.initialDX = tgt.getDX();
//        this.initialDY = tgt.getDY();
//    }
//
//    public Knockback(Character c, int dx, int dy) {
//        this.character = c;
//        time.reset();
//        this.dx = dx * DEFAULT_D;
//        this.dy = dy * DEFAULT_D;
//        this.ddx = dx * DEFAULT_DD;
//        this.ddy = dy * DEFAULT_DD;
//        this.initialDX = c.getDX();
//        this.initialDY = c.getDY();
//    }
	public Knockback(Character c, Point source, int intensity) {
		this.character = c;
		//obtain the vector
		float dx = c.getX() - source.getX();
		float dy = c.getY() - source.getY();

		float dist = (float)Maths.dist(c.getLoc(), source);
		//normalize the vector
		dx = dx/dist;
		dy = dy/dist;

		this.time = new Cooldown(intensity/(-DEFAULT_DD));
		time.reset();

		this.dx = dx * intensity;
		this.dy = dy * intensity;
		this.ddx = dx * DEFAULT_DD;
		this.ddy = dy * DEFAULT_DD;
		this.initialDX = c.getDX();
		this.initialDY = c.getDY();
	}
    public void tick() {
        time.tick();
        if (time.offCooldown()) {
            character.setKnockback(null);
            character.setDX(initialDX);
            character.setDY(initialDY);
        }
        dx += ddx;
        dy += ddy;
        character.setDX((int)dx);
        character.setDY((int)dy);
    }
	public Cooldown getCooldown(){return time;}
}
