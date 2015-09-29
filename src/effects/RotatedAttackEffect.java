package effects;

import entities.characters.Character;
import entities.characters.personas.Persona;
import game.Engine;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Line;
import util.Constants;
import util.Images;
import util.OnHitListener;

import java.util.ArrayList;

public class RotatedAttackEffect extends RotatedProjectileEffect {

	//private double damage;
	private Character character;
	private transient SpriteSheet image;
	public static final int ATTACK_LIFETIME = (int)(.25 * Constants.TICKS_PER_SECOND);
	private int lifetime = 0;
	private boolean hasHit = false;
	private boolean horizontalAttack;
	private int type = 0;

	private Point startPoint;
	private Point endPoint;

	public static final int PUNCH_ATTACK = -1;
	public static final int DEFAULT_ATTACK = 0;
	public static final int GREATSWORD_ATTACK = 1;
	public static final int SPEAR_ATTACK = 2;
	public static final int POLEARM_ATTACK = 3;

	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;

	private int offsetX, offsetY;

	private OnHitListener onHitListener;

	public RotatedAttackEffect(Character caster, Point target, double damage, double speed, double range, int type) {
		super(caster, target, damage, 0, range);
		this.character = caster;
		this.type = type;
		this.setDX(0);
		this.setDY(0);
		if(type == DEFAULT_ATTACK
				||type == GREATSWORD_ATTACK
				||type == POLEARM_ATTACK){

			this.image = Images.swingAttackEffectSheet;
		}
		if(type == SPEAR_ATTACK
				||type == PUNCH_ATTACK)this.image = Images.stabAttackEffectSheet;

		float x;
		float y;

		FIND_LOCATION:{
			float radius = 16;

			if(type == PUNCH_ATTACK)radius*=1.5f;
			if(type == DEFAULT_ATTACK)radius*=2.0f;
			if(type == GREATSWORD_ATTACK)radius*=2.5f;
			if(type == SPEAR_ATTACK)radius*=3.0f;
			if(type == POLEARM_ATTACK)radius*=4.0f;

			x = (float)(radius * Math.cos(Math.toRadians(this.angle)));
			y = (float)(radius * Math.sin(Math.toRadians(this.angle)));

//			if(x%16<=6.60)x+=16;
//			if(y%16<=6.60)y+=16;

			//int gx = (int)(x/16);
			//int gy = (int)(y/16);
//
			//x = gx * 16;
			//y = gy * 16;
			offsetX = (int)x;
			offsetY = (int)y;

			if(type == SPEAR_ATTACK
					||type == PUNCH_ATTACK){
				startPoint = new Point(character.getXi(), character.getYi());
				endPoint = new Point(character.getXi() + (int)(offsetX+x), character.getYi() + (int)(offsetY+y));
			}

			x += character.getX();
			y += character.getY();


		}

		this.setXY(x, y);
//		System.out.println(
//				"x,y: " + x + ", " + y + "    angle:" + angle
//		);
		if(type == PUNCH_ATTACK){
			this.setWidthHeight(32,32);
		}
		if(type == DEFAULT_ATTACK){
			this.setWidthHeight(32,32);
		}
		if(type == GREATSWORD_ATTACK){
			this.setWidthHeight(48,48);
		}
		if(type == SPEAR_ATTACK){
			this.setWidthHeight(96,96);//Yes, this is supposed to be larger

		}
		if(type == POLEARM_ATTACK){
			this.setWidthHeight(32,32);
		}
	}


	@Override
	public void draw(float sx, float sy, Graphics g, float zoom) {
		g.setColor(new Color(1.0f,1.0f,1.0f,0.5f));
		//g.fillRect((getAbsX()-(getWidth()/2)-sx)*zoom, (getAbsY()-(getHeight()/2)-sy)*zoom, getWidth()*zoom, getHeight()*zoom);

		if(type == DEFAULT_ATTACK
				||type == GREATSWORD_ATTACK
				||type == POLEARM_ATTACK){
			drawImage(image.getSprite(4-(lifetime/(ATTACK_LIFETIME/5)),1), sx, sy, g, zoom);
		}else if(type == SPEAR_ATTACK
				|| type == PUNCH_ATTACK){

			drawImage(image.getSprite(4-(lifetime/(ATTACK_LIFETIME/5)),1), 96, 16, sx, sy, g, zoom);
		}

	}

	@Override
	public Effect tick() {

		this.setXY((character.getX() + offsetX), (character.getY() + offsetY));
		startPoint = new Point(character.getXi(), character.getYi());
		endPoint = new Point(character.getXi() + (int)(offsetX*2), character.getYi() + (int)(offsetY*2));


		if(!hasHit){


			ArrayList<Persona> temp = new ArrayList<Persona>();

			//two cases, one for spear attacks, and one for non-spear attacks
			//non-spear attacks will use normal bounds checking
			//spear attacks will have a first bounds check, getting all the rectangle from their start to finish
				//then another check on all the intersected personas to see if they cross
				//the line of the spear.
			Rectangle hitbox = new Rectangle(
					(int)(this.getAbsX() - getWidth()/2),
					(int)(this.getAbsY() - getHeight()/2),
					(int)getWidth(),
					(int)getHeight());
			for(Persona p : Engine.getActiveArea().getPersonas().get(hitbox)){
				if (p.getTeam() != character.getTeam()){
					if(type == SPEAR_ATTACK
							|| type == PUNCH_ATTACK){
						Line line = new Line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
						org.newdawn.slick.geom.Rectangle characterHitbox =
								new org.newdawn.slick.geom.Rectangle(
										p.getCharacter().getAbsHitbox().getX(),
										p.getCharacter().getAbsHitbox().getY(),
										p.getCharacter().getAbsHitbox().getWidth(),
										p.getCharacter().getAbsHitbox().getHeight()
								);
						if(line.intersects(characterHitbox)){
							hasHit = true;
							if(Character.attack(damage, character, p.getCharacter(), true)){//returns true if killed
								temp.add(p);
							}
							if(onHitListener!=null)onHitListener.onHit(character, p.getCharacter());
						}
					}
					else{
						hasHit = true;
						if(Character.attack(damage, character, p.getCharacter(), true)){
							temp.add(p);
						}
						if(onHitListener!=null)onHitListener.onHit(character, p.getCharacter());
					}

				}
			}

			for(Persona p : temp){
				Engine.addToBeRemoved(p);
			}
		}

		lifetime++;
		if(lifetime == ATTACK_LIFETIME)return this;
		//if(hasHit)return this;

		return null;
	}
	public void setOnHitListener(OnHitListener listener){
		this.onHitListener = listener;
	}
}
