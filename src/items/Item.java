package items;

import conditions.etc.Cooldown;
import effects.AttackEffect;
import effects.Effect;
import effects.RotatedAttackEffect;
import enchantments.Enchantment;
import entities.characters.Character;
import game.*;
import java.io.*;
import java.util.ArrayList;

import org.lwjgl.util.Point;
import util.*;
import util.animation.Animation;
import util.exceptions.UncheckedDemigodsException;

public class Item implements Serializable {

	private double weight = 0;
	protected int recipeValue;
	protected double damage = 1;
	protected short range = 1;

	//useSpeed is the default amount of time that it takes to use the item.
	private float useSpeed = Constants.ITEM_SPEED;
	public void setUseSpeed(float time){
		this.useSpeed = time;
		initializeUseTime();
	}
	private Cooldown useTime;// = new Cooldown(useSpeed);
	protected void setUseTime(Cooldown useTime){
		this.useTime = useTime;
	}


	//NEW CONSTANTS: items/swords 5, daggers will be 3, greatswords 10, and spears 8
	public boolean isOffCooldown(){
		if(this.useTime == null)initializeUseTime();
		return useTime.offCooldown();
		}
	public void tick(){
//		boolean offCD = useTime.offCooldown();
		if(this.useTime == null)initializeUseTime();
		useTime.tick();
//		if(offCD != useTime.offCooldown()) System.out.println(user + "'s " + this + " came off cd");
	}
	public void use(){
		initializeUseTime();
		useTime.reset();
	}
	protected void initializeUseTime(){
		float useDuration;
		if(this.getUser() != null){
			useDuration = (float)(useSpeed + user.getStat(Character.ATTACK_SPEED));
		}else useDuration = useSpeed;
		if(useTime != null){
			if(useTime.getDuration() != (int)useDuration){
				setUseTime(new Cooldown(useDuration));
			}
		}else setUseTime(new Cooldown(useDuration));
	}
	public int getRecipeValue() {
		return recipeValue;
	}
	public Cooldown getCooldown(){return useTime;}

							////TODO:
	protected Character user;//Re-arranging this and the related methods will
							//fix a ton of circular references
	private boolean
			isAxe = false,
	        isPick = false,
	        isLight = false,
	        isWeapon = false,
	        isSpellBook = false,
			isRoad = false,
			isFoundation = false,
			isInteractableItem = false,
			isCounted = false,
			isLeggings = false,
			isChest = false,
			isHat = false,
			isOffhand = false,
			isArmor = false,
			isEquipped = false;

	protected String name = "nullItem";

	private String tooltip = "Tool Tips!";

	private int[] imageID = new int[] { 18, 18 };


	protected ArrayList<Enchantment> enchantments = new ArrayList<Enchantment>();

	public boolean getIsCounted() {
		return isCounted;
	}

	public void setIsCounted(boolean c) {
		isCounted = c;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double w) {
		weight = w;
	}

	public boolean getIsAxe() {
		return isAxe;
	}

	public boolean getIsPick() {
		return isPick;
	}

	public void setIsAxe(boolean a) {
		isAxe = a;
	}

	public void setIsPick(boolean p) {
		isPick = p;
	}

	public void setIsWeapon(boolean w) {
		isWeapon = w;
	}

	public void setFoundation(boolean isFoundation) {
		this.isFoundation = isFoundation;
	}

	public boolean isFoundation() {
		return isFoundation;
	}
	public boolean isEquipped(){
		return isEquipped;
	}
	public int[] getImageID() {
		return this.imageID;
	}

	public void setImageID(int x, int y) {
		this.imageID[0] = x;
		this.imageID[1] = y;
	}
	public ArrayList<Enchantment> getEnchantments() {
		return enchantments;
	}

	public Item() { }

