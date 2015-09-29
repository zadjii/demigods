package gui.guiPanels;

import java.io.Serializable;
import java.util.ArrayList;

import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import skills.*;

import entities.characters.Character;

public abstract class SkillListItem implements Serializable, Comparable {
	private static final SkillListItem[] SKILL_LIST_ITEMS =
			new SkillListItem[]{

					//FIRE SPELLS
					new BurnSLI(),
					new FireballSLI(),
					new FlareSLI(),
					new FlamethrowerSLI(),
					new FirestormSLI(),
					new MeteorSLI(),
					new ArmageddonSLI(),

					//ICE/WATER SPELLS
					new IcicleSLI(),
					new IceSpikeSLI(),
					new FrostSLI(),
					new BlizzardSLI(),
					new WaveSLI(),
					new PulseSLI(),
					new SummonIceElementalSLI(),
					new TyphoonSLI(),

					// AIR SPELLS
					new AirBladeSLI(),
					new SparkSLI(),
					new ThunderStormSLI(),

					//EARTH SPELLS
					new PillarSLI(),
					new TremorSLI(),

					//PURPLE SPELLS
					new ConjureBladeSLI(),
					new CastBladeSLI(),

					//LIGHT SPELLS
					new LightJavelinSLI(),
					new SmiteSLI(),
					new HealingLightSLI(),

					//DARK SPELLS
					new DarkTendrilSLI(),
					new DarkShardSLI(),

					//MAGE SKILS
					new EnergyRuneSLI(),

					//MELEE SKILS
					new BashSLI(),
					new PowerStrikeSLI(),
					new RendSLI(),
					new CleaveSLI(),
					new ThrowWeaponSLI(),
					new TripleSlashSLI(),
					new RallyingStrikeSLI(),
					new LungeSLI(),

					//ADRENALINE SKILLS

					//COMBO SKILLS
					new ThiefsGambleSLI(),

					//COMBO SKILLS
					new QuickStrikeSLI(),

			};
	protected static final Color WHITE =        new Color(1.0f, 1.0f, 1.0f, 0.5f);
	protected static final Color MELEE_NORMAL = new Color(1.0f, 0.85f, 0.75f, 0.5f);
	protected static final Color FIRERED =      new Color(1.0f, 0.5f, 0.35f, 0.5f);
	protected static final Color ICEBLUE =      new Color(0.5f, 0.5f, 0.8f, 0.5f);
	protected static final Color WATERBLUE =    new Color(0.2f, 0.2f, 0.9f, 0.5f);
	protected static final Color AIRGREEN =     new Color(0.2f, 0.9f, 0.5f, 0.5f);
	protected static final Color LIGHTNING_GREEN = new Color(0.1f, 0.9f, 0.2f, 0.5f);
	protected static final Color EARTHBROWN=    new Color(0.4f, 0.4f, 0.1f, 0.5f);
	protected static final Color PURPLE =       new Color(0.6f, 0.1f, 0.6f, 0.5f);
	protected static final Color LIGHT =        new Color(1.0f, 0.95f, 0.85f, 0.6f);
	protected static final Color DARKBLACK =    new Color(0.2f, 0.2f, 0.2f, 0.5f);
	protected static final Color THIEFGREY =    new Color(0.2f, 0.2f, 0.2f, 0.5f);
	protected static final Color MAGEBLUE =     new Color(0.2f, 0.2f, 0.2f, 0.5f);

