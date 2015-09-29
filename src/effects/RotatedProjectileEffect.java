package effects;

import entities.characters.Character;
import entities.characters.personas.Persona;
import game.Engine;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import util.EndOfPathListener;
import util.Maths;
import util.OnHitListener;

import java.util.ArrayList;

public class RotatedProjectileEffect extends Effect {

    protected double angle;
    protected Character caster;
    protected double damage;
    protected double range;
    protected Image image;
    protected double distanceTraveled = 0;
    protected boolean knockback = true;
    protected boolean stopOnHit = true;
	protected boolean hasHitSomething = false;
    ArrayList<Persona> hit = new ArrayList<Persona>();

	protected OnHitListener onHitEffect;
	protected EndOfPathListener onEndOfPathEffect;

    public RotatedProjectileEffect(Character caster, Point target, double damage, double speed, double range) {
        int targetX = target.getX();
        int targetY = target.getY();
        this.caster = caster;
        this.setLoc(caster.getLoc());
        float thisX = this.getAbsX();
        float thisY = this.getAbsY();
        this.damage = damage;
        this.range = range;
        double theta = (targetY - thisY) / (targetX - thisX);
        double distX = targetX - thisX;
        double distY = targetY - thisY;
        this.angle = Math.toDegrees(Math.atan2(distY, distX));
        dx = (float)(-((thisX - targetX) / Maths.dist(caster.getLoc(), new Point(targetX, targetY))) * speed);
        dy = (float)(-((thisY - targetY) / Maths.dist(caster.getLoc(), new Point(targetX, targetY))) * speed);
    }

    public void draw(float sx, float sy, Graphics g, float zoom) {
        g.rotate((getAbsX() - sx) * zoom, (getAbsY() - sy) * zoom, (float)angle);
        image.draw((getAbsX() - sx - (getWidth() / 2)) * zoom, (getAbsY() - sy - (getHeight() / 2)) * zoom, (getWidth()) * zoom, (getHeight()) * zoom);
        g.rotate((getAbsX() - sx) * zoom, (getAbsY() - sy) * zoom, (float)-angle);
    }

    /**
     * Draws an image at this projectile's location.
     * @param image
     * @param sx
     * @param sy
     * @param g
     * @param zoom
     */
    protected void drawImage(Image image, float sx, float sy, Graphics g, float zoom) {
        g.rotate((getAbsX() - sx) * zoom, (getAbsY() - sy) * zoom, (float)angle);
        image.draw((getAbsX() - sx - (getWidth() / 2)) * zoom, (getAbsY() - sy - (getHeight() / 2)) * zoom, (getWidth()) * zoom, (getHeight()) * zoom);
        g.rotate((getAbsX() - sx) * zoom, (getAbsY() - sy) * zoom, (float)-angle);
    }

    /**
     * Draws an image at this projectile's location, with specific width and height.
     */
    protected void drawImage(Image image, float w, float h, float sx, float sy, Graphics g, float zoom) {
        g.rotate((getAbsX() - sx) * zoom, (getAbsY() - sy) * zoom, (float)angle);
        image.draw((getAbsX() - sx - (w / 2)) * zoom, (getAbsY() - sy - (h / 2)) * zoom, (w) * zoom, (h) * zoom);
        g.rotate((getAbsX() - sx) * zoom, (getAbsY() - sy) * zoom, (float)-angle);
    }

    @Override
    public Effect tick() {
        Point initial = this.getAbsoluteLoc();
        additionalGuidanceEffects();
        this.setX(this.getX() + this.getDX());
        this.setY(this.getY() + this.getDY());
        distanceTraveled += Maths.dist(initial, this.getAbsoluteLoc());
        ArrayList<Persona> temp = new ArrayList<Persona>();
        for (Persona p : Engine.getActiveArea().getPersonas().get(this.getAbsBounds())) {
            if (p.getTeam() != caster.getTeam()) {
                if (!hit.contains(p)) {
                    additionalHitEffects(p);
	                if(onHitEffect != null)onHitEffect.onHit(caster, p.getCharacter());
                    hit.add(p);
	                hasHitSomething = true;
                    if (Character.attack(damage, caster, p.getCharacter(), knockback)) {
                        temp.add(p);
                    }
                    if (stopOnHit) distanceTraveled = range;
                }
            }
        }
        for (Persona p : temp) {
            Engine.addToBeRemoved(p);
        }
        if (distanceTraveled >= range) {
	        if(onEndOfPathEffect != null && !hasHitSomething)onEndOfPathEffect.onEndOfPath(caster);
            return this;
        }
        return null;
    }

    protected void additionalHitEffects(Persona tgt) {
    }

    protected void additionalGuidanceEffects() {
    }

    public Rectangle getAbsBounds() {
        double angle = Math.abs(this.angle);
        if (angle >= 0 && angle < 45) {
            return new Rectangle((int)(this.getAbsX() - w / 2), (int)(this.getAbsY() - h / 2), (int)w, (int)h);
        } else if (angle >= 45 && angle < 135) {
            return new Rectangle((int)(this.getAbsX() - h / 2), (int)(this.getAbsY() - w / 2), (int)h, (int)w);
        } else if (angle >= 135 && angle < 225) {
            return new Rectangle((int)(this.getAbsX() - w / 2), (int)(this.getAbsY() - h / 2), (int)w, (int)h);
        } else if (angle >= 225 && angle < 315) {
            return new Rectangle((int)(this.getAbsX() - h / 2), (int)(this.getAbsY() - w / 2), (int)h, (int)w);
        } else return super.getAbsBounds();
    }

	public OnHitListener getOnHitEffect() {
		return onHitEffect;
	}

	public void setOnHitEffect(OnHitListener onHitEffect) {
		this.onHitEffect = onHitEffect;
	}

	public EndOfPathListener getOnEndOfPathEffect() {
		return onEndOfPathEffect;
	}

	public void setOnEndOfPathEffect(EndOfPathListener onEndOfPathEffect) {
		this.onEndOfPathEffect = onEndOfPathEffect;
	}

}
