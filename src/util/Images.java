package util;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.Font;
import java.util.HashMap;

public class Images {

	public static SpriteSheet baldmanSheet;
	public static SpriteSheet orcSheet;
	public static SpriteSheet floatingTorsoSheet;
	public static SpriteSheet greenSlimeSheet;
	public static SpriteSheet blueSlimeSheet;
	public static SpriteSheet redSlimeSheet;
	public static SpriteSheet batSheet;
	public static SpriteSheet meepSheet;
	public static SpriteSheet greenLizardSheet;
	public static SpriteSheet ironGolemSheet;
	public static SpriteSheet ironGolemHandSheet;

    public static SpriteSheet humanHeadSheet;
    public static SpriteSheet humanHeadBWSheet;
    public static SpriteSheet humanChestSheet;
    public static SpriteSheet humanChestBWSheet;
    public static SpriteSheet humanLegsSheet;
    public static SpriteSheet humanLegsBWSheet;

	public static SpriteSheet skeletonHeadSheet;
	public static SpriteSheet skeletonChestSheet;
	public static SpriteSheet skeletonLegsSheet;

	public static SpriteSheet orcHeadSheet;
	public static SpriteSheet orcChestSheet;
	public static SpriteSheet orcLegsSheet;

	public static SpriteSheet squirrelHeadSheet;
	public static SpriteSheet squirrelChestSheet;
	public static SpriteSheet squirrelLegsSheet;
	public static Image squirrelHand;

	public static SpriteSheet goblinHeadSheet;
	public static SpriteSheet goblinChestSheet;
	public static SpriteSheet goblinLegsSheet;
	public static Image goblinHand;

	public static SpriteSheet itemSheet;
	public static SpriteSheet skillSheet;
	public static SpriteSheet tileSheet;
	public static SpriteSheet mapObjectSheet;
	public static SpriteSheet buildingSheet;

    public static Image companyLogo;
    public static Image menuScreen;
    public static Image tiles;
    public static Image inventoryPanel;
    public static Image baldmanHand;
    public static Image baldmanHandBW;
    public static Image orcHand;
    public static Image skeletonHand;
    public static Image testItem;
    public static Image terrain;

    public static Image scroll;
    public static Image scrollTop;
    public static Image scrollEdge;

	public static HashMap<String, RotatedBigPicture> rotatedBigPictures = new HashMap<String, RotatedBigPicture>();

	public static SpriteSheet bronzeArmorSheet;
	public static SpriteSheet ironArmorSheet;
	public static SpriteSheet paladinArmorSheet;
	public static SpriteSheet chronomancerArmorSheet;
	public static SpriteSheet blueWizardArmorSheet;
	public static SpriteSheet farmerArmorSheet;


	///////////
	//Effects//
	///////////

	public static SpriteSheet fireballEffectSheet;
	public static SpriteSheet lightningBallEffectSheet;
	public static SpriteSheet iceSpikeEffectSheet;
	public static SpriteSheet icicleEffectSheet;
	public static SpriteSheet sparkEffectSheet;


	public static Image arrowEffectImage;

	public static SpriteSheet swingAttackEffectSheet;
	public static SpriteSheet stabAttackEffectSheet;

	public static Image shadow;
	public static Image selectedArrow;
	public static SpriteSheet setDestAnimationSheet;
	public static Image cursor;
	public static Image panelBG;
	public static Image notification;
	public static Image notification02;
	public static Image darkness;


