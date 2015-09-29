package entities.buildings.functions;

import entities.buildings.Building;
import entities.buildings.OccupyableBuilding;
import game.Engine;

public class OccupantsFunction implements BuildingFunction {

    OccupyableBuilding host;

    public OccupantsFunction(OccupyableBuilding b) {
        this.host = b;
    }

    public void setHost(Building b) {
        if (b.isOccupyable()) {
            this.host = (OccupyableBuilding)b;
        }
    }

    public void execute() {
    }

    public String text() {
        return host.getNumOccupants() + "/" + host.getOccupants().length + " Occupants";
    }
}
