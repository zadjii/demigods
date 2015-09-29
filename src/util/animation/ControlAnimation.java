package util.animation;
import entities.characters.Character;
import org.lwjgl.util.Point;
import util.Maths;

public class ControlAnimation extends Animation {
	private float dx = 0;
	private float dy = 0;
	private float dz = 0;
	private float ddx = 0;
	private float ddy = 0;
	private float ddz = 0;
	private Character character;

	protected ControlAnimation(Character user, Animation animation){
		this.character = user;
		for(AnimationPoint point : animation.leftHand) this.leftHand.add(point);
		for(AnimationPoint point : animation.rightHand) this.rightHand.add(point);
	}

	public void setCharacter(Character character){
		this.character = character;
	}

	public boolean tick(){
//		System.out.println(this.dx);
		this.character.setDXDYDZ(this.dx, this.dy, this.dz);
//		System.out.println(character.getDX()+ "\n");

		dx += ddx;
		dy += ddy;
		dz += ddz;
		boolean returnValue = super.tick();
		if(returnValue){
			character.setDXDYDZ(0,0,0);
		}
		return returnValue;
	}

	protected void setDXDYDZ(float dx, float dy, float dz){
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}
	protected void setDDXDDYDDZ(float dx, float dy, float dz){
		this.ddx = dx;
		this.ddy = dy;
		this.ddz = dz;
	}
	public float getDX(){return dx;}
	public float getDY(){return dy;}
	public float getDZ(){return dz;}
	protected void setFinalDXDYDZ(float dx, float dy, float dz){
		if(rightHand.size() != leftHand.size()){
			return;
		}
		int lifetime = rightHand.size();
		if(lifetime == 0){
			return;
		}
		float ddx = (dx - this.dx)/lifetime;
		float ddy = (dy - this.dy)/lifetime;
		float ddz = (dz - this.dz)/lifetime;
		this.ddx = ddx;
		this.ddy = ddy;
		this.ddz = ddz;
	}

	public static ControlAnimation getLungeAnimation(Character user, Point tgt){
		int RANGE = 5*16;
		float speed = 10.0f;

		ControlAnimation lunge = new ControlAnimation(user, getStab(user.getDirection()));
		Animation swing2 = getStab(user.getDirection());

		//for(AnimationPoint point : swing2.leftHand) lunge.leftHand.add(point);
		//for(AnimationPoint point : swing2.rightHand) lunge.rightHand.add(point);

		float distance = (float)Maths.dist(user.getLoc(), tgt);
		float distX = tgt.getX() - user.getX();
		float distY = tgt.getY() - user.getY();


		if(distance < RANGE){
			//distance = RANGE;
			//distX *= (RANGE/distance);
			//distY *= (RANGE/distance);
		}
		float dx = ((distX)/ distance)*speed;
		float dy = ((distY)/ distance)*speed;








		lunge.setDXDYDZ(dx, dy, 0);
		lunge.setFinalDXDYDZ(dx, dy, 0);

		return lunge;
	}

}
