package entities.tiles;


import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;

import util.Images;

/**
 * INSTRUCTIONS FOR ADDING TILE TYPES: The new tile format is very picky as to
 * hard coding each tile. Each tile has a series of steps that must done in
 * order to not break the game.
 *
 * 1. Determine an int value for the tile. (not one already used) 2. Add to the
 * list of constants. 3. Name appropriately. this declaration will be used for
 * the rest of the program. 4. update the boolean array to the passability of
 * your new Tile. 5. Update the getImageReadCoords() switch with the new value.
 * 6. If the tile has a second layer that will need drawing: 6a. add the tile's
 * value to the if() at the beggining of drawSecondLayer() 6b. add the code for
 * drawing the second layer. 7. If the tile has resources, update the
 * NewResourceTile class: 7a. Update the generateResources method. 7b. Update
 * the isAppropriateTool method. 8. Add the tile's minimap color to the color[]
 */

public class Tiles {

	public static final int BLANK           = 0;
	public static final int GRASS           = 1;
	public static final int OVERWORLD_STONE = 2;
	public static final int WATER           = 3;
	public static final int SAND            = 4;
	public static final int DUNGEONFLOOR    = 5;
	public static final int DUNGEONWALL     = 6;
	public static final int LEAVES          = 7;
	public static final int SNOW            = 8;
	public static final int DIRT            = 9;
	public static final int TREE            = 10;
	public static final int CACTUS          = 11;
	public static final int WOODFLOOR       = 12;
	public static final int WOODWALL        = 13;
	public static final int STUMP           = 14;
	public static final int STONEFLOOR      = 15;
	public static final int COBBLEROAD      = 16;
	public static final int CAVEWALL        = 17;
	public static final int SNOWTREE        = 18;
	public static final int CAVE_STONE      = 19;
	public static final int GRASS00         = 20;//center
	public static final int GRASS01         = 21;//top
	public static final int GRASS02         = 22;//left
	public static final int GRASS03         = 23;//all
	public static final int GRASS10         = 24;//center
	public static final int GRASS11         = 25;//top
	public static final int GRASS12         = 26;//left
	public static final int GRASS13         = 27;//all
	public static final int DUNGEONDOOR     = 28;




	public static final boolean[] passable = new boolean[] {
		false, // 0
		true, // 1
		false, // 2//
		false, // 3//
		true, // 4
		true, // 5
		false, // 6//
		true, // 7
		true, // 8
		true, // 9
		false, // 10//
		false, // 11//
		true, // 12
		false, // 13
		true, // 14
		true, // 15
		true, // 16
		false, // 17
		false, // 18
		false, // 19
		true,//20 - grass00
		true,//21 - grass01
		true,//22 - grass02
		true,//23 - grass03
		true,//24 - grass10
		true,//25 - grass11
		true,//26 - grass12
		true,//27 - grass13
		false,//28 - dungeon door
	};
	public static final Color[] MINIMAP_COLORS = new Color[] {
		new Color(0, 0, 0), // 0
		new Color(100, 210, 50), // 1
		Color.gray, // 2
		new Color(40, 125, 255), // 3
		new Color(244, 228, 177), // 4
		Color.lightGray, // 5
		Color.gray, // 6
		new Color(10, 180, 10), // 7
		Color.white, // 8
		new Color(100, 100, 10), // 9
		new Color(10, 180, 10), // 10
		new Color(10, 180, 10), // 11
		Color.cyan, // 12
		Color.cyan, // 13
		new Color(10, 180, 10), // 14
		Color.cyan, // 15
		Color.cyan, // 16
		new Color(75, 75, 7), // 17
		new Color(10, 180, 10), // 18
		Color.gray, // 19
			new Color(100, 210, 50),//20 - grass00
			new Color(100, 210, 50),//21 - grass01
			new Color(100, 210, 50),//22 - grass02
			new Color(100, 210, 50),//23 - grass03
			new Color(100, 210, 50),//24 - grass10
			new Color(100, 210, 50),//25 - grass11
			new Color(100, 210, 50),//26 - grass12
			new Color(100, 210, 50),//27 - grass13
			Color.gray,//28 - dungeon door

	};

