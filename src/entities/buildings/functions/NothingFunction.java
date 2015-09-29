package entities.buildings.functions;

import entities.buildings.Building;
import game.Engine;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 5/20/12
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class NothingFunction implements BuildingFunction {
    Building host;

    public NothingFunction(Building b) {
        this.host = b;
    }

    public void setHost(Building b) {
        this.host = b;
    }

    public void execute() {
    }

    public String text() {
        return "-no functions-";
    }
}
