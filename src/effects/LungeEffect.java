package effects;

import entities.characters.Character;
import entities.characters.personas.Persona;
import game.Engine;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import util.Images;

import java.util.ArrayList;


public class LungeEffect extends Effect {

	private ArrayList<LungeStep> points = new ArrayList<LungeStep>();
	ArrayList<Persona> hit = new ArrayList<Persona>();

	private double damage = 0;
	private int lifetime = 0;
	private int life = 0;
	private double angle = 0;
	private Character caster;

	public LungeEffect(Character caster, Point target, double damage, float dx, float dy, int lifetime){
		int targetX = target.getX();
		int targetY = target.getY();
		this.caster = caster;
		this.setLoc(caster.getLoc());
		float thisX = this.getAbsX();
		float thisY = this.getAbsY();
		this.damage = damage;

		//double theta = (targetY - thisY)/(targetX - thisX);
		double distX = targetX - thisX;
		double distY = targetY - thisY;
//		double distX = thisX - targetX;
//		double distY = thisY - targetY;
		this.angle = Math.toDegrees(Math.atan2(distY, distX));

		this.dx = dx;
		this.dy = dy;
		this.lifetime = lifetime;

		this.setWidth(caster.getWidth());
		this.setHeight(caster.getHeight());

	}

	public void draw(float sx, float sy, Graphics g, float zoom) {
		for(LungeStep step : points){
			step.draw(sx, sy, g, zoom);
		}
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public Effect tick() {
		life++;
		ArrayList<LungeStep> tempSteps = new ArrayList<LungeStep>();
		ArrayList<Persona> temp = new ArrayList<Persona>();
		for(LungeStep step : points){
			if(step.tick() != null)tempSteps.add(step);
		}
		for (LungeStep step : tempSteps){
			points.remove(step);
		}


		this.setX(this.getX() + this.getDX());
		this.setY(this.getY() + this.getDY());

		if(life < lifetime){

			if(life % 2 == 0)points.add(new LungeStep(this.getAbsoluteLoc(), angle));


			for(Persona p : Engine.getActiveArea().getPersonas().get(caster.getAbsBounds())){
				if(!hit.contains(p))
				if (p.getTeam() != caster.getTeam()){
					if(Character.attack(damage, caster, p.getCharacter(), true)){
						//e.removePerson(p);
						temp.add(p);
					}
					hit.add(p);
					//distanceTraveled = range;
				}
			}
			for(Persona p : temp){
				if(hit.contains(p))hit.remove(p);
				Engine.addToBeRemoved(p);
			}
		}
		if(life >= lifetime && points.size() == 0){
			return this;
		}
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
class LungeStep extends Effect {

	static int TOTAL_STEPS = 5;
	static int FRAMES_PER_STEP = 2;
	int step = 0;
	double angle = 0;
	static Image image = Images.swingAttackEffectSheet.getSprite(1, 1);

	public LungeStep(Point absLoc, double angle){
		this.angle = angle;
		this.setLoc(absLoc);
		this.setWidth(32);
		this.setHeight(32);
	}

	public void draw(float sx, float sy, Graphics g, float zoom) {
		g.rotate((getAbsX() - sx)*zoom, (getAbsY() - sy)*zoom, (float)angle);

		image.draw(
				(getAbsX() - sx - (getWidth()/2))*zoom,
				(getAbsY() - sy - (getHeight()/2))*zoom,
				(getWidth())*zoom,
				(getHeight())*zoom,
				Color.darkGray
		);
		//else
		//image.draw((getAbsX() - sx - (getWidth()/2))*zoom, (getAbsY() - sy - getHeight()/2)*zoom, (getWidth())*zoom, (getHeight())*zoom);
		g.rotate((getAbsX() - sx)*zoom, (getAbsY() - sy)*zoom, (float)-angle);	}

	@Override
	public Effect tick() {
		this.step++;
		if( step == (TOTAL_STEPS * FRAMES_PER_STEP ) )return this;
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}