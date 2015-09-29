package effects;

import entities.characters.personas.Persona;
import game.Engine;
import org.lwjgl.util.Point;
import util.Images;
import entities.characters.Character;
import util.Maths;

import java.util.ArrayList;

public class SparkEffect extends RotatedProjectileEffect {

    protected int width = 16;
    protected int height = 16;
    protected int frame = 0;
    protected static final int FRAME_RATIO = 4;
    protected Persona target = null;

    public SparkEffect(Character caster, Point target, double damage, double speed, double range) {
        super(caster, target, damage, speed, range);
        this.speed = (float)speed;
        this.setWidth(width);
        this.setHeight(height);
        knockback = true;
        stopOnHit = true;
        image = Images.sparkEffectSheet.getSprite(0, 0);
    }

    public SparkEffect(Character caster, Point start, Persona target, double damage, double speed, double range) {
        super(caster, target.getCharacter().getLoc(), damage, speed, range);
        this.setLoc(start);
        this.target = target;
        this.speed = (float)speed;
        this.setWidth(width);
        this.setHeight(height);
        knockback = true;
        stopOnHit = true;
        image = Images.sparkEffectSheet.getSprite(0, 0);
    }

    public Effect tick() {
        frame++;
        if (frame >= 2 * FRAME_RATIO) frame = 0;
        image = Images.sparkEffectSheet.getSprite(frame / 3, 0);
        this.speed *= 1.1f;
        Point initial = this.getAbsoluteLoc();
        additionalGuidanceEffects();
        this.setX(this.getX() + this.getDX());
        this.setY(this.getY() + this.getDY());
        distanceTraveled += Maths.dist(initial, this.getAbsoluteLoc());
        ArrayList<Persona> temp = new ArrayList<Persona>();
        for (Persona p : Engine.getActiveArea().getPersonas().get(this.getAbsBounds())) {
            if (p.getTeam() != caster.getTeam()) {
                if (!hit.contains(p)) {
                    hit.add(p);
                    if (Character.dealAirDamage((int)damage, caster, p.getCharacter(), knockback)) {
                        temp.add(p);
                    }
                    distanceTraveled = range;
                }
            }
        }
        for (Persona p : temp) {
            Engine.addToBeRemoved(p);
        }
        if (distanceTraveled >= range) {
            return this;
        }
        return null;
    }

    protected void additionalHitEffects(Persona tgt) {
    }

    protected void additionalGuidanceEffects() {
        if (this.target != null) {
            if (target.getCharacter().getHP() <= 0) {
                target = null;
                return;
            }
            int targetX = target.getCharacter().getXi();
            int targetY = target.getCharacter().getYi();
            float thisX = this.getAbsX();
            float thisY = this.getAbsY();
            double distX = targetX - thisX;
            double distY = targetY - thisY;
            this.angle = Math.toDegrees(Math.atan2(distY, distX));
            dx = (float)(-((thisX - targetX) / Maths.dist(caster.getLoc(), new Point(targetX, targetY))) * speed);
            dy = (float)(-((thisY - targetY) / Maths.dist(caster.getLoc(), new Point(targetX, targetY))) * speed);
        }
    }
}
