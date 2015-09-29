package effects;

import org.lwjgl.util.Point;
import org.newdawn.slick.Graphics;
import util.Images;

public class SetDestAnimationEffect extends Effect {

	private static final int FRAME_RATIO = 3;
	private static final int WIDTH = 16;
	private static final int HEIGHT = 16;

	private int frame = 0;

	public SetDestAnimationEffect(Point src){

		setLoc(src);



	}

	public void draw(float sx, float sy, Graphics g, float zoom) {

		Images.setDestAnimationSheet.getSprite(frame/FRAME_RATIO, 0).draw(
				((int)(this.getAbsX()-WIDTH/2)-sx)*zoom,
				((int)(this.getAbsY()-HEIGHT/2)-sy)*zoom,
				WIDTH*zoom,
				HEIGHT*zoom
				);



	}
	public Effect tick(){
		if(frame < 5*FRAME_RATIO){
			frame++;
			return null;
		}
		else{return this;}
	}

}
