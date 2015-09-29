package game;

import effects.SandPileEffect;
import entities.buildings.CaveBuilding;
import entities.buildings.DungeonBuilding;
import entities.characters.personas.NewVillager;
import entities.characters.personas.Persona;
import entities.characters.personas.Player;

import game.Demigods;
import game.Engine;
import game.GameScreen;
import org.lwjgl.util.Point;
import org.newdawn.slick.Input;


public class KeyHandler {

    public static boolean shiftKeyPressed = false;

    public static void keyPressed(int key, char c) {
//	    System.out.println("keypressed");
		Player player = Demigods.getPlayer();

		if (key == Input.KEY_ESCAPE) {
			if(!Engine.closeGUI()){
				Engine.togglePause();
			}
		}
		if (key == Input.KEY_LSHIFT || key == Input.KEY_RSHIFT) {
            shiftKeyPressed = true;
		}
		if (key == Input.KEY_LCONTROL) {

		}
		if (key == Input.KEY_SPACE) {
			for(Persona p : Engine.getAdjacentPersonas()){
				if(p instanceof NewVillager){
					NewVillager v = (NewVillager)p;
					v.talk();
					break;
				}
			}
		}
		if (c == 'e' || c == 'E') {
			Engine.toggleInventory();
		}
	    if (c == 'm' || c == 'M') {
		    Engine.toggleMap();
	    }
		if (c == 'r' || c == 'R') {
		}
	    if (c == 'l' || c == 'L') {
		    Engine.toggleSkillLevelUpPanel(Demigods.getPlayer().getCharacter());
	    }
	    if (c == '!') {
			player.setSelectedItemNum(0);
		}
		if (c == '@') {
			player.setSelectedItemNum(1);
		}
		if (c == '#') {
			player.setSelectedItemNum(2);
		}
		if (c == '$') {
			player.setSelectedItemNum(3);
		}
		if (c == '%') {
			player.setSelectedItemNum(4);
		}
	    if (c == '|') {//fixme
		    GameScreen.drawDebug = !GameScreen.drawDebug;
	    }
	    if (c == '}') {//fixme
		    GameScreen.drawTiming = !GameScreen.drawTiming;
	    }
	    if (c == '{') {//fixme
		    GameScreen.drawPaths = !GameScreen.drawPaths;
	    }
	    if (c == '1') {
		    useSkillBar(0);
	    }
	    if (c == '2') {
		    useSkillBar(1);
	    }
	    if (c == '3') {
		    useSkillBar(2);
	    }
	    if (c == '4') {
		    useSkillBar(3);
	    }
	    if (c == '5') {
		    useSkillBar(4);
	    }
	    if (c == ',') {
		    if (Engine.getZoom() > 0.25)
			    Engine.setZoom(Engine.getZoom() - 0.1f);
		    if (Engine.getZoom() < 0.25)
			    Engine.setZoom(0.25f);
	    }
	    if (c == '.') {
		    if (Engine.getZoom() < 4.0)
			    Engine.setZoom(Engine.getZoom() + 0.1f);
		    if (Engine.getZoom() > 4.0)
			    Engine.setZoom(4.0f);
	    }
	    if (c == 'C') {//fixme
		    try {
			    CaveBuilding building = new CaveBuilding(0,0);
			    Point mouse = Engine.getMouse();
//			    land.makeBuilding(
//					    building,
//					    mouse.getX(), mouse.getY(),
//					    new CaveFoundation(), Demigods.getEngine().getNextInteriorID()
//					    );
		    } catch (NullPointerException ignored) { }
	    }
	    if (c == 'V') {//fixme
		    try{
			    DungeonBuilding building = new DungeonBuilding(0,0);
			    Point mouse = Engine.getMouse();
//			    land.makeBuilding(
//					    building,
//					    mouse.getX(), mouse.getY(),
//					    new DungeonFoundation(), Demigods.getEngine().getNextInteriorID()
//			    );
		    } catch (NullPointerException ignored) {

		    }
	    }

	    if(c=='B'){//fixme
		    Engine.exploreMenu();
	    }

	    if(c==':'){//fixme
		    Engine.respawn();//woo
	    }
	    if (c == 'Y') {
		    Engine.add(new SandPileEffect(player.getCharacter(), Engine.getMouse(), 15));
	    }
    }


	private static void useSkillBar(int index){
		Player player = Demigods.getPlayer();
		try {
			if (player.getEquippedSkills()[index].isReadyable()) {
				player.getCharacter().setSkillReadied(true);
				player.getCharacter().setReadiedSkill(player.getEquippedSkills()[index]);
			}
			else {
				player.getCharacter().useSkill(index, Engine.getMouse());
			}
		} catch (NullPointerException ignored) {

		}
	}

    public static void keyReleased(int key, char c) {
	    Player player = Demigods.getPlayer();
		if (player.getCharacter().getIsStopped()) {
			player.getCharacter().setIsStopped(false);
		}
		if (key == Input.KEY_LSHIFT || key == Input.KEY_RSHIFT) {
            shiftKeyPressed = false;
		}
	    if( key == Input.KEY_ENTER){
		    Engine.walkOutOfArea();
	    }
    }
}
