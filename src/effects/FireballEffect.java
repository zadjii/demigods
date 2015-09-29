package effects;

import conditions.Burning;
import entities.characters.personas.Persona;
import entities.characters.Character;
import game.Engine;
import entities.particles.Particle;
import areas.NewGameArea;
import org.lwjgl.util.Point;
import org.newdawn.slick.Graphics;
import util.Constants;
import util.Images;
import util.Maths;

import java.util.ArrayList;

public class FireballEffect extends Effect {

	private int frameNum = 0;
	private int frameRatio = 4;
	private int speed = 5;
	private Character caster;
	private int damage;
	private int lifetime = -1;



	int type = 0;
	//type 0: just fireballs
	//type 1: fireballs that pass through the target, no knockback.
	//type 2: large fireballs?
	//type 3: no damage, burns for a duration = damage

	ArrayList<Persona> hit = new ArrayList<Persona>();


	public FireballEffect(Character caster, int targetX, int targetY, int damage, int type, int lifetime){
		this.caster = caster;

		this.setLoc(caster.getLoc());
		this.damage = damage;
		dx = (float)(-((caster.getX()-targetX) / Maths.dist(caster.getLoc(), new Point(targetX, targetY))) * speed);
		dy = (float)(-((caster.getY()-targetY) / Maths.dist(caster.getLoc(), new Point(targetX, targetY))) * speed);
		this.lifetime = lifetime;
		this.type = type;
		if(type != 2){
			setWidth(16);
			setHeight(16);
		}
		//Incindiary fireballs
		if(this.type == 3){

		}

	}

	public FireballEffect(Character caster,int srcX, int srcY, float dx, float dy, int damage, int type, int lifetime){
		this.caster = caster;

		this.setXY(srcX, srcY);
		this.damage = damage;
		this.dx = dx;
		this.dy = dy;
		this.lifetime = lifetime;
		this.type = type;
		if(type != 2){
			setWidth(16);
			setHeight(16);
		}
		//Incindiary fireballs
		if(this.type == 3){

		}

	}

	public void draw(float sx, float sy, Graphics g, float zoom) {
		Images.fireballEffectSheet.getSprite(frameNum/frameRatio,0 ).draw(
				(getAbsX() - sx)*zoom, (getAbsY() - sy)*zoom, 16*zoom, 16*zoom);
		//Images.fireballEffectSheet.getSprite(frameNum/frameRatio,0 ).draw(getX() - sx - getDX(), getY() - sy - getDY(), new Color(1f, 1f, 0f, .5f));

		++frameNum;
		if(frameNum == (4*frameRatio))frameNum = 0;
	}
	public Effect tick(){

		this.setX(this.getX() + this.getDX());
		this.setY(this.getY() + this.getDY());


		ArrayList<Persona> temp = new ArrayList<Persona>();

		NewGameArea area = Engine.getActiveArea();

		for(Persona p : area.getPersonas().get(this.getAbsBounds())){
			if (p.getTeam() != caster.getTeam()){
				//if(p.getCharacter().getAbsHitbox().intersects(this.getAbsBounds())){
					if(type==0){//Normal, impactable Fireball
						if(Character.dealFireDamage(damage, caster, p.getCharacter(), true)){
							temp.add(p);
						}
						splash();
						lifetime = 1;
					}
					else if(type == 1){//flamethrower piercing fireball
						TYPE_1:{
						for(Persona anotherPersona : hit){
							if(anotherPersona.equals(p)){
								break TYPE_1;
							}
						}
						if(Character.dealFireDamage(damage, caster, p.getCharacter(), false)){
							temp.add(p);
						}
						hit.add(p);
						}
					}
					else if (type == 3){//burning fireball
						TYPE_3:{
							for(Persona anotherPersona : hit){
								if(anotherPersona.equals(p)){
									break TYPE_3;
								}
							}
							Character tgt = p.getCharacter();
							tgt.addCondition(new Burning(caster, tgt, damage * Constants.TICKS_PER_SECOND));
							hit.add(p);
						}
//						lifetime = 1;
					}
					//remove the fireball here
				//}
			}
		}

		for(Persona p : temp){
			Engine.addToBeRemoved(p);
		}
		lifetime--;
		if(this.hitImpassable()){
			splash();
			return this;
		}
		if (lifetime == 0)return this;
		return null;
	}
	private void splash(){
		for(int i = 0; i < 8; i++){
			Engine.add(Particle.newFireEffect(this));
		}
	}
}
