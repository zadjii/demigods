package util.runnable;

import areas.World;

import java.io.IOException;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 2/21/13
 * Time: 10:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorldTester {
	public static void main(String[] args) {
		for (int i = 0; i < 25; i++) {
			int seed = (int)(Math.random()*Integer.MAX_VALUE);
			World world
					= new World(seed, new Random(seed), Integer.toString(seed), "_WORLD_TESTER_BOT_");
//			try {
////				world.testerOutputImage();
//			} catch (IOException e) {
//				System.out.println("ERRORS");
//			}
		}
	}
}
