package entities.characters.personas;

import entities.characters.*;
import entities.characters.Character;
import game.Engine;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 3/7/13
 * Time: 9:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class PassiveMob extends Persona{
	private static final long serialVersionUID = 1L;

	public PassiveMob(Character c) {
		character = c;
	}
	public PassiveMob() {

	}

	public void moveAI() {
		switch ((int)(Math.random() * 150)) {
			case 1:
				//character.setPreviousDirection(character.getDirection());
				character.setDY(-character.getSpeed());
				character.setDX(0);
				character.setDirection(Character.Direction.UP);
				break;
			case 2:
				//character.setPreviousDirection(character.getDirection());
				character.setDY(0);
				character.setDX(-character.getSpeed());
				character.setDirection(Character.Direction.LEFT);
				break;
			case 3:
				//character.setPreviousDirection(character.getDirection());
				character.setDY(0);
				character.setDX(character.getSpeed());
				character.setDirection(Character.Direction.RIGHT);
				break;
			case 4:
				//character.setPreviousDirection(character.getDirection());
				character.setDY(character.getSpeed());
				character.setDX(0);
				character.setDirection(Character.Direction.DOWN);
				break;
			case 5:
				//character.setPreviousDirection(character.getDirection());
				character.setDY(0);
				character.setDX(0);
				break;
			default:
				break;
		}
	}

	public String toString(){
		return "PassiveMob - " + character;
	}
}
