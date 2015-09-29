package entities.particles;

import conditions.etc.Cooldown;
import effects.Effect;
import entities.tiles.Tiles;
import game.Engine;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import util.Constants;
import util.Images;
import util.Font;

import entities.Entity;
import entities.characters.Character;
import entities.characters.personas.Persona;
import util.Maths;


public class Particle extends Entity {

    public Particle(float x, float y, float dx, float dy,
                    int time, Color color, boolean isAString,
                    boolean isASquare, boolean isACircle, float size, String text) {
        this.color = color;
        duration = new Cooldown(time);
        duration.reset();
        this.isAString = isAString;
        this.isASquare = isASquare;
        this.isACircle = isACircle;
        this.size = size;
        this.text = text;
        this.setXY(x, y);
        this.setDX(dx);
        this.setDY(dy);
    }
	protected Particle() {
		this.color = null;
		duration = new Cooldown(15);
		duration.reset();
		this.isAString = false;
		this.isASquare = false;
		this.isACircle = false;
		this.size = 0;
		this.text = null;
		this.setXY(0,0);
		this.setDX(0);
		this.setDY(0);
	}

    private static final long serialVersionUID = 1L;

    private Color color;
    private Cooldown duration;
    private boolean isAString;
    private boolean isASquare;
    private boolean isACircle;
    private float size;
    private String text;

    public void tick() {
        duration.tick();
        this.setXY(this.getX() + this.getDX(), this.getY() + this.getDY());
    }


    public boolean isOff() {
        return duration.offCooldown();
    }


    public void draw(float sx, float sy, Graphics g, float zoom) {
        if (isAString) {
            Font.drawMonospacedFontString(text, (getX() - sx) * zoom, (getY() - sy) * zoom, 20, color);
        }
        if (isASquare) {
            g.setColor(color);
            g.fillRect((getX() - sx) * zoom, (getY() - sy) * zoom, size * zoom, size * zoom);
            return;
        }
        if (isACircle) {
            g.setColor(color);
            g.fillOval((getX() - sx) * zoom, (getY() - sy) * zoom, size * zoom, size * zoom);
            return;
        }
    }

    public static Particle newDamage(Persona p, double damage) {
        return new Particle(
                p.getCharacter().getX(),
                p.getCharacter().getY(),
                0,
                -1,
                50,
                Color.red,
                true, false, false,
                1, Integer.toString((int) (-1 * damage)));
    }

    public static Particle newDamage(Character p, double damage) {
        return new Particle(
                p.getX(),
                p.getY(),
                0,
                -1,
                50,
                Color.red,
                true, false, false,
                1, Integer.toString((int) (-1 * damage)));
    }

    public static Particle newTreeParticle(Entity entity) {
        int x = entity.getXi();
        int y = entity.getYi();
        int dx = (int) ((Math.random() * 4) - 2);
        int dy = (int) ((Math.random() * 2) + 1);
        return new Particle(
                x + ((int) (Math.random() * 16)),
                y + ((int) (Math.random() * 16)),
                dx, dy,
                15,
                new Color(114, 76, 2),
                false, true, false,
                6.0f, ""
        );
    }

    private static final Color treeGreen = new Color(13, 140, 45);

    public static Particle newGreenTreeParticle(Entity entity) {
        int x = entity.getXi();
        int y = entity.getYi();
        int dx = (int) ((Math.random() * 4) - 2);
        int dy = (int) ((Math.random() * 2) + 1);
        return new Particle(
                x + ((int) (Math.random() * 16)),
                y + ((int) (Math.random() * 16)),
                dx, dy,
                15,
                treeGreen,
                false, true, false,
                6.0f, ""
        );
    }

    public static Particle newTreeParticle(int gx, int gy, int dx, int dy) {
        return new Particle(
                gx * 16 + ((int) (Math.random() * 16)),
                gy * 16 + ((int) (Math.random() * 16)),
                dx, dy,
                15,
                new Color(114, 76, 2),
                false, true, false,
                6.0f, ""
        );

    }

    public static Particle newXP(Persona dead, Persona killer) {
        return new Particle(
                killer.getCharacter().getX(),
                killer.getCharacter().getY(),
                0,
                -1,
                50,
                Constants.XP_COLOR,
                true, false, false,
                1, "+" + Integer.toString(((int) (dead.getCharacter().getExp() * (.1 / dead.getCharacter().getLvl())) + (10 * dead.getCharacter().getLvl()))));
    }

    public static Particle newXP(Character dead, Character killer, int amount) {
        return new Particle(
                killer.getX(),
                killer.getY(),
                0,
                -2,
                50,
                Constants.XP_COLOR,
                true, false, false,
                1, "+" + Integer.toString(amount));
    }

    public static Particle newAttack(Point point) {
        return new Particle(
                point.getX() * 16,
                point.getY() * 16,
                0,
                0,
                50,
                new Color(1.0f, 1.0f, 1.0f, 0.5f),
                false, true, false,
                16, "");
    }