	public static int[] getImageReadCoords(int type) {
		int[] rtnArray = new int[2];

		switch (type) {
		case 0:
			rtnArray[0] = 0;
			rtnArray[1] = 0;
			return rtnArray;
		case GRASS:
			rtnArray[0] = 7;
			rtnArray[1] = 2;
			return rtnArray;
		case OVERWORLD_STONE:
			rtnArray[0] = 11;
			rtnArray[1] = 4;
			return rtnArray;
		case WATER:
			rtnArray[0] = 9;
			rtnArray[1] = 4;
			return rtnArray;
		case SAND:
			rtnArray[0] = 7;
			rtnArray[1] = 4;
			return rtnArray;
		case 5:
			rtnArray[0] = 5;
			rtnArray[1] = 0;
			return rtnArray;
		case DUNGEONWALL:
			rtnArray[0] = 4;
			rtnArray[1] = 0;
			return rtnArray;
		case 7:
			rtnArray[0] = 7;
			rtnArray[1] = 0;
			return rtnArray;
		case SNOW:
			rtnArray[0] = 13;
			rtnArray[1] = 2;
			return rtnArray;
		case DIRT:
			rtnArray[0] = 11;
			rtnArray[1] = 4;
			return rtnArray;
		case TREE:
			rtnArray[0] = 7;
			rtnArray[1] = 0;
			return rtnArray;
		case CACTUS:
			rtnArray[0] = 7;
			rtnArray[1] = 4;
			return rtnArray;
		case 12:
			rtnArray[0] = 1;
			rtnArray[1] = 3;
			return rtnArray;
		case 13:
			rtnArray[0] = 0;
			rtnArray[1] = 3;
			return rtnArray;
		case 14:
			rtnArray[0] = 7;
			rtnArray[1] = 2;
			return rtnArray;
		case 15:
			rtnArray[0] = 0;
			rtnArray[1] = 5;
			return rtnArray;
		case 16:
			rtnArray[0] = 11;
			rtnArray[1] = 2;
			return rtnArray;
		case CAVEWALL:
			rtnArray[0] = 1;
			rtnArray[1] = 6;
			return rtnArray;
		case SNOWTREE:
			rtnArray[0] = 13;
			rtnArray[1] = 2;
			return rtnArray;
		case CAVE_STONE:
			rtnArray[0] = 11;
			rtnArray[1] = 4;
			return rtnArray;
			case DUNGEONDOOR:
				rtnArray[0] = 4;
				rtnArray[1] = 0;
				return rtnArray;
		}
		if(       type == GRASS00
				||type == GRASS01
				||type == GRASS02
				||type == GRASS03
				){
			rtnArray[0] = 7;
			rtnArray[1] = 2;
			return rtnArray;
		}
		if(
				type == GRASS10
				||type == GRASS11
				||type == GRASS12
				||type == GRASS13
				){
			rtnArray[0] = 7;
			rtnArray[1] = 6;
			return rtnArray;
		}


		return rtnArray;
	}

