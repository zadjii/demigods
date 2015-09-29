package entities.characters.personas;

import entities.characters.Character;
import entities.characters.Character.Direction;
import game.Engine;

public class SimpleMob extends Persona {

    private static final long serialVersionUID = 1L;

    public SimpleMob(Character c) {
        character = c;
    }

    public SimpleMob() {
    }

    public void moveAI() {
        switch ((int)(Math.random() * 150)) {
            case 1:
                character.setDY(-character.getSpeed());
                character.setDX(0);
                character.setDirection(Direction.UP);
                break;
            case 2:
                character.setDY(0);
                character.setDX(-character.getSpeed());
                character.setDirection(Direction.LEFT);
                break;
            case 3:
                character.setDY(0);
                character.setDX(character.getSpeed());
                character.setDirection(Direction.RIGHT);
                break;
            case 4:
                character.setDY(character.getSpeed());
                character.setDX(0);
                character.setDirection(Direction.DOWN);
                break;
            case 5:
                character.setDY(0);
                character.setDX(0);
                break;
            default:
                break;
        }
        damageAdjacent();
    }

    public String toString() {
        return "SimpleMob - " + character;
    }
}
