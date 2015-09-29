package effects;

import entities.characters.Character;
import entities.characters.personas.Persona;
import entities.particles.Particle;
import entities.tiles.Tiles;
import game.Engine;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Graphics;
import util.EndOfPathListener;
import util.Images;

public class SandPillarEffect extends RotatedProjectileEffect {

	protected int frame = 0;
	protected static final int FRAME_RATIO = 4;

	protected float size = 8;
	protected float originalRange;



	public SandPillarEffect(Character caster, Point target, double damage, double speed, double range) {
		super(caster, target, damage, speed, range);
		this.speed = (float)speed;
		this.setWidth(size);
		this.setHeight(size);
		knockback = false;
		stopOnHit = false;
		image = Images.sparkEffectSheet.getSprite(0, 0);
		this.angle = 0;
		this.originalRange = (float)range;
		this.setOnEndOfPathEffect(new EndOfPathListener() {
			@Override
			public void onEndOfPath(Character src) {
				for (int i = 0; i < size * 3; i++) {
					Engine.add(Particle.newSandPileEffectDestruction(getLoc()));
				}
			}
		});
	}

	protected void additionalHitEffects(Persona tgt) {

	}

	protected void additionalGuidanceEffects() {

		Engine.add(Particle.newSandPileEffectDestruction(getLoc()));
		Engine.add(Particle.newSandPileEffectDestruction(getLoc()));
		Engine.add(Particle.newSandPileEffectDestruction(getLoc()));

		for(Effect effect : Engine.getActiveArea().getEffects()){
			if(effect instanceof SandPileEffect){
				SandPileEffect pile = (SandPileEffect)effect;
				if(this.getAbsBounds().intersects(pile.getAbsBounds())){
					pile.pickup();
					pickupSand();
				}
			}
		}
	}
	public void draw(float sx, float sy, Graphics g, float zoom) {
		g.setColor(Tiles.MINIMAP_COLORS[Tiles.SAND]);
		g.fillRect(
				(getX() - w/2 - sx) * zoom,
				(getY() - (3*h / 2) - sy) * zoom,
				getWidth() * zoom,
				getHeight() * 2 * zoom
		);

	}
	private void pickupSand(){
		size += 8;
		this.w = size;
		this.h = size;
		this.range += originalRange;
		this.dx *= 1.05f;
		this.dy *= 1.05f;
		this.damage += 5;
	}
	public Rectangle getAbsBounds() {
		return new Rectangle((int)(x - w/2), (int)(y - h/2), (int)(w), (int)(h));
	}
}
