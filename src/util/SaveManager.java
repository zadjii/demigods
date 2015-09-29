package util;

import entities.characters.personas.Player;
import game.Engine;
import areas.*;
import org.lwjgl.util.Point;

import java.io.*;
import java.util.ArrayList;

public class SaveManager {

    private static String playerName, worldName;

    public static void setPlayerName(String name) {
        playerName = processName(name);
    }

    public static void setWorldName(String name) {
        worldName = processName(name);
    }

    public static File[] listPlayers() {
	    File savDir = new File("sav");
	    if(!savDir.exists()){
		    if(!savDir.exists())savDir.mkdir();
	    }
        File playersDir = new File("sav/players");
	    if(!playersDir.exists()){
		    if(!playersDir.exists())playersDir.mkdir();
	    }
	    File[] allPlayerFolders = playersDir.listFiles();
	    int actualNumberOfPlayers = 0;
	    ArrayList<File> actualPlayersList = new ArrayList<File>();
	    for (int i = 0; i < allPlayerFolders.length; i++) {
		    if(allPlayerFolders[i].getName() != null && allPlayerFolders[i].getName().charAt(0) != '.'){
			    actualNumberOfPlayers++;
			    actualPlayersList.add(allPlayerFolders[i]);
		    }
	    }
	    return actualPlayersList.toArray(new File[actualNumberOfPlayers]);
//        return playersDir.listFiles();
    }

    public static File[] listWorlds() {
        File worldsDir = new File("sav/players/" + playerName + "/worlds");
        return worldsDir.listFiles();
    }

    private static void initializeXDirectory(int x){
        //System.out.println("initializing: " + x);


//	    try {
//		    File worldDir = new File("sav/players/" + playerName +
//				    "/worlds/" + worldName);
//		    if(!worldDir.exists())SaveManager.setUpNewWorldDirectory();//worldDir.createNewFile();
//	    } catch (IOException e) {
//		    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//	    }

	    File xDir = new File("sav/players/" + playerName +
                "/worlds/" + worldName + "/" + x);

//		System.out.println("Directory is: " + "sav/players/" + playerName +
//				"/worlds/" + worldName + "/" + x );


        if(!xDir.exists()){
            //	System.out.println("xDir doesn't exist");
            if (!xDir.mkdir()){
                //throw new IOException("Unable to create directory " + xDir);
                //		System.out.println("i didnt make the xdir. wat.");
            }//else System.out.println("no problems making xDir " + x);
        }
        //for(String s: xDir.list()) System.out.println('\t' + s);


        //if(xDir.exists()) System.out.println(x + " folder exists!");
    }

    public static void savePlayer(Player p) {
//        try {
//            if (overwrite) {
//                setUpNewPlayerDirectory();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        writeObjectToPath(p, "sav/players/" + playerName + "/player.dp");
    }

    public static Player loadPlayer(String name) {
        setPlayerName(name);
        return (Player)readObjectFromPath("sav/players/" + playerName + "/player.dp");
    }


	public static void saveGame(Engine e) {
		saveEngine(e, Engine.getPlayer(), Engine.getWorldName());
		savePlayer(Engine.getPlayer());
		Engine.saveArea();
	}

    private static void saveEngine(Engine e, Player player, String worldName) {
//        try {
//            if (overwrite) {
//                setUpNewWorldDirectory();
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//	        try {
//		        Thread.sleep(10000L);
//	        } catch (InterruptedException e1) {
//		        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//	        }
//        }
        writeObjectToPath(e, "sav/players/" + player.getName() + "/worlds/" + worldName + "/engine.de");
//	    try {
//		    e.getWorld().outputImage();
//	    } catch (IOException e1) {
//		    System.out.println("World wasn't printed");
//	    }
    }

    public static Engine loadEngine(String name) {
        setWorldName(name);
        return (Engine)readObjectFromPath("sav/players/" + playerName + "/worlds/" + worldName + "/engine.de");
    }

    public static void saveArea(NewGameArea area) {
        writeObjectToPath(area, "sav/players/" + playerName + "/worlds/" + worldName + "/" + area.getID() + ".da");
    }