	public Item(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		String returnString = "";
		for(Enchantment enchantment : enchantments){
			if(!enchantment.getPrefix().equalsIgnoreCase(""))returnString += enchantment.getPrefix() + " ";
		}
		returnString += name;
		for(Enchantment enchantment : enchantments){
			if(!enchantment.getSuffix().equalsIgnoreCase(""))returnString += " " + enchantment.getSuffix();
		}
		return returnString;
	}
	public Character getUser() {
		return user;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setRoad(boolean isRoad) {
		this.isRoad = isRoad;
	}

	public boolean isRoad() {
		return isRoad;
	}

	public void setInteractableItem(boolean isInteractableItem) {
		this.isInteractableItem = isInteractableItem;
	}

	public boolean isInteractableItem() {
		return isInteractableItem;
	}
	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getDamage() {
		return damage;
	}
	public short getRange(){
		return range;
	}
	public void setLight(boolean isLight) {
		this.isLight = isLight;
	}
	public boolean isLight() {
		return isLight;
	}
	public boolean isLeggings() {
		return isLeggings;
	}
	public void setLeggings(boolean isLeggings) {
		this.isLeggings = isLeggings;
		if(isLeggings)isArmor = true;
	}
	public boolean isChest() {
		return isChest;
	}
	public void setChest(boolean isChest) {
		this.isChest = isChest;
		if(isChest)isArmor = true;
	}
	public boolean isHat() {
		return isHat;
	}
	public void setHat(boolean isHat) {
		this.isHat = isHat;
		if(isHat)isArmor = true;
	}
	public boolean isOffhand() {
		return isOffhand;
	}
	public void setOffhand(boolean isOffhand) {
		this.isOffhand = isOffhand;
	}
	public boolean isArmor() {
		return isArmor;
	}
	public void setArmor(boolean isArmor) {
		this.isArmor = isArmor;
	}
	//TODO: This but for offhands V V V V V V V V
	public static RotatedBigPicture getRotatedPicture(Item item){
		return Images.rotatedBigPictures.get(item.getName().toLowerCase());
	}


	public void equip(Character user){
		this.user = user;
		this.isEquipped = true;
		/*
		for(Enchantment enchantment : enchantments){
			enchantment.apply(user);
		}
		 */
	}
	public void unequip(Character user){
		this.user = user;
		this.isEquipped = false;
		/*
		for(Enchantment enchantment : enchantments){
			enchantment.unapply(user);
		}
		 */
	}
	public Effect attack(Point p){
		if(user == null)throw new UncheckedDemigodsException("A Item without" +
				" a user cannot possibly be used, how did that happen?");

		if(user.getAnimation()!=null)return null;
		int dx = 0;
		int dy = 0;
		if(Math.abs(p.getX() - user.getX()) >=
				Math.abs(p.getY() - user.getY()))
		{
			if(p.getX() >= user.getX())dx = 1;
			else dx = -1;
		}
		else{
			if(p.getY() >= user.getY())dy = 1;
			else dy = -1;
		}

		if(dx == 0 && dy == -1){
			user.setDirection(Character.Direction.UP);
		}
		else if(dx == 0 && dy == 1){
			user.setDirection(Character.Direction.DOWN);
		}
		else if(dx == -1 && dy == 0){
			user.setDirection(Character.Direction.LEFT);

		}
		else if(dx == 1 && dy == 0){
			user.setDirection(Character.Direction.RIGHT);
		}

		double damage = this.damage + user.getStat(Character.ATTACK);
		Effect effect = null;
		int width = (int)user.getWidth() - 2*user.getImageXOffset();
		int height = (int)user.getHeight() - user.getImageYOffset();
		int centerXOffset = -user.getImageXOffset() + (int)(user.getWidth())/2;
		int centerYOffset = -user.getImageYOffset() + (int)(user.getHeight())/2;
		switch (this.getRange()){
			case 0://Dagger
				effect = new RotatedAttackEffect(user, p,damage, 0, 1, RotatedAttackEffect.PUNCH_ATTACK);
				//user.setAnimation(Animation.getOneHandedSwing(user.getDirection()));
				user.setAnimation(Animation.getRotatedSwing(user.getLoc(), p, 15, RotatedAttackEffect.DEFAULT_ATTACK, user.getImageXOffset(), user.getImageYOffset()));
				break;
			case 1://default

				effect = new RotatedAttackEffect(user, p,damage, 0, 1, RotatedAttackEffect.DEFAULT_ATTACK);

//				user.setAnimation(Animation.getOneHandedSwing(user.getDirection()));
				user.setAnimation(Animation.getRotatedSwing(user.getLoc(),p, 15, RotatedAttackEffect.DEFAULT_ATTACK, user.getImageXOffset(), user.getImageYOffset() ));

				break;
			case 2://Greatswords

				effect = new RotatedAttackEffect(user, p,damage, 0, 1, RotatedAttackEffect.GREATSWORD_ATTACK);

//				user.setAnimation(Animation.getTwoHandedSwing(user.getDirection()));
				user.setAnimation(Animation.getRotatedSwing(user.getLoc(),p, 15, RotatedAttackEffect.DEFAULT_ATTACK, user.getImageXOffset(), user.getImageYOffset() ));

				break;
			case 3://Spears

				effect = new RotatedAttackEffect(user, p,damage, 0, 1, RotatedAttackEffect.SPEAR_ATTACK);

//				user.setAnimation(Animation.getStab(user.getDirection()));
				user.setAnimation(Animation.getRotatedStab(user.getLoc(), p, 10, user.getImageXOffset(), user.getImageYOffset() ));
				break;
			case 4://Polearm
				if(dx == 0 && dy == -1){
					effect = new AttackEffect(damage, user, centerXOffset-16, -64, Images.swingAttackEffectSheet, 32, 32);
				}
				else if(dx == 0 && dy == 1){
					effect = new AttackEffect(damage, user, centerXOffset-16, height + 32, Images.swingAttackEffectSheet, 32, 32);
				}
				else if(dx == -1 && dy == 0){
					effect = new AttackEffect(damage, user, -64, -16, Images.swingAttackEffectSheet, 32, 32);
				}
				else if(dx == 1 && dy == 0){
					effect = new AttackEffect(damage, user, width + 32, -16, Images.swingAttackEffectSheet, 32, 32);
				}
//				user.setAnimation(Animation.getOneHandedSwing(user.getDirection()));
				user.setAnimation(Animation.getRotatedSwing(user.getLoc(),p, 15, RotatedAttackEffect.DEFAULT_ATTACK, user.getImageXOffset(), user.getImageYOffset() ));

				break;
		}
		if(effect == null)throw new UncheckedDemigodsException("Somehow " +
				"the item attacked, but didn't make an effect");

		Engine.add(effect);
		this.use();
		return effect;
	}


}