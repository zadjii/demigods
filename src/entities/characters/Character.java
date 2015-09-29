package entities.characters;

import conditions.etc.Cooldown;
import enchantments.Enchantment;
import entities.characters.etc.Experience;
import entities.characters.etc.Inventory;
import entities.characters.etc.Knockback;
import entities.characters.personas.Persona;
import entities.particles.etc.DroppedItem;
import entities.particles.Particle;
import entities.tiles.Tiles;
import game.*;
import areas.NewGameArea;
import items.*;

import java.util.ArrayList;
import java.util.HashMap;

import entities.Entity;


import conditions.Condition;

import items.armor.Armor;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Graphics;
import skills.Skill;
import util.*;
import util.animation.Animation;
import util.animation.ControlAnimation;
import util.exceptions.DemigodsException;
import util.exceptions.UncheckedDemigodsException;

import static util.Constants.xpLevels;

public class Character extends Entity {

	private static final long serialVersionUID = 1L;

	private float baseSpeed = 1;

	public enum Direction {UP, DOWN, LEFT, RIGHT, NONE}
	protected Knockback knockback = null;
	private Cooldown stunDuration = null;
	protected Animation animation = null;
	private HashMap<String, ArrayList<Condition>> conditionMap = new HashMap<String, ArrayList<Condition>>();

	protected Direction direction = Direction.LEFT;

	protected int imageXOffset = 0;
	protected int imageYOffset = 0;
	protected boolean isStopped = false;

	protected double hp = 1;
	protected double mp = 1;

	protected int exp = 0;
	protected int level = 1;//""
	protected int sp = 0;//skill points

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	protected int team = -1;
	protected Inventory inventory = new Inventory(100);
	protected Item equippedItem = null;
	protected int stepNum;
	protected ArrayList<Skill> skills = new ArrayList<Skill>();
	protected Skill[] equippedSkills = new Skill[5];

	protected boolean swinging = false;

	protected boolean skillReadied = false;
	protected Skill readiedSkill = null;

	public void setSkillReadied(boolean state){this.skillReadied = state;}
	public void setReadiedSkill(Skill skill){this.readiedSkill = skill;}
	public boolean getSkillReadied(){return this.skillReadied;}
	public Skill getReadiedSkill(){return this.readiedSkill;}
	public Item getEquippedItem(){
		if(this.equals(Demigods.getPlayer().getCharacter())){
			return Demigods.getPlayer().getEquippedItem();
		}
		return equippedItem;
	}
	public void setEquippedItem(Item item){
		item.equip(this);
		this.equippedItem = item;
	}
	public void setSwinging(boolean value){
		swinging = value;
	}
	public void stun(float duration){
		this.stunDuration = new Cooldown(duration);
		stunDuration.reset();
		Engine.add(Particle.newStun(this));
	}
	/**
	 * Each index corresponds to exactly one armor slot, as specified by the
	 * constants that follow it.
	 * <p>LEGS 	= 0;
	 * <p>CHEST 	= 1;
	 * <p>HEAD 	= 2;
	 * <p>OFFHAND = 3; => offhand is now going to be a seperate item slot
	 */
	protected Armor[] armor = new Armor[3];
	public static final int HEAD 	= 0;
	public static final int CHEST = 1;
	public static final int LEGS 	= 2;

	protected OffhandItem offhandItem = null;

	public Item[] getArmorArray(){return this.armor;}
	public OffhandItem getOffhandItem(){return this.offhandItem;}
	public void equipOffhand(OffhandItem item){
		if(this.getOffhandItem() != null){
			this.getOffhandItem().offhandUnequip(this);
		}
		this.offhandItem = item;

		if(item != null)this.getOffhandItem().offhandEquip(this);
	}

	public boolean isStepingX() {
		return isStepingX;
	}

	public boolean isStepingY() {
		return isStepingY;
	}

