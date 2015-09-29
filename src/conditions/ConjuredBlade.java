package conditions;

import entities.characters.Character;
import org.lwjgl.util.Point;

public class ConjuredBlade extends Condition {

	private static final long serialVersionUID = 1L;

	private double atkMod = 0;

	public ConjuredBlade(Character src, Character tgt, int duration, int level){
		super(src, tgt, duration);
		switch (level){
		case 1:
			atkMod = 5;
			break;
		case 2:
			atkMod = 7;
			break;
		case 3:
			atkMod = 10;
			break;
		case 4:
			atkMod = 14;
			break;
		case 5:
			atkMod = 19;
			break;
		case 6:
			atkMod = 25;
			break;
		case 7:
			atkMod = 35;
			break;
		}
		atkMod += tgt.getStat(Character.ABILITY)/4;
		tgt.modifyFlatBonus(Character.ATTACK, atkMod);
	}

	public String getTooltip(){
		return "Your weapon is infused with a magical blade, causing you" +
				" to hit for " + atkMod + " more damage";
	}
	public void turnOff(){
		if(tgt!=null)tgt.modifyFlatBonus(Character.ATTACK, -atkMod);
	}
	public String toString(){
		return "Conjured Blade";
	}
	public Point getImageLoc(){return new Point(1, 0);}
	public boolean isVisible(){return true;}
}
