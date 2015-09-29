package effects;

import conditions.etc.Cooldown;
import entities.characters.Character;
import entities.characters.personas.Persona;
import entities.particles.Particle;
import game.Engine;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.Constants;

import java.util.ArrayList;

public class EnergyRuneEffect extends Effect {

	private Character caster;
	private boolean hasDetonated = false;
	private double damage;
	private Cooldown explosionDuration = new Cooldown(.5f * Constants.TICKS_PER_SECOND);

	private static final float MAX_RADIUS = 48;


	ArrayList<Particle> particles = new ArrayList<Particle>();

	int type = 0;

	ArrayList<Persona> hit = new ArrayList<Persona>();


	public EnergyRuneEffect(Character caster, Point tgt, double damage){
		this.w = 32;
		this.h = 32;

		this.caster = caster;

		this.setXY(tgt.getX() - w / 2, tgt.getY() - h / 2);
		//setXY(caster.getX(), caster.getY());
		this.damage = damage;
		explosionDuration.reset();




	}
	public void draw(float sx, float sy, Graphics g, float zoom) {}
	public void drawBelow(float sx, float sy, Graphics g, float zoom) {
		for (Particle p : particles){
			p.draw(sx, sy, g, zoom);
		}
		if(!hasDetonated){
			g.setColor(new Color(
					(int)(Math.random()*50)+100,
					((int)(Math.random()*50))+100,
					(int)(Math.random()*150)+200));
			g.setLineWidth(3.0f);
			g.drawOval(
					(this.getX()-sx  )*zoom   ,
					(this.getY()-sy  )*zoom   ,
					(this.getWidth() )*zoom   ,
					(this.getHeight())*zoom   );
			g.drawOval(//center
					(this.getX()+w/4-sx  )*zoom   ,
					(this.getY()+h/4-sy  )*zoom   ,
					(this.getWidth()/2 )*zoom   ,
					(this.getHeight()/2)*zoom   );
			g.drawOval(//left
					(this.getX()-w/4-sx  )*zoom   ,
					(this.getY()+h/4-sy  )*zoom   ,
					(this.getWidth()/2 )*zoom   ,
					(this.getHeight()/2)*zoom   );
			g.drawOval(//right
					(this.getX()+(3*w/4)-sx  )*zoom   ,
					(this.getY()+h/4-sy  )*zoom   ,
					(this.getWidth()/2 )*zoom   ,
					(this.getHeight()/2)*zoom   );
			g.drawOval(//bot
					(this.getX()+w/4-sx  )*zoom   ,
					(this.getY()+(3*h/4)-sy  )*zoom   ,
					(this.getWidth()/2 )*zoom   ,
					(this.getHeight()/2)*zoom   );
			g.drawOval(//top
					(this.getX()+w/4-sx  )*zoom   ,
					(this.getY()-(h/4)-sy  )*zoom   ,
					(this.getWidth()/2 )*zoom   ,
					(this.getHeight()/2)*zoom   );
			g.setLineWidth(1.0f);
		}
		else{
			g.setColor(new Color(
					(int)(Math.random()*50)+50,
					((int)(Math.random()*50))+50,
					(int)(Math.random()*150)+200));
			float radius = explosionDuration.getFractionRemaining() * MAX_RADIUS;

			g.fillOval(
					(this.getX() + this.w/2 - radius-sx  )*zoom   ,
					(this.getY() + this.h/2 - radius-sy  )*zoom   ,
					radius*2*zoom   ,
					radius*2*zoom   );
		}
	}

    public Effect tick(){

		if(!hasDetonated){
			for(Persona p : Engine.getActiveArea().getPersonas().get(this.getBounds())){
				if(p.getTeam() != this.caster.getTeam()){
					hasDetonated = true;
					break;
				}
			}
		}
	    else{
			explosionDuration.tick();
			float radius = explosionDuration.getFractionRemaining() * MAX_RADIUS;
			Rectangle hitBounds = new Rectangle(
					(int)(this.getX() + this.w/2 - radius),
					(int)(this.getY() + this.h/2 - radius),
					(int)(radius*2),
					(int)(radius*2)
			);
			for(Persona p : Engine.getActiveArea().getPersonas().get(hitBounds)){
				if(p.getTeam() != this.caster.getTeam()){
					if(!hit.contains(p)){
						for (int i = 0; i < 12; i++) {
							Engine.add(Particle.newEnergyEffectHit(this));
						}
						Character.attack(this.damage, caster, p.getCharacter(), true);
						caster.heal(damage/2);
						if(p.getCharacter().getMP() > 5){p.getCharacter().drainMana(5);caster.chargeMana(5);}
						else{
							int mana = p.getCharacter().getMP();
							p.getCharacter().drainMana(mana);
							caster.chargeMana(mana);
						}
						hit.add(p);
					}
				}
			}
			if(explosionDuration.offCooldown())return this;
		}



		return null;
	}

}
