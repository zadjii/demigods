package entities.characters.personas;

import effects.LaserEyeEffect;
import effects.RocketPunchEffect;
import entities.characters.Character;
import util.animation.Animation;
import conditions.etc.Cooldown;
import game.Engine;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import util.Constants;
import util.Maths;

import java.util.ArrayList;

public class IronGolemAI extends Persona {

    private static final long serialVersionUID = 1L;
    Persona target = null;
    ArrayList<Point> path = new ArrayList<Point>();
    private static final float findTargetChance = .0833f;
    private boolean chargingLaser = false;
    private Cooldown chargingTimer = new Cooldown(3 * Constants.TICKS_PER_SECOND);
    private Cooldown actionBreak = new Cooldown(2.5f * Constants.TICKS_PER_SECOND);
    private Point chargingStartPoint;
    private Point chargingEndPoint;
    private boolean punching = false;
    private boolean smashing = false;

    public IronGolemAI(Character c) {
        character = c;
    }

    public IronGolemAI() {
    }

    private void initializePathToPlayer() {
        if (Math.random() < findTargetChance) {
            Persona p = Engine.getPlayer();
            target = p;
            this.path = AStarHunter.calculateRoute(this.character, target.getCharacter());
        }
    }

    private void useRocketPunch() {
        Engine.add(new RocketPunchEffect(this.character, target.getCharacter().getLoc(), 10, 200));
        this.getCharacter().drainMana(6);
    }

    private void fireLaser() {
        Engine.add(new LaserEyeEffect(this.character, chargingStartPoint, chargingEndPoint, 15, 60));
        this.getCharacter().drainMana(10);
    }

    private void smash() {
        character.getSkills().get(0).use(this.character, target.getCharacter().getLoc());
        punching = false;
        smashing = true;
        actionBreak.reset();
    }

    private boolean smashOffCD() {
        return character.getSkills().get(0).offCooldown();
    }

    private void punch() {
        Point targetLoc = this.target.getCharacter().getLoc();
        Character user = this.getCharacter();
        int imageOriginX = character.getXi() - character.getImageXOffset();
        int imageOriginY = character.getYi() - character.getImageYOffset();
        int userX = user.getXi() - user.getImageXOffset() + (int)(user.getWidth() / 2);
        int userY = user.getYi() - user.getImageYOffset() + (int)(user.getHeight() / 2);
        int dx = 0;
        int dy = 0;
        if (Math.abs(targetLoc.getX() - userX) >=
                Math.abs(targetLoc.getY() - userY)) {
            if (targetLoc.getX() >= userX) dx = 1;
            else dx = -1;
        } else {
            if (targetLoc.getY() >= userY) dy = 1;
            else dy = -1;
        }
        if (dx == 0 && dy == -1) {
            user.setDirection(Character.Direction.UP);
        } else if (dx == 0 && dy == 1) {
            user.setDirection(Character.Direction.DOWN);
        } else if (dx == -1 && dy == 0) {
            user.setDirection(Character.Direction.LEFT);
        } else if (dx == 1 && dy == 0) {
            user.setDirection(Character.Direction.RIGHT);
        }
        targetLoc = new Point(targetLoc.getX() - imageOriginX, targetLoc.getY() - imageOriginY);
        this.getCharacter().setAnimation(Animation.getMassivePunch(this.getCharacter().getDirection(), targetLoc));
        this.character.setDX(0);
        this.character.setDY(0);
        this.path = null;
        punching = true;
        smashing = false;
    }

    private void punchChecks() {
        Animation animation = this.getCharacter().getAnimation();
        float imageOriginX = character.getX() - character.getImageXOffset();
        float imageOriginY = character.getY() - character.getImageYOffset();
        int hitX = animation.getRightHand().x() + (int)imageOriginX;
        int hitY = animation.getRightHand().y() + (int)imageOriginY;
        Rectangle hitRegion = new Rectangle(hitX, hitY, 32, 32);
        ArrayList<Persona> temp = new ArrayList<Persona>();
        boolean hit = false;
        for (Persona p : Engine.getActiveArea().getPersonas().get(hitRegion)) {
            if (p.getTeam() != character.getTeam()) {
                double damage = this.getCharacter().getStat(Character.ATTACK);
                hit = true;
                if (Character.attack(damage, character, p.getCharacter(), true)) {
                    temp.add(p);
                }
            }
        }
        for (Persona p : temp) {
            Engine.addToBeRemoved(p);
        }
        if (hit) {
            this.getCharacter().setAnimation(null);
            actionBreak.reset();
        }
    }

    public void moveAI() {
        if (target == null) {
            initializePathToPlayer();
        } else {
            if (Math.random() < findTargetChance / 10f) {
                this.path = AStarHunter.calculateRoute(this.character, target.getCharacter());
            }
        }
        if (target != null) {
            if (chargingLaser) {
                chargingTimer.tick();
                if (chargingTimer.offCooldown()) {
                    chargingLaser = false;
                    fireLaser();
                    this.path = AStarHunter.calculateRoute(this.character, target.getCharacter());
                } else if (chargingTimer.getRemaining() > (.0125f * (float)Constants.TICKS_PER_SECOND)) {
                    chargingEndPoint = target.getCharacter().getLoc();
                }
            } else if (this.character.getAnimation() != null) {
                if (punching) punchChecks();
            } else {
                actionBreak.tick();
                if (!followPath(path, this.character)) {
                    moveTowardsTarget(this.character, target.getCharacter().getXi(), target.getCharacter().getYi());
                }
                if (target.getCharacter().getHP() <= 0) {
                    target = null;
                    this.path = null;
                } else if (actionBreak.offCooldown()) {
                    punching = false;
                    smashing = false;
                    if (Maths.dist(this, target) <= 5 * 16 && smashOffCD()) {
                        smash();
                    } else if (Maths.dist(this, target) <= 8 * 16) {
                        actionBreak.reset();
                    } else if (Maths.dist(this, target) > 10 * 16) {
                        if (this.getCharacter().getMP() > 10) {
                            chargingLaser = true;
                            chargingTimer.reset();
                            chargingStartPoint = target.getCharacter().getLoc();
                            this.character.setDX(0);
                            this.character.setDY(0);
                            this.path = null;
                            actionBreak.reset();
                        }
                    }
                } else {
                    if (smashing) {
                        smashing = false;
                        if (this.getCharacter().getMP() > 10) {
                            chargingLaser = true;
                            chargingTimer.reset();
                            chargingStartPoint = target.getCharacter().getLoc();
                            this.character.setDX(0);
                            this.character.setDY(0);
                            this.path = null;
                            actionBreak.reset();
                        }
                    }
                }
            }
        } else {
            super.moveAI();
        }
    }

    public String toString() {
        return "Iron Golem AI - " + character;
    }
}