package entities.characters.personas;

import entities.characters.Character;
import conditions.etc.Cooldown;
import game.Engine;
import items.Item;
import org.lwjgl.util.Point;
import util.Constants;
import util.Maths;

import java.util.ArrayList;

/**
 * The A* Simple Mob is a Mob that meanders, preferably towards the player.
 * Actually, I'm not sure what it'll do. It'll just do, you know?
 */
public class AStarSimpleMob extends SimpleMob {
    Persona target = null;
    ArrayList<Point> path = new ArrayList<Point>();

    private Cooldown attackBufferTimer = new Cooldown(1.25f * Constants.TICKS_PER_SECOND);

    public AStarSimpleMob(Character c) {
        character = c;
    }

    public AStarSimpleMob() {
    }

    private static final double findTargetChance = .00833;

    public void moveAI() {
        if (target == null) {
            findTarget();
        } else {
            if (Math.random() < 2 * findTargetChance) {
                this.path = AStarHunter.calculateRoute(this.character, target.getCharacter());
            }
        }
        attackBufferTimer.tick();
        if (target != null) {
            if (target.getCharacter().getHP() <= 0) {
                target = null;
                path = null;
                return;
            }
            //followPath will try and follow the path, and if there is no path, then it just moves the
            //hunter towards the target.
            if (Maths.dist(this, target) >= 70 * 16) {
                target = null;
                path = null;
                return;
            }
            if (Maths.dist(this, target) >= 5 * 16) {
                if (!followPath(path, this.character)) {
                    moveTowardsTarget(this.character, target.getCharacter().getXi(), target.getCharacter().getYi());
                }
	            if(Math.random() < .0001 * (Maths.dist(this, target)<16*5?50:.75)){
		            //randomly ocassionally stop hunting.
		            target = null;
		            path = null;
		            return;
	            }
            } else if (attackBufferTimer.offCooldown()) {
                if (character.getEquippedItem() != null) {
                    Item equippedItem = character.getEquippedItem();
                    equippedItem.attack(target.getCharacter().getLoc());
                    breakAttackRun();
                } else {
                    if (!followPath(path, this.character)) {
                        moveTowardsTarget(this.character, target.getCharacter().getXi(), target.getCharacter().getYi());
                    }
                    if (damageAdjacent()) breakAttackRun();
                }
            }
        } else {
            super.moveAI();
        }
    }

    private void breakAttackRun() {
        attackBufferTimer.reset();
        this.target = null;
        this.path = null;
    }

    private void findTarget() {
        if (!attackBufferTimer.offCooldown()) return;
        if (Math.random() < findTargetChance) {
            Persona p =
                    Engine.getActiveArea().getPersonas().getClosest(
                            this.getCharacter().getLoc(), false, true, this.getTeam()
                    );
            if (p != null && Maths.dist(this, p) < 64 * 16) {
                if (p.getTeam() != this.getTeam()) {
                    target = p;
                    this.path = AStarHunter.calculateRoute(this.character, target.getCharacter());
                    return;
                }
            }
            target = null;
            if (path != null) {
                path.clear();
            }
        }
    }

    public String toString() {
        return "A*SimpleMob:" + character;
    }
}