    public static Particle newCritical(Character criter) {
        Particle particle = new Particle(
                criter.getX(),
                criter.getY() - criter.getImageYOffset(),
                0,
                -1,
                50,
                Color.orange,
                true, false, false,
                1, "CRITICAL HIT");
        return particle;
    }

    public static Particle newStun(Character stunned) {
        Particle particle = new Particle(
                stunned.getX(),
                stunned.getY() - stunned.getImageYOffset(),
                0,
                -1,
                50,
                Color.lightGray,
                true, false, false,
                1, "STUNNED");
        return particle;
    }
	public static Particle newEnergyEffectHit(Effect effect) {
		boolean randBool = Engine.getRand().nextBoolean();
		return new Particle(
				(int)effect.getX() + ((int) (Math.random() * 8)) - 4,
				(int)effect.getY() + ((int) (Math.random() * 8)) - 4,
				(effect.getDX()/2.0f) + (float)(Math.random()*6 - 3),
				(effect.getDY()/2.0f) + (float)(Math.random()*6 - 3),
				20,
				new Color(
						(int)(Math.random()*50)+50,
						((int)(Math.random()*50))+50,
						(int)(Math.random()*150)+200),
				false, randBool , !randBool,
				4.0f, ""
		);

	}
	public static Particle newEnergyEffectTrail(Effect effect) {
		boolean randBool = Engine.getRand().nextBoolean();
		return new Particle(
				(int)effect.getX() + ((int) (Math.random() * 8)) - 4,
				(int)effect.getY() + ((int) (Math.random() * 8)) - 4,
				(effect.getDX()/4) + (float)(Math.random()*2 - 1),
				(effect.getDY()/4) + (float)(Math.random()*2 - 1),
				15,
				new Color(
						(int)(Math.random()*50)+100,
						((int)(Math.random()*50))+100,
						(int)(Math.random()*150)+200),
				false, randBool , !randBool,
				3.0f, ""
		);

	}
    public static Particle newFireEffect(Effect effect) {

		return new Particle(
				(int)effect.getX() + ((int) (Math.random() * 8)) - 4,
				(int)effect.getY() + ((int) (Math.random() * 8)) - 4,
				(effect.getDX()/2) + ((int)Math.random()*6),
				(effect.getDY()/2) + ((int)Math.random()*6),
				15,
				new Color(
						(int)(Math.random()*50)+200,
						((int)(Math.random()*50)),
						0),
				false, true, false,
				3.0f, ""
		);

	}

	public static Particle newSlimeHitEffect(Entity src, Color color) {

		return new Particle(
				(int)src.getX() + ((int) (Math.random() * 8)) - 4,
				(int)src.getY() + ((int) (Math.random() * 8)) - 4,
				(src.getDX()/4) + (float)(Math.random()*2 - 1),
				(src.getDY()/4) + (float)(Math.random()*2 - 1),
				15,
				new Color(
						color.r + ((int)(Math.random()*50)-25)/255.0f,
						color.g + ((int)(Math.random()*50)-25)/255.0f,
						color.b + ((int)(Math.random()*50)-25)/255.0f,
						(100/255.0f)),
				false, false, true,
				3.0f, ""
		);

	}
    public static Particle newFireParticle(Entity e) {

        return new Particle(
                (int)e.getX() + ((int) (Math.random() * 16)) - 8,
                (int)e.getY() + ((int) (Math.random() * 16)) - 8,
                (e.getDX()/2),
                (e.getDY()/2),
                15,
                new Color(
                        (int)(Math.random()*75)+180,
                        ((int)(Math.random()*50) + 100),
                        0),
                false, true, false,
                3.0f, ""
        );

    }
	public static Particle newSandPileEffectCreation(Point tgt) {
		boolean randBool = Engine.getRand().nextBoolean();
		Particle p = new Particle();
		Color sandDefault = Tiles.MINIMAP_COLORS[Tiles.SAND];
		float dColor = ((int)(Math.random()*72)-36)/255.0f;
		p.color = new Color(
				sandDefault.r + dColor,
				sandDefault.g + dColor,
				sandDefault.b + dColor);
		Point src = new Point(
				tgt.getX() + (int)(((float)Math.random() * 48) * (Math.random() > .5 ? -1 : 1) ),
				tgt.getY() + (int)(((float)Math.random() * 48) * (Math.random() > .5 ? -1 : 1) )
		);
		float dx = (float)(-((src.getX()-tgt.getX()) / Maths.dist(src, tgt)));
		float dy = (float)(-((src.getY()-tgt.getY()) / Maths.dist(src, tgt)));

		dx *= 2.25f * (float)(Math.random() + .1f);
		dy *= 2.25f * (float)(Math.random() + .1f);
		p.dx = dx;
		p.dy = dy;
		p.x = src.getX();
		p.y = src.getY();
		p.isASquare = true;
		p.size = 1.0f;

		p.duration = new Cooldown(.5f * Constants.TICKS_PER_SECOND);p.duration.reset();
		return p;
	}

