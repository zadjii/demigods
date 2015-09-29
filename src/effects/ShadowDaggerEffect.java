package effects;

import entities.characters.personas.Persona;
import game.Engine;
import org.lwjgl.util.Point;
import util.EndOfPathListener;
import util.Images;
import entities.characters.Character;
import util.Maths;
import util.OnHitListener;

import java.util.ArrayList;

public class ShadowDaggerEffect extends RotatedProjectileEffect {

	protected int frame = 0;
	protected static final int FRAME_RATIO = 4;

	public ShadowDaggerEffect(Character caster, Point target, double damage, double speed, double range) {
		super(caster, target, damage, speed, range);
		this.speed = (float)speed;
		this.setWidth(16);
		this.setHeight(16);
		knockback = true;
		stopOnHit = true;
		image = Images.sparkEffectSheet.getSprite(0, 0);

	}

	protected void additionalHitEffects(Persona tgt) {
	}

	protected void additionalGuidanceEffects() {

	}
}
