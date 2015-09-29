package entities.characters;

import areas.NewGameArea;
import entities.particles.Particle;
import game.Engine;
import items.materials.Bone;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.Images;
import util.exceptions.DemigodsException;

public abstract class Slime extends Character {

    private static final long serialVersionUID = 1L;
    protected int animStep = 0;
	private float previousHP;
	protected Color blobletColor;

    public Slime() {
        super();
        init();
    }

	protected void init() {
        setWidth(16);
        setHeight(16);
//        this.setBaseStat(HP, 35);
//        this.setBaseStat(ARMOR, 2);
//	    this.setBaseStat(ATTACK, 3);
//        try {
//            inventory.addItem(new Bone());
//        } catch (DemigodsException ignored) {
//        }
//        setBaseSpeed(1);
	    this.blobletColor = new Color(100,100,100);
	    this.previousHP = this.getHP();
    }

	@Override
	public void tick(NewGameArea area){
		super.tick(area);
		if(this.knockback != null){
			if(knockback.getCooldown().getFractionRemaining() <= .9){
				this.knockback = null;
			}else{
				for (int i = 0; i < 20; i++) {
					Engine.add(Particle.newSlimeHitEffect(this, this.blobletColor));
				}
			}
		}
//		if(this.previousHP < this.getHP()){
//			for (int i = 0; i < 20; i++) {
//				Engine.add(Particle.newSlimeHitEffect(this, new Color(100, 100, 200)));
//			}
//		}
//		this.previousHP = this.getHP();
	}
    public void updateSpriteXY() {
        animStep++;
        if (animStep == 12) animStep = 0;
    }

    public String toString() {
        return "Slime " + this.getLocString();
    }
}
