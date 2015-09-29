package effects;

import entities.characters.Character;
import entities.characters.personas.Persona;
import entities.particles.etc.DroppedItem;
import game.Engine;
import items.weapons.Arrow;
import org.lwjgl.util.Point;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import util.Images;
import util.Maths;

import java.util.ArrayList;

public class ArrowEffect extends Effect {

    private double angle;
    private Character caster;
    private double damage;
    private double range;
    private double distanceTraveled = 0;

    public ArrowEffect(Character caster, Point target, double damage, double speed) {
        int targetX = target.getX();
        int targetY = target.getY();
        this.caster = caster;
        this.setLoc(caster.getLoc());
        float thisX = this.getAbsX();
        float thisY = this.getAbsY();
        this.damage = damage;
        if (speed > 7) this.range = speed * 25;
        else this.range = speed * 20;
        double theta = (targetY - thisY) / (targetX - thisX);
        this.angle = Math.toDegrees(Math.atan(theta));
        if (targetX < thisX) {
            angle += 180;
        }
        dx = (float)(-((thisX - targetX) / Maths.dist(caster.getLoc(), new Point(targetX, targetY))) * speed);
        dy = (float)(-((thisY - targetY) / Maths.dist(caster.getLoc(), new Point(targetX, targetY))) * speed);
        this.setWidth(8);
        this.setHeight(8);
    }

    public void draw(float sx, float sy, Graphics g, float zoom) {
        Image image = Images.arrowEffectImage;
        image.setRotation((float)angle);
        image.draw((getAbsX() - sx - 8) * zoom, (getAbsY() - sy - 8) * zoom, 16 * zoom, 16 * zoom);
    }

    @Override
    public Effect tick() {
        Point initial = this.getAbsoluteLoc();
        this.setX(this.getX() + this.getDX());
        this.setY(this.getY() + this.getDY());
        distanceTraveled += Maths.dist(initial, this.getAbsoluteLoc());
        ArrayList<Persona> temp = new ArrayList<Persona>();
        for (Persona p : Engine.getActiveArea().getPersonas().get(this.getAbsBounds())) {
            if (p.getTeam() != caster.getTeam()) {
                if (Character.attack(damage, caster, p.getCharacter(), true)) {
                    temp.add(p);
                }
                distanceTraveled = range;
            }
        }
        for (Persona p : temp) {
            Engine.getActiveArea().getPersonas().remove(p);
        }
        if (distanceTraveled >= range) {
            if (Math.random() < .25) {
                DroppedItem arrow = new DroppedItem(new Arrow());
                arrow.setLoc(this.getLoc());
                Engine.getActiveArea().getDroppedItems().add(arrow);
            }
            return this;
        }
        return null;
    }
}
