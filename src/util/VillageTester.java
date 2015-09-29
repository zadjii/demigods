package util;

import areas.OverworldArea;
import areas.PassiveVillage;
import areas.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Used in rapid testing the Passive Village Generation
 * User: zadjii
 * Date: 6/22/13
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class VillageTester {


	public static final Color ROAD_COLOR = new Color(0x755022);
	public static final Color SPAWN_COLOR = new Color(104,187,255);
	public static final Color BUILDING_COLOR = new Color(0xd0d0c0);
	public static final Color FARM_COLOR = new Color(0x8aec34);
	public static final Color CENTER_COLOR = new Color(0xaa4c34);
	public static final Color NOTHING_COLOR = new Color(855325, true);
	public static final Color EMPTY_COLOR = new Color(-1946157056, true);




	public static void main(String[] args) {


		int sizeOfArea = 64;
		int sizeOfVillage= 14;
		int id = 1;
		int level = 1;



		for (int i = 0; i < 8; i++) {

			int seed = (int)(Math.random()*Integer.MAX_VALUE);

			Random rand = new Random(seed);

			OverworldArea area = new OverworldArea(rand, sizeOfArea, id, level, i+2, World.NOTHING, World.CLEARED, 0);
			int[][] overview = PassiveVillage.generateVillage(area, sizeOfVillage);
			try {
				testerOutputImage(overview, seed);
			} catch (IOException e) {
				System.out.println("IOE in outputting image");
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

			}
		}


	}


	public static void testerOutputImage(int[][] overview, int seed) throws IOException {

		int size = overview.length;

		File savDir = new File("map/");
		if(!savDir.exists())savDir.mkdir();

		File playersDir = new File("map/villageTest/");
		if(!playersDir.exists())playersDir.mkdir();

//		File playerDir = new File("sav/players/"+playerName);
//		if(!playerDir.exists())playerDir.mkdir();
//		File worldsDir = new File("sav/players/"+playerName+"/worlds");
//		if(!worldsDir.exists())worldsDir.mkdir();
//		File worldDir = new File("sav/players/"+playerName+"/worlds/"+worldName);
//		if(!worldDir.exists())worldDir.mkdir();

//		File imgOut = new File("sav/players/"+playerName+"_"+worldName+"_map.png");
		File imgOut = new File("map/villageTest/"+seed+"_overview.png");
		BufferedImage image = new BufferedImage(overview.length * 5, overview.length * 5, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		//Image map = new Image(size,size);

		//Graphics g = map.getGraphics();
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				////////////////////////////TERRAIN/////////////////////////////
				if(overview[x][y] == PassiveVillage.EMPTY){
					g.setColor(EMPTY_COLOR);
				}
				else if(overview[x][y] == PassiveVillage.NOTHING){
					g.setColor(NOTHING_COLOR);
				}
				else if(overview[x][y] == PassiveVillage.ROAD){
					g.setColor(ROAD_COLOR);
				}
				else if(overview[x][y] == PassiveVillage.SPAWN){
					g.setColor(SPAWN_COLOR);
				}
				else if(overview[x][y] == PassiveVillage.BUILDING){
					g.setColor(BUILDING_COLOR);
				}
				else if(overview[x][y] == PassiveVillage.FARM){
					g.setColor(FARM_COLOR);
				}
				else if(overview[x][y] == PassiveVillage.TOWNCENTER){
					g.setColor(CENTER_COLOR);
				}
				////////////////////////////TERRAIN/////////////////////////////

				g.fillRect(x * 5, y * 5, 5, 5);

			}
		}

		ImageIO.write(image, "png", imgOut);
		System.out.println(
				"Wrote a " +size+ "x" +size+ "image, " + seed + ".png"
		);
	}
}
