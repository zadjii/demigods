package entities.buildings.functions;

import entities.buildings.Building;
import game.Engine;

public class DisplayResourcesFunction implements BuildingFunction {

    Building host;

    public DisplayResourcesFunction(Building b) {
        this.host = b;
    }

    public void setHost(Building b) {
        if (b.isOccupyable()) {
            this.host = b;
        }
    }

    public void execute() {
    }

    public String text() {
        return host.getStorage().toString();
    }
}
