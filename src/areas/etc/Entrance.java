package areas.etc;

import entities.Entity;
import org.lwjgl.util.Point;

public class Entrance extends Entity {

    private static final long serialVersionUID = 1L;
    private boolean isOn = false;
    private boolean active = false;
    private boolean toOverworld;

    private Point targetAbsLoc;

	private int ID;
    private boolean toInterior;

    private int tickCount = 0;

	private boolean facingUp;

	public Entrance(int gx, int gy, boolean facingUp, int id) {
		this.setGXGY(gx, gy);
		targetAbsLoc = new Point(0, 0);
		this.setFacingUp(facingUp);
		ID = id;
	}

	public Entrance(int gx, int gy, boolean facingUp, Point targetAbsLoc, int id) {
		this.setGXGY(gx, gy);
		this.targetAbsLoc = targetAbsLoc;
		this.setFacingUp(facingUp);
		ID = id;
	}

    public void tick() {
        if (tickCount <= 14) {
            setOn(false);
        } else if (tickCount <= 28) {
            setOn(true);
        } else {
            tickCount = 0;
        }
        ++tickCount;
    }



    public void setFacingUp(boolean facingUp) {
        this.facingUp = facingUp;
    }

    public boolean isFacingUp() {
        return facingUp;
    }

	public void setTargetAbsLoc(Point targetAbsLoc) {
		this.targetAbsLoc = targetAbsLoc;
	}
	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	public boolean isOn() {
		return isOn && active;
	}

	public int getID() {
		return ID;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void setToOverworld(boolean toOverworld) {
		this.toOverworld = toOverworld;
	}

	public boolean isToOverworld() {
		return toOverworld;
	}

	public Point getExitPoint() {
		return targetAbsLoc;
	}
}