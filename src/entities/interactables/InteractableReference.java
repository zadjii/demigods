package entities.interactables;

import entities.Entity;
import game.Engine;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Graphics;
import util.Images;


public class InteractableReference extends Entity implements Interactable {
	private Interactable referencedObject;


	public InteractableReference(int X, int Y, Interactable referencedObject){
		this.setXY(X, Y) ;
		this.referencedObject = referencedObject;
	}


	public void interact() {
		referencedObject.interact();
	}

	@Override
	public void draw(float sx, float sy, float zoom, Graphics g) {
		/*Does nothing.*/
	}

	public Rectangle getAbsHitbox() {
		return referencedObject.getAbsHitbox();
	}

	@Override
	public boolean isPassable() {
		return referencedObject.isPassable();
	}

	public Rectangle getImpassableBounds() {
		return referencedObject.getImpassableBounds();
	}

	@Override
	public boolean drawnAbove() {
		return false;
	}
	public boolean needToRelease() {
		return referencedObject.needToRelease();
	}
	public String toString(){
		return referencedObject.toString();
	}
	public boolean aestheticOnly(){return referencedObject.aestheticOnly();}

}
