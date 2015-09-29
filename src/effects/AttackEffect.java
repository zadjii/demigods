package effects;

import entities.characters.Character;
import entities.characters.Character.Direction;
import entities.characters.personas.Persona;
import game.Engine;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Line;
import util.Constants;
import util.Images;

import java.util.ArrayList;

public class AttackEffect extends Effect {

	private double damage;
	private Character character;
	private transient SpriteSheet image;
	public static final int ATTACK_LIFETIME = (int)(.25 * Constants.TICKS_PER_SECOND);
	private int lifetime = 0;
	private boolean hasHit = false;
	private boolean horizontalAttack;

	public AttackEffect(double damage, Character character, int dx, int dy,
	                    SpriteSheet image, int width, int height) {
		this.damage = damage;
		this.character = character;
		this.image = image;
		this.dx = dx;
		this.dy = dy;
		Direction direction = character.getDirection();
        horizontalAttack = direction == Direction.LEFT || direction == Direction.RIGHT;
		this.setXY((character.getX() + dx), (character.getY() + dy));
		this.setWidth(width);
		this.setHeight(height);
	}

	@Override
	public void draw(float sx, float sy, Graphics g, float zoom) {
		g.setColor(new Color(1.0f,1.0f,1.0f,0.5f));
		g.fillRect((getAbsX()-sx)*zoom, (getAbsY()-sy)*zoom, getWidth()*zoom, getHeight()*zoom);
		float startX = (getAbsX()-sx)*zoom;
		float startY = (getAbsY()-sy)*zoom;
		float sixteen = 16 * zoom;
		float endX = (getAbsX()-sx + getWidth())*zoom;
		float endY = (getAbsY()-sy + getHeight())*zoom;
		if(image == null){
			g.setLineWidth(3);
			g.draw(new Line(startX, startY, endX, endY));
			g.setLineWidth(1);
			return;
		}
		if(image.equals(Images.swingAttackEffectSheet)){
			if(horizontalAttack){
				if(dx < 0){
					image.getSprite(lifetime/(ATTACK_LIFETIME/5),0).draw(startX, startY, endX - startX, endY - startY);
				}
				else if(dx > 0){
					image.getSprite(4-(lifetime/(ATTACK_LIFETIME/5)),1).draw(startX, startY, endX-startX, endY-startY);
				}
			}
			else{
				if(dy < 0){
					image.getSprite((lifetime/(ATTACK_LIFETIME/5)),2).draw(startX, startY, endX - startX, endY - startY);
				}
				else if(dy > 0){
					image.getSprite((lifetime/(ATTACK_LIFETIME/5)),3).draw(startX, startY, endX - startX, endY - startY);
				}
			}

			return;
		}
		if(image.equals(Images.stabAttackEffectSheet)){
			if(horizontalAttack){
				if(dx < 0){
					startY += (4*zoom);
					image.getSprite(lifetime/(ATTACK_LIFETIME/5),0).draw(startX + 0, startY, sixteen, sixteen);
					image.getSprite(lifetime/(ATTACK_LIFETIME/5),0).draw(startX + .5f*sixteen, startY, sixteen, sixteen);
					image.getSprite(lifetime/(ATTACK_LIFETIME/5),0).draw(startX + sixteen, startY, sixteen, sixteen);
					image.getSprite(lifetime/(ATTACK_LIFETIME/5),0).draw(startX + 1.5f*sixteen, startY, sixteen, sixteen);
					image.getSprite(lifetime/(ATTACK_LIFETIME/5),0).draw(startX + 2*sixteen, startY, sixteen, sixteen);
					image.getSprite(lifetime/(ATTACK_LIFETIME/5),0).draw(startX + 2.5f*sixteen, startY, sixteen, sixteen);
					image.getSprite(lifetime/(ATTACK_LIFETIME/5),0).draw(startX + 3.0f*sixteen, startY, sixteen, sixteen);
				}
				else if(dx > 0){
					startY += (4*zoom);
					image.getSprite(4-(lifetime/(ATTACK_LIFETIME/5)),1).draw(startX + 0, startY, sixteen, sixteen);
					image.getSprite(4-(lifetime/(ATTACK_LIFETIME/5)),1).draw(startX + .5f*sixteen, startY, sixteen, sixteen);
					image.getSprite(4-(lifetime/(ATTACK_LIFETIME/5)),1).draw(startX + sixteen, startY, sixteen, sixteen);
					image.getSprite(4-(lifetime/(ATTACK_LIFETIME/5)),1).draw(startX + 1.5f*sixteen, startY, sixteen, sixteen);
					image.getSprite(4-(lifetime/(ATTACK_LIFETIME/5)),1).draw(startX + 2*sixteen, startY, sixteen, sixteen);
					image.getSprite(4-(lifetime/(ATTACK_LIFETIME/5)),1).draw(startX + 2.5f*sixteen, startY, sixteen, sixteen);
					image.getSprite(4-(lifetime/(ATTACK_LIFETIME/5)),1).draw(startX + 3.0f*sixteen, startY, sixteen, sixteen);
				}
			}
			else{

				if(dy < 0){
					startX += (8*zoom);
					image.getSprite((lifetime/(ATTACK_LIFETIME/5)),2).draw(startX, startY + 0, sixteen, sixteen);
					image.getSprite((lifetime/(ATTACK_LIFETIME/5)),2).draw(startX, startY + 0.5f*sixteen, sixteen, sixteen);
					image.getSprite((lifetime/(ATTACK_LIFETIME/5)),2).draw(startX, startY + 1.0f*sixteen, sixteen, sixteen);
					image.getSprite((lifetime/(ATTACK_LIFETIME/5)),2).draw(startX, startY + 1.5f*sixteen, sixteen, sixteen);
					image.getSprite((lifetime/(ATTACK_LIFETIME/5)),2).draw(startX, startY + 2.0f*sixteen, sixteen, sixteen);
					image.getSprite((lifetime/(ATTACK_LIFETIME/5)),2).draw(startX, startY + 2.5f*sixteen, sixteen, sixteen);
					image.getSprite((lifetime/(ATTACK_LIFETIME/5)),2).draw(startX, startY + 3.0f*sixteen, sixteen, sixteen);
				}
				else if(dy > 0){
					startX += (-8*zoom);
					image.getSprite((lifetime/(ATTACK_LIFETIME/5)),3).draw(startX, startY + 0, sixteen, sixteen);
					image.getSprite((lifetime/(ATTACK_LIFETIME/5)),3).draw(startX, startY + 0.5f*sixteen, sixteen, sixteen);
					image.getSprite((lifetime/(ATTACK_LIFETIME/5)),3).draw(startX, startY + 1.0f*sixteen, sixteen, sixteen);
					image.getSprite((lifetime/(ATTACK_LIFETIME/5)),3).draw(startX, startY + 1.5f*sixteen, sixteen, sixteen);
					image.getSprite((lifetime/(ATTACK_LIFETIME/5)),3).draw(startX, startY + 2.0f*sixteen, sixteen, sixteen);
					image.getSprite((lifetime/(ATTACK_LIFETIME/5)),3).draw(startX, startY + 2.5f*sixteen, sixteen, sixteen);
					image.getSprite((lifetime/(ATTACK_LIFETIME/5)),3).draw(startX, startY + 3.0f*sixteen, sixteen, sixteen);
				}
			}

			return;
		}
		g.setLineWidth(3);
		g.draw(new Line(startX, startY, endX, endY));
		g.setLineWidth(1);
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public Effect tick() {
		this.setXY((character.getX() + dx), (character.getY() + dy));
		if(!hasHit){
			ArrayList<Persona> temp = new ArrayList<Persona>();

			for(Persona p : Engine.getActiveArea().getPersonas().get(this.getAbsBounds())){
				if (p.getTeam() != character.getTeam()){
					hasHit = true;
					if(Character.attack(damage, character, p.getCharacter(), true)){
						temp.add(p);
					}
				}
			}

			for(Persona p : temp){
				Engine.getActiveArea().getPersonas().remove(p);
			}
		}

		lifetime++;
		if(lifetime == ATTACK_LIFETIME)return this;
		//if(hasHit)return this;

		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
