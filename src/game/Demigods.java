package game;

import entities.characters.etc.Inventory;
import entities.characters.personas.Player;
import gui.titleScreen.*;
import org.lwjgl.util.Point;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import util.SaveManager;

public class Demigods extends StateBasedGame {

    public static Demigods demigods;
    private static Player player;
    private static String worldName;

    public static final String GAME_VERSION = "v0.1";
    public static final int LOGO_SCREEN = 0;
    public static final int TITLE_SCREEN = 1;
    public static final int PLAYER_SELECT_SCREEN = 2;
    public static final int NEW_PLAYER_SCREEN = 3;
    public static final int WORLD_SELECT_SCREEN = 4;
    public static final int NEW_WORLD_SCREEN = 5;
    public static final int LOADING_SCREEN = 6;
    public static final int GAME_SCREEN = 7;

    public static final int DEFAULT_SCREEN_HEIGHT = 600;//1152x768 is nice too //Yea but at this point changing the window size would involve a LOT of refactoring. We should save that for final release.
    public static final int DEFAULT_SCREEN_WIDTH = 1056;


    private static TitleScreen.LogoScreen logoScreen;
    private static TitleScreen titleScreen;
    private static PlayerSelectScreen playerSelectScreen;
    private static NewPlayerScreen newPlayerScreen;
    private static WorldSelectScreen worldSelectScreen;
    private static NewWorldScreen newWorldScreen;
    private static LoadingScreen loadingScreen;
    private static GameScreen gameScreen;

	private static int screenWidth = DEFAULT_SCREEN_WIDTH;
	private static int screenHeight = DEFAULT_SCREEN_HEIGHT;

    public Demigods() throws SlickException {
        super("Demigods: Adventures " + GAME_VERSION);
        addState(titleScreen);
        addState(logoScreen);
    }

    public static void main(String[] args) throws SlickException {
        logoScreen = new TitleScreen.LogoScreen();
        titleScreen = new TitleScreen();
        playerSelectScreen = new PlayerSelectScreen();
        newPlayerScreen = new NewPlayerScreen();
        worldSelectScreen = new WorldSelectScreen();
        newWorldScreen = new NewWorldScreen();
        loadingScreen = new LoadingScreen();

        demigods = new Demigods();
        AppGameContainer app = new AppGameContainer(demigods);
        demigods.initStatesList(app);
        app.setDisplayMode(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT, false);

        app.setVSync(true);//This here is keeping your fps down. it can be commented out for testing, but i think it needs to be on.
        app.setTargetFrameRate(60);
//        app.setShowFPS(false);
        demigods.enterState(LOGO_SCREEN);
        app.start();
    }

    public static void startGameThread(String name, final boolean newWorld) {
        worldName = name;
        new Thread(new Runnable() {
            public void run() {
                try {
                    startTheGodDamnGame(newWorld);
                } catch (SlickException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void startTheGodDamnGame(boolean newWorld) throws SlickException {
        gameScreen = new GameScreen();
        gameScreen.init(demigods.getContainer(), demigods);

	    if(newWorld){
		    Engine.init(player, worldName);
	    }
	    else {

		    Engine.setPlayer(player);
			Engine.getActiveArea().getPersonas().add(player);
	    }
	    Engine.input = new Input(600);
	    SaveManager.setWorldName(worldName);
	    SaveManager.setPlayerName(player.getName());

	    Engine.setGameScreen(gameScreen);
	    SaveManager.saveGame(Engine.get());

	    gameScreen.setInput(Engine.getInput());

//	    if(demigods.getState(GAME_SCREEN) == null)
		    demigods.addState(gameScreen);
        demigods.enterState(GAME_SCREEN);

    }

    public static void enterMenuState(int state) {
        demigods.enterState(state);
    }

    public void initStatesList(GameContainer gc) throws SlickException {
        addState(logoScreen);
        addState(titleScreen);
        addState(playerSelectScreen);
        addState(newPlayerScreen);
        addState(worldSelectScreen);
        addState(newWorldScreen);
        addState(loadingScreen);
    }

    public static String getWorldName() {
        return worldName;
    }

    public static void setPlayer(Player p) {
        player = p;
        SaveManager.setPlayerName(p.getName());
    }

    public static void setNewPlayer(Player np) {
        SaveManager.setPlayerName(np.getName());
        SaveManager.savePlayer(np);
        player = np;
    }

    public static Player getPlayer() {
        return player;
    }

    public static Inventory getPlayerInventory() {
        return getPlayer().getCharacter().getInventory();
    }

	public static String getLoc(Point p) {
        return "(" + p.getX() + ", " + p.getY() + ")";
    }
	public static int getScreenWidth(){
		return screenWidth;
	}

	public static int getScreenHeight(){
		return screenHeight;
	}

//	public static void setScreenHeight(int screenHeight) {
//		Demigods.screenHeight = screenHeight;
//	}
//
//	public static void setScreenWidth(int screenWidth) {
//		Demigods.screenWidth = screenWidth;
//	}
	public static void setScreenSize(int screenWidth, int screenHeight, GameContainer gc){
		Demigods.screenWidth = screenWidth;
		Demigods.screenHeight= screenHeight;
		try {
			((AppGameContainer) gc).setDisplayMode(
					Demigods.screenWidth,
					Demigods.screenHeight,
					false
			);
		} catch (SlickException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}
}