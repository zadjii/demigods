package effects;

import entities.characters.personas.Persona;
import game.Engine;
import org.lwjgl.util.Point;
import util.Constants;
import util.Images;
import entities.characters.Character;

public class WaveEffect extends RotatedProjectileEffect {

    protected int width = 32;
    protected int height = 5 * 16;

    public WaveEffect(Character caster, Point target, double damage, double speed, double range) {
        super(caster, target, damage, speed, range);
        this.speed = (float)speed;
        this.setWidth(width);
        this.setHeight(height);
        knockback = false;
        stopOnHit = false;
        image = Images.swingAttackEffectSheet.getSprite(1, 1);
    }

    protected void additionalHitEffects(Persona tgt) {
        tgt.getCharacter().setDX((int)this.dx);
        tgt.getCharacter().setDY((int)this.dy);
    }

    protected void additionalGuidanceEffects() {
        if (distanceTraveled < (range - (2 * speed))) {
            for (Persona p : hit) {
                Character character = p.getCharacter();
                character.setDX((int)this.dx);
                character.setDY((int)this.dy);
            }
        } else {
            for (Persona p : hit) {
                Character character = p.getCharacter();
                character.setDX(0);
                character.setDY(0);
                character.stun(1.5f * Constants.TICKS_PER_SECOND);
            }
        }
    }
}
