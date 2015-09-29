package util;

import items.armor.BronzeChest;
import items.armor.ChronomancerHat;
import items.foundations.*;
import items.materials.*;
import items.placements.*;
import items.tools.*;
import items.weapons.*;
import org.newdawn.slick.Color;
import items.*;
import items.armor.*;
import util.exceptions.DemigodsException;
import util.exceptions.UncheckedDemigodsException;

public class Constants {

    public static final Color XP_COLOR = Color.yellow;

	public static final Color DARK_BEIGE = new Color(153, 132, 98);
	public static final Color DARKER_BEIGE = new Color(113, 92, 58);

	public static final int TICKS_PER_SECOND = 60;
	public static final int LARGEST_CHARACTER_SIZE = 5*16;
	//THIS WILL NEED TO BE UPDATED REGULARILY

	public static final int OVERWORLD_ID = -1;

	public static final int HUT_GRID_SIZE = 3;
	public static final int HOUSE_GRID_SIZE = 5;
	public static final int BU = 5;//building unit.

	public static final short PLAYERTEAM = 0;
	public static final short MONSTERTEAM = -1;

	private static final int speedRatio = 9;

	public static final int DAGGER_SPEED        = 18;
	public static final int ITEM_SPEED          = 45;
	public static final int SPEAR_SPEED         = 72;
	public static final int GREATSWORD_SPEED    = 90;
	public static final int HAMMER_SPEED        = 126;

	public static final int DAGGER_RANGE        = 0;
	public static final int ITEM_RANGE          = 1;
	public static final int SWORD_RANGE         = 1;
	public static final int GREATSWORD_RANGE    = 2;
	public static final int SPEAR_RANGE         = 3;
	public static final int POLEARM_RANGE       = 4;
	public static final int HAMMER_RANGE        = 5;

	public static final int WOOD_TOOL_WEIGHT = 1;
	public static final int STONE_TOOL_WEIGHT = 5;
	public static final int BONE_TOOL_WEIGHT = 2;
	public static final int BRONZE_TOOL_WEIGHT = 3;
	public static final int IRON_TOOL_WEIGHT = 4;
	public static final int STEEL_TOOL_WEIGHT = 5;
	//public static final int TITANIUM_TOOL_WEIGHT = 6;
	public static final int BLACKMETAL_TOOL_WEIGHT = 6;
	public static final int XERIUM_TOOL_WEIGHT = 5;
	public static final int BLOOD_XERIUM_TOOL_WEIGHT = 3;

//	public static final int WOOD_TOOL_DURABILITY = 50;
//	public static final int STONE_TOOL_DURABILITY = 200;
//	public static final int BONE_TOOL_DURABILITY = 150;
//	public static final int IRON_TOOL_DURABILITY = 500;
//	public static final int STEEL_TOOL_DURABILITY = 1000;
//	public static final int TITANIUM_TOOL_DURABILITY = 2000;
//	public static final int BLACKMETAL_TOOL_DURABILITY = 2000;
//	public static final int XERIUM_TOOL_DURABILITY = 10000;
//	public static final int BLOOD_XERIUM_TOOL_DURABILITY = 1000000000;

	public static final double WOOD_TOOL_UNITS_PER_SECOND = .4;
	public static final double STONE_TOOL_UNITS_PER_SECOND = .45;
	public static final double BONE_TOOL_UNITS_PER_SECOND = .5;
	public static final double BRONZE_TOOL_UNITS_PER_SECOND = .6;
	public static final double IRON_TOOL_UNITS_PER_SECOND = .7;
	public static final double STEEL_TOOL_UNITS_PER_SECOND = .75;
	//public static final double TITANIUM_TOOL_UNITS_PER_SECOND = .5;
	public static final double BLACKMETAL_TOOL_UNITS_PER_SECOND = .85;
	public static final double XERIUM_TOOL_UNITS_PER_SECOND = .95;
	public static final double BLOOD_XERIUM_TOOL_UNITS_PER_SECOND = .78;

	public static final char[] acceptedCharacters = new char[] {
		'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
		'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p',
		'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l',
		'z', 'x', 'c', 'v', 'b', 'n', 'm',
		'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P',
		'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L',
		'Z', 'X', 'C', 'V', 'B', 'N', 'M',
		'~', '`', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '-', '+', '=',
		':', ';', '\'', '"', '<', '>', '?'
	};
	public static final Color MAIN_MENU_BG_COLOR = new Color(0, 0, 0, 100);
	public static final Color TRANSPARENT_BLACK_100 = new Color(0, 0, 0, 100);
	public static final Color TRANSPARENT_BLACK_200 = new Color(0, 0, 0, 200);