	private boolean isStepingX = false;
	private boolean isStepingY = false;

	public Character(){
		super();
		initStats();
	}


	public void addXP(int xp){
		exp += xp;
		if(this.getLvl() == xpLevels.length) return;
		while(this.getExp() >= xpLevels[this.getLvl()+1] && this.getLvl() != xpLevels.length){
			this.levelUp();

		}
	}
	public void levelUp(){
		++level;
		++sp;
		this.modifyBaseStat(HP, 10);
//		this.modifyBaseStat(ABILITY, 1);
//		this.modifyBaseStat(ATTACK, 1);
//		this.modifyBaseStat(MP, 1);

		for(ArrayList<Condition> conditions : conditionMap.values()){
			for(Condition condition : conditions){
				condition.applyLevelUp(this);
			}
		}

		hp = (int)baseStats[HP];
		mp = (int)baseStats[MP];

	}
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction d) {
		direction = d;
	}


	public boolean getIsStopped() {
		return isStopped;
	}

	public void setIsStopped(boolean s) {
		isStopped = s;
	}

	public int getHP() {return (int)hp;}
	public int getMP() {return (int)mp;}

	public int getSP() {return sp;}
	public void removeSP(int sp) {this.sp -= sp;}

	public void damage(double h) {
		if (hp != 0) hp -= h;
		else if (hp - h < 0) hp = 0;
	}
	public void drainMana(int m) {mp -= m;}

	public void heal(double h) {
		hp += h;
		if(hp > this.getMaxHp())this.hp = (this.getMaxHp());
	}
	public void chargeMana(double m) {
		mp += m;
		if(mp > this.getMaxMana())this.mp = (this.getMaxMana());
	}
	public void respawn() {
		hp = this.getMaxHp();
		mp = this.getMaxMana();
		this.knockback = null;
		this.stunDuration = null;
		this.animation = null;
	}
	public int getMaxHp() {return (int) getStat(HP);}
	public int getMaxMana() {return (int) getStat(MP);}

	public int getExp() {return exp;}
	public int getLvl() {return level;}
	public void setLvl(int l) {level = l;}

	public void setBaseSpeed(float speed){
		this.baseSpeed = speed;
		this.speed = baseSpeed;
	}

	public void increaseSpeedForCobbleRoad() {
		setSpeed((this.baseSpeed * 1.5f));
		if(dx>0)dx=speed;
		else if(dx<0)dx=-speed;
		if(dy>0)dy=speed;
		else if(dy<0)dy=-speed;
		//if(this.equals(Demigods.getPlayer().getCharacter()))System.out.println("im on a road" + this.getSpeed());
	}

	public void resetSpeed() {
		setSpeed(this.baseSpeed);
		if(dx>0)dx=speed;
		else if(dx<0)dx=-speed;
		if(dy>0)dy=speed;
		else if(dy<0)dy=-speed;
	}

	public Rectangle getAbsHitbox() {
		int x = this.getXi()-this.getImageXOffset();
		int y = this.getYi()-this.getImageYOffset();
		return new Rectangle(x, y, (int)this.getWidth(), (int)this.getHeight());
	}

	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory){
		this.inventory = inventory;
	}
	public Animation getAnimation() {
		return animation;
	}
	public void setAnimation(Animation animation){
//		System.out.println("set anim: " + animation);
		this.animation = animation;
	}

	public HashMap<String, ArrayList<Condition>> getConditionMap(){return conditionMap;}
	public int getImageXOffset() {
		return this.imageXOffset;
	}

	public int getImageYOffset() {
		return this.imageYOffset;
	}

	public Character(int gx, int gy) {
		this();
		this.setSpeed(8);
		setGX(gx);
		setGY(gy);
	}



	public void updateSpriteXY() {

	}
	public void updateAttacking(){

	}
	public void tick(NewGameArea area) {
		//Shortcut to Character.tick()
		if(knockback != null){
			knockback.tick();
		}
		movingTick(area);
		itemThings(area.getDroppedItems());
		tickHPMP(this);
		tickSkills();



	}
	protected void movingTick(NewGameArea area){
		if(stunDuration != null){
			stunDuration.tick();
			//System.out.println(stunDuration.getRemaining());
			if(stunDuration.offCooldown()){
				stunDuration = null;
			}
			else return;
		}
		boolean move = true;
		if(animation != null){
			if(animation.tick()){
				animation = null;
			}
			else{
				move = animation instanceof ControlAnimation;
				stepNum = 0;
			}
		}
		if(move){
			if(swinging){
				updateAttacking();
			}
			if (this.getDX() != 0 || this.getDY() != 0) {
				movabilityCheck(area);
			} else {
				isStepingX = false;
				isStepingY = false;
			}
			if (isStepingX || isStepingY) {
				step();

			} else {
				updateSpriteXY();
			}
			if(this.getDY()>0)this.direction=Direction.DOWN;
			else if(this.getDY()<0)this.direction=Direction.UP;
			if(this.getDX()>0)this.direction=Direction.RIGHT;
			else if(this.getDX()<0)this.direction=Direction.LEFT;

			this.roadThings(area);
		}
	}
	protected void roadThings(NewGameArea area) {
		try{

			if(area.getMap()[(this.getXi() + (int)this.getWidth() / 2)/16][(this.getYi() + (int)this.getHeight() / 2)/16]
					== Tiles.COBBLEROAD||
					area.getMap()[(this.getXi())/16][(this.getYi())/16] == Tiles.COBBLEROAD	){
				this.increaseSpeedForCobbleRoad();

			} else {
				this.resetSpeed();
				//if(this.equals(Demigods.getPlayer().getCharacter()))System.out.println("no Im not!");
			}
		}catch(ArrayIndexOutOfBoundsException e){
			this.resetSpeed();
		} catch (NullPointerException e){
			isStepingX = false;
			isStepingY = false;
		}

	}

	protected void itemThings(ArrayList<DroppedItem> droppedItems) {
		if(this.getInventory().isFull())return;
		ArrayList<DroppedItem> returnArray = new ArrayList<DroppedItem>();
		for (DroppedItem i : droppedItems) {
			if (Maths.dist(i.getX(), this.getX(), i.getY(), this.getY()) < 40) {

				if (i.getX() > this.getX()) {
					if (i.getY() > this.getY() + 1) {
						i.setXY(i.getX() - 1, i.getY() - 1);
					} else if (i.getY() > this.getY() - 1) {
						i.setXY(i.getX() - 1, i.getY());
					} else {
						i.setXY(i.getX() - 1, i.getY() + 1);
					}
				}
				if (i.getX() == this.getX()) {
					if (i.getY() > this.getY()) {
						i.setXY(i.getX(), i.getY() - 1);
					} else {
						i.setXY(i.getX(), i.getY() + 1);
					}
				}
				if (i.getX() < this.getX()) {
					if (i.getY() > this.getY() + 1) {
						i.setXY(i.getX() + 1, i.getY() - 1);
					} else if (i.getY() > this.getY() - 1) {
						i.setXY(i.getX() + 1, i.getY());
					} else {
						i.setXY(i.getX() + 1, i.getY() + 1);
					}
				}
			}

			if (Maths.dist(i.getX(), this.getX(), i.getY(), this.getY()) <= 6) {
				try {
					this.inventory.addItem(i.item);
					returnArray.add(i);
				} catch (DemigodsException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		for(DroppedItem i : returnArray ){
			droppedItems.remove(i);
		}
		//return returnArray;
	}

	protected void movabilityCheck(NewGameArea area) {

		int newAbsX;
		int newAbsY;

		// _0 is the lesser corner, _1 is the greater
		int frontX_0 = this.getXi() - 6;
		int frontY_0 = this.getYi();
		int frontX_1 = frontX_0 + 12;
		int frontY_1 = frontY_0 + 12;

		newAbsX = ((frontX_0 + (int)this.getDX()) );
		newAbsY = ((frontY_0 + (int)this.getDY()) );

		if (this.getDX() > 0) {
			newAbsX = ( frontX_1 + (int)this.getDX() );
		}
		if (this.getDY() > 0) {
			newAbsY = ( frontY_1 + (int)this.getDY() );
		}
//		if (this.getDX() < 0) {
//			frontX_0 = this.getAbsX();
//		}
//		if (this.getDY() < 0) {
//			frontY_0 = this.getAbsY();
//		}

		int nagx = newAbsX/16;
		int nagy = newAbsY/16;

		int fgx0 = frontX_0/16;
		int fgx1 = frontX_1/16;
		int fgy0 = frontY_0/16;
		int fgy1 = frontY_1/16;
		/*
		ArrayList<Persona> nearbyPeople = area.getPersonas().get(new Rectangle(newAbsX, newAbsY, 16, 16));
		boolean movePeopleX = true, movePeopleY = true;
//		if(nearbyPeople == null ||nearbyPeople.size() < 2)movePeopleX = movePeopleY = true;
		if(nearbyPeople.size() >= 2){
			Rectangle xBox = new Rectangle(newAbsX, frontY_0, frontX_1-newAbsX, frontY_1-frontY_0);
			Rectangle yBox = new Rectangle(frontX_0, newAbsY, frontX_1-frontX_0, frontY_1-newAbsY);
			for(Persona other : nearbyPeople){
				if(other.getCharacter().equals(this))continue;
				Rectangle otherBox = new Rectangle((int)other.getCharacter().getX() - 8,(int)other.getCharacter().getY() - 8, 16,16);
				if(movePeopleX)if(xBox.intersects(otherBox))movePeopleX = false;
				if(movePeopleY)if(yBox.intersects(otherBox))movePeopleY = false;
//				if(xBox.contains((int)other.getCharacter().getX(), (int)other.getCharacter().getY())){
//					movePeopleX = false;
//					break;
//				}
			}
//			for(Persona other : nearbyPeople){
//				if(other.getCharacter().equals(this))continue;
//				if(yBox.contains((int)other.getCharacter().getX(), (int)other.getCharacter().getY())){
//					movePeopleY = false;
//					break;
//				}
//			}
		}
		*/
		try {
			isStepingX =
					(area.getPassable()[nagx][fgy0])
							&&(area.getPassable()[nagx][fgy1])
//							&&movePeopleX
//							&&area.getPersonas().get(new Rectangle(nagx*16, fgy0*16, 8, 8)).size()<2
//							&&area.getPersonas().get(new Rectangle(nagx*16, fgy1*16, 16, 16)).size()<3
			;
			isStepingY =
					(area.getPassable()[fgx0][nagy])
							&&(area.getPassable()[fgx1][nagy])
//							&&movePeopleY
//							&&area.getPersonas().get(new Rectangle(fgx0*16, nagy*16, 8, 8)).size()<2
//							&&area.getPersonas().get(new Rectangle(fgx1*16, nagy*16, 16, 16)).size()<3
			;

//			if(land.getPersonas().get(new Point(newAbsX, frontY_0)).size()!=1
//					&&land.getPersonas().get(new Point(newAbsX, frontY_1)).size()!=1){
//				isStepingX = false;
//			}

		} catch (UncheckedDemigodsException e) {
			System.out.println("Character.movabilityCheck: Caught UDE");
			isStepingX = false;
			isStepingY = false;
		}catch (IndexOutOfBoundsException e) {
//			System.out.println("Caught IOoBE");
			isStepingX = false;
			isStepingY = false;
		}


	}

	public void step() {
		if(isStepingX){
			this.setXY((this.getX() + this.getDX()), (this.getY()));
		}
		if(isStepingY){
			this.setXY((this.getX()),(this.getY() + this.getDY()));
		}
		if(this.getDX() > 0)this.setDirection(Character.Direction.RIGHT);
		else if(this.getDX() < 0)this.setDirection(Character.Direction.LEFT);

		if(this.getDY() > 0)this.setDirection(Character.Direction.DOWN);
		else if(this.getDY() < 0)this.setDirection(Character.Direction.UP);
		updateSpriteXY();
	}

	public void setKnockback(Knockback knockback) {
		this.knockback = knockback;
	}

	public Knockback getKnockback() {
		return knockback;
	}

	public void draw(float sx, float sy, float zoom, Graphics g) {

	}




	/**
	 *  These constants represent each stat's location in the stat arrays.
	 */
	public static final int ATTACK = 0;
	public static final int ARMOR = 1;
	public static final int MAGIC_RESIST = 2;
	public static final int ABILITY = 3;
	public static final int FIRE_POWER = 4;
	public static final int WATER_POWER = 5;
	public static final int AIR_POWER = 6;
	public static final int EARTH_POWER = 7;
	public static final int PURPLE_POWER = 8;
	public static final int LIGHT_POWER = 9;
	public static final int DARK_POWER = 10;
	public static final int FIRE_RESIST = 11;
	public static final int WATER_RESIST = 12;
	public static final int AIR_RESIST = 13;
	public static final int EARTH_RESIST = 14;
	public static final int PURPLE_RESIST = 15;
	public static final int LIGHT_RESIST = 16;
	public static final int DARK_RESIST = 17;
	public static final int CRIT_CHANCE = 18;
	public static final int HP = 19;
	public static final int MP = 20;
	public static final int HPREGEN = 21;
	public static final int MPREGEN = 22;
	public static final int VAMPIRISM = 23;
	public static final int ATTACK_SPEED = 24;

	private double[] baseStats = new double[25];
	private double[] flatStats = new double[25];
	private double[] pcntStats = new double[25];

	private void initStats(){
		baseStats[HP] = 100;
		baseStats[MP] = 40;
		baseStats[MPREGEN] = 0.025;
		baseStats[HPREGEN] = 0.0;
		baseStats[ATTACK_SPEED] = 30.0;
		hp = (int)baseStats[HP];
		mp = (int)baseStats[MP];
	}


	public double getStat(int stat){
		return (baseStats[stat] + flatStats[stat]) * (1 + pcntStats[stat]);
	}
	public void setBaseStat(int stat, double value){
		baseStats[stat] = value;
	}
	public void setFlatBonus(int stat, double value){
		flatStats[stat] = value;
	}

	public void modifyBaseStat(int stat, double value){
		baseStats[stat] += value;
	}
	public void modifyFlatBonus(int stat, double value){
		flatStats[stat] += value;
	}
	public void modifyPercentBonus(int stat, double value){
		pcntStats[stat] += value;
	}
	public static final int MAX_ARMOR = 250;

//	public static Persona attack(Persona src, Persona tgt, GameScreen gameScreen){
//		//attack(src.getEquippedItem().getDamage(), src.getCharacter(), tgt.getCharacter(), gameScreen);
//		if(tgt.getCharacter().getKnockback()!=null)return null;
//		Engine.bounce(src.getCharacter(), tgt.getCharacter());
//
//		if(tgt.getCharacter().getHP() <= 0){
//			System.out.println(tgt + " is dead");
//			for(Item q: tgt.getCharacter().getInventory().getItems()){
//				if(q != null && !q.toString().equalsIgnoreCase("empty hands")){
//					Engine.getActiveArea().getDroppedItems().add(
//							new DroppedItem(tgt.getCharacter().getXi(), tgt.getCharacter().getYi(), q)
//					);
//				}
//			}
//			Experience.grantXP(tgt.getCharacter(), src.getCharacter());
//			Engine.add(Particle.newXP(tgt, src));
////			gameScreen.getParticles().add(Particle.newXP(tgt, src));
//			return tgt;
//		}
//		else return null;
//
//	}
	public static boolean attack(double damage, Character src, Character tgt, boolean knockback){
		//double damage = src.getStat(ATTACK) + (float)itemDmg;

		boolean crit = false;
		COND_LISTS:for(ArrayList<Condition> conditions : src.getConditionMap().values()){
			for(Condition cond : conditions){
				if(cond.isOnHit()){
					damage += cond.applyOnHit(src, tgt);
				}
				else continue COND_LISTS;
			}
		}
//		for(Condition cond : src.getConditions()){
//			if(cond.isOnHit()){
//				damage += cond.applyOnHit(src, tgt);
//			}
//		}
		if(src.getEquippedItem() != null){
			for(Enchantment enchantment : src.getEquippedItem().getEnchantments()){
				enchantment.applyOnHit(src, tgt);
			}
		}

		if(Math.random() <= src.getStat(CRIT_CHANCE)){
			crit = true;
			damage *= 2;
		}
		if(crit){
			damage = decreaseDamageForArmor(damage, tgt.getStat(ARMOR) * (0.75));
		}
		else{
			damage = decreaseDamageForArmor(damage, tgt.getStat(ARMOR));
		}
		stackAdrenaline(src);
		tgt.damage((int)(damage));



		double lifeVamp = damage * src.getStat(VAMPIRISM);
		src.heal((int)lifeVamp);
		Audio.playHit();
		Engine.add(Particle.newDamage(tgt, damage));
		if(knockback) Engine.bounce(src, tgt);
		if(tgt.getHP() <= 0){
			killCharacter(src, tgt);
			return true;
		}
		return false;
	}
	public static void dealTrueDamage(double damage, Character src, Character tgt, boolean knockback){
		tgt.damage((int)(damage));
		Audio.playHit();
		Particle damageParticle = Particle.newDamage(tgt, damage);
		damageParticle.setX(damageParticle.getX() - 16);
		Engine.add(damageParticle);
		if(knockback) Engine.bounce(src, tgt);
		if(tgt.getHP() <= 0){
			killCharacter(src, tgt);
		}
	}
	public static void stackAdrenaline(Character src){
		if(src.getConditionMap().containsKey("Adrenaline")){
			Condition adrenaline = src.getConditionMap().get("Adrenaline").get(0);
			adrenaline.addDuration();
		}
	}
	private static double decreaseDamageForArmor(double initialDamage, double armor){

		double armorValue = armor;
		double damage = initialDamage;

		int additionalDecrease = (int)((armorValue - MAX_ARMOR) / 10);
		if(armorValue > MAX_ARMOR)armorValue = MAX_ARMOR;
		double armorDecrease = damage * ((armorValue / MAX_ARMOR) * 0.8);
		damage -= armorDecrease;
		//damage -= additionalDecrease;


		if(damage < 1) damage = 1;

		return damage;
	}
	public static void killCharacter(Character src, Character tgt){
			//System.out.println(tgt + " is dead");
			for(Item q: tgt.getInventory().getItems()){
				if(q != null && !q.toString().equalsIgnoreCase("empty hands")){
                    Engine.getActiveArea().getDroppedItems().add(new DroppedItem(tgt.getXi(), tgt.getYi(), q));
				}
			}
			Experience.grantXP(tgt, src);
//        Engine.add(Particle.newXP(tgt, src));
	}
	public static boolean dealMixedDamage(int damage, Character src, Character tgt, boolean knockback){
		if (tgt.getKnockback() == null){
			double pain = damage;
			pain += src.getStat(ABILITY) + src.getStat(ATTACK);
			pain = decreaseDamageForArmor(pain, tgt.getStat(MAGIC_RESIST)/2 + tgt.getStat(ARMOR)/2);
			tgt.damage(pain);

			if(knockback) Engine.bounce(src, tgt);

			Engine.add(Particle.newDamage(tgt, pain));
			if(tgt.getHP() <= 0){
				//System.out.println(tgt + " is dead");
				killCharacter(src, tgt);
				return true;
			}
		}
		return false;
	}
	public static boolean dealFireDamage(int damage, Character src, Character tgt, boolean knockback){
		if (tgt.getKnockback() == null){
			double pain = damage;
			pain += src.getStat(ABILITY) + src.getStat(FIRE_POWER);
			pain = decreaseDamageForArmor(pain, tgt.getStat(MAGIC_RESIST) + tgt.getStat(FIRE_RESIST));
			tgt.damage(pain);

			if(knockback) Engine.bounce(src, tgt);

            Engine.add(Particle.newDamage(tgt, pain));
			if(tgt.getHP() <= 0){
				if(tgt.getHP() <= 0){
					killCharacter(src, tgt);
					return true;
				}
				return true;
			}
		}
		return false;
	}

	public static boolean dealWaterDamage(int damage, Character src, Character tgt, boolean knockback){
		if (tgt.getKnockback() == null){
			double pain = damage;
			pain += src.getStat(ABILITY) + src.getStat(WATER_POWER);
			pain = decreaseDamageForArmor(pain, tgt.getStat(MAGIC_RESIST) + tgt.getStat(WATER_RESIST));
			tgt.damage(pain);

			if(knockback) Engine.bounce(src, tgt);

            Engine.add(Particle.newDamage(tgt, pain));
			if(tgt.getHP() <= 0){
				if(tgt.getHP() <= 0){
					killCharacter(src, tgt);
					return true;
				}
				return true;
			}
		}
		return false;
	}
	public static boolean dealEarthDamage(int damage, Character src, Character tgt, boolean knockback){
		if (tgt.getKnockback() == null){
			double pain = damage;
			pain += src.getStat(ABILITY) + src.getStat(EARTH_POWER);
			pain = decreaseDamageForArmor(pain, tgt.getStat(MAGIC_RESIST) + tgt.getStat(EARTH_RESIST));
			tgt.damage(pain);

			if(knockback) Engine.bounce(src, tgt);

            Engine.add(Particle.newDamage(tgt, pain));
			if(tgt.getHP() <= 0){
				if(tgt.getHP() <= 0){
					killCharacter(src, tgt);
					return true;
				}
				return true;
			}
		}
		return false;
	}
	public static boolean dealAirDamage(int damage, Character src, Character tgt, boolean knockback){
		if (tgt.getKnockback() == null){
			double pain = damage;
			pain += src.getStat(ABILITY) + src.getStat(AIR_POWER);
			pain = decreaseDamageForArmor(pain, tgt.getStat(MAGIC_RESIST) + tgt.getStat(AIR_RESIST));
			tgt.damage(pain);

			if(knockback) Engine.bounce(src, tgt);

            Engine.add(Particle.newDamage(tgt, pain));
			if(tgt.getHP() <= 0){
				if(tgt.getHP() <= 0){
					killCharacter(src, tgt);
					return true;
				}
				return true;
			}
		}
		return false;
	}
	public static boolean dealPurpleDamage(int damage, Character src, Character tgt, boolean knockback){
		if (tgt.getKnockback() == null){
			double pain = damage;
			pain += src.getStat(ABILITY) + src.getStat(PURPLE_POWER);
			pain = decreaseDamageForArmor(pain, tgt.getStat(PURPLE_RESIST));
			tgt.damage(pain);

			if(knockback) Engine.bounce(src, tgt);

            Engine.add(Particle.newDamage(tgt, pain));
			if(tgt.getHP() <= 0){
				if(tgt.getHP() <= 0){
					killCharacter(src, tgt);
					return true;
				}
				return true;
			}
		}
		return false;
	}
	public static boolean dealLightDamage(int damage, Character src, Character tgt, boolean knockback){
		if (tgt.getKnockback() == null){
			double pain = damage;
			pain += src.getStat(ABILITY) + src.getStat(LIGHT_POWER);
			pain = decreaseDamageForArmor(pain, tgt.getStat(ARMOR) + tgt.getStat(LIGHT_RESIST));
			tgt.damage(pain);

			if(knockback) Engine.bounce(src, tgt);

            Engine.add(Particle.newDamage(tgt, pain));
			if(tgt.getHP() <= 0){
				if(tgt.getHP() <= 0){
					killCharacter(src, tgt);
					return true;
				}
				return true;
			}
		}
		return false;
	}
	public static boolean dealDarkDamage(int damage, Character src, Character tgt, boolean knockback){
		if (tgt.getKnockback() == null){
			double pain = damage;
			pain += src.getStat(ABILITY) + src.getStat(DARK_POWER);
			pain = decreaseDamageForArmor(pain, tgt.getStat(ARMOR) + tgt.getStat(DARK_RESIST));
			tgt.damage(pain);

			if(knockback) Engine.bounce(src, tgt);

            Engine.add(Particle.newDamage(tgt, pain));
			if(tgt.getHP() <= 0){
				if(tgt.getHP() <= 0){
					killCharacter(src, tgt);
					return true;
				}
				return true;
			}
		}
		return false;
	}



	public static void tickMP(Character src){
		src.chargeMana(src.getStat(MPREGEN));
	}
	public static void tickHP(Character src){
		src.heal(src.getStat(HPREGEN));
	}
	public static void tickHPMP(Character src){
		tickHP(src);
		tickMP(src);
	}
	public ArrayList<Skill> getSkills(){
		return this.skills;
	}

	public void setSkill(int index, Skill skill){
		this.equippedSkills[index] = skill;
		if(!this.skills.contains(skill)){
			skills.add(skill);
		}
	}

	public void useSkill(int index, Point point){
		try{
			this.equippedSkills[index].use(this, point);
		}catch (NullPointerException e){
			//do nothing...
		}
	}
	protected void tickSkills(){
        for (Skill equippedSkill : equippedSkills) {
            try {
                equippedSkill.tick();
            } catch (NullPointerException e) {
                //do nothing...
            }
        }
	}
	public Skill[] getEquippedSkills(){
		return this.equippedSkills;
	}
	protected void drawShadow(float sx, float sy, float zoom){
		float imageOriginX = getX() - sx - (getImageXOffset()/2);
		float imageOriginY =  getY() - sy - getImageYOffset() + getHeight() - 4;

		imageOriginX *= zoom;
		imageOriginY *= zoom;

		Images.shadow.draw(imageOriginX, imageOriginY, (this.getWidth()- getImageXOffset())*zoom, 8*zoom);
	}
	public void addCondition(Condition condition){
		String name = condition.toString();
		if(conditionMap.get(name) == null){
			conditionMap.put(name, new ArrayList<Condition>());
			conditionMap.get(name).add(condition);
		}
		else{
			//if the condition doesn't stack intensity, stack its duration
			if(condition.refreshesDuration()){
				if(conditionMap.get(name).size() == 0)conditionMap.get(name).add(condition);
				else conditionMap.get(name).get(0).addDuration();
			}
			else{
				conditionMap.get(name).add(condition);
			}
		}
	}
	public Condition removeCondition(String name){
		if(conditionMap.get(name) == null)return null;
		//the condition is on the character, remove the first,
		//get(0) is the first or oldest.
		else {
			Condition c = conditionMap.get(name).remove(0);
			if(conditionMap.get(name).size() == 0)conditionMap.remove(name);
			return c;
		}
	}
	public String toString(){
		return "Character (" + this.getLoc() + ")";
	}


}