	public static final Rectangle[] loc = new Rectangle[]{
			//            gX          gY          W      H
			new Rectangle(0 * 16    , 0 * 16    , 16   , 16),       // BLANK           = 0
			new Rectangle(7 * 16    , 2 * 16    , 16   , 16),       // GRASS           = 1;
			new Rectangle(11* 16    , 4 * 16    , 16   , 16),       // OVERWORLD_STONE = 2;
			new Rectangle(9 * 16    , 4 * 16    , 16   , 16),       // WATER           = 3;
			new Rectangle(7 * 16    , 4 * 16    , 16   , 16),       // BEACH            = 4;
			new Rectangle(5 * 16    , 0 * 16    , 16   , 16),       // DUNGEONFLOOR    = 5;
			new Rectangle(4 * 16    , 0 * 16    , 16   , 16),       // DUNGEONWALL     = 6;
			new Rectangle(7 * 16    , 0 * 16    , 16   , 16),       // LEAVES          = 7;
			new Rectangle(13* 16    , 2 * 16    , 16   , 16),       // SNOW            = 8;
			new Rectangle(11* 16    , 4 * 16    , 16   , 16),       // DIRT            = 9;
			new Rectangle(7 * 16    , 2 * 16    , 16   , 16),       // TREE            = 10;
			new Rectangle(7 * 16    , 4 * 16    , 16   , 16),       // CACTUS          = 11;
			new Rectangle(1 * 16    , 3 * 16    , 16   , 16),       // WOODFLOOR       = 12;
			new Rectangle(0 * 16    , 3 * 16    , 16   , 16),       // WOODWALL        = 13;
			new Rectangle(7 * 16    , 2 * 16    , 16   , 16),       // STUMP           = 14;
			new Rectangle(0 * 16    , 5 * 16    , 16   , 16),       // STONEFLOOR      = 15;
			new Rectangle(11* 16    , 2 * 16    , 16   , 16),       // COBBLEROAD      = 16;
			new Rectangle(1 * 16    , 6 * 16    , 16   , 16),       // CAVEWALL        = 17;
			new Rectangle(13* 16    , 2 * 16    , 16   , 16),       // SNOWTREE        = 18;
			new Rectangle(11* 16    , 4 * 16    , 16   , 16),       // CAVE_STONE      = 19;
			new Rectangle(7 * 16    , 2 * 16    , 16   , 16),       // GRASS00         = 20;//center
			new Rectangle(7 * 16    , 2 * 16 - 2, 16   , 18),       // GRASS01         = 21;//top
			new Rectangle(7 * 16 - 2, 2 * 16    , 18   , 16),       // GRASS02         = 22;//left
			new Rectangle(7 * 16 - 2, 2 * 16 - 2, 18   , 18),       // GRASS03         = 23;//all
			new Rectangle(7 * 16    , 6 * 16    , 16   , 16),       // GRASS10         = 24;//center
			new Rectangle(7 * 16    , 6 * 16 - 2, 16   , 18),       // GRASS11         = 25;//top
			new Rectangle(7 * 16 - 2, 6 * 16    , 18   , 16),       // GRASS12         = 26;//left
			new Rectangle(7 * 16 - 2, 6 * 16 - 2, 18   , 18),       // GRASS13         = 27;//all
			new Rectangle(4 * 16    , 0 * 16    , 16   , 16),       // DUNGEONDOOR     = 28;
			//need dirt, water, sand, snow, cobble road, and stone rounded tiles.
	};
	public static void simpleDrawTile(int x, int y, float sx, float sy, int type, float z){
		Rectangle rect = loc[type];
		int dx = rect.getWidth() - 16;
		int dy = rect.getHeight() - 16;
		Images.tileSheet.draw(
				(x - dx - sx) * z,
				(y - dy - sy) * z,
				(x - dx - sx + rect.getWidth()) * z,
				(y - dy - sy + rect.getHeight()) * z,
				rect.getX(),
				rect.getY(),
				rect.getX()+rect.getWidth(),
				rect.getY()+rect.getHeight()
		);
	}
	public static void drawTile(int x, int y, int r, int c, float screenX, float screenY, int type, int[][] map, float zoom) {

		if (type != TREE && type != CACTUS && type != STUMP
				&& type != SNOWTREE
				&& !isRoundedTile(type)
				) {
			Images.tileSheet.getSprite(getImageReadCoords(type)[0], getImageReadCoords(type)[1])
					.draw((x*16-screenX)*zoom,
							(y*16-screenY)*zoom,
							16*zoom,
							16*zoom);
			return;
		}
		if(type == TREE)type = GRASS;
		else if(type == CACTUS)type = SAND;
//		else if(type == OVERWORLD_STONE || type == CAVE_STONE)type = DIRT;
		else if(type == SNOWTREE)type = SNOW;
		if(
			type == GRASS00
			||type == GRASS10
				){
			Images.tileSheet.getSprite(getImageReadCoords(type)[0], getImageReadCoords(type)[1])
					.draw((x*16-screenX)*zoom,
							(y*16-screenY)*zoom,
							16*zoom,
							16*zoom);
			return;

		}else if(
				type == GRASS01
						||type == GRASS11
				){
			Images.tileSheet.draw(
					(x * 16 - screenX) * zoom,
					(y * 16 - screenY - 2) * zoom,
					(x * 16 - screenX + 16) * zoom,
					(y * 16 - screenY + 16) * zoom,

					getImageReadCoords(type)[0] * 16,
					getImageReadCoords(type)[1] * 16 - 2,
					getImageReadCoords(type)[0] * 16 + 16,
					getImageReadCoords(type)[1] * 16 + 16);
			return;
		}else if(
				type == GRASS02
						||type == GRASS12
				){
			Images.tileSheet.draw(
					(x * 16 - screenX - 2)*zoom,
					(y * 16 - screenY)*zoom,
					(x * 16 - screenX + 16)*zoom,
					(y * 16 - screenY + 16)*zoom,

					getImageReadCoords(type)[0]*16 - 2,
					getImageReadCoords(type)[1]*16,
					getImageReadCoords(type)[0]*16 + 16,
					getImageReadCoords(type)[1]*16 + 16);

			return;

		}else if(
				type == GRASS03
						||type == GRASS13
				){
			Images.tileSheet.draw(
					(x * 16 - screenX - 2)*zoom,
					(y * 16 - screenY - 2)*zoom,
					(x * 16 - screenX + 16)*zoom,
					(y * 16 - screenY + 16)*zoom,

					getImageReadCoords(type)[0]*16 - 2,
					getImageReadCoords(type)[1]*16 - 2,
					getImageReadCoords(type)[0]*16 + 16,
					getImageReadCoords(type)[1]*16 + 16);
			return;

		}
		if (type == GRASS || type == SAND || type == WATER || type == OVERWORLD_STONE || type == COBBLEROAD || type == DIRT || type == SNOW|| type == CAVE_STONE) {
			int pos = 1;
			try {
				if (map[r][c - 1] != type) {
					pos *= 2;
				}
				if (map[r - 1][c] != type) {
					pos *= 3;
				}
				//if (map[r][c + 1] != type) {
				//	pos *= 7;
				//}
				//if (map[r + 1][c] != type) {
				//	pos *= 5;
				//}
			} catch (IndexOutOfBoundsException e) {
				//pos = 210;
			}

			switch (pos) {
				case 2://
					Images.tileSheet.draw((x * 16 - screenX)*zoom, (y * 16 - screenY - 2)*zoom, (x * 16 - screenX + 16)*zoom, (y * 16 - screenY + 16)*zoom, getImageReadCoords(type)[0]*16, getImageReadCoords(type)[1]*16 - 2, getImageReadCoords(type)[0]*16 + 16, getImageReadCoords(type)[1]*16 + 16);
					break;
				case 3://
					Images.tileSheet.draw((x * 16 - screenX - 2)*zoom, (y * 16 - screenY)*zoom, (x * 16 - screenX + 16)*zoom, (y * 16 - screenY + 16)*zoom, getImageReadCoords(type)[0]*16 - 2, getImageReadCoords(type)[1]*16, getImageReadCoords(type)[0]*16 + 16, getImageReadCoords(type)[1]*16 + 16);
					break;
				case 5://
					Images.tileSheet.draw((x * 16 - screenX)*zoom, (y * 16 - screenY)*zoom, (x * 16 - screenX + 18)*zoom, (y * 16 - screenY + 16)*zoom, getImageReadCoords(type)[0]*16, getImageReadCoords(type)[1]*16, getImageReadCoords(type)[0]*16 + 18, getImageReadCoords(type)[1]*16 + 16);
					break;
				case 6://
					Images.tileSheet.draw((x * 16 - screenX - 2)*zoom, (y * 16 - screenY - 2)*zoom, (x * 16 - screenX + 16)*zoom, (y * 16 - screenY + 16)*zoom, getImageReadCoords(type)[0]*16 - 2, getImageReadCoords(type)[1]*16 - 2, getImageReadCoords(type)[0]*16 + 16, getImageReadCoords(type)[1]*16 + 16);
					break;
				case 7:
					Images.tileSheet.draw((x * 16 - screenX)*zoom, (y * 16 - screenY)*zoom, (x * 16 - screenX + 16)*zoom, (y * 16 - screenY + 18)*zoom, getImageReadCoords(type)[0]*16, getImageReadCoords(type)[1]*16, getImageReadCoords(type)[0]*16 + 16, getImageReadCoords(type)[1]*16 + 18);
					break;
				case 10://
					Images.tileSheet.draw((x * 16 - screenX)*zoom, (y * 16 - screenY - 2)*zoom, (x * 16 - screenX + 16)*zoom, (y * 16 - screenY + 16)*zoom, getImageReadCoords(type)[0]*16, getImageReadCoords(type)[1]*16 - 2, getImageReadCoords(type)[0]*16 + 18, getImageReadCoords(type)[1]*16 + 16);
					break;
				case 14://
					Images.tileSheet.draw((x * 16 - screenX)*zoom, (y * 16 - screenY - 2)*zoom, (x * 16 - screenX + 18)*zoom, (y * 16 - screenY + 18)*zoom, getImageReadCoords(type)[0]*16, getImageReadCoords(type)[1]*16 - 2, getImageReadCoords(type)[0]*16 + 16, getImageReadCoords(type)[1]*16 + 18);
					break;
				case 15://
					Images.tileSheet.draw((x * 16 - screenX - 2)*zoom, (y * 16 - screenY)*zoom, (x * 16 - screenX + 18)*zoom, (y * 16 - screenY + 16)*zoom, getImageReadCoords(type)[0]*16 - 2, getImageReadCoords(type)[1]*16, getImageReadCoords(type)[0]*16 + 18, getImageReadCoords(type)[1]*16 + 16);
					break;
				case 21://
					Images.tileSheet.draw((x * 16 - screenX - 2)*zoom, (y * 16 - screenY)*zoom, (x * 16 - screenX + 16)*zoom, (y * 16 - screenY + 18)*zoom, getImageReadCoords(type)[0]*16 - 2, getImageReadCoords(type)[1]*16, getImageReadCoords(type)[0]*16 + 16, getImageReadCoords(type)[1]*16 + 18);
					break;
				case 30://
					Images.tileSheet.draw((x * 16 - screenX - 2)*zoom, (y * 16 - screenY - 2)*zoom, (x * 16 - screenX + 18)*zoom, (y * 16 - screenY + 16)*zoom, getImageReadCoords(type)[0]*16 - 2, getImageReadCoords(type)[1]*16 - 2, getImageReadCoords(type)[0]*16 + 18, getImageReadCoords(type)[1]*16 + 16);
					break;
				case 35://
					Images.tileSheet.draw((x * 16 - screenX)*zoom, (y * 16 - screenY)*zoom, (x * 16 - screenX + 18)*zoom, (y * 16 - screenY + 18)*zoom, getImageReadCoords(type)[0]*16, getImageReadCoords(type)[1]*16, getImageReadCoords(type)[0]*16 + 18, getImageReadCoords(type)[1]*16 + 18);
					break;
				case 42://
					Images.tileSheet.draw((x * 16 - screenX - 2)*zoom, (y * 16 - screenY - 2)*zoom, (x * 16 - screenX + 16)*zoom, (y * 16 - screenY + 18)*zoom, getImageReadCoords(type)[0]*16 - 2, getImageReadCoords(type)[1]*16 - 2, getImageReadCoords(type)[0]*16 + 16, getImageReadCoords(type)[1]*16 + 18);
					break;
				case 70://
					Images.tileSheet.draw((x * 16 - screenX)*zoom, (y * 16 - screenY - 2)*zoom, (x * 16 - screenX + 18)*zoom, (y * 16 - screenY + 18)*zoom, getImageReadCoords(type)[0]*16, getImageReadCoords(type)[1]*16 - 2, getImageReadCoords(type)[0]*16 + 18, getImageReadCoords(type)[1]*16 + 18);
					break;
				case 105://
					Images.tileSheet.draw((x * 16 - screenX - 2)*zoom, (y * 16 - screenY)*zoom, (x * 16 - screenX + 18)*zoom, (y * 16 - screenY + 18)*zoom, getImageReadCoords(type)[0]*16 - 2, getImageReadCoords(type)[1]*16, getImageReadCoords(type)[0]*16 + 18, getImageReadCoords(type)[1]*16 + 18);
					break;
				case 210://
					Images.tileSheet.draw((x * 16 - screenX - 2)*zoom, (y * 16 - screenY - 2)*zoom, (x * 16 - screenX + 18)*zoom, (y * 16 - screenY + 18)*zoom, getImageReadCoords(type)[0]*16 - 2, getImageReadCoords(type)[1]*16 - 2, getImageReadCoords(type)[0]*16 + 18, getImageReadCoords(type)[1]*16 + 18);
					break;
				default://
					Images.tileSheet.draw((x * 16 - screenX)*zoom, (y * 16 - screenY)*zoom, (x * 16 - screenX + 16)*zoom, (y * 16 - screenY + 16)*zoom, getImageReadCoords(type)[0]*16, getImageReadCoords(type)[1]*16, getImageReadCoords(type)[0]*16 + 16, getImageReadCoords(type)[1]*16 + 16);
					break;
			}
		}
//
		if (type == STUMP) {
			Images.tileSheet.getSprite(getImageReadCoords(type)[0], getImageReadCoords(type)[1]).draw((x * 16 - screenX)*zoom, (y * 16 - screenY)*zoom, 16*zoom, 16*zoom);
			Images.tileSheet.getSprite(0, 2).draw((x * 16 - screenX)*zoom, (y * 16 - screenY)*zoom, 16*zoom, 16*zoom);

		}
	}


