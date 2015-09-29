package entities.characters.etc;

import entities.characters.Character;
import entities.particles.Particle;
import game.Engine;

import static util.Constants.*;

public class Experience {

    public static void grantXP(Character dead, Character killer) {
	    //wow this is fucking retarded. no downscaling of experience gain, but benefit for higher levels.
	    //should change this to decrease when the player is a higher level.
	    //something like cos^2? WA: cos^2((x*4)/(pi)) on [0,1]
        int levelDifference = dead.getLvl() - killer.getLvl();
	    int defaultExperienceGain = (5 * dead.getLvl()) + (dead.getExp() / 100);
	    if(levelDifference > 3)levelDifference = 3;
	    else if(levelDifference < -3)levelDifference = -3;
	    double modifier = Math.exp(levelDifference/2);
//        if (levelDifference < 1) levelDifference = 1;
//        killer.addXP((int)(dead.getExp() * (.1 * levelDifference)) + (10 * dead.getLvl()));
	    int amount = (int)(modifier * defaultExperienceGain);
	    if(amount < 1)amount = 1;
	    killer.addXP(amount);
	    Engine.add(Particle.newXP(dead, killer, amount));
    }
}
