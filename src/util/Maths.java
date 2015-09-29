package util;

import entities.Entity;
import entities.characters.personas.Persona;
import entities.characters.Character;
import org.lwjgl.util.Point;

public class Maths {
	public static double dist(Persona i, Persona j){
		return Maths.dist(i.getCharacter().getLoc(), j.getCharacter().getLoc());
	}
	public static double dist(Character i, Character j){
		return Maths.dist(i.getLoc(), j.getLoc());
	}
	public static double dist(Entity i, Entity j){
		return Maths.dist(i.getLoc(), j.getLoc());
	}
	public static double dist(Point i, Point j){
		return Math.sqrt(
			( Math.pow((i.getX() - j.getX()), 2) + Math.pow((i.getY() - j.getY()), 2) )
		);
	}
	public static int intDist(Point i, Point j){
		return (int)(Math.sqrt(
				( Math.pow((i.getX() - j.getX()), 2) + Math.pow((i.getY() - j.getY()), 2) )
		));
	}
	/**
	 * x1, x2, y1, y2
	 * */
	public static double dist(float f, float g, float h, float i) {
		if (f > g) {
			if (h > i) {
				return Math.sqrt(((Math.pow((f - g), 2)) + (Math.pow((h - i), 2))));
			} else {
				return Math.sqrt(((Math.pow((f - g), 2)) + (Math.pow((i - h), 2))));
			}
		} else {
			if (h > i) {
				return Math.sqrt(((Math.pow((g - f), 2)) + (Math.pow((h - i), 2))));
			} else {
				return Math.sqrt(((Math.pow((g - f), 2)) + (Math.pow((i - h), 2))));
			}
		}
	}
}
