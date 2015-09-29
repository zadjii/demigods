package entities.characters.aiUtil;

import game.Demigods;
import org.lwjgl.util.Point;
import util.Maths;

public class AStarNode implements Comparable {

    protected Point coordinates;
    protected float distanceFromTarget;
    protected AStarNode parent;
    protected float costSoFar;

    public Point getCoordinates() {
        return coordinates;
    }

    public float getDistanceFromTarget() {
        return distanceFromTarget;
    }

    public AStarNode getParent() {
        return parent;
    }

    public float getCostSoFar() {
        return costSoFar;
    }

    public void setCostSoFar(float costSoFar) {
        this.costSoFar = costSoFar;
    }

    public void setParent(AStarNode parent) {
        this.parent = parent;
    }

    public AStarNode(Point coordinates, Point target, AStarNode parent, float traversalCost) {
        this.coordinates = coordinates;
        this.distanceFromTarget = (float)Maths.dist(coordinates, target);
        this.parent = parent;
        if (parent != null) {
            this.costSoFar = parent.costSoFar + traversalCost;
        } else this.costSoFar = 0;
    }

    public int getX() {
        return this.coordinates.getX();
    }

    public int getY() {
        return this.coordinates.getY();
    }

    private static final double distanceWeightModifier = 1.1;

    public int compareTo(Object o) {
        if (!(o instanceof AStarNode))
            throw new IllegalArgumentException("Cannot compare an AStarNode to a " + o.getClass().getName());
        else {
            AStarNode other = (AStarNode)o;
            if (other.costSoFar + (other.distanceFromTarget) * distanceWeightModifier > this.costSoFar + (this.distanceFromTarget) * distanceWeightModifier)
                return -1;
            if (other.costSoFar + (other.distanceFromTarget) * distanceWeightModifier < this.costSoFar + (this.distanceFromTarget) * distanceWeightModifier)
                return 1;
        }
        return 0;
    }

    public boolean equals(Object o) {
        if (!(o instanceof AStarNode)) return false;
        AStarNode other = (AStarNode)o;
        return other.coordinates.equals(this.coordinates);
    }

    public String toString() {
        return "A*Node(" + Demigods.getLoc(coordinates) + ")";
    }
}
