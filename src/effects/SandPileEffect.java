package effects;

import conditions.etc.Cooldown;
import entities.characters.Character;
import entities.characters.personas.Persona;
import entities.particles.Particle;
import entities.tiles.Tiles;
import game.Engine;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.Constants;

import java.util.ArrayList;

public class SandPileEffect extends Effect {

	private Character caster;
	private boolean hasDetonated = false;
	private double damage;
	private Cooldown formationDelay =  new Cooldown(.5f * Constants.TICKS_PER_SECOND);

	private boolean pickedUp = false;
	private static final float NUM_PARTICLES = 48;


	public SandPileEffect(Character caster, Point tgt, double damage){
		this.w = 8;
		this.h = 8;

		this.caster = caster;
		this.formationDelay.reset();

//		float dx = (float)Math.random() * 32 + 16;
//		float dy = (float)Math.random() * 32 + 16;
//
//		dx *= (Math.random() > .5 ? -1 : 1);
//		dy *= (Math.random() > .5 ? -1 : 1);

		this.setXY(tgt.getX() - w / 2, tgt.getY() - h / 2);
		for (int i = 0; i < NUM_PARTICLES; i++) {
			Engine.add(Particle.newSandPileEffectCreation(tgt));
		}
		//setXY(caster.getX(), caster.getY());
		this.damage = damage;






	}
	public void draw(float sx, float sy, Graphics g, float zoom) {}
	public void drawBelow(float sx, float sy, Graphics g, float zoom) {

		if(formationDelay.offCooldown() && !hasDetonated){
			g.setColor(new Color(
					(int)(Math.random()*50)+100,
					((int)(Math.random()*50))+100,
					(int)(Math.random()*150)+200));
			g.setColor(Tiles.MINIMAP_COLORS[Tiles.SAND]);
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
//			g.setColor(new Color(
//					(int)(Math.random()*50)+50,
//					((int)(Math.random()*50))+50,
//					(int)(Math.random()*150)+200));
//			float radius = explosionDuration.getFractionRemaining() * MAX_RADIUS;
//
//			g.fillOval(
//					(this.getX() + this.w/2 - radius-sx  )*zoom   ,
//					(this.getY() + this.h/2 - radius-sy  )*zoom   ,
//					radius*2*zoom   ,
//					radius*2*zoom   );
		}
	}

    public Effect tick(){
	    if(pickedUp)return this;
	    if(!formationDelay.offCooldown()){
			formationDelay.tick();
		}
		else if(!hasDetonated){
			for(Persona p : Engine.getActiveArea().getPersonas().get(this.getBounds())){
				if(p.getTeam() != this.caster.getTeam() && this.getAbsBounds().contains(p.getCharacter().getLoc())){
					hasDetonated = true;
					detonate(p);
					return this;
				}
			}
		}

		return null;
	}
	private void detonate(Persona p){

//		Character.attack(this.damage, caster, p.getCharacter(), true);//FIXME: this will create an infinite loop of sand
								//FIXME (cont): but it'll be funny as hell


//		hit.add(p);
		for (int i = 0; i < NUM_PARTICLES; i++) {
			Engine.add(Particle.newSandPileEffectDestruction(this.getLoc()));
		}

	}
	public void pickup(){
		for (int i = 0; i < NUM_PARTICLES/4; i++) {
			Engine.add(Particle.newSandPileEffectDestruction(this.getLoc()));
		}
		this.pickedUp = true;
	}

}