	public static boolean isAcceptedCharacter(char c) {
		for (char chr : acceptedCharacters) {
			if (chr == c) return true;
		}
		return false;
	}


	public static final Item[] allTheItems = new Item[] {
			new BlueWizardHat(),
			new BronzeChest(),
			new BronzeHat(),
			new BronzeLegs(),
			new ChronomancerChest(),
			new ChronomancerHat(),
			new ChronomancerLegs(),
			new FarmerHat(),
			new IronChest(),
			new IronHat(),
			new IronLegs(),
			new PaladinChest(),
			new PaladinHat(),
			new PaladinLegs(),
			new SteelHelm(),
			new Coin(),
			new CaveFoundation(),
			new CraftingHutFoundation(),
			new DungeonFoundation(),
			new FieldFoundation(),
			new Foundation(),
			new HouseFoundation(),
			new HutFoundation(),
			new ResourcePileFoundation(),
			new SmithyFoundation(),
			new StoneQuarryFoundation(),
			new VillageCenterFoundation(),
			new WoodenBarricadeFoundation(),
			new WorkshopFoundation(),
			new MasterKey(),
			new Bone(),
			new BronzeBar(),
			new Coal(),
			new CopperOre(),
			new IronBar(),
			new IronOre(),
			new Scraps(),
			new Seeds(),
			new Stone(),
			new TinOre(),
			new Wood(),
			new XeriumBar(),
			new ChestItem(),
			new CobbleRoad(),
			new Road(),
			new TorchItem(),
			new PotionOfTesting(),
			new Sapling(),
			new BonePick(),
			new BronzePick(),
			new IronPick(),
			new SteelPick(),
			new WoodPick(),
			new XeriumPick(),
			new Unarmed(),
			new Arrow(),
			new BoneAxe(),
			new BoneSpear(),
			new BoneSword(),
			new BronzeAxe(),
			new BronzeSpear(),
			new BronzeSword(),
			new ChronomancerBlade(),
			new EnergyStaff(),
			new GodSword(),
			new IronAxe(),
			new IronGreatsword(),
			new IronSpear(),
			new IronSword(),
			new LargeSharpStick(),
			new SharpenedStick(),
			new SmallPointyStick(),
			new SteelAxe(),
			new SteelDagger(),
			new SteelSpear(),
			new SteelSword(),
			new Stick(),
			new StoneAxe(),
			new StoneGreatsword(),
			new StoneSpear(),
			new TestShield(),
			new ThrowingKnife(),
			new WoodAxe(),
			new WoodenBow(),
			new WoodenShield(),
			new WoodSword(),
			new XeriumAxe(),
			new XeriumSpear(),
			new XeriumSword(),
	};


	public static final int[] blankItemIntArray = new int[] {
			0, //   BlueWizardHat
			0, //   BronzeChest
			0, //   BronzeHat
			0, //   BronzeLegs
			0, //   ChronomancerChest
			0, //   ChronomancerHat
			0, //   ChronomancerLegs
			0, //   FarmerHat
			0, //   IronChest
			0, //   IronHat
			0, //   IronLegs
			0, //   PaladinChest
			0, //   PaladinHat
			0, //   PaladinLegs
			0, //   SteelHelm
			0, //   Coin
			0, //   CaveFoundation
			0, //   CraftingHutFoundation
			0, //   DungeonFoundation
			0, //   FieldFoundation
			0, //   Foundation
			0, //   HouseFoundation
			0, //   HutFoundation
			0, //   ResourcePileFoundation
			0, //   SmithyFoundation
			0, //   StoneQuarryFoundation
			0, //   VillageCenterFoundation
			0, //   WoodenBarricadeFoundation
			0, //   WorkshopFoundation
			0, //   MasterKey
			0, //   Bone
			0, //   BronzeBar
			0, //   Coal
			0, //   CopperOre
			0, //   IronBar
			0, //   IronOre
			0, //   Scraps
			0, //   Seeds
			0, //   Stone
			0, //   TinOre
			0, //   Wood
			0, //   XeriumBar
			0, //   ChestItem
			0, //   CobbleRoad
			0, //   Road
			0, //   TorchItem
			0, //   PotionOfTesting
			0, //   Sapling
			0, //   BonePick
			0, //   BronzePick
			0, //   IronPick
			0, //   SteelPick
			0, //   WoodPick
			0, //   XeriumPick
			0, //   Unarmed
			0, //   Arrow
			0, //   BoneAxe
			0, //   BoneSpear
			0, //   BoneSword
			0, //   BronzeAxe
			0, //   BronzeSpear
			0, //   BronzeSword
			0, //   ChronomancerBlade
			0, //   EnergyStaff
			0, //   GodSword
			0, //   IronAxe
			0, //   IronGreatsword
			0, //   IronSpear
			0, //   IronSword
			0, //   LargeSharpStick
			0, //   SharpenedStick
			0, //   SmallPointyStick
			0, //   SteelAxe
			0, //   SteelDagger
			0, //   SteelSpear
			0, //   SteelSword
			0, //   Stick
			0, //   StoneAxe
			0, //   StoneGreatsword
			0, //   StoneSpear
			0, //   TestShield
			0, //   ThrowingKnife
			0, //   WoodAxe
			0, //   WoodenBow
			0, //   WoodenShield
			0, //   WoodSword
			0, //   XeriumAxe
			0, //   XeriumSpear
			0, //   XeriumSword
	};
	public static int getItemID(Item item) {
		for (int i = 0; i < allTheItems.length; ++i) {
			if (item.toString().equalsIgnoreCase(allTheItems[i].toString())) { return i; }
		}
		throw new UncheckedDemigodsException("Item " + item + " does not have a spot in the ID list. Check the spelling.");
	}

