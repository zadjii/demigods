package entities.characters.personas;

import entities.characters.Character;
import game.Engine;
import org.lwjgl.util.Point;
import skills.Skill;
import util.Maths;

import java.util.ArrayList;

/**
 * The Simple Caster will try and get within 7 tiles
 * of the target, and then try firing off it's spell,
 * regardless of what the spell is. It will be optimal
 * for a simple projectile spell, such as Fireball, or
 * possibly IceSpike.
 */
public class SimpleCasterAI extends SimpleMob {
	Persona target = null;
	ArrayList<Point> path = new ArrayList<Point>();


	public SimpleCasterAI(Character c) {
		character = c;
	}
	public SimpleCasterAI() {

	}
	private static final double findTargetChance = .00833;
	public void moveAI() {

		if(target == null){
			findTarget();
		}
		else{
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
			//followPath will try and follow the path, and if there is no path, then it just moves the
			//hunter towards the target.
			if(Maths.dist(this, target) >= 5*16)
			if(!followPath(path, this.character)){
				moveTowardsTarget(this.character, target.getCharacter().getXi(), target.getCharacter().getYi());
			}


			else{
				if(Maths.dist(this, target) <= 10*16){
//					Item equippedItem = character.getEquippedItem();
//					if(equippedItem != null){
//						equippedItem.attack(
//								target.getCharacter().
//										getAbsoluteLoc());
//					}
					Skill skill = character.getSkills().get(0);

					this.path = null;
					if(skill != null && skill.offCooldown()){
						skill.use(character, target.getCharacter().getLoc());
						if(!skill.offCooldown())this.target = null;
					}

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
			//System.out.println("-----HUNTING-----");
			Persona p =
					Engine.getActiveArea().getPersonas().getClosest(
					this.getCharacter().getLoc(), false, true, this.getTeam()
			);

			if(p != null && Maths.dist(this, p) < 20*16){
				if(p.getTeam() != this.getTeam()){
					target = p;
					this.path =
							AStarHunter.calculateRoute(
									this.character, target.getCharacter()
							);
					return;
				}
			}

			target = null;
			if(path != null){
				path.clear();
			}
		}
	}
	public String toString(){
		return "SimpleCaster - " + character;
	}

}
