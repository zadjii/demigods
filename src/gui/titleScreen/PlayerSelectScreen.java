package gui.titleScreen;

import entities.characters.personas.Player;
import game.Demigods;
import util.Constants;
import util.SaveManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import util.Font;
import util.Images;

import java.io.File;

public class PlayerSelectScreen extends BasicGameState {

	private Input input;
    private int backgroundX, backgroundY;
    private int scrollLocation;
    private boolean[] hovering;
    private File[] savedPlayersFiles;
    private Player[] savedPlayers;
    private int selectedFile = -1;

    public PlayerSelectScreen() {
        savedPlayersFiles = SaveManager.listPlayers();
		if(savedPlayersFiles == null) savedPlayersFiles = new File[0];
        resetSelectionBooleans();
    }

    public void init(GameContainer gc, StateBasedGame game) {
        input = new Input(600);
        input.addListener(this);
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        Images.terrain.draw(backgroundX, backgroundY);
        g.setColor(Constants.MAIN_MENU_BG_COLOR);
        g.fillRect(0, 0, 1056, 600);

        Font.drawDkNorthumbriaString("SELECT PLAYER", 320, 75, 50, null, Font.FontType.RENDERED);
        Font.drawDkNorthumbriaString("BACK", 20, 545, 40, hovering[0] ? Color.white : null, hovering[0] ? null : Font.FontType.RENDERED);
        Font.drawDkNorthumbriaString("NEW PLAYER", 755, 545, 40, hovering[1] ? Color.white : null, hovering[1] ? null : Font.FontType.RENDERED);
        Font.drawTriniganFgString("Player", 10, 150, 30, Color.black);
        Font.drawTriniganFgString("Name", 100, 150, 30, Color.black);
        Font.drawTriniganFgString("Level", 600, 150, 30, Color.black);
        Font.drawTriniganFgString("Worlds", 665, 150, 30, Color.black);
        Font.drawTriniganFgString("Date Created", 750, 150, 30, Color.black);
        Font.drawTriniganFgString("Hours Played", 900, 150, 30, Color.black);

        for (int i = 0; i < savedPlayersFiles.length; ++i) {
            if (5 + scrollLocation + i * 40 > 0
		            && scrollLocation + i * 40 < 300) {
                Font.drawTriniganFgString(
		                savedPlayersFiles[i].getName(),
		                105,
		                195 + scrollLocation + i * 40,
		                24,
		                i == selectedFile ? Color.white : Color.black);
            }
        }

        g.setColor(Color.black);
        g.setLineWidth(2);
        g.drawLine(0, 190, 1056, 190);//line under column titles
        g.drawLine(0, 525, 1056, 525);//line above back button, new player button
        g.drawLine(90, 150, 90, 525);//between player&name
        g.drawLine(590, 150, 590, 525);//between name&level
        g.drawLine(655, 150, 655, 525);//between level&worlds
        g.drawLine(740, 150, 740, 525);//between worlds&dateCreated
        g.drawLine(890, 150, 890, 525);//between dateCreated&hoursPlayed
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) {
	    savedPlayersFiles = SaveManager.listPlayers();
        resetSelectionBooleans();

        int x = input.getMouseX(), y = input.getMouseY();
        backgroundX = -419 * x / 528 - 1365;
        backgroundY = -533 * y / 300 - 1365;

        if (x > 20 && x < 140 && y > 545 && y < 580) hovering[0] = true;
        else if (x > 750 && x < 1030 && y > 545 && y < 580) hovering[1] = true;
        else if (y > 190 && y < 525) {
            selectedFile = (y - 190 - scrollLocation) / 40;
        }
    }

    public int getID() {
        return Demigods.PLAYER_SELECT_SCREEN;
    }

    public void mousePressed(int button, int x, int y) {
//        if (x > 20 && x < 140 && y > 545 && y < 580) Demigods.enterMenuState(Demigods.TITLE_SCREEN);
//        else if (x > 750 && x < 1030 && y > 545 && y < 580) Demigods.enterMenuState(Demigods.NEW_PLAYER_SCREEN);

//	    if (y > 190 && y < 525) {
//
//		    //<hack>
//		    for (int i = 0; i < savedPlayersFiles.length; ++i) {
//			    if ( y > 195 + scrollLocation + i * 40
//					    &&  y < 195 + scrollLocation + i * 40 + 24 ) {
//				    selectedFile = i;
//			    }
//		    }
//		    //</hack>
//
//	    }
	    System.out.println("PlayerSelectionScreen:selectedFile = " + selectedFile);
    }
	public void mouseReleased(int button, int x, int y) {
		if (x > 20 && x < 140 && y > 545 && y < 580) Demigods.enterMenuState(Demigods.TITLE_SCREEN);
		else if (x > 750 && x < 1030 && y > 545 && y < 580) Demigods.enterMenuState(Demigods.NEW_PLAYER_SCREEN);



		else if (y > 190 && y < 525) {

			System.out.println("PlayerSelectionScreen:releasedMouse; " + selectedFile);

			if(selectedFile >= 0 && selectedFile < savedPlayersFiles.length){

				Demigods.setPlayer(SaveManager.loadPlayer(savedPlayersFiles[selectedFile].getName()));
				System.out.println(Demigods.getPlayer());
				selectedFile = -1;
				Demigods.enterMenuState(Demigods.WORLD_SELECT_SCREEN);

			}
		}
	}
    public void mouseWheelMoved(int change) {
        if (savedPlayersFiles.length * 40 > 525 - 190) {
            scrollLocation += (int)(0.01D * change);
            if (scrollLocation > 0) scrollLocation = 0;
            else if (scrollLocation < -(savedPlayersFiles.length * 40 - 335))
                scrollLocation = -(savedPlayersFiles.length * 40 - 335);
        }
    }

    private void resetSelectionBooleans() {
        hovering = new boolean[2];
//        selectedFile = -1;
    }
}
