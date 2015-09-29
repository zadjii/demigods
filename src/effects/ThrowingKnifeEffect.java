package effects;

import entities.characters.Character;
import entities.characters.personas.Persona;
import entities.particles.etc.DroppedItem;
import game.Engine;
import items.weapons.ThrowingKnife;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.Maths;

import java.util.ArrayList;

public class ThrowingKnifeEffect extends Effect {

	private int speed = 7;
	private Character caster;
	private int damage;
	private static int maxRange = 200;
	private double distanceTraveled = 0;

	public ThrowingKnifeEffect(Character caster, Point target, int damage){
		int targetX = target.getX();
		int targetY = target.getY();
		this.caster = caster;
		this.setLoc(caster.getLoc());
		this.damage = damage;
		dx = (float)(-((caster.getX()-targetX) / Maths.dist(caster.getLoc(), new Point(targetX, targetY))) * speed);
		dy = (float)(-((caster.getY()-targetY) / Maths.dist(caster.getLoc(), new Point(targetX, targetY))) * speed);
		this.setWidth(8);
		this.setHeight(8);


	}

	public void draw(float sx, float sy, Graphics g, float zoom) {
		g.setColor(Color.gray);
		g.fillRect((getAbsX() - sx - 4)*zoom, (getAbsY() - sy - 4)*zoom, 8*zoom, 8*zoom);
	}

	@Override
	public Effect tick() {
		Point initial = this.getAbsoluteLoc();
		this.setX(this.getX() + this.getDX());
		this.setY(this.getY() + this.getDY());
		distanceTraveled += Maths.dist(initial, this.getAbsoluteLoc());
		ArrayList<Persona> temp = new ArrayList<Persona>();


		for(Persona p : Engine.getActiveArea().getPersonas().get(this.getAbsBounds())){
			if (p.getTeam() != caster.getTeam()){

				if(Character.attack(damage, caster, p.getCharacter(), true)){
					temp.add(p);
				}
				//e.add(new DroppedItem(new ThrowingKnife()), p.getCharacter());
				distanceTraveled = maxRange;
			}
		}

		for(Persona p : temp){
			Engine.addToBeRemoved(p);
		}

		if (distanceTraveled >= maxRange){
			DroppedItem knife = new DroppedItem(new ThrowingKnife());
			knife.setLoc(this.getAbsoluteLoc());
			Engine.getActiveArea().getDroppedItems().add(knife);
			return this;
		}
		return null;
	}
}
