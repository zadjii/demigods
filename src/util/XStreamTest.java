package util;

import areas.NewGameArea;
import areas.OverworldArea;
import areas.World;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import entities.characters.Human;
import entities.characters.personas.Player;
import entities.interactables.ResourceTile;
import game.Engine;
import entities.characters.Character;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 7/5/13
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class XStreamTest {
	public static void main(String[] args) {
		XStream xstream = new XStream(new StaxDriver());
		Player player = new Player();
		player.setCharacter(new Human(32, 32));
		while (player.getCharacter().getLvl() < 15){
			player.getCharacter().addXP(17);
		}
		//String playerXMLString = xstream.toXML(player);
		//System.out.println(playerXMLString);
//		Engine engine = new Engine(player, "This iS a XML TeSt");
		//Random rand, int size, int ID, int playerLevel
//		OverworldArea area = new OverworldArea(new Random(), NewGameArea.DEFAULT_SIZE, 100, 10, World.PLAINS, World.NOTHING);
//		NewGameArea area = new NewGameArea(new Random(), 512, 10202);
		System.out.println("blah\n...\n...\n...");
		xstream.alias("Demigods_Character", Character.class);
		xstream.alias("Demigods_Player", Player.class);
		xstream.aliasPackage("interactables_", "entities.interactables");
		xstream.aliasPackage("chars_", "entities.characters");
		//xstream.alias("Demigods_ResourceTile", ResourceTile.class);
//		System.out.println(xstream.toXML(area));
//		System.out.println(xstream.toXML(engine));
//		System.out.println(xstream.toXML(player));
//		jsonFileContents = gson.toJson(area);

//		String areaXMLString = xstream.toXML(engine.getActiveArea());
//		writeToFile(areaXMLString, "XML_TEST_FILE_000.txt");
//		engine.setActiveArea(null);
//		areaXMLString = null;
//		String engineXMLString = xstream.toXML(engine);
//		writeToFile(engineXMLString, "XML_TEST_FILE_001.txt");

//		writeToXMLFile(area,"XML_TEST_FILE_002.txt", xstream );
		System.out.println("Main Complete");
	}
	private static void writeToXMLFile(Object obj, String path, XStream xstream){
		try{
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream out = xstream.createObjectOutputStream(fos);
//			fos.write(fileContents.getBytes());
			out.writeObject(obj);
			out.close();
			fos.close();
//			System.out.println("Save complete");
		} catch (FileNotFoundException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}
}