    public static void saveArea(NewGameArea area, Point loc) {
        int x = loc.getX();
        int y = loc.getY();

		/*
		 * Some weird ass shit happend the first time
		  * you try and save an area, like only for the first area.
		  *
		  * Idk why.
		  *
		  * Anyhow, when a player leaves that area, it'll save that area just
		  * fine, so we really have nothing to worry about, there
		  * will just always be an exception at the beginning of the game.
		 */
        //try{
        initializeXDirectory(x);
        System.out.println("x initialization finished");
        //}catch (IOException e){
			/**/
        //	System.err.println("caught ioe initializing xdir");
        //}

        writeObjectToPath(area, "sav/players/" + playerName +
                "/worlds/" + worldName + "/" + x + "/" + y + ".da");
    }

    public static NewGameArea loadArea(int id) {
        return (NewGameArea)readObjectFromPath("sav/players/" + playerName + "/worlds/" + worldName + "/" + id + ".da");
    }

    public static NewGameArea loadArea(Point loc) {
        return (NewGameArea)readObjectFromPath("sav/players/" + playerName + "/worlds/" + worldName + "/" + loc.getX() + "/" + loc.getY() + ".da");
    }

    private static void writeObjectToPath(Object obj, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object readObjectFromPath(String path) {
        Object obj = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

	public static void saveNewPlayerDir(Player player){

		File playersDir = new File("sav/players");
		if(!playersDir.exists())playersDir.mkdir();

		File playerDir = new File("sav/players/" + player.getName());
		if (playerDir.exists()) try {
			delete(playerDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		playerDir.mkdir();

		File worldsDir = new File("sav/players/" + player.getName() + "/worlds");
		if(!worldsDir.exists())worldsDir.mkdir();

		savePlayer(player);

	}

	public static void saveNewWorldDir(Player player, String worldName){
		File playerDir = new File("sav/players/" + player.getName());
		if(!playerDir.exists())saveNewPlayerDir(player);

		File worldDir = new File("sav/players/" + playerName + "/worlds/" + worldName);
		if (worldDir.exists()) try {
			delete(worldDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		worldDir.mkdir();
		File worldsDir = new File("sav/players/" + player.getName() + "/worlds");
		System.out.println(player.getName() + "'s worlds: " + worldsDir.list());
	}
//    private static void setUpNewPlayerDirectory() throws IOException {
//        File playersDir = new File("sav/players");
//	    if(!playersDir.exists())playersDir.mkdir();
//        File playerDir = new File("sav/players/" + playerName);
//        if (playerDir.exists()) try {
//            delete(playerDir);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (!playerDir.mkdir()) throw new IOException("Unable to create directory " + playerDir);
//        File worldsDir = new File("sav/players/" + playerName + "/worlds");
//        if (!worldsDir.mkdir()) throw new IOException("Unable to create directory " + worldsDir);
//    }

//    private static void setUpNewWorldDirectory() throws IOException {
//
//	    File playerDir = new File("sav/players/" + playerName);
//	    if(!playerDir.exists())SaveManager.setUpNewPlayerDirectory();
//
//	    File worldsDir = new File("sav/players/" + playerName + "/worlds/");
//	    if(!worldsDir.exists())worldsDir.mkdir();
//        File worldDir = new File("sav/players/" + playerName + "/worlds/" + worldName);
//        if (worldDir.exists()) try {
//            delete(worldDir);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (!worldDir.mkdir()) throw new IOException("Unable to create directory " + worldDir);
//    }

    private static void delete(File file) throws IOException {
        if (file.isDirectory()) {
            if (file.list().length == 0) {
                if (!file.delete()) throw new IOException("Unable to delete directory " + file);
            } else {
                String files[] = file.list();
                for (String temp : files) {
                    File fileDelete = new File(file, temp);
                    delete(fileDelete);
                }
                if (file.list().length == 0) {
                    if (!file.delete()) throw new IOException("Unable to delete directory " + file);
                }
            }
        } else {
            if (!file.delete()) throw new IOException("Unable to delete file " + file);
        }
    }

    private static String processName(String name) {
        String newName = "";
        for (char c : name.toCharArray()) {
            if (c == ' ') newName += '_';
            else newName += c;
        }
        return newName;
    }
}
