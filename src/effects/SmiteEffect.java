package effects;

import conditions.etc.Cooldown;
import entities.characters.personas.Persona;
import entities.characters.Character;
import entities.particles.Particle;
import game.Engine;
import org.lwjgl.util.Point;
import org.newdawn.slick.Graphics;
import util.Constants;
import util.Images;
import util.Maths;

import java.util.ArrayList;

public class SmiteEffect extends Effect {

	private Character caster;
	private Persona tgt;
	private double damage;
	private Cooldown delay = new Cooldown(.3f * Constants.TICKS_PER_SECOND);
	private boolean hasReset = false;



	ArrayList<Particle> particles = new ArrayList<Particle>();

	int type = 0;

	ArrayList<Persona> hit = new ArrayList<Persona>();


	public SmiteEffect(Character caster, Persona tgt, double damage){
		this.caster = caster;
		this.tgt = tgt;
		this.setLoc(caster.getLoc());
		//setXY(caster.getX(), caster.getY());
		this.damage = damage;
		delay.reset();

		for(float theta = -180.0f; theta < 180.0f; theta += 3.0f){
			double x = Math.cos(Math.toRadians(theta)) * 16 * 3;
			double y = Math.sin(Math.toRadians(theta)) * 16 * 3;

			x += tgt.getCharacter().getX();
			y += tgt.getCharacter().getY();

			particles.add(Particle.newSmiteParticle(new Point((int)x,(int)y), tgt.getCharacter()));


		}
		this.setLoc(tgt.getCharacter().getLoc());


	}

	public void draw(float sY, float sy, Graphics g, float zoom) {
		for (Particle p : particles){
			p.draw(sY, sy, g, zoom);
		}

	}

    public Effect tick(){
		if(tgt == null || tgt.getCharacter() == null || tgt.getCharacter().getHP()<0)return this;
		delay.tick();

		//we're using the effect's x,y to track where the target was last tick

		float dx = tgt.getCharacter().getX() - this.getX();
		float dy = tgt.getCharacter().getY() - this.getY();
		for (Particle p : particles){
			p.setX(p.getX() + dx);
			p.setY(p.getY() + dy);
			//p.translate(dx, dy);

			//float dx = (float)(-((p.getX()-tgt.getCharacter().getX()) / Maths.dist(p.getLoc(), tgt.getCharacter().getLoc())));
			//float dy = (float)(-((p.getY()-tgt.getCharacter().getY()) / Maths.dist(p.getLoc(), tgt.getCharacter().getLoc())));
//
			//p.setDX(dx);
			//p.setDY(dy);
			p.tick();
		}
		this.setLoc(tgt.getCharacter().getLoc());
		if(delay.offCooldown() && !hasReset){

			//TODO: make deal true damage
			Character.dealTrueDamage(damage, caster, tgt.getCharacter(), false);
			if(tgt.getCharacter().getHP() < 0)
                Engine.addToBeRemoved(tgt);
			delay = new Cooldown(0.2f * Constants.TICKS_PER_SECOND);
			delay.reset();
			hasReset = true;
		}else if(delay.offCooldown() && hasReset)return this;
		return null;
	}

}
