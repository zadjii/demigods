package entities.characters.personas;

import game.Engine;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 7/9/12
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class BatAI extends Persona {

    public void moveAI() {
        if (!character.isStepingX()) {
            character.setDX(-character.getDX());
        }
        if (!character.isStepingY()) {
            character.setDY(-character.getDY());
        }
        switch ((int)(Math.random() * 150)) {
            case 1:
                character.setDY(-character.getSpeed());
                break;
            case 2:
                character.setDX(-character.getSpeed());
                break;
            case 3:
                character.setDX(character.getSpeed());
                break;
            case 4:
                character.setDY(character.getSpeed());
                break;
            case 50:
            case 51:
            case 52:
                if (Math.abs(character.getDX()) < character.getSpeed()) character.setDX(character.getDX() + 1);
                break;
            case 60:
            case 61:
            case 62:
                if (Math.abs(character.getDX()) < character.getSpeed()) character.setDX(character.getDX() - 1);
                break;
            case 70:
            case 71:
            case 72:
                if (Math.abs(character.getDY()) < character.getSpeed()) character.setDY(character.getDY() + 1);
                break;
            case 80:
            case 81:
            case 82:
                if (Math.abs(character.getDY()) < character.getSpeed()) character.setDY(character.getDY() - 1);
                break;
            default:
                break;
        }
        damageAdjacent();
    }

    public String toString() {
        return "BatAI - " + character;
    }
}
