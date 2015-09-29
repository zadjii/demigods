package entities.buildings.functions;

import entities.buildings.Building;
import entities.buildings.OccupyableBuilding;
import game.Engine;

public class AddOccupantFunction implements BuildingFunction {

    OccupyableBuilding host;

    public AddOccupantFunction(OccupyableBuilding b) {
        this.host = b;
    }

    public void setHost(Building b) {
        if (b.isOccupyable()) {
            OccupyableBuilding building = (OccupyableBuilding)b;
            this.host = building;
        }
    }

    public void execute() {
    }

    public String text() {
        return "Add Occupant";
    }
}