	//public static final GameArea[] allTheAreas = new GameArea[] {
	//		new Region(),
	//		new Dungeon(),
	//		new HutInterior(),
	//		new CraftingHutInterior(),
	//		new HouseInterior(),//5
	//		new WorkshopInterior(),
	//		new SmithyInterior(),
	//		new IronGolemChamber(),
	//		new Cave(),//9
	//};
//
	//public static int getAreaID(GameArea area) {
	//	for (int i = 0; i < allTheAreas.length; ++i) {
	//		if (area.toString().equalsIgnoreCase(allTheAreas[i].toString())) return i;
	//	}
	//	throw new UncheckedDemigodsException("Area does not have a spot in the ID list. Check the spelling.");
	//}

	public static final boolean[] gameAreaBooleans = new boolean[] {
			false,
			false,
			false,
			false,
			false,//5
			false,
			false,
			false,
			false,//9
	};

	public static final String[] areaStrings = new String[] {
			"Overworld",
			"Dungeon",
			"Hut",
			"Crafting Hut",
			"House",//5
			"Workshop",
			"Smithy",
			"IronGolemChamber",
			"Cave",//9
	};

	public static int[] getItemsIntArray() {
		int[] i = blankItemIntArray;
		return i;
	}

	public static boolean[] getInteriorsBooleanArray() {
		boolean[] b = gameAreaBooleans;
		return b;
	}

	public static Item getItemFromString(String s) throws DemigodsException {
		Item item = null;
		for (Item i : allTheItems) {
			if (i.toString().equalsIgnoreCase(s)) {
				item = i;
				break;
			}
		}
		if (item == null) throw new DemigodsException("Item not found.");
		return item;
	}