	public boolean satisfiesPreReqs(Character character){
		return false;
	}
	public boolean satisfiesVisibilityPreReqs(Character character){
		return false;
	}
	public void unlock(Character character){//todo this should be made abstract, to get all the still locked skills
//		System.err.println(
//				"^^^UNIMPLEMENTED"
//		);
		character.removeSP(this.getSPCost());

		try{
			throw new Exception("SkillListItem.unlock was called, the base implementation. You didn't get your skill");
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Shouldnt ever be called without ALREADY checking that the pre-reqs
	 * have been met.
	 *
	 * This ONLY means that the character has enough SP. The prereqs NEED to have been checked prior.
	 * @param character
	 * @return
	 */
	protected boolean unlockable(Character character){
		if(SkillListItem.skillListContains(character, this.getName())){
			Skill skill =
					SkillListItem.getSkillFromName(character, this.getName());
			if(skill.getLevel() >= skill.getMaxLevel()){
				return false;
			}
		}
		return true;
	}
	public Point getImageLoc(){
		return new Point(1,1);//todo:switch default to 0,0
	}
	public String getName(){
		return "nullSkill";
	}
	public int getSPCost(){
		return 0;
	}
	public static ArrayList<SkillListItem> populateSkillList(Character character){
		ArrayList<SkillListItem> list = new ArrayList<SkillListItem>();
		ArrayList<SkillListItem> randomList = new ArrayList<SkillListItem>();
		for(SkillListItem item : SKILL_LIST_ITEMS){
			if(item.satisfiesVisibilityPreReqs(character))list.add(item);
		}
		return list;
//		while(!list.isEmpty()){
//			int index = (int)(Math.random() * (list.size()-1));
//			SkillListItem item = list.remove(index);
//			randomList.add(item);
//		}
//		return randomList;

	}
	public Color getBackgroundColor(){
		return WHITE;
	}
	public static boolean skillListContains(Character character, String skillName){
		for(Skill skill : character.getSkills()){
			if(skill.toString().equalsIgnoreCase(skillName))
				return true;
		}
		return false;
	}
	public static boolean conditionsListContains(Character character, String skillName){
//
		return character.getConditionMap().containsKey(skillName);
//		for(Condition condition : character.getConditions()){
//			if(condition.toString().equalsIgnoreCase(skillName))
//				return true;
//		}
//		return false;
	}
	public static Skill getSkillFromName(Character character, String name){
		for(Skill skill : character.getSkills()){
			if(skill.toString().equalsIgnoreCase(name))
				return skill;
		}
		return null;
	}
	public int compareTo(Object o) {
		if(o != null){
			// Dirty hacks ftl...
			return ((SkillListItem)o).getName().compareTo(this.getName());
		}
		return -1;
	}
	protected void unlockSkillForCharacter(Character c, Skill newSkill){
		if(unlockable(c)){
			Skill skill = getSkillFromName(c, newSkill.toString());
			if(skill!=null)skill.levelUp();
			else{
				c.getSkills().add(newSkill);
			}
		}
		c.removeSP(this.getSPCost());
	}
}

////////////////////////////////
//      FIRE SPELLS
////////////////////////////////
class BurnSLI extends SkillListItem{
	public String getName(){
		return "Burn";
	}
	public boolean satisfiesVisibilityPreReqs(Character character){
		return true;
	}
	public boolean satisfiesPreReqs(Character character){
		return true;
	}
	public int getSPCost(){
		return 1;
	}
	public void unlock(Character character){
		unlockSkillForCharacter(character, new Burn());
	}
	public Color getBackgroundColor(){return FIRERED;}
}
///////////////////////////////////////////////////////////////////////////////
class FireballSLI extends SkillListItem{
	public String getName(){
		return "Fireball";
	}
	public int getSPCost(){
		return 1;
	}
	public boolean satisfiesVisibilityPreReqs(Character character){
		return true;
	}
	public boolean satisfiesPreReqs(Character character){
		return (SkillListItem.skillListContains(character, "burn")
				//&& SkillListItem.conditionsListContains(character, "fire knowledge")
		);
	}
	public Color getBackgroundColor(){return FIRERED;}
	public void unlock(Character character){
		unlockSkillForCharacter(character, new Fireball());
	}
}
///////////////////////////////////////////////////////////////////////////////
class FlareSLI extends SkillListItem{
	public String getName(){
		return "Flare";
	}
	public int getSPCost(){
		return 1;
	}
	public boolean satisfiesPreReqs(Character character){
		boolean hasRequiredSkills =
				SkillListItem.skillListContains(character, "burn")
				&& SkillListItem.skillListContains(character, "fireball"
//			    && SkillListItem.conditionsListContains(character, "fire knowledge")
				);
		return (hasRequiredSkills);
	}
	public Color getBackgroundColor(){return FIRERED;}
}
class FlamethrowerSLI extends SkillListItem{
	public String getName(){
		return "Flamethrower";
	}
	public int getSPCost(){
		return 2;
	}
	public boolean satisfiesPreReqs(Character character){
		boolean hasRequiredSkills =
				SkillListItem.skillListContains(character, "burn")
						&& SkillListItem.skillListContains(character, "fireball"
//			    && SkillListItem.conditionsListContains(character, "fire knowledge")
				);
		return (hasRequiredSkills);
	}
	public void unlock(Character character){
		unlockSkillForCharacter(character, new Flamethrower());
	}
	public Color getBackgroundColor(){return FIRERED;}
}
class FirestormSLI extends SkillListItem{
	public String getName(){
		return "Firestorm";
	}
	public int getSPCost(){
		return 2;
	}
	public Color getBackgroundColor(){return FIRERED;}
}
class MeteorSLI extends SkillListItem{
	public String getName(){
		return "Meteor";
	}
	public int getSPCost(){
		return 2;
	}
	public Color getBackgroundColor(){return FIRERED;}
}
class ArmageddonSLI extends SkillListItem{
	public String getName(){
		return "Armageddon";
	}
	public int getSPCost(){
		return 10;
	}
	public Color getBackgroundColor(){return FIRERED;}
}

////////////////////////////////
//      ICE/WATER SPELLS
////////////////////////////////
class IcicleSLI extends SkillListItem{
	public String getName(){
		return "Icicle";
	}
	public int getSPCost(){
		return 1;
	}public boolean satisfiesVisibilityPreReqs(Character character){
		return true;
	}
	public boolean satisfiesPreReqs(Character character){
		return true;
	}
	public void unlock(Character character){
		unlockSkillForCharacter(character, new Icicle());
	}
	public Color getBackgroundColor(){return ICEBLUE;}
}
class IceSpikeSLI extends SkillListItem{
	public String getName(){
		return "Ice Spike";
	}
	public int getSPCost(){
		return 1;
	}
	public boolean satisfiesVisibilityPreReqs(Character character){
		return true;
	}
	public boolean satisfiesPreReqs(Character character){
		boolean hasRequiredSkills =(
				SkillListItem.skillListContains(character, "icicle")
				);
		return (hasRequiredSkills);
	}
	public void unlock(Character character){
		unlockSkillForCharacter(character, new IceSpike());
	}
	public Color getBackgroundColor(){return ICEBLUE;}
}
class FrostSLI extends SkillListItem{
	/*
		Ice version of Burn
	 */
	public String getName(){
		return "Frost";
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return ICEBLUE;}
}
class WaveSLI extends SkillListItem{
	/*
		Releases a torrent of water at target location that rushes
		 outwards, dealing damage and moving foes backward
	 */
	public String getName(){
		return "Wave";
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return WATERBLUE;}
}
class PulseSLI extends SkillListItem{
	/*
		Releases a pulse at tgt location.
		 marked foes take more damage from the next air damage.
	 */
	public String getName(){
		return "Pulse";
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return WATERBLUE;}
}
class BlizzardSLI extends SkillListItem{
	public String getName(){
		return "Blizzard";
	}
	public int getSPCost(){
		return 2;
	}
	public Color getBackgroundColor(){return ICEBLUE;}
}
class SummonIceElementalSLI extends SkillListItem{
	public String getName(){
		return "Summon: Ice Elemental";
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return ICEBLUE;}
}
class TyphoonSLI extends SkillListItem{
	public String getName(){
		return "Typhoon";
	}
	public int getSPCost(){
		return 5;
	}
	public Color getBackgroundColor(){return WATERBLUE;}
}

////////////////////////////////
//      AIR SPELLS
////////////////////////////////


class SparkSLI extends SkillListItem{
	public String getName(){
		return "Spark";
	}
	public boolean satisfiesVisibilityPreReqs(Character character){
		return true;
	}
	public boolean satisfiesPreReqs(Character character){
		return true;
	}
	public void unlock(Character character){
		unlockSkillForCharacter(character, new Spark());
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return LIGHTNING_GREEN;}
}
class AirBladeSLI extends SkillListItem{
	public String getName(){
		return "Air Blade";
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return AIRGREEN;}
}
class ThunderStormSLI extends SkillListItem{
	public String getName(){
		return "Thunder Storm";
	}
	public void unlock(Character character){
		unlockSkillForCharacter(character, new ThunderStorm());
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return LIGHTNING_GREEN;}
}

////////////////////////////////
//      EARTH SPELLS
////////////////////////////////
class PillarSLI extends SkillListItem{
	public String getName(){
		return "Pillar";
	}
	public boolean satisfiesVisibilityPreReqs(Character character){
		return true;
	}
	public boolean satisfiesPreReqs(Character character){
		return true;
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return EARTHBROWN;}
}
class TremorSLI extends SkillListItem{
	public String getName(){
		return "Tremor";
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return EARTHBROWN;}
}

////////////////////////////////
//      PURPLE SPELLS
////////////////////////////////
class ConjureBladeSLI extends SkillListItem{
	public String getName(){
		return "Conjure Blade";
	}
	public boolean satisfiesVisibilityPreReqs(Character character){
		return true;
	}
	public boolean satisfiesPreReqs(Character character){
		return true;
	}
	public void unlock(Character character){
		unlockSkillForCharacter(character, new ConjureBlade());
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return PURPLE;}
}
class CastBladeSLI extends SkillListItem{
	public String getName(){
		return "Cast Blade";
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return PURPLE;}
}

////////////////////////////////
//      LIGHT SPELLS
////////////////////////////////
class LightJavelinSLI extends SkillListItem{
	public String getName(){
		return "Light Javelin";
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return LIGHT;}
}
class SmiteSLI extends SkillListItem{
	public String getName(){
		return "Smite";
	}
	public boolean satisfiesVisibilityPreReqs(Character character){
		return true;
	}
	public boolean satisfiesPreReqs(Character character){
		return true;
	}
	public void unlock(Character character){
		unlockSkillForCharacter(character, new Smite());
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return LIGHT;}
}
class HealingLightSLI extends SkillListItem{
	public String getName(){
		return "Healing Light";
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return LIGHT;}
}
////////////////////////////////
//      DARK SPELLS
////////////////////////////////
class DarkTendrilSLI extends SkillListItem{
	public String getName(){
		return "Dark Tendril";
	}

	public boolean satisfiesVisibilityPreReqs(Character character){
		return true;
	}
	public boolean satisfiesPreReqs(Character character){
		return true;
	}
	public void unlock(Character character){
		unlockSkillForCharacter(character, new DarkTendril());
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return DARKBLACK;}
}
class DarkShardSLI extends SkillListItem{
	public String getName(){
		return "Dark Shard";
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return DARKBLACK;}
}
////////////////////////////////
//      ENERGY SPELLS
////////////////////////////////
class EnergyRuneSLI extends SkillListItem{
	public String getName(){
		return "Energy Rune";
	}

	public boolean satisfiesVisibilityPreReqs(Character character){
		return true;
	}
	public boolean satisfiesPreReqs(Character character){
		return true;
	}
	public void unlock(Character character){
		unlockSkillForCharacter(character, new EnergyRune());
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return MAGEBLUE;}
}
////////////////////////////////
//      MELEE SKILLS
////////////////////////////////
class BashSLI extends SkillListItem{
	public String getName(){
		return "Bash";
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return MELEE_NORMAL;}
}
class PowerStrikeSLI extends SkillListItem{
	public String getName(){
		return "Power Strike";
	}
	public boolean satisfiesVisibilityPreReqs(Character character){
		return true;
	}
	public boolean satisfiesPreReqs(Character character){
		return true;
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return MELEE_NORMAL;}
}
class RendSLI extends SkillListItem{
	public String getName(){
		return "Rend";
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return MELEE_NORMAL;}
}
class CleaveSLI extends SkillListItem{
	public String getName(){
		return "Cleave";
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return MELEE_NORMAL;}
}
class ThrowWeaponSLI extends SkillListItem{
	public String getName(){
		return "Throw Weapon";
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return MELEE_NORMAL;}
}
class TripleSlashSLI extends SkillListItem{
	public String getName(){
		return "Triple Slash";
	}
	public boolean satisfiesVisibilityPreReqs(Character character){
		return true;
	}
	public boolean satisfiesPreReqs(Character character){
		return true;
	}
	public void unlock(Character character){
		unlockSkillForCharacter(character, new TripleSlash());
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return MELEE_NORMAL;}
}
class RallyingStrikeSLI extends SkillListItem{
	public String getName(){
		return "Rallying Stike";
	}
	public boolean satisfiesVisibilityPreReqs(Character character){
		return true;
	}
	public boolean satisfiesPreReqs(Character character){
		return true;
	}
	public void unlock(Character character){
		unlockSkillForCharacter(character, new RallyingStrike());
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return MELEE_NORMAL;}
}
class LungeSLI extends SkillListItem{
	public String getName(){
		return "Lunge";
	}
	public boolean satisfiesVisibilityPreReqs(Character character){
		return true;
	}
	public boolean satisfiesPreReqs(Character character){
		return true;
	}
	public void unlock(Character character){
		unlockSkillForCharacter(character, new Lunge());
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return MELEE_NORMAL;}
}


////////////////////////////////
//      THIEF SKILLS SKILLS
////////////////////////////////
class ThiefsGambleSLI extends SkillListItem{
	public String getName(){
		return "Thief's Gamble";
	}
	public boolean satisfiesVisibilityPreReqs(Character character){
		return true;
	}
	public boolean satisfiesPreReqs(Character character){
		return true;
	}
	public void unlock(Character character){
		unlockSkillForCharacter(character, new ThiefsGamble());
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return THIEFGREY;}
}

////////////////////////////////
//      COMBO SKILLS
////////////////////////////////

class QuickStrikeSLI extends SkillListItem{
	public String getName(){
		return "Quick Strike";
	}
	public boolean satisfiesVisibilityPreReqs(Character character){
		return true;
	}
	public boolean satisfiesPreReqs(Character character){
		return true;
	}
	public void unlock(Character character){
		unlockSkillForCharacter(character, new QuickStrike());
	}
	public int getSPCost(){
		return 1;
	}
	public Color getBackgroundColor(){return MELEE_NORMAL;}
}