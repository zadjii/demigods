package conditions.etc;

import java.io.Serializable;

public class Cooldown implements Serializable {

    private static final long serialVersionUID = 1L;
    private float length;
    private float timer = 0;

    public Cooldown(float length) {
        this.length = length;
    }

    public void tick() {
        if (this.timer > 0) {
            --this.timer;
        } else {
            this.timer = 0;
        }
    }

    public void clear() {
        this.timer = 0;
    }

    public boolean offCooldown() {
        return timer == 0;
    }

    public void reset() {
        this.timer = length;
    }

    public int getRemaining() {
        return (int)this.timer;
    }

	public int getDuration() {
		return (int)this.length;
	}

	public float getFractionRemaining() {
		return this.timer / this.length;
	}

    public void addDuration() {
        this.timer += length;
    }

    public void addDuration(int time) {
        this.timer += time;
    }
}
