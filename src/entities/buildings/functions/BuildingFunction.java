package entities.buildings.functions;
import entities.buildings.Building;
import game.Engine;

import java.io.Serializable;

public interface BuildingFunction extends Serializable {
	//TODO: Remove these too
	public void setHost(Building b);
	public void execute();
	public String text();
}
