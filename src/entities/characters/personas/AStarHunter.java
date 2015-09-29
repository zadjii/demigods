package entities.characters.personas;

import entities.characters.Character;
import entities.characters.aiUtil.AStarNode;
import entities.tiles.Tiles;
import game.Engine;
import areas.NewGameArea;
import items.Item;
import org.lwjgl.util.Point;
import util.Maths;

import java.util.ArrayList;
import java.util.TreeSet;

public class AStarHunter extends SimpleMob {

    private static final long serialVersionUID = 1L;
    Persona target = null;
    ArrayList<Point> path = new ArrayList<Point>();

    public AStarHunter(Character c) {
        character = c;
    }

    public AStarHunter() {
    }

    private static final double findTargetChance = .00833;

    public void moveAI() {
        if (target == null) {
            FINDING_TARGET:
            if (Math.random() < findTargetChance) {
                Persona p = Engine.getActiveArea().getPersonas().getClosest(this.getCharacter().getLoc(), false, true, this.getTeam());
                if (p != null && Maths.dist(this, p) < 20 * 16) {
                    if (p.getTeam() != this.getTeam()) {
                        target = p;
                        this.path = calculateRoute(this.character, target.getCharacter());
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
                this.path = calculateRoute(this.character, target.getCharacter());
            }
        }
        if (target != null) {
            //followPath will try and follow the path, and if there is no path, then it just moves the
            //hunter towards the target.
            boolean moveTowardsTarget = true;
            if (target.getCharacter().getHP() <= 0) {
                target = null;
                this.path = null;
                return;
            } else {
                if (Maths.dist(this, target) <= 3 * 16) {
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

    public String toString() {
        return "AStarHunter - " + character;
    }

    protected static ArrayList<Point> calculateRoute(Character src, Character tgt) {
        return calculateRoute(src, tgt.getLoc());
    }

    protected static ArrayList<Point> calculateRoute(Character src, Point tgt) {
        Point startGLoc = new Point(src.getXi() / 16, src.getYi() / 16);
        Point targetGLoc = new Point(tgt.getX() / 16, tgt.getY() / 16);
        TreeSet<AStarNode> openSet = new TreeSet<AStarNode>();
        TreeSet<AStarNode> closedSet = new TreeSet<AStarNode>();
        NewGameArea area = Engine.getActiveArea();
        ArrayList<Point> path = new ArrayList<Point>();
        openSet.add(new AStarNode(startGLoc, targetGLoc, null, 0));
        boolean targetFound = false;
        AStarNode curr = openSet.first();
        int attempts = 0;
        while (!targetFound) {
            if (openSet.size() == 0) {
                targetFound = false;
                break;
            }
            if (openSet.size() == 100000) {
                targetFound = false;
                break;
            }
            curr = openSet.pollFirst();
            closedSet.add(curr);
            if (curr.getCoordinates().equals(targetGLoc)) {
                targetFound = true;
                break;
            }
            attempts++;
            if (attempts > 25 * (Maths.dist(startGLoc, targetGLoc)) && Maths.dist(targetGLoc, curr.getCoordinates()) > (Maths.dist(startGLoc, targetGLoc)) / 10) {
                break;
            }
            DX:
            for (int dx = -1; dx < 2; ++dx)
                DY:for (int dy = -1; dy < 2; ++dy) {
                    if (dx == 0 && dy == 0) continue;
                    Point next = new Point(curr.getX() + dx, curr.getY() + dy);
                    Point nextAbsLoc = new Point(next.getX() * 16, next.getY() * 16);
                    if (next.equals(targetGLoc)) {
                        openSet.add(new AStarNode(next, targetGLoc, curr, 0));
                        break DX;
                    }
                    try {
                        if (!area.getPassable()[next.getX()][next.getY()]) continue;
                    } catch (IndexOutOfBoundsException e) {
                        continue;
                    }
                    float terrainValue = Tiles.terrainValue(area.getMap()[next.getX()][next.getY()]);
                    if (Maths.dist(startGLoc, next) > 5) {
                        for (int dx2 = -1; dx2 <= 1; ++dx2)
                            for (int dy2 = -1; dy2 <= 1; ++dy2) {
                                try {
                                    if (!area.getPassable()[next.getX() + dx2][next.getY() + dy2]) {
                                        //continue DY;
                                        if (dx2 * dy2 != 0) terrainValue += 2;
                                        terrainValue += 2;
                                    }
                                } catch (IndexOutOfBoundsException ignored) {
                                }
                            }
                    }
                    AStarNode nextNode = new AStarNode(next, targetGLoc, curr, terrainValue);
                    for (AStarNode node : closedSet) {
                        if (nextNode.getCoordinates().equals(node.getCoordinates())) {
                            continue DY;
                        }
                    }
                    if (closedSet.contains(nextNode)) continue;
                    boolean openSetContainsNode = false;
                    for (AStarNode node : openSet) {
                        if (nextNode.getCoordinates().equals(node.getCoordinates())) {
                            openSetContainsNode = true;
                            break;
                        }
                    }
                    if (!openSetContainsNode) {
                        openSet.add(nextNode);
                    } else {
                        float newCost = nextNode.getCostSoFar();
                        for (AStarNode node : openSet) {
                            if (nextNode.getCoordinates().equals(node.getCoordinates())) {
                                nextNode = node;
                                break;
                            }
                        }
                        if (newCost < nextNode.getCostSoFar()) {
                            nextNode.setCostSoFar(newCost);
                            nextNode.setParent(curr);
                        }
                    }
                }
        }
        if (!targetFound) {
            return null;
        } else {
            while (curr != null) {
                path.add(0, curr.getCoordinates());
                curr = curr.getParent();
            }
        }
        //Some path smoothing (hopefully)
        ArrayList<Point> temp = new ArrayList<Point>();
        for (int i = 0; i < path.size() - 2; i++) {
            if (path.get(i).getX() != path.get(i + 1).getX()
                    && path.get(i).getX() == path.get(i + 2).getX()) {
                temp.add(path.get(i + 1));
            } else if (path.get(i).getY() != path.get(i + 1).getY()
                    && path.get(i).getY() == path.get(i + 2).getY()) {
                temp.add(path.get(i + 1));
            }
        }
        for (Point point : temp) {
            path.remove(point);
        }
        return path;
    }
}
