package entities.characters.personas;

import conditions.Adrenaline;
import conditions.etc.Cooldown;
import entities.characters.Character;
import game.Engine;
import items.Item;
import org.lwjgl.util.Point;
import skills.Lunge;
import skills.Skill;
import skills.Smite;
import util.Constants;
import util.Maths;

import java.util.ArrayList;

/**
 * The Zealot Champion.
 * (the prototype enemy champion, so bear with us)
 *
 * Should be relavtively high level boss. like 40+.
 *     //yay testing without being able to test up until that point :/
 *
 * Skills:
 * Attacking!
 * 0 Lunge (TODO: replace with LeapStrike)
 * TODO: 1 A Channeled heal skill
 * TODO: 2 Purging Strike
 * TODO: 3 Zealotry
 * Uses Adrenaline
 * TODO: 4 Reckless Swing? - (more damage w/ more adrenaline, expends more HP w/ adrenaline)
 * 5 Smite
 * TODO: 6 Cleave
 * Todo: 7 Crippling Strike
 * General skill usage:
 *
 * He can stack up a fair amount of conditions on a person...
 *
 *
 *
 *
 */
public class ZealotChampionAI extends SimpleMob {
	Persona target = null;
	ArrayList<Point> path = new ArrayList<Point>();
	private Cooldown skillUseTimer = new Cooldown(15.0f * Constants.TICKS_PER_SECOND);
	private Cooldown attackTimeout = new Cooldown(1.5f * Constants.TICKS_PER_SECOND);
	private boolean hasBeenInRange = false;
	Adrenaline adrenaline;

	public ZealotChampionAI(Character c) {
		character = c;
		adrenaline = new Adrenaline(c, c, 0);
		character.addCondition(adrenaline);

		character.getSkills().add(0, new Lunge());
		character.getSkills().add(1, new Lunge());
		character.getSkills().add(2, new Lunge());
		character.getSkills().add(3, new Lunge());
		character.getSkills().add(4, new Lunge());
		character.getSkills().add(5, new Smite());

		skillUseTimer.reset();
		attackTimeout.reset();
	}
	private static final double findTargetChance = .00833;
	//GOD DAMMIT JAKE
	public void moveAI() {
		skillUseTimer.tick();
		attackTimeout.tick();
		for(Skill skill : this.character.getSkills())
				skill.tick();
//		System.out.println(skillUseTimer.getRemaining());
		if(target == null){
			findTarget();
//			System.out.println("ZealotChampAI: sought target:" + target);
		}
		else{
//			System.out.println("ZealotChampAI: have target:" + target);
			if(Math.random() < 2*findTargetChance){
				this.path = AStarHunter.calculateRoute(this.character, target.getCharacter());
			}
		}


		if(target != null){
			if( target.getCharacter().getHP() <= 0){
				target = null;
				path = null;
				return;
			}

			boolean moveTowardsTarget = true;
			if(this.character.getAnimation() != null){
				moveTowardsTarget = false;
//				this.character.setDX(0);
//				this.character.setDY(0);
			}

			/*TODO: Determine if we should heal up,
			  TODO:      Activate zealotry
			*/
			if(Maths.dist(this, target) <= 4*16 && attackTimeout.offCooldown()){
				System.out.println("Zealot: ATTACK");
				//If super close, attack
				moveTowardsTarget = false;
				//TODO: Decide to purging strike or others
				Item equippedItem = character.getEquippedItem();
				if(equippedItem != null){
					equippedItem.attack(
							target.getCharacter().
									getLoc());

				}
				attackTimeout.reset();
				hasBeenInRange = true;
			}
			else if (Maths.dist(this, target) <= 9*16){
				System.out.println("Zealot: YOU CANNOT ESCAPE");
				//kinda close, lunge and see what happens
				if(this.character.getAnimation() == null && skillUseTimer.offCooldown()){
					character.getSkills().get(0).use(character, target.getCharacter().getLoc());
					skillUseTimer.reset();
					moveTowardsTarget = false;
				}
			}
			else if (Maths.dist(this, target) <= 13*16){
				System.out.println("Zealot: YOU WILL SEE THE LIGHT");
				//pretty far away, smite the fucker
				if(this.character.getAnimation() == null && skillUseTimer.offCooldown() && hasBeenInRange){
					character.getSkills().get(5).use(character, target.getCharacter().getLoc());
					skillUseTimer.reset();
					moveTowardsTarget = false;
					hasBeenInRange = false;
				}
			}
			else if(Maths.dist(this, target) >= 17*16){
				System.out.println("Zealot: OPEN YOUR EYES!");
				moveTowardsTarget = false;
				path = null;
				target = null;
				hasBeenInRange = false;
			}

			if(moveTowardsTarget){
				if(!followPath(path, this.character)){
					moveTowardsTarget(this.character, target.getCharacter().getXi(), target.getCharacter().getYi());
				}
			}


		}
		else{
			super.moveAI();
		}
		//damageAdjacent();
	}
	private void findTarget(){
		if(Math.random() < findTargetChance){
//			System.out.println("ZChampAI:-----HUNTING-----");
			System.out.println(this.team);
			Persona p =
					Engine.getActiveArea().getPersonas().getClosest(
					this.getCharacter().getLoc(), false, true, this.getTeam()
			);

			if(p != null && Maths.dist(this, p) < 15*16){
				if(p.getTeam() != this.getTeam()){
					target = p;
					this.path =
							AStarHunter.calculateRoute(this.character, target.getCharacter());
					return;
				}
			}

			target = null;
			if(path != null){
				path.clear();
			}
		}
//		else System.out.println("ZealotChampionAI: chance too low");
	}
	public String toString(){
		return "ZealotChampionAI - " + character;
	}

}
