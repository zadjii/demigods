package util;

import com.google.gson.Gson;
import entities.characters.Human;
import entities.characters.personas.Player;
import game.Engine;
import areas.NewGameArea;
import areas.OverworldArea;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 6/28/13
 * Time: 11:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class GSONTest {

	public static void main(String[] args) {
		Gson gson = new Gson();
		String jsonFileContents = "";
		Player player = new Player();
		player.setCharacter(new Human(32, 32));
		while (player.getCharacter().getLvl() < 15){
			player.getCharacter().addXP(17);
		}
		//Engine engine = new Engine(player, "This iS a JsOn TeSt");
		//Random rand, int size, int ID, int playerLevel
//		OverworldArea area = new OverworldArea(new Random(), 2, 100, 10);
//		NewGameArea area = new NewGameArea(new Random(), 512, 10202);
		System.out.println("blah");
//		jsonFileContents = gson.toJson(area);
//		writeToFile(jsonFileContents, "JSON_TEST_FILE_001.txt");


	}
	private static void writeToFile(String jsonContents, String path){
		try{
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(jsonContents.getBytes());
			fos.close();
			System.out.println("Save complete");
		} catch (FileNotFoundException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}
}
