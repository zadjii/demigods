package entities.buildings;

import entities.buildings.functions.AddOccupantFunction;
import entities.buildings.functions.OccupantsFunction;
import entities.buildings.functions.ReturnOccupantsFunction;
import entities.characters.personas.Persona;
import game.Engine;

public class OccupyableBuilding extends Building {

    private static final long serialVersionUID = 1L;

    protected Persona[] occupants;
    private short nextSpot = 0;

    public Persona[] getOccupants() {
        return occupants;
    }

    public OccupyableBuilding(int x, int y) {
        super(x, y);
        isOccupyable = true;
        functions.clear();
        functions.add(new OccupantsFunction(this));
        functions.add(new ReturnOccupantsFunction(this));
        functions.add(new AddOccupantFunction(this));
    }

    public boolean add(Persona p) {
        if (this.isOpen()) {
            occupants[nextSpot] = p;
            ++nextSpot;
            return true;
        }
        return false;
    }

    public boolean isOpen() {
        return nextSpot != occupants.length;
    }

    public void returnAllOccupants() {
        for (Persona p : occupants) {
            if (p != null) {
                Engine.getActiveArea().getPersonas().add(p);
            }
        }
        nextSpot = 0;
        for (int i = 0; i < occupants.length; ++i) {
            occupants[i] = null;
        }
    }

    public int getFreeSpots() {
        return occupants.length - nextSpot;
    }

    public int getNumOccupants() {
        return nextSpot;
    }
}
