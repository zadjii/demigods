package entities.characters.personas;

import java.io.Serializable;
import java.util.ArrayList;

import entities.characters.Character;
import game.Demigods;

import game.Engine;
import org.lwjgl.util.Point;

import items.*;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Graphics;
import util.Maths;

public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    protected Character character;
    protected int team = -1;
    private transient boolean drawn = false;

    public Item getEquippedItem() {
        return character.getEquippedItem();
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character c) {
        character = c;
    }

    public void moveAI() {
    }

    public Persona() {
    }

    public void undraw() {
        this.drawn = false;
    }

    /**
     * Params:
     * gx&gy: the grid loc that the screen is currently drawing. if this is not
     * the grid loc of the actual end of the persona (bot right) then
     * don't draw just yet.
     */
    public void draw(float sx, float sy, float zoom, Graphics g, int gx, int gy) {
        if (drawn) return;
        int botRightX = (int)((character.getX()));
        int botRightY = (int)((character.getY() - character.getImageYOffset() + character.getHeight() - 2));
        Rectangle rect = new Rectangle(gx * 16, gy * 16, 17, 17);
        if (!rect.contains(botRightX, botRightY)) return;
        character.draw(sx, sy, zoom, g);
        drawn = true;
    }

    public void draw(float sx, float sy, float zoom, Graphics g) {
        character.draw(sx, sy, zoom, g);
    }

    public boolean damageAdjacent() {
        try {
            Rectangle hitbox = this.character.getAbsHitbox();
            hitbox = new Rectangle(
                    hitbox.getX() + 2,
                    hitbox.getY() + 2,
                    hitbox.getWidth() - 4,
                    hitbox.getHeight() - 4
            );
            ArrayList<Persona> hit = Engine.getActiveArea().getPersonas().get(hitbox);
            for (Persona tgt : hit) {
                if (tgt.getTeam() == this.getTeam() || tgt.knockedBack() || this.knockedBack()) continue;
	            if(hitbox.intersects(tgt.getCharacter().getAbsHitbox()))
		            if (Character.attack(this.character.getStat(Character.ATTACK), this.character, tgt.getCharacter(), true)) {
			            Engine.addToBeRemoved(tgt);
		            }
            }
            if (hit.size() > 0) return true;
        } catch (NullPointerException e) {
            System.err.println(
                    e.getLocalizedMessage()
            );
        }
        return false;
    }

    public void setTeam(int team) {
        this.team = team;
        this.character.setTeam(team);
    }

    public int getTeam() {
        return team;
    }

    public boolean knockedBack() {
        return (this.character.getKnockback() != null);
    }

    public Point getMapLoc() {
        return character.getBaseMap();
    }

    public String toString() {
        return "Persona - " + character;
    }

    protected static void moveTowardsTarget(Character character, Point tgt) {
        moveTowardsTarget(character, tgt.getX(), tgt.getY());
    }

    protected static void moveTowardsTarget(Character character, float destX, float destY) {
        float x = character.getX();
        float y = character.getY();
        float newDX = character.getSpeed();
        float newDY = character.getSpeed();
        if (newDX > Math.abs(x - destX)) newDX = Math.abs(x - destX);
        if (newDY > Math.abs(y - destY)) newDY = Math.abs(y - destY);
        if (x > destX) {
            newDX = -newDX;
        }
        if (y > destY) {
            newDY = -newDY;
        }
        character.setDX(newDX);
        character.setDY(newDY);
        if (x == destX) {
            character.setDX(0);
        }
        if (y == destY) {
            character.setDY(0);
        }
        if (character.getDX() > 0) character.setDirection(Character.Direction.RIGHT);
        else if (character.getDX() < 0) character.setDirection(Character.Direction.LEFT);
        if (character.getDY() > 0) character.setDirection(Character.Direction.DOWN);
        else if (character.getDY() < 0) character.setDirection(Character.Direction.UP);
    }

    protected static boolean followPath(ArrayList<Point> path, Character character) {
        if (path != null && path.size() != 0) {
            Point nextGridSpot = path.get(0);
            Point nextSpot = new Point(8 + nextGridSpot.getX() * 16, 8 + nextGridSpot.getY() * 16);
            moveTowardsTarget(character, nextSpot);
            if (Maths.dist(character.getLoc(), nextSpot) < 8) {
                path.remove(0);
            }
            if (path.size() == 1) {
                if (Maths.dist(character.getLoc(), nextSpot) < 32) {
                    path.remove(0);
                    character.setDX(0);
                    character.setDY(0);
                }
            }
            return true;
        }
        character.setDX(0);
        character.setDY(0);
        return false;
    }
}
