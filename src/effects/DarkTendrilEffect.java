package effects;

import entities.characters.personas.Persona;
import entities.characters.Character;
import game.Engine;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.Constants;
import util.Maths;

import java.util.ArrayList;

public class DarkTendrilEffect extends Effect {

    public static final int SIZE = 8;
    public static final int RANGE = 200;
    private float maxDD = .25f;
    private static float maxSpeed = 10f;
    private float damage;
    private int maxTargets;
    private int targetsHit = 0;
    private int team;
    private int lifetime = 0;
    private Character caster;
    private Character target = null;
    private Persona targetPersona = null;
    private ArrayList<TendrilPuffEffect> puffs = new ArrayList<TendrilPuffEffect>();

    public DarkTendrilEffect(float damage, Character caster, int maxTargets) {
        this.setLoc(caster.getLoc());
        this.setWidth(SIZE);
        this.setHeight(SIZE);
        this.team = caster.getTeam();
        this.maxTargets = maxTargets;
        this.damage = damage;
        this.caster = caster;
        targetPersona = Engine.getActiveArea().getPersonas().getClosestEnemy(caster, team);
        target = targetPersona.getCharacter();
        if (target != null) {
            int targetX = target.getXi();
            int targetY = target.getYi();
            double distanceToTarget = Maths.dist(caster.getLoc(), new Point(targetX, targetY));
            dx = (float)(-((caster.getX() - targetX) / distanceToTarget) * speed);
            dy = (float)(-((caster.getY() - targetY) / distanceToTarget) * speed);
        }
    }

    public void draw(float sx, float sy, Graphics g, float zoom) {
        g.setColor(Color.black);
        g.fillRect((getAbsX() - SIZE / 2 - sx) * zoom, (getAbsY() - SIZE / 2 - sy) * zoom, SIZE * zoom, SIZE * zoom);
        for (TendrilPuffEffect puff : puffs) {
            puff.draw(sx, sy, g, zoom);
        }
    }

    public Effect tick() {
        if (targetPersona == null) return this;
        if (target == null) return this;
        if (target.getHP() <= 0) findTarget();
        if (targetPersona == null) return this;
        if (target == null) return this;
        lifetime++;
        if (lifetime > Constants.TICKS_PER_SECOND * .5) maxDD += .15;
        ArrayList<TendrilPuffEffect> toBeRemoved = new ArrayList<TendrilPuffEffect>();
        for (TendrilPuffEffect puff : puffs) {
            puff.tick();
            if (puff.getFrame() == TendrilPuffEffect.MAXLIFETIME) toBeRemoved.add(puff);
        }
        for (TendrilPuffEffect puff : toBeRemoved) {
            puffs.remove(puff);
        }
        Point halfStep = new Point((int)(this.getX() + this.getDX() / 2), (int)(this.getY() + this.getDY() / 2));
        puffs.add(new TendrilPuffEffect(halfStep, 0, 0));
        this.setX(this.getX() + this.getDX());
        this.setY(this.getY() + this.getDY());
        puffs.add(new TendrilPuffEffect(this.getAbsoluteLoc(), 0, 0));
        int targetX = target.getXi();
        int targetY = target.getYi();
        double distanceToTarget = Maths.dist(this.getAbsoluteLoc(), new Point(targetX, targetY));
        float ddx = (float)(-((this.getAbsX() - targetX) / distanceToTarget) * maxDD);
        float ddy = (float)(-((this.getAbsY() - targetY) / distanceToTarget) * maxDD);
        if ((dx) < maxSpeed && ddx > 0) dx += ddx;
        else if ((dx) > -maxSpeed && ddx < 0) dx += ddx;
        if ((dy) < maxSpeed && ddy > 0) dy += ddy;
        else if ((dy) > -maxSpeed && ddy < 0) dy += ddy;
        if (this.getAbsBounds().intersects(target.getAbsHitbox())) {
            if (Character.dealDarkDamage((int)damage, caster, target, false)) {
                Engine.addToBeRemoved(targetPersona);
            }
            targetsHit++;
            if (targetsHit == maxTargets) {
                return this;
            }
            findTarget();
        }
        return null;
    }

    private void findTarget() {
        targetPersona =
                Engine.getActiveArea().getPersonas().getClosestEnemy(
                        this.getAbsoluteLoc(), team, new Character[]{target}
                );
        if (targetPersona != null && Maths.dist(target, targetPersona.getCharacter()) <= RANGE) {
            target = targetPersona.getCharacter();
        } else {
            target = null;
            targetPersona = null;
        }
    }
}

class TendrilPuffEffect extends EffectEntity {
    public static final int MAXLIFETIME = 2 * Constants.TICKS_PER_SECOND;

    private int frame = 0;

    TendrilPuffEffect(Point absLoc, float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
        this.setLoc(absLoc);
    }

    public void draw(float sx, float sy, Graphics g, float zoom) {
        g.setColor(Color.black);
        g.fillOval(
                (getAbsX() - DarkTendrilEffect.SIZE / 2 - sx) * zoom,
                (getAbsY() - DarkTendrilEffect.SIZE / 2 - sy) * zoom,
                DarkTendrilEffect.SIZE * zoom,
                DarkTendrilEffect.SIZE * zoom
        );
    }

    public void tick() {
        frame++;
        this.setX(this.getX() + this.getDX());
        this.setY(this.getY() + this.getDY());
    }

    public int getFrame() {
        return frame;
    }
}
