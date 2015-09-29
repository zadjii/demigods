package conditions;

import entities.characters.Character;
import entities.particles.Particle;
import game.Engine;
import org.lwjgl.util.Point;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 10/16/12
 * Time: 11:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestPotionHeal extends Condition {

	private static final long serialVersionUID = 1L;

	private double healthRegenMod = +0.15;
	private double manaRegenMod = +0.05;

	public TestPotionHeal(Character src, Character tgt, int duration){
		super(src, tgt, duration);
		this.refreshDuration = true;
		tgt.modifyFlatBonus(Character.HPREGEN, healthRegenMod);
		tgt.modifyFlatBonus(Character.MPREGEN, manaRegenMod);

	}
	public void turnOff(){
		if(tgt!=null){
			tgt.modifyFlatBonus(Character.HPREGEN, -healthRegenMod);
			tgt.modifyFlatBonus(Character.MPREGEN, -manaRegenMod);
		}

	}
	public void tick(Engine engine){
//		System.out.println(
//				"BURNING " + tgt.getHP() + " - " + duration.getRemaining() + " remaining"
//		);
		if(this.duration.getRemaining()%2==0){
			engine.add(Particle.newHealHPParticle(this.tgt));
			engine.add(Particle.newHealMPParticle(this.tgt));

		}

		super.tick();

	}

	public String getTooltip(){
		return "Test Potion!";
	}
	public String toString(){
		return "Test Potion!";
	}
	public Point getImageLoc(){return new Point(8, 0);}
//	public void draw(float sx, float sy, float zoom, Graphics g){
//
//	}


}
