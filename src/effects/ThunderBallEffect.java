package effects;

import entities.characters.personas.Persona;
import entities.characters.Character;
import game.Engine;
import org.newdawn.slick.Graphics;
import util.Images;

import java.util.ArrayList;

public class ThunderBallEffect extends Effect {

    public static final int THUNDERBOLT_SPEED = 10;

    private int frameNum = 0;
    private int frameRatio = 2;
    private int speed = THUNDERBOLT_SPEED;
    private Character caster;
    private int damage;
    private int lifetime = -1;

    int type = 0;

    ArrayList<Persona> hit = new ArrayList<Persona>();

    public ThunderBallEffect(Character caster, float dx, float dy, int damage, int type, int lifetime) {
        this.caster = caster;
        this.setLoc(caster.getLoc());
        this.damage = damage;
        this.dx = dx;
        this.dy = dy;
        this.lifetime = lifetime;
        this.type = type;
        if (type != 2) {
            setWidth(16);
            setHeight(16);
        }
    }

    public void draw(float sx, float sy, Graphics g, float zoom) {
        Images.lightningBallEffectSheet.getSprite(frameNum / frameRatio, 0).draw(
                (getAbsX() - sx) * zoom, (getAbsY() - sy) * zoom, 16 * zoom, 16 * zoom);
        ++frameNum;
        if (frameNum == (4 * frameRatio)) frameNum = 0;
    }

    public Effect tick() {
        this.setX(this.getX() + this.getDX());
        this.setY(this.getY() + this.getDY());
        ArrayList<Persona> temp = new ArrayList<Persona>();
        for (Persona p : Engine.getActiveArea().getPersonas().get(this.getAbsBounds())) {
            if (p.getTeam() != caster.getTeam()) {
                if (type == 0) {
                    if (Character.dealAirDamage(damage, caster, p.getCharacter(), true)) {
                        temp.add(p);
                    }
                    lifetime = 1;
                } else if (type == 1) {
                    if (Character.dealAirDamage(damage, caster, p.getCharacter(), false)) {
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