	public static Particle newSandPileEffectDestruction(Point src) {
		boolean randBool = Engine.getRand().nextBoolean();
		Particle p = new Particle();
		Color sandDefault = Tiles.MINIMAP_COLORS[Tiles.SAND];
		float dColor = ((int)(Math.random()*25)-12)/255.0f;
		p.color = new Color(
				sandDefault.r + dColor,
				sandDefault.g + dColor,
				sandDefault.b + dColor);
		Point tgt = new Point(
				src.getX() + (int)(((float)Math.random() * 48) * (Math.random() > .5 ? -1 : 1) ),
				src.getY() + (int)(((float)Math.random() * 48) * (Math.random() > .5 ? -1 : 1) )
		);
		float dx = (float)(-((src.getX()-tgt.getX()) / Maths.dist(src, tgt)));
		float dy = (float)(-((src.getY()-tgt.getY()) / Maths.dist(src, tgt)));

		dx *= 2.25f * (float)(Math.random() + .5f);
		dy *= 2.25f * (float)(Math.random() + .5f);
		p.dx = dx;
		p.dy = dy;
		p.x = src.getX();
		p.y = src.getY();
		p.isASquare = true;
		p.size = 1.0f;
		p.duration = new Cooldown(10);p.duration.reset();
		return p;
	}
    public static Particle newHealHPParticle(Entity e) {

        return new Particle(
                (int)e.getX() + ((int) (Math.random() * 32)) - 16,
                (int)e.getY() + ((int) (Math.random() * 32)) - 16,
                (e.getDX()/2) + (float)((Math.random()>.5?1:-1)*(Math.random()*2.0)),
                (e.getDY()/2) - ((int)(Math.random()*5)),
                15,
                new Color(
                        (int)(Math.random()*15)+50,
                        ((int)(Math.random()*150) + 200),
                        (int)(Math.random()*15)+50
                ),
                false, true, false,
                2.0f, ""
        );

    }
	public static Particle newRespawnParticle(Entity e) {

		return new Particle(
				(int)e.getX() + ((int) (Math.random() * 64)) - 32,
				(int)e.getY() + ((int) (Math.random() * 64)) - 32,
				(e.getDX()/2) + (float)((Math.random()>.5?1:-1)*(Math.random()*2.0)),
				(e.getDY()/2) - ((int)(Math.random()*5)) - 2,
				60,
				new Color(
						(int)(Math.random()*150)+150,
						((int)(Math.random()*150) + 150),
						(int)(Math.random()*150)+150
				),
				false, false, true,
				2.0f, ""
		);

	}

    public static Particle newHealMPParticle(Entity e) {

        return new Particle(
                (int)e.getX() + ((int) (Math.random() * 32)) - 16,
                (int)e.getY() + ((int) (Math.random() * 32)) - 16,
                (e.getDX()/2) + (float)((Math.random()>.5?1:-1)*(Math.random()*2.0)),
                (e.getDY()/2) - ((int)(Math.random()*5)),
                15,
                new Color(
                        (int)(Math.random()*15)+50,
                        (int)(Math.random()*15)+50,
                        ((int)(Math.random()*150) + 200)
                ),
                false, false, true,
                2.0f, ""
        );

    }
    public static Particle newSmiteParticle(Point src, Entity tgt) {
        //System.out.println(src.getX() + ", " + src.getY());
        float dx = (float)(-((src.getX()-tgt.getX()) / Maths.dist(src, tgt.getLoc())));
        float dy = (float)(-((src.getY()-tgt.getY()) / Maths.dist(src, tgt.getLoc())));

        dx *= 2.25f;
        dy *= 2.25f;

        Color color;
        switch ((int)(Math.round(Math.random() * 2))){
            case 0:color = new Color(242, 220, 184);break;
            case 1:color = new Color(255, 255, 255);break;
            default:color = new Color(171, 255, 255);break;
        }

        return new Particle(
                src.getX() + ((int) (Math.random() * 8)) - 4,
                src.getY() + ((int) (Math.random() * 8)) - 4,
                dx,
                dy,
                3,
                color,
                false, false, true,
                2.0f, ""
        );

    }

	public static Particle newRallyParticle(Entity tgt) {
		//System.out.println(src.getX() + ", " + src.getY());
		float dx = 0.0f;
		float dy = -1.0f;


		Color color = new Color(242, 220, 184);


		return new Particle(
				tgt.getX(),
				tgt.getY() - 16,
				dx,
				dy,
				60,
				color,
				true, false, false,
				2.0f, "Rally!"
		);

	}
	public static Particle newHealTextParticle(Entity tgt, int amount) {
		//System.out.println(src.getX() + ", " + src.getY());
		float dx = 0.0f;
		float dy = -1.0f;


		Color color = new Color(25, 220, 25);


		return new Particle(
				tgt.getX(),
				tgt.getY() - 16,
				dx,
				dy,
				60,
				color,
				true, false, false,
				2.0f, ("+" + amount)
		);

	}
}
