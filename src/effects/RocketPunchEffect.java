package effects;

import entities.characters.personas.Persona;
import entities.characters.Character;
import game.Engine;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.Constants;
import util.Images;
import util.Maths;

import java.util.ArrayList;

public class RocketPunchEffect extends Effect {

    private int frameNum = 0;
    private int frameRatio = 15;
    private int speed = 15;
    private Character caster;
    private int damage;
    private int lifetime = -1;

    public RocketPunchEffect(Character caster, Point target, int damage, int lifetime) {
        int targetX = target.getX();
        int targetY = target.getY();
        this.caster = caster;
        this.setLoc(caster.getLoc());
        this.damage = damage;
        dx = (float)(-((caster.getX() - targetX) / Maths.dist(caster.getLoc(), new Point(targetX, targetY))) * speed);
        dy = (float)(-((caster.getY() - targetY) / Maths.dist(caster.getLoc(), new Point(targetX, targetY))) * speed);
        this.lifetime = lifetime;
        setWidth(16);
        setHeight(16);
    }

    public RocketPunchEffect(Character caster, int targetX, int targetY, int damage, int lifetime) {
        this.caster = caster;
        this.setLoc(caster.getLoc());
        this.damage = damage;
        dx = (float)(-((caster.getX() - targetX) / Maths.dist(caster.getLoc(), new Point(targetX, targetY))) * speed);
        dy = (float)(-((caster.getY() - targetY) / Maths.dist(caster.getLoc(), new Point(targetX, targetY))) * speed);
        this.lifetime = lifetime;
        setWidth(32);
        setHeight(32);
    }

    public void draw(float sx, float sy, Graphics g, float zoom) {
        g.setColor(Color.darkGray);
        g.fillRoundRect((getAbsX() - sx) * zoom, (getAbsY() - sy) * zoom, getWidth() * zoom, getHeight() * zoom, 2);
        Images.fireballEffectSheet.getSprite(frameNum / frameRatio, 0).draw(
                (getAbsX() - sx) * zoom, (getAbsY() - sy) * zoom, getWidth() * zoom, getHeight() * zoom);
        ++frameNum;
        if (frameNum == (4 * frameRatio)) frameNum = 0;
    }

    public Effect tick() {
        this.setX(this.getX() + this.getDX());
        this.setY(this.getY() + this.getDY());
        ArrayList<Persona> temp = new ArrayList<Persona>();
        for (Persona p : Engine.getActiveArea().getPersonas().get(this.getAbsBounds())) {
            if (p.getTeam() != caster.getTeam()) {
                p.getCharacter().stun(2.5f * Constants.TICKS_PER_SECOND);
                if (Character.dealEarthDamage(damage, caster, p.getCharacter(), true)) {
                    temp.add(p);
                }
                lifetime = 1;
            }
        }
        for (Persona p : temp) {
            Engine.addToBeRemoved(p);
        }
        lifetime--;
        if (lifetime == 0) return this;
        return null;
    }
}
