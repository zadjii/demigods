package gui.util;

import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import static util.Constants.DARK_BEIGE;
import static util.Constants.DARKER_BEIGE;
/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 10/28/13
 * Time: 11:46 AM
 */
public class Button {
	protected Rectangle bounds;
	protected OnClickListener onClickListener;
	protected boolean clicked = false;


	protected boolean enabled = true;

	public boolean click(Point mouse){
		if(enabled && bounds.contains(mouse.getX(), mouse.getY())){
			if(onClickListener != null)onClickListener.onClick();
			clicked = true;
			return true;
		}
		return false;
	}
	public void draw(Graphics g){
		if(enabled){
			if(clicked){
				g.setColor(DARK_BEIGE);
				g.fillRect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
				clicked = false;
			}
			g.setColor(DARKER_BEIGE);
			g.setLineWidth(3.0f);
			g.drawRect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
			g.setLineWidth(1.0f);
		}
		else{
			g.setColor(Color.gray);
			g.fillRect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
			g.setColor(Color.darkGray);
			g.setLineWidth(3.0f);
			g.drawRect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
			g.setLineWidth(1.0f);
		}

	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public OnClickListener getOnClickListener() {
		return onClickListener;
	}

	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public void enable() {
		this.enabled = true;
	}
	public void disable() {
		this.enabled = false;
	}
}
