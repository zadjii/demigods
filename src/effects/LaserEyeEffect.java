package effects;

import entities.characters.personas.Persona;
import entities.characters.Character;
import game.Engine;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;
import util.Maths;

import java.util.ArrayList;

public class LaserEyeEffect extends Effect {

    private int frameNum = 0;
    private int frameRatio = 15;
    private int speed = 2;
    private Character caster;
    private int damage;
    private int lifetime = -1;

    ArrayList<Persona> hit = new ArrayList<Persona>();

    private static Color[] reds = new Color[]{
            Color.red,
            new Color(255, 100, 100),
            new Color(255, 100, 0),
            new Color(255, 0, 100),
            new Color(255, 200, 0),
    };
    private static Color darkRed = new Color(175, 50, 0);

    public LaserEyeEffect(Character caster, Point start, Point target, int damage, int lifetime) {
        int targetX = target.getX();
        int targetY = target.getY();
        int startX = start.getX();
        int startY = start.getY();
        this.caster = caster;
        this.setLoc(target);
        this.damage = damage;
        dx = (float)(-((startX - targetX) / Maths.dist(start, target)) * speed);
        dy = (float)(-((startY - targetY) / Maths.dist(start, target)) * speed);
        this.lifetime = lifetime;
        setWidth(4);
        setHeight(4);
    }

    public void draw(float sx, float sy, Graphics g, float zoom) {
        Line line = new Line((caster.getX() + 16 - sx) * zoom, (caster.getY() - 64 - sy) * zoom, (this.getAbsX() - sx) * zoom, (this.getAbsY() - sy) * zoom);
        g.setColor(darkRed);
        g.setLineWidth(20);
        g.draw(line);
        g.fillOval((caster.getX() + 10 - sx) * zoom, (caster.getY() - 70 - sy) * zoom, 16 * zoom, 16 * zoom);
        g.fillOval((getAbsX() - 5 - sx) * zoom, (getAbsY() - 5 - sy) * zoom, 10 * zoom, 10 * zoom);
        int index = (int)(reds.length * Math.random());
        g.setColor(reds[index]);
        g.fillOval((getAbsX() - 5 - sx) * zoom, (getAbsY() - 5 - sy) * zoom, 10 * zoom, 10 * zoom);
        g.setLineWidth(14);
        g.draw(line);
        g.setLineWidth(1);
    }

    public Effect tick() {
        this.setX(this.getX() + this.getDX());
        this.setY(this.getY() + this.getDY());
        ArrayList<Persona> temp = new ArrayList<Persona>();
        for (Persona p : Engine.getActiveArea().getPersonas().get(this.getAbsBounds())) {
            if (p.getTeam() != caster.getTeam()) {
                TYPE_1:
                {
                    for (Persona anotherPersona : hit) {
                        if (anotherPersona.equals(p)) {
                            break TYPE_1;
                        }
                    }
                    if (Character.dealFireDamage(damage, caster, p.getCharacter(), false)) {
                        temp.add(p);
                    }
                    hit.add(p);
                }
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
