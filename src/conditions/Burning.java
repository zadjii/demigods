package conditions;

import entities.characters.Character;
import game.Engine;
import entities.particles.Particle;
import org.lwjgl.util.Point;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 10/16/12
 * Time: 11:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class Burning extends Condition {

	private static final long serialVersionUID = 1L;

	private double healthRegenMod = -0.05;

	public Burning(Character src, Character tgt, int duration){
		super(src, tgt, duration);
		this.refreshDuration = false;
//		System.out.println(
//				"_____________________________________" +
//						"\n" + tgt + " SOMETHING WAS INFECTED \n" +
//				"_____________________________________"
//		);
		tgt.modifyFlatBonus(Character.HPREGEN, healthRegenMod);

	}
	public void turnOff(){
		if(tgt!=null)
		tgt.modifyFlatBonus(Character.HPREGEN, -healthRegenMod);
	}
	public void tick(){
//		System.out.println(
//				"BURNING " + tgt.getHP() + " - " + duration.getRemaining() + " remaining"
//		);
		if(this.duration.getRemaining()%3==0)Engine.add(Particle.newFireParticle(this.tgt));
		super.tick();

	}

	public String getTooltip(){
		return "On fire, causing " +
				-this.healthRegenMod + " health degeneration";
	}
	public String toString(){
		return "Burning";
	}
	public Point getImageLoc(){return new Point(8, 0);}
//	public void draw(float sx, float sy, float zoom, Graphics g){
//
//	}


}
