package entities.buildings.functions;

import entities.buildings.Building;
import entities.characters.Human;
import entities.characters.personas.Villager;
import game.Engine;

public class BuildVillagerFunction implements BuildingFunction {

    Building host;

    public BuildVillagerFunction(Building b) {
        this.host = b;
    }

    public void setHost(Building b) {
        this.host = b;
    }

    public void execute() {
        Human human = new Human(host.getGX() + host.getGridSize() / 2, host.getGY() + host.getGridSize() + 1);
        human.setXY(host.getX() + (host.getGridSize() / 2) * 16, host.getY() + (host.getGridSize() + 1) * 16);
        Villager villager = new Villager(human);
        villager.setTeam(host.getTeam());
        Engine.getActiveArea().getPersonas().add(villager);
    }

    public String text() {
        return "Build Villager";
    }
}
