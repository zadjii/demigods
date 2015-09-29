package entities.characters.personas;

import entities.characters.Character;
import game.Engine;
import items.Item;
import org.lwjgl.util.Point;
import util.Maths;

import java.util.ArrayList;

public class OrcWarriorAI extends SimpleMob {

    private static final long serialVersionUID = 1L;
    Persona target = null;
    Point origin = null;
    ArrayList<Point> path = new ArrayList<Point>();

    public OrcWarriorAI(Character c) {
        character = c;
    }

    public OrcWarriorAI() {
    }

    private static final double findTargetChance = .00833;

    public void moveAI() {
        if (this.origin != null) {
            if (Maths.dist(origin, this.character.getLoc()) > (64 / 2) * 16) {
                this.path = AStarHunter.calculateRoute(this.character, origin);
                if (!followPath(path, this.character)) {
                    moveTowardsTarget(this.character, origin.getX(), origin.getY());
                }
                return;
            }
        }
        if (target == null) {
            FINDING_TARGET:
            if (Math.random() < findTargetChance) {
                Persona p = Engine.getActiveArea().getPersonas().getClosest(this.getCharacter().getLoc(), false, true, this.getTeam());
                if (p != null && Maths.dist(this, p) < 15 * 16) {
                    if (p.getTeam() != this.getTeam()) {
                        target = p;
                        this.path = AStarHunter.calculateRoute(this.character, target.getCharacter());
                        break FINDING_TARGET;
                    }
                }
                target = null;
                if (path != null) {
                    path.clear();
                }
            }
        } else {
            if (Math.random() < 2 * findTargetChance) {
                this.path = AStarHunter.calculateRoute(this.character, target.getCharacter());
            }
        }
        if (target != null) {
            boolean moveTowardsTarget = true;
            if (target.getCharacter().getHP() <= 0) {
                target = null;
                this.path = null;
                return;
            } else {
                if (Maths.dist(this, target) <= 2 * 16) {
                    moveTowardsTarget = false;
                    Item equippedItem = character.getEquippedItem();
                    if (equippedItem != null) {
                        equippedItem.attack(
                                target.getCharacter().
                                        getLoc());
                    }
                }
            }
            if (moveTowardsTarget) {
                if (!followPath(path, this.character)) {
                    moveTowardsTarget(this.character, target.getCharacter().getXi(), target.getCharacter().getYi());
                }
            }
        } else {
            super.moveAI();
        }
    }

    public void setOrigin(Point point) {
        this.origin = point;
    }

    public String toString() {
        return "Orc Warrior - " + character;
    }
}
