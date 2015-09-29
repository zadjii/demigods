package entities.characters.personas;

import conditions.etc.Cooldown;
import game.Engine;
import items.Item;
import util.Constants;
import util.Maths;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 7/9/12
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class WaspAI extends AStarSimpleMob {

	private Cooldown behaviorSwitchTimer = new Cooldown(1* Constants.TICKS_PER_SECOND + (float)(Math.random() * 3* Constants.TICKS_PER_SECOND));
	private boolean wandering = true;
    public void moveAI() {

	    if (behaviorSwitchTimer.offCooldown()){
		    behaviorSwitchTimer.reset();
		    wandering = !wandering;
		    if(!wandering)this.getCharacter().setBaseSpeed(3.0f);
		    else this.getCharacter().setBaseSpeed(1.0f);
	    }

	    behaviorSwitchTimer.tick();

        if (!character.isStepingX()) {
            character.setDX((Math.random() < .5 ? -1 : 1) * character.getSpeed() / 2);
        }
        if (!character.isStepingY()) {
            character.setDY((Math.random() < .5 ? -1 : 1) * character.getSpeed() / 2);
        }
	    if(wandering){
		    switch ((int)(Math.random() * 130)) {
			    case 50:
			    case 51:
			    case 52:
			    case 53:
				    character.setDY(0);
			    case 54:
			    case 55:
			    case 56:
			    case 57:
			    case 58:
				    character.setDX(character.getDX() + .5f);
				    break;
			    case 60:
			    case 61:
			    case 62:
			    case 63:
			    case 64:
				    character.setDY(0);
			    case 65:
			    case 66:
			    case 67:
			    case 68:
				    character.setDX(character.getDX() - .5f);
				    break;
			    case 70:
			    case 71:
			    case 72:
			    case 73:
			    case 74:
				    character.setDX(0);
			    case 75:
			    case 76:
			    case 77:
			    case 78:
				    character.setDY(character.getDY() + .5f);
				    break;
			    case 80:
			    case 81:
			    case 82:
			    case 83:
			    case 84:
				    character.setDX(0);
			    case 85:
			    case 86:
			    case 87:
			    case 88:
				    character.setDY(character.getDY() - .5f);
				    break;
			    case 110:
			    case 111:
				    character.setDX(character.getDX() / 2);
				    break;
			    case 120:
			    case 121:
				    character.setDY(character.getDY() / 2);
				    break;
			    default:
				    break;
		    }
		    if (character.getDX() > character.getSpeed()) character.setDX(character.getSpeed());
		    else if (character.getDX() < -character.getSpeed()) character.setDX(-character.getSpeed());
		    if (character.getDY() > character.getSpeed()) character.setDY(character.getSpeed());
		    else if (character.getDY() < -character.getSpeed()) character.setDY(-character.getSpeed());
	    }
        else{//not wandering, "charging"
//		    System.out.println("    wasp wasnt wandering");
		    if (target == null) {
			    Persona p = Engine.getActiveArea().getPersonas().getClosest(this.getCharacter().getLoc(), false, true, this.getTeam());
			    if (p != null && Maths.dist(this, p) < 32 * 16) {
				    if (p.getTeam() != this.getTeam()) {
					    target = p;
					    this.path = AStarHunter.calculateRoute(this.character, target.getCharacter());

				    }
			    }
		    }
		    if (target != null) {
//				if (!followPath(path, this.character)) {
				    moveTowardsTarget(this.character, target.getCharacter().getXi(), target.getCharacter().getYi());
//				}
			    if(Maths.dist(this.character, target.character) < 32){
				    if(!wandering)this.behaviorSwitchTimer.reset();
				    this.wandering = true;
				    this.getCharacter().setBaseSpeed(1.0f);
			    }
		    }
	    }
        damageAdjacent();

    }

    public String toString() {
        return "WaspAI - " + character;
    }
}
