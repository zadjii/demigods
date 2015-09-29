package effects;

import entities.characters.personas.Persona;
import game.Engine;
import org.lwjgl.util.Point;
import util.Images;
import entities.characters.Character;
import util.Maths;

import java.util.ArrayList;

public class IcicleEffect extends RotatedProjectileEffect {

	protected int width = 1*16;
	protected int height = 1*16;
	protected int frame = 0;
	protected static final int FRAME_RATIO = 4;
	public IcicleEffect(Character caster, Point target, double damage, double speed, double range) {
		super(caster, target, damage, speed, range);
		this.speed = (float)speed;
		this.setWidth(width);
		this.setHeight(height);
		knockback = true;
		stopOnHit = true;
		image = Images.icicleEffectSheet.getSprite(0,0);

	}
	public Effect tick() {
		frame++;
		if(frame >= 2 * FRAME_RATIO)frame = 0;
		image = Images.icicleEffectSheet.getSprite(frame/3,0);
		Point initial = this.getAbsoluteLoc();
		additionalGuidanceEffects();
		this.setX(this.getX() + this.getDX());
		this.setY(this.getY() + this.getDY());
		distanceTraveled += Maths.dist(initial, this.getAbsoluteLoc());
		ArrayList<Persona> temp = new ArrayList<Persona>();


		for(Persona p : Engine.getActiveArea().getPersonas().get(this.getAbsBounds())){
			if (p.getTeam() != caster.getTeam()){
				if(!hit.contains(p)){
					hit.add(p);
					if(Character.dealWaterDamage((int)damage, caster, p.getCharacter(), knockback)){
						temp.add(p);
					}distanceTraveled = range;
				}

			}
		}

		for(Persona p : temp){
			Engine.addToBeRemoved(p);
		}

		if (distanceTraveled >= range){

			return this;
		}
		return null;
	}
	protected void additionalHitEffects(Persona tgt){

	}
	protected void additionalGuidanceEffects(){

	}
}
