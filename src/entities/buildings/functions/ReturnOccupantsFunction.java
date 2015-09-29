package entities.buildings.functions;

import entities.buildings.Building;
import entities.buildings.OccupyableBuilding;
import game.Engine;

public class ReturnOccupantsFunction implements BuildingFunction {

    OccupyableBuilding host;

    public ReturnOccupantsFunction(OccupyableBuilding b) {
        this.host = b;
    }

    public void setHost(Building b) {
        if (b.isOccupyable()) {
            this.host = (OccupyableBuilding)b;
        }
    }

    public void execute() {
        host.returnAllOccupants();
    }

    public String text() {
        return "Return Occupants";
    }
}