	public static void drawSecondLayer(int x, int y, int r, int c, float screenX, float screenY, int type, int[][] map, float zoom) {
		if(type != WOODWALL &&
				type != DUNGEONWALL&&
				type != DUNGEONDOOR&&
				type != CAVEWALL
				)return;

		if(type == WOODWALL){
			Images.tileSheet.getSprite(getImageReadCoords(type)[0], getImageReadCoords(type)[1]).draw(
					(x*16-screenX)*zoom, (y*16-screenY)*zoom,
					16*zoom, 16*zoom
			);
			Images.tileSheet.getSprite(getImageReadCoords(type)[0], getImageReadCoords(type)[1]).draw(
					(x*16-screenX)*zoom, (y*16-screenY-16)*zoom,
					16*zoom, 16*zoom
			);
			//Images.tileSheet.getSprite(5, 4).draw(x*16-screenX, y*16-screenY - 32);
		}
		else if(type == DUNGEONDOOR){
			Images.tileSheet.getSprite(
					getImageReadCoords(type)[0] + 2, getImageReadCoords(type)[1]
			).draw(
					(x*16-screenX)*zoom, (y*16-screenY-16)*zoom,
					16*zoom, 16*zoom
			);

			Images.tileSheet.getSprite(
					getImageReadCoords(type)[0], getImageReadCoords(type)[1]).draw(
					(x*16-screenX)*zoom, (y*16-screenY)*zoom, 16*zoom, 16*zoom
			);
			//Images.tileSheet.getSprite(5, 4).draw(x*16-screenX, y*16-screenY - 32);

		}
		else if(type == DUNGEONWALL){
			Images.tileSheet.getSprite(
					getImageReadCoords(type)[0] + 2, getImageReadCoords(type)[1]
			).draw(
					(x*16-screenX)*zoom, (y*16-screenY-16)*zoom,
					16*zoom, 16*zoom
			);

			Images.tileSheet.getSprite(
					getImageReadCoords(type)[0], getImageReadCoords(type)[1]).draw(
					(x*16-screenX)*zoom, (y*16-screenY)*zoom, 16*zoom, 16*zoom
			);
			//Images.tileSheet.getSprite(5, 4).draw(x*16-screenX, y*16-screenY - 32);

		}else if(type == CAVEWALL){
			Images.tileSheet.getSprite(
					getImageReadCoords(type)[0], getImageReadCoords(type)[1]-1
			).draw(
					(x*16-screenX)*zoom, (y*16-screenY-16)*zoom,
					16*zoom, 16*zoom
			);

			Images.tileSheet.getSprite(
					getImageReadCoords(type)[0], getImageReadCoords(type)[1]).draw(
					(x*16-screenX)*zoom, (y*16-screenY)*zoom, 16*zoom, 16*zoom
			);
			//Images.tileSheet.getSprite(5, 4).draw(x*16-screenX, y*16-screenY - 32);

		}

	}
	public static void drawSecondLayerSimple(int x, int y, float sx, float sy, int type, float z) {
		if(type != WOODWALL &&
				type != DUNGEONWALL&&
				type != CAVEWALL)return;

		if(type == WOODWALL){
			Images.tileSheet.getSprite(getImageReadCoords(type)[0], getImageReadCoords(type)[1]).draw(
					(x*16- sx)* z, (y*16- sy)* z,
					16* z, 16* z
			);
			Images.tileSheet.getSprite(getImageReadCoords(type)[0], getImageReadCoords(type)[1]).draw(
					(x*16- sx)* z, (y*16- sy -16)* z,
					16* z, 16* z
			);
			//Images.tileSheet.getSprite(5, 4).draw(x*16-screenX, y*16-screenY - 32);
		}
		else if(type == DUNGEONWALL){
			Images.tileSheet.getSprite(
					getImageReadCoords(type)[0] + 2, getImageReadCoords(type)[1]
			).draw(
					(x*16- sx)* z, (y*16- sy -16)* z,
					16* z, 16* z
			);

			Images.tileSheet.getSprite(
					getImageReadCoords(type)[0], getImageReadCoords(type)[1]).draw(
					(x*16- sx)* z, (y*16- sy)* z, 16* z, 16* z
			);
			//Images.tileSheet.getSprite(5, 4).draw(x*16-screenX, y*16-screenY - 32);

		}
		else if(type == DUNGEONDOOR){
			Images.tileSheet.getSprite(
					getImageReadCoords(type)[0] + 2, getImageReadCoords(type)[1]
			).draw(
					(x*16- sx)* z, (y*16- sy -16)* z,
					16* z, 16* z
			);

			Images.tileSheet.getSprite(
					getImageReadCoords(type)[0], getImageReadCoords(type)[1]).draw(
					(x*16- sx)* z, (y*16- sy)* z, 16* z, 16* z
			);
			//Images.tileSheet.getSprite(5, 4).draw(x*16-screenX, y*16-screenY - 32);

		}else if(type == CAVEWALL){
			Images.tileSheet.getSprite(
					getImageReadCoords(type)[0], getImageReadCoords(type)[1]-1
			).draw(
					(x*16- sx)* z, (y*16- sy -16)* z,
					16* z, 16* z
			);

			Images.tileSheet.getSprite(
					getImageReadCoords(type)[0], getImageReadCoords(type)[1]).draw(
					(x*16- sx)* z, (y*16- sy)* z, 16* z, 16* z
			);
			//Images.tileSheet.getSprite(5, 4).draw(x*16-screenX, y*16-screenY - 32);

		}

	}

