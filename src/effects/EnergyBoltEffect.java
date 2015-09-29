package effects;

import entities.characters.Character;
import entities.characters.personas.Persona;
import entities.particles.Particle;
import game.Engine;
import org.lwjgl.util.Point;
import util.Images;

public class EnergyBoltEffect extends RotatedProjectileEffect {

	protected int frame = 0;
	protected static final int FRAME_RATIO = 4;

	public EnergyBoltEffect(Character caster, Point target, double damage, double speed, double range) {
		super(caster, target, damage, speed, range);
		this.speed = (float)speed;
		this.setWidth(16);
		this.setHeight(16);
		knockback = false;
		stopOnHit = false;
		image = Images.sparkEffectSheet.getSprite(0, 0);

	}

	protected void additionalHitEffects(Persona tgt) {
		for (int i = 0; i < 12; i++) {
			Engine.add(Particle.newEnergyEffectHit(this));
		}
	}

	protected void additionalGuidanceEffects() {
		Engine.add(Particle.newEnergyEffectTrail(this));
	}
}