    @SuppressWarnings("unchecked")
    public static void init() throws SlickException {
        //characters should probably be initalized in a sub-method initCharacters(), with an arraylist and for loop for ease
//		Image humanImage = new Image("images/characters/humanoids/human.png");
//		humanImage.setFilter(Image.FILTER_NEAREST);
//		humanSheet = new SpriteSheet(humanImage, 32, 64);

		Image baldmanImage = new Image("img/characters/LittleBaldMan001.png");
		baldmanImage.setFilter(Image.FILTER_NEAREST);
		baldmanSheet = new SpriteSheet(baldmanImage, 32, 32);

        Image humanHead = new Image("img/characters/human/head.png");
        humanHead.setFilter(Image.FILTER_NEAREST);
        humanHeadSheet = new SpriteSheet(humanHead, 32, 32);
        Image humanHeadBW = new Image("img/characters/human/headBW.png");
        humanHeadBW.setFilter(Image.FILTER_NEAREST);
        humanHeadBWSheet = new SpriteSheet(humanHeadBW, 32, 32);
        Image humanChest = new Image("img/characters/human/chest.png");
        humanChest.setFilter(Image.FILTER_NEAREST);
        humanChestSheet = new SpriteSheet(humanChest, 32, 32);
        Image humanChestBW = new Image("img/characters/human/chestBW.png");
        humanChestBW.setFilter(Image.FILTER_NEAREST);
        humanChestBWSheet = new SpriteSheet(humanChestBW, 32, 32);
        Image humanLegs = new Image("img/characters/human/legs.png");
        humanLegs.setFilter(Image.FILTER_NEAREST);
        humanLegsSheet = new SpriteSheet(humanLegs, 32, 32);
        Image humanLegsBW = new Image("img/characters/human/legsBW.png");
        humanLegsBW.setFilter(Image.FILTER_NEAREST);
        humanLegsBWSheet = new SpriteSheet(humanLegsBW, 32, 32);

		Image skeletonHead = new Image("img/characters/skeleton/head.png");
		skeletonHead.setFilter(Image.FILTER_NEAREST);
		skeletonHeadSheet = new SpriteSheet(skeletonHead, 32, 32);
		Image skeletonChest = new Image("img/characters/skeleton/chest.png");
		skeletonChest.setFilter(Image.FILTER_NEAREST);
		skeletonChestSheet = new SpriteSheet(skeletonChest, 32, 32);
		Image skeletonLegs = new Image("img/characters/skeleton/legs.png");
		skeletonLegs.setFilter(Image.FILTER_NEAREST);
		skeletonLegsSheet = new SpriteSheet(skeletonLegs, 32, 32);

		Image orcHead = new Image("img/characters/orc/head.png");
		orcHead.setFilter(Image.FILTER_NEAREST);
		orcHeadSheet = new SpriteSheet(orcHead, 32, 32);
		Image orcChest = new Image("img/characters/orc/chest.png");
		orcChest.setFilter(Image.FILTER_NEAREST);
		orcChestSheet = new SpriteSheet(orcChest, 32, 32);
		Image orcLegs = new Image("img/characters/orc/legs.png");
		orcLegs.setFilter(Image.FILTER_NEAREST);
		orcLegsSheet = new SpriteSheet(orcLegs, 32, 32);

	    Image squirrelHead = new Image("img/characters/squirrel/head.png");
	    squirrelHead.setFilter(Image.FILTER_NEAREST);
	    squirrelHeadSheet = new SpriteSheet(squirrelHead, 32, 32);
	    Image squirrelChest = new Image("img/characters/squirrel/chest.png");
	    squirrelChest.setFilter(Image.FILTER_NEAREST);
	    squirrelChestSheet = new SpriteSheet(squirrelChest, 32, 32);
	    Image squirrelLegs = new Image("img/characters/squirrel/legs.png");
	    squirrelLegs.setFilter(Image.FILTER_NEAREST);
	    squirrelLegsSheet = new SpriteSheet(squirrelLegs, 32, 32);
	    squirrelHand = new Image("img/characters/squirrel/hand.png");
	    squirrelHand.setFilter(Image.FILTER_NEAREST);

	    Image goblinHead = new Image("img/characters/goblin/head.png");
	    goblinHead.setFilter(Image.FILTER_NEAREST);
	    goblinHeadSheet = new SpriteSheet(goblinHead, 32, 32);
	    Image goblinChest = new Image("img/characters/goblin/chest.png");
	    goblinChest.setFilter(Image.FILTER_NEAREST);
	    goblinChestSheet = new SpriteSheet(goblinChest, 32, 32);
	    Image goblinLegs = new Image("img/characters/goblin/legs.png");
	    goblinLegs.setFilter(Image.FILTER_NEAREST);
	    goblinLegsSheet = new SpriteSheet(goblinLegs, 32, 32);
	    goblinHand = new Image("img/characters/goblin/hand.png");
	    goblinHand.setFilter(Image.FILTER_NEAREST);
//		Image orcImage = new Image("img/characters/orc.png");
//		orcImage.setFilter(Image.FILTER_NEAREST);
//		orcSheet = new SpriteSheet(orcImage, 32, 32);

		Image ironGolem = new Image("img/characters/ironGolem.png");
		ironGolem.setFilter(Image.FILTER_NEAREST);
		ironGolemSheet = new SpriteSheet(ironGolem, 48, 48);
		Image ironGolemHand = new Image("img/characters/ironGolemHand.png");
		ironGolemHand.setFilter(Image.FILTER_NEAREST);
		ironGolemHandSheet = new SpriteSheet(ironGolemHand, 16, 16);

		Image floatingTorsoImage = new Image("img/characters/FloatingBody.png");
		floatingTorsoImage.setFilter(Image.FILTER_NEAREST);
		floatingTorsoSheet = new SpriteSheet(floatingTorsoImage, 64, 64);

		Image items = new Image("img/items/ItemGrid_alt.png");
		items.setFilter(Image.FILTER_NEAREST);
		itemSheet = new SpriteSheet(items, 16, 16);

		Image buildings = new Image("img/buildings/Buildings2.png");
		buildings.setFilter(Image.FILTER_NEAREST);
		buildingSheet = new SpriteSheet(buildings, 16, 16);

		Image greenSlime = new Image("img/characters/greenSlime.png");
		greenSlime.setFilter(Image.FILTER_NEAREST);
		greenSlimeSheet = new SpriteSheet(greenSlime, 16, 16);

		Image blueSlime = new Image("img/characters/blueSlime.png");
		blueSlime.setFilter(Image.FILTER_NEAREST);
		blueSlimeSheet = new SpriteSheet(blueSlime, 16, 16);

		Image redSlime = new Image("img/characters/redSlime.png");
		redSlime.setFilter(Image.FILTER_NEAREST);
		redSlimeSheet = new SpriteSheet(redSlime, 32, 32);

		Image bat = new Image("img/characters/bat.png");
		bat.setFilter(Image.FILTER_NEAREST);
		batSheet = new SpriteSheet(bat, 16, 16);

		Image meep = new Image("img/characters/meep.png");
		meep.setFilter(Image.FILTER_NEAREST);
		meepSheet = new SpriteSheet(meep, 32, 32);

		Image greenLizard = new Image("img/characters/greenLizard.png");
	    greenLizard.setFilter(Image.FILTER_NEAREST);
	    greenLizardSheet = new SpriteSheet(greenLizard, 32, 32);


		Image tiles = new Image("img/tiles/Tiles_alt.PNG");
		tiles.setFilter(Image.FILTER_NEAREST);
		tileSheet = new SpriteSheet(tiles, 16, 16);


		skillSheet = new SpriteSheet(new Image("img/skills/skillgrid.png"), 50, 50);
		mapObjectSheet = new SpriteSheet(new Image("img/tiles/MapObjects.png"), 16, 16);
		companyLogo = new Image("img/mainMenu/nthStudios_logo_4.png");
		menuScreen = new Image("img/mainMenu/menuScreen.png");
        terrain = new Image("img/mainMenu/terrain.png");

        scroll = new Image("img/panels/scroll.png");
        scrollTop = new Image("img/panels/scrollTop.png");
        scrollEdge = new Image("img/panels/scrollEdge.png");

        baldmanHand = new Image("img/characters/human/BaldManHand.png");
        baldmanHand.setFilter(Image.FILTER_NEAREST);
        baldmanHandBW = new Image("img/characters/human/BaldManHandBW.png");
        baldmanHandBW.setFilter(Image.FILTER_NEAREST);
        orcHand = new Image("img/characters/orc/orcHand.png");
        orcHand.setFilter(Image.FILTER_NEAREST);
        skeletonHand = new Image("img/characters/skeleton/hand.png");
        skeletonHand.setFilter(Image.FILTER_NEAREST);

		testItem = new Image("img/items/testItem.png");
		testItem.setFilter(Image.FILTER_NEAREST);


		//LOWER CASE!!!
//		rotatedBigPictures.put("steel axe",         new RotatedBigPicture("img/items/steelAxeh.png", 10, 7));


//		rotatedBigPictures.put("wood axe",        new RotatedBigPicture("img/items/woodAxeh.png", 10, 7));
		rotatedBigPictures.put("wood pickaxe",    new RotatedBigPicture("img/items/woodPickaxe.png", 10, 7));
//		rotatedBigPictures.put("wood sword",      new RotatedBigPicture("img/items/woodSwordh.png", 8, 8));
//		rotatedBigPictures.put("wood greatsword", new RotatedBigPicture("img/items/woodGreatswordh.png", 6, 8));
//		rotatedBigPictures.put("wood spear",      new RotatedBigPicture("img/items/woodSpearh.png", 10, 7));

	    rotatedBigPictures.put("sharpened stick",       new RotatedBigPicture("img/items/sharpenedStick.png", 8, 8));
	    rotatedBigPictures.put("small pointy stick",      new RotatedBigPicture("img/items/smallPointyStick.png", 2, 8));
	    rotatedBigPictures.put("small pointy stick_above",      new RotatedBigPicture("img/items/smallPointyStick.png", 2, 8));
	    rotatedBigPictures.put("small pointy stick_below",      new RotatedBigPicture("img/items/smallPointyStick.png", 2, 8));
	    rotatedBigPictures.put("large sharp stick",     new RotatedBigPicture("img/items/largeSharpStick.png", 6, 8));
	    rotatedBigPictures.put("wooden shield",         new RotatedBigPicture("img/items/woodenShield.png", 2, 8));
	    rotatedBigPictures.put("wooden shield_above",   new ShieldRotatedBigPicture("img/items/woodenShield_above.png", 8, 8));
	    rotatedBigPictures.put("wooden shield_below",   new ShieldRotatedBigPicture("img/items/woodenShield_below.png", 8, 8));

		rotatedBigPictures.put("bronze axe",        new RotatedBigPicture("img/items/bronzeAxeh.png", 10, 7));
		rotatedBigPictures.put("bronze pickaxe",    new RotatedBigPicture("img/items/bronzePickaxe.png", 10, 7));
		rotatedBigPictures.put("bronze sword",      new RotatedBigPicture("img/items/bronzeSwordh.png", 8, 8));
		rotatedBigPictures.put("bronze dagger",      new RotatedBigPicture("img/items/bronzeDagger.png", 2, 8));
		rotatedBigPictures.put("bronze dagger_above",      new RotatedBigPicture("img/items/bronzeDagger.png", 2, 8));
		rotatedBigPictures.put("bronze dagger_below",      new RotatedBigPicture("img/items/bronzeDagger.png", 2, 8));
		rotatedBigPictures.put("bronze greatsword", new RotatedBigPicture("img/items/bronzeGreatsword.png", 6, 8));
		rotatedBigPictures.put("bronze spear",      new RotatedBigPicture("img/items/bronzeSpearh.png", 16, 7));

		rotatedBigPictures.put("bone axe",        new RotatedBigPicture("img/items/boneAxeh.png", 10, 7));
//		rotatedBigPictures.put("bone pickaxe",        new RotatedBigPicture("img/items/boneAxeh.png", 10, 7));
		rotatedBigPictures.put("bone sword",      new RotatedBigPicture("img/items/boneSwordh.png", 8, 8));
//		rotatedBigPictures.put("bone dagger",      new RotatedBigPicture("img/items/boneDagger.png", 2, 8));
//		rotatedBigPictures.put("bone dagger_above",      new RotatedBigPicture("img/items/boneDagger.png", 2, 8));
//		rotatedBigPictures.put("bone dagger_below",      new RotatedBigPicture("img/items/boneDagger.png", 2, 8));
//		rotatedBigPictures.put("bone greatsword", new RotatedBigPicture("img/items/boneGreatsword.png", 6, 8));
		rotatedBigPictures.put("bone spear",      new RotatedBigPicture("img/items/boneSpearh.png", 16, 7));
//		rotatedBigPictures.put("bone scythe",      new RotatedBigPicture("img/items/boneSpearh.png", 10, 7));

		rotatedBigPictures.put("iron axe",         new RotatedBigPicture("img/items/ironAxeh.png", 10, 7));
		rotatedBigPictures.put("iron pickaxe",         new RotatedBigPicture("img/items/ironPickaxe.png", 10, 7));
		rotatedBigPictures.put("iron sword",       new RotatedBigPicture("img/items/ironSwordh.png", 8, 8));
		rotatedBigPictures.put("iron dagger",       new RotatedBigPicture("img/items/ironDagger.png", 2, 8));
		rotatedBigPictures.put("iron dagger_above",       new RotatedBigPicture("img/items/ironDagger.png", 2, 8));
		rotatedBigPictures.put("iron dagger_below",       new RotatedBigPicture("img/items/ironDagger.png", 2, 8));
		rotatedBigPictures.put("iron greatsword",  new RotatedBigPicture("img/items/ironGreatsword.png", 6, 8));
		rotatedBigPictures.put("iron spear",       new RotatedBigPicture("img/items/ironSpearh.png", 16, 7));
		rotatedBigPictures.put("iron scythe",       new RotatedBigPicture("img/items/ironScythe.png", 10, 7));

		rotatedBigPictures.put("steel axe",         new RotatedBigPicture("img/items/steelAxeh.png", 10, 7));
		rotatedBigPictures.put("steel pickaxe",         new RotatedBigPicture("img/items/steelPickaxe.png", 10, 7));
		rotatedBigPictures.put("steel sword",       new RotatedBigPicture("img/items/steelSwordh.png", 8, 8));
		rotatedBigPictures.put("steel dagger",       new RotatedBigPicture("img/items/steelDagger.png", 2, 8));
		rotatedBigPictures.put("steel dagger_below",       new RotatedBigPicture("img/items/steelDagger.png", 2, 8));
		rotatedBigPictures.put("steel dagger_above",       new RotatedBigPicture("img/items/steelDagger.png", 2, 8));
		rotatedBigPictures.put("steel greatsword",  new RotatedBigPicture("img/items/steelGreatsword.png", 6, 8));
		rotatedBigPictures.put("steel spear",       new RotatedBigPicture("img/items/steelSpearh.png", 16, 7));
		rotatedBigPictures.put("steel scythe",       new RotatedBigPicture("img/items/steelScythe.png", 10, 7));

		rotatedBigPictures.put("blackmetal axe",         new RotatedBigPicture("img/items/blackMetalAxeh.png", 10, 7));
		rotatedBigPictures.put("blackmetal sword",       new RotatedBigPicture("img/items/blackMetalSwordh.png", 8, 8));
//		rotatedBigPictures.put("blackmetal dagger",       new RotatedBigPicture("img/items/blackMetalDagger.png", 2, 8));
//		rotatedBigPictures.put("blackmetal dagger_above",       new RotatedBigPicture("img/items/blackMetalDagger.png", 2, 8));
//		rotatedBigPictures.put("blackmetal dagger_below",       new RotatedBigPicture("img/items/blackMetalDagger.png", 2, 8));
//		rotatedBigPictures.put("blackmetal greatsword",  new RotatedBigPicture("img/items/blackMetalGreatsword.png", 6, 8));
		rotatedBigPictures.put("blackmetal spear",       new RotatedBigPicture("img/items/blackMetalSpearh.png", 16, 7));
		rotatedBigPictures.put("blackmetal scythe",       new RotatedBigPicture("img/items/blackMetalScythe.png", 10, 7));

		rotatedBigPictures.put("xerium axe",         new RotatedBigPicture("img/items/xeriumAxeh.png", 10, 7));
		rotatedBigPictures.put("xerium pickaxe",         new RotatedBigPicture("img/items/xeriumPickaxe.png", 10, 7));
		rotatedBigPictures.put("xerium sword",       new RotatedBigPicture("img/items/xeriumSwordh.png", 8, 8));
		rotatedBigPictures.put("xerium dagger",       new RotatedBigPicture("img/items/xeriumDagger.png", 2, 8));
		rotatedBigPictures.put("xerium dagger_above",       new RotatedBigPicture("img/items/xeriumDagger.png", 2, 8));
		rotatedBigPictures.put("xerium dagger_below",       new RotatedBigPicture("img/items/xeriumDagger.png", 2, 8));
		rotatedBigPictures.put("xerium greatsword",  new RotatedBigPicture("img/items/xeriumGreatsword.png", 6, 8));
		rotatedBigPictures.put("xerium spear",       new RotatedBigPicture("img/items/xeriumSpearh.png", 16, 7));
		rotatedBigPictures.put("xerium scythe",       new RotatedBigPicture("img/items/xeriumScythe.png", 10, 7));

		rotatedBigPictures.put("blood xerium axe",         new RotatedBigPicture("img/items/bloodXeriumAxeh.png", 10, 7));
		rotatedBigPictures.put("blood xerium pickaxe",         new RotatedBigPicture("img/items/bloodXeriumPickaxe.png", 10, 7));
		rotatedBigPictures.put("blood xerium sword",       new RotatedBigPicture("img/items/bloodXeriumSwordh.png", 8, 8));
		rotatedBigPictures.put("blood xerium dagger",       new RotatedBigPicture("img/items/bloodXeriumDagger.png", 2, 8));
		rotatedBigPictures.put("blood xerium dagger_above",       new RotatedBigPicture("img/items/bloodXeriumDagger.png", 2, 8));
		rotatedBigPictures.put("blood xerium dagger_below",       new RotatedBigPicture("img/items/bloodXeriumDagger.png", 2, 8));
		rotatedBigPictures.put("blood xerium greatsword",  new RotatedBigPicture("img/items/bloodXeriumGreatsword.png", 6, 8));
		rotatedBigPictures.put("blood xerium spear",       new RotatedBigPicture("img/items/bloodXeriumSpear.png", 16, 7));
		rotatedBigPictures.put("blood xerium scythe",       new RotatedBigPicture("img/items/bloodXeriumScythe.png", 10, 7));

		rotatedBigPictures.put("stone greatsword", new RotatedBigPicture("img/items/stoneGreatswordh.png", 6, 8));
	    rotatedBigPictures.put("stone spear",      new RotatedBigPicture("img/items/stoneSpearh.png", 16, 7));



	    rotatedBigPictures.put("god sword", new RotatedBigPicture("img/items/godSword.png", 5, 8));
	    rotatedBigPictures.put("chronomancer blade", new RotatedBigPicture("img/items/chronomancerBlade.png", 6, 8));

		rotatedBigPictures.put("wooden bow", new RotatedBigPicture("img/items/woodenBow.png", 15, 9));

	    rotatedBigPictures.put("test shield",       new RotatedBigPicture("img/items/steelDagger.png", 2, 8));
	    rotatedBigPictures.put("test shield_below",       new RotatedBigPicture("img/items/bloodXeriumSpear.png", 2, 8));
	    rotatedBigPictures.put("test shield_above",       new RotatedBigPicture("img/items/xeriumScythe.png", 2, 8));

		Image bronzeArmor = new Image("img/items/armor/bronze.png");
		bronzeArmor.setFilter(Image.FILTER_NEAREST);
		bronzeArmorSheet = new SpriteSheet(bronzeArmor, 32, 32);

		Image ironArmor = new Image("img/items/armor/iron.png");
		ironArmor.setFilter(Image.FILTER_NEAREST);
		ironArmorSheet = new SpriteSheet(ironArmor, 32, 32);

	    Image paladinArmor = new Image("img/items/armor/paladin.png");
	    paladinArmor.setFilter(Image.FILTER_NEAREST);
	    paladinArmorSheet = new SpriteSheet(paladinArmor, 32, 32);

		Image chronomancerArmor = new Image("img/items/armor/chronomancer.png");
		chronomancerArmor.setFilter(Image.FILTER_NEAREST);
		chronomancerArmorSheet = new SpriteSheet(chronomancerArmor, 32, 32);

		Image blueWizardArmor = new Image("img/items/armor/blueWizard.png");
		blueWizardArmor.setFilter(Image.FILTER_NEAREST);
		blueWizardArmorSheet = new SpriteSheet(blueWizardArmor, 32, 32);

		Image farmerArmor = new Image("img/items/armor/farmer.png");
		farmerArmor.setFilter(Image.FILTER_NEAREST);
		farmerArmorSheet = new SpriteSheet(farmerArmor, 32, 32);

		Image fireballEffect = new Image("img/effects/spells/fireballEffect.png");
		fireballEffect.setFilter(Image.FILTER_NEAREST);
		fireballEffectSheet = new SpriteSheet(fireballEffect, 16, 16);


		Image lightningBallEffect = new Image("img/effects/spells/lightningBallEffect.png");
		fireballEffect.setFilter(Image.FILTER_NEAREST);
		lightningBallEffectSheet = new SpriteSheet(lightningBallEffect, 16, 16);


		Image iceSpikeEffect = new Image("img/effects/spells/iceSpikeEffect.png");
		iceSpikeEffect.setFilter(Image.FILTER_NEAREST);
		iceSpikeEffectSheet = new SpriteSheet(iceSpikeEffect, 16, 16);

		Image icicleEffect = new Image("img/effects/spells/icicleEffect.png");
		icicleEffect.setFilter(Image.FILTER_NEAREST);
		icicleEffectSheet = new SpriteSheet(icicleEffect, 16, 16);

		Image sparkEffect = new Image("img/effects/spells/sparkEffect.png");
		sparkEffect.setFilter(Image.FILTER_NEAREST);
		sparkEffectSheet = new SpriteSheet(sparkEffect, 16, 16);

		arrowEffectImage = new Image("img/effects/arrowEffect.png");
		arrowEffectImage.setFilter(Image.FILTER_NEAREST);

		Image oneHandHorizontalAttackEffect = new Image("img/effects/swingAttackEffect.png");
		oneHandHorizontalAttackEffect.setFilter(Image.FILTER_NEAREST);
		swingAttackEffectSheet = new SpriteSheet(oneHandHorizontalAttackEffect, 32, 32);

		Image stabAttackEffect = new Image("img/effects/stabEffect.png");
		stabAttackEffect.setFilter(Image.FILTER_NEAREST);
		stabAttackEffectSheet = new SpriteSheet(stabAttackEffect, 16, 16);


		inventoryPanel = new Image("img/panels/inventoryPanel.png");
		//this^ entire image needs a re-work. I'm thinking a scroll?

		itemSheet.setFilter(Image.FILTER_NEAREST);
		buildingSheet.setFilter(Image.FILTER_NEAREST);
		greenSlimeSheet.setFilter(Image.FILTER_NEAREST);
		tileSheet.setFilter(Image.FILTER_NEAREST);
		mapObjectSheet.setFilter(Image.FILTER_NEAREST);

		shadow = new Image("img/effects/shadow.png");
		shadow.setFilter(Image.FILTER_NEAREST);
		selectedArrow = new Image("img/effects/selectedIcon.png");
		selectedArrow.setFilter(Image.FILTER_NEAREST);


		Image setDest = new Image("img/effects/setDestAnimation3.png");
		setDest.setFilter(Image.FILTER_NEAREST);
		setDestAnimationSheet = new SpriteSheet(setDest, 16, 16);

		cursor = new Image("img/mainMenu/cursor.png");

	    panelBG = new Image("img/panels/panelBG.png");
	    panelBG.setFilter(Image.FILTER_NEAREST);

	    notification = new Image("img/panels/notification.png");
	    notification.setFilter(Image.FILTER_NEAREST);
	    notification02 = new Image("img/panels/notification002.png");
	    notification02.setFilter(Image.FILTER_NEAREST);

	    darkness= new Image("img/effects/darknessOverlay.png");

    }
}
