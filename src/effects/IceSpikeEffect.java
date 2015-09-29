package effects;

import entities.characters.personas.Persona;
import entities.characters.Character;
import game.Engine;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Graphics;
import util.Images;
import util.Maths;

import java.util.ArrayList;

public class IceSpikeEffect extends Effect {

    private int speed = 18;
    private Character caster;
    private int damage;

    int type = 0;
    //type 0: just goes to target
    //type 1: tracks a persona

    ArrayList<Point> hit = new ArrayList<Point>();
    private boolean drawingSpike = false;
    private int targetX;
    private int targetY;
    private int lifeTime = 50;
    private int spikeFrame = 0;

    public IceSpikeEffect(Character caster, int targetX, int targetY, int damage, int type) {
        this.caster = caster;
        setXY(caster.getX(), caster.getY());
        this.damage = damage;
        this.targetX = targetX;
        this.targetY = targetY;
        dx = (float)(-((caster.getX() - targetX) / Maths.dist(caster.getLoc(), new Point(targetX, targetY))) * speed);
        dy = (float)(-((caster.getY() - targetY) / Maths.dist(caster.getLoc(), new Point(targetX, targetY))) * speed);
        this.type = type;
        if (type != 2) {
            setWidth(48);
            setHeight(48);
        }
    }

    public void draw(float sx, float sy, Graphics g, float zoom) {
        for (Point p : hit) {
            Images.iceSpikeEffectSheet.getSprite(0, 1).draw((p.getX() - sx) * zoom, (p.getY() - sy) * zoom, 16 * zoom, 16 * zoom);
        }
        if (drawingSpike) {
            Point p = hit.get(hit.size() - 1);
            float adjustedPointX = p.getX() - sx - 8;
            if (spikeFrame < 1) {
                Images.iceSpikeEffectSheet.getSprite(1, 0).draw((adjustedPointX) * zoom, (p.getY() - sy - 8) * zoom, 16 * zoom, 16 * zoom);
            } else if (spikeFrame < 2) {
                Images.iceSpikeEffectSheet.getSprite(1, 0).draw((adjustedPointX) * zoom, (p.getY() - sy - 6) * zoom, 16 * zoom, 16 * zoom);
            } else if (spikeFrame < 3) {
                Images.iceSpikeEffectSheet.getSprite(1, 0).draw((adjustedPointX) * zoom, (p.getY() - sy - 4) * zoom, 16 * zoom, 16 * zoom);
            } else if (spikeFrame < 4) {
                Images.iceSpikeEffectSheet.getSprite(1, 0).draw((adjustedPointX) * zoom, (p.getY() - sy) * zoom, 16 * zoom, 16 * zoom);
            } else {
                Images.iceSpikeEffectSheet.getSprite(1, 1).draw((adjustedPointX) * zoom, (p.getY() - sy) * zoom, 16 * zoom, 16 * zoom);
                Images.iceSpikeEffectSheet.getSprite(1, 0).draw((adjustedPointX) * zoom, (p.getY() - sy - 16) * zoom, 16 * zoom, 16 * zoom);
                Images.iceSpikeEffectSheet.getSprite(0, 1).draw((adjustedPointX - 16) * zoom, (p.getY() - sy - 16) * zoom, 16 * zoom, 16 * zoom);
                Images.iceSpikeEffectSheet.getSprite(0, 1).draw((adjustedPointX) * zoom, (p.getY() - sy - 16) * zoom, 16 * zoom, 16 * zoom);
                Images.iceSpikeEffectSheet.getSprite(0, 1).draw((adjustedPointX + 16) * zoom, (p.getY() - sy - 16) * zoom, 16 * zoom, 16 * zoom);
                Images.iceSpikeEffectSheet.getSprite(0, 1).draw((adjustedPointX - 16) * zoom, (p.getY() - sy) * zoom, 16 * zoom, 16 * zoom);
                Images.iceSpikeEffectSheet.getSprite(0, 1).draw((adjustedPointX + 16) * zoom, (p.getY() - sy) * zoom, 16 * zoom, 16 * zoom);
                Images.iceSpikeEffectSheet.getSprite(0, 1).draw((adjustedPointX - 16) * zoom, (p.getY() - sy + 16) * zoom, 16 * zoom, 16 * zoom);
                Images.iceSpikeEffectSheet.getSprite(0, 1).draw((adjustedPointX) * zoom, (p.getY() - sy + 16) * zoom, 16 * zoom, 16 * zoom);
                Images.iceSpikeEffectSheet.getSprite(0, 1).draw((adjustedPointX + 16) * zoom, (p.getY() - sy + 16) * zoom, 16 * zoom, 16 * zoom);
            }
        }
    }

    public Effect tick() {
        if (!drawingSpike) {
            this.setX(this.getX() + this.getDX());
            this.setY(this.getY() + this.getDY());
            hit.add(new Point((int)this.getAbsX(), (int)this.getAbsY()));
            ArrayList<Persona> temp = new ArrayList<Persona>();
            Rectangle hitbox = new Rectangle((int)this.getAbsX() - 16, (int)this.getAbsY() - 16, 48, 48);
            Point coords = new Point(getAbsoluteLoc());
            Rectangle coordsBox = new Rectangle(coords.getX() - 8, coords.getY() - 8, 16, 16);
            if (coordsBox.contains(new Point(targetX, targetY))) {
                hit.add(new Point(targetX, targetY));
//
//			}
                drawingSpike = true;
                for (Persona p : Engine.getActiveArea().getPersonas().get(hitbox)) {
                    if (p.getTeam() != caster.getTeam()) {
                        if (Character.dealWaterDamage(damage, caster, p.getCharacter(), true)) {
                            temp.add(p);
                        }
                    }
                }
            }
            for (Persona p : temp) {
                Engine.addToBeRemoved(p);
            }
        } else {
            spikeFrame++;
            if (spikeFrame == 40) return this;
        }
        lifeTime--;
        if (lifeTime == 0) return this;
        return null;
    }
}