	/**
	 * Whenever a character gains experience, check to see if the character's
	 * new xp is greater than xpLevels[character.level + 1], and if it is,
	 * level up the character (give them a skill point, increment level)
	 *
	 * <p> The amount of exp between levels increases with a static slope
	 * function currently. It can get more complex, but its a good starting
	 * point.
	 */
	public static final int[] xpLevels = new int[]{
		0, // 0, added for completeness and ease of programming.
		0, // 1
		100, // 2
		323, // 3
		769, // 4
		1474, // 5
		2482, // 6
		3869, // 7
		5610, // 8
		7805, // 9
		10471, // 10
		13699, // 11
		17522, // 12
		21991, // 13
		27145, // 14
		33074, // 15
		39787, // 16
		47365, // 17
		55862, // 18
		65310, // 19
		75752, // 20
		87245, // 21
		99842, // 22
		113618, // 23
		128595, // 24
		144829, // 25
		162366, // 26
		181239, // 27
		201494, // 28
		223165, // 29
		246349, // 30
		271075, // 31
		297392, // 32
		325357, // 33
		355004, // 34
		386380, // 35
		419542, // 36
		454552, // 37
		491444, // 38
		530306, // 39
		571155, // 40
		614036, // 41
		658996, // 42
		706072, // 43
		755345, // 44
		806859, // 45
		860670, // 46
		916821, // 47
		975371, // 48
		1036383, // 49
		1099897, // 50
		1166000, // 51
		1234685, // 52
		1306009, // 53
		1380050, // 54
		1456816, // 55
		1536380, // 56
		1618802, // 57
		1704165, // 58
		1792504, // 59
		1883854, // 60
		1978255, // 61
		2075734, // 62
		2176334, // 63
		2280105, // 64
		2387165, // 65
		2497523, // 66
		2611214, // 67
		2728296, // 68
		2848835, // 69
		2972872, // 70
		3100447, // 71
		3231596, // 72
		3366405, // 73
		3504940, // 74
		3647260, // 75
		3793351, // 76
		3943295, // 77
		4097119, // 78
		4254878, // 79
		4416653, // 80
		4582464, // 81
		4752386, // 82
		4926446, // 83
		5104703, // 84
		5287206, // 85
		5474032, // 86
		5665189, // 87
		5860743, // 88
		6060769, // 89
		6265314, // 90
		6474384, // 91
		6688051, // 92
		6906362, // 93
		7129395, // 94
		7357179, // 95
		7589764, // 96
		7827214, // 97
		8069533, // 98
		8316788, // 99
		8569057, // 100
		8826361, // 101
		9088788, // 102
		9356360, // 103
		9629103, // 104
		9907078, // 105
		10190337, // 106
		10478926, // 107
		10772931, // 108
		11072398, // 109
		11377355, // 110
		11687867, // 111
		12003961, // 112
		12325746, // 113
		12653200, // 114
		12986373, // 115
		13325342, // 116
		13670158, // 117
		14020875, // 118
		14377535, // 119
		14740222, // 120
		15108945, // 121
		15483779, // 122
		15864765, // 123
		16251919, // 124
		16645324, // 125
		17044991, // 126
		17451031, // 127
		17863495, // 128
		18282415, // 129
		18707838, // 130
		19139774, // 131
		19578349, // 132
		20023558, // 133
		20475476, // 134
		20934145, // 135
		21399596, // 136
		21871884, // 137
		22351070, // 138
		22837214, // 139
		23330341, // 140
		23830526, // 141
		24337836, // 142
		24852317, // 143
		25374029, // 144
		25902974, // 145
		26439208, // 146
		26982779, // 147
		27533735, // 148
		28092123, // 149
		28658053, // 150
		29231549, // 151
		29812650, // 152
		30401400, // 153
		30997854, // 154
		31602098, // 155
		32214134, // 156
		32834023, // 157
		33461812, // 158
		34097573, // 159
		34741345, // 160
		35393169, // 161
		36053083, // 162
		36721127, // 163
		37397407, // 164
		38081979, // 165
		38774861, // 166
		39476085, // 167
		40185691, // 168
		40903738, // 169
		41630303, // 170
		42365427, // 171
		43109156, // 172
		43861507, // 173
		44622568, // 174
		45392342, // 175
		46170930, // 176
		46958366, // 177
		47754707, // 178
		48559955, // 179
		49374201, // 180
		50197487, // 181
		51029854, // 182
		51871418, // 183
		52722195, // 184
		53582220, // 185
		54451519, // 186
		55330181, // 187
		56218208, // 188
		57115672, // 189
		58022636, // 190
		58939107, // 191
		59865170, // 192
		60800875, // 193
		61746260, // 194
		62701428, // 195
		63666393, // 196
		64641215, // 197
		65625945, // 198
		66620629, // 199
		67625310, // 200
		68640052, // 201
		69664861, // 202
		70699846, // 203
		71745013, // 204
		72800432, // 205
		73866117, // 206
		74942148, // 207
		76028563, // 208
		77125413, // 209
		78232762, // 210
		79350693, // 211
		80479247, // 212
		81618435, // 213
		82768294, // 214
		83928900, // 215
		85100325, // 216
		86282636, // 217
		87475811, // 218
		88679903, // 219
		89895026, // 220
		91121218, // 221
		92358474, // 222
		93606910, // 223
		94866527, // 224
		96137382, // 225
		97419541, // 226
		98713065, // 227
		100017992, // 228
		101334331, // 229
		102662168, // 230
		104001563, // 231
		105352542, // 232
		106715162, // 233
		108089508, // 234
		109475593, // 235
		110873474, // 236
		112283195, // 237
		113704812, // 238
		115138380, // 239
		116583960, // 240
		118041563, // 241
		119511295, // 242
		120993209, // 243
		122487308, // 244
		123993637, // 245
		125512231, // 246
		127043200, // 247
		128586581, // 248
		130142421, // 249
	};

    public static int getXpLengthFormattedForHudBar(int lvl, int xp) {
        //
        return (int)((((double)( - xpLevels[lvl] + xp))/((double)(xpLevels[lvl + 1] - xpLevels[lvl])))*315);
    }
}