	public static void updateMap(int[][] map, int gx, int gy){
		if(map[gx][gy] != DIRT && map[gx][gy] != STUMP){
		}
		else if(map[gx][gy] == DIRT || map[gx][gy] == STUMP){
			double rand = Math.random();
			if(rand < .00001){
				map[gx][gy] = GRASS;
			}
		}
	}


	public static float terrainValue(int tileType){
		if(tileType == COBBLEROAD)return .067f;

		return 1;
	}
	private static boolean isRoundedTile(int type){
		if(        type == GRASS
				|| type == GRASS00
				|| type == GRASS01
				|| type == GRASS02
				|| type == GRASS03
				|| type == GRASS10
				|| type == GRASS11
				|| type == GRASS12
				|| type == GRASS13
				|| type == SAND
				|| type == SNOW
				|| type == DIRT
				|| type == COBBLEROAD
				|| type == WATER
				|| type == OVERWORLD_STONE
				|| type == CAVE_STONE
				){
			return true;
		}
		return false;
	}
	public static boolean isGrass(int type){
		if(        type == GRASS
				|| type == GRASS00
				|| type == GRASS01
				|| type == GRASS02
				|| type == GRASS03
				|| type == GRASS10
				|| type == GRASS11
				|| type == GRASS12
				|| type == GRASS13
				){
			return true;
		}
		return false;
	}

}
