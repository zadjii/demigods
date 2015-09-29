package game;

import effects.Effect;
import entities.characters.Character;
import entities.characters.personas.Persona;
import entities.interactables.Interactable;
import entities.particles.etc.DroppedItem;
import entities.particles.Particle;
import entities.tiles.Tiles;
import areas.*;
import areas.etc.Entrance;
import gui.*;
import gui.guiPanels.*;
import gui.guiPanels.GUIPanel;
import items.foundations.Foundation;
import org.lwjgl.util.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import util.*;

import java.util.ArrayList;

public class GameScreen extends BasicGameState {

//	private Point mouse = new Point(-1, -1);


	public boolean haveToRelease = false;
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private static final Color transparentBlack = new Color(0, 0, 0, .5f);
	private double startOfTick = 0;
	protected static boolean drawDebug = false;
	protected static boolean drawTiming = false;
	protected static boolean drawPaths = false;
	//private Image lighting;
	private Graphics overlay;
	//	private GUIPanel guiPanel;
	private Rectangle notificationZone = new Rectangle(800, 10, 32, 32);

	private byte tenCountTicker = 0;
	private byte sixtyCountTicker = 0;
	private boolean areaClearedNotification = false;
	private Color darkness = new Color(0f,0f,0f,.5f);
//	public Point getMouse() {
//		return mouse;
//	}

	public GUIPanel getGuiPanel() {
		return GUIManager.getActivePanel();
	}

	public void setGuiPanel(GUIPanel panel) {
//		this.guiPanel = panel;
		GUIManager.setActivePanel(panel);
	}
	public void closeGUIPanel(){
//		this.guiPanel = null;
		GUIManager.setActivePanel(null);
		Engine.unpause();
	}
	public boolean drawingPaths(){return drawPaths;}

    //todo Convert all the GUI stuf to use Engine.tick() and an Input object
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		GUIManager.mouseDragged(oldx, oldy, newx, newy);
	}

	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		GUIManager.mouseMoved(oldx, oldy, newx,  newy);
	}

	public void mousePressed(int button, int x, int y) {

		GUIManager.mousePressed(button, x, y);

	}

	public void mouseReleased(int button, int x, int y) {
		GUIManager.mouseReleased(button, x, y);

	}

	public void mouseWheelMoved(int change) {
		GUIManager.mouseWheelMoved(change);
	}

	public void keyPressed(int key, char c) {
		KeyHandler.keyPressed(key, c);
//		System.out.println("Key Pressed: " + key);
	}

	public void keyReleased(int key, char c) {
		KeyHandler.keyReleased(key, c);
	}

	public void init(GameContainer gc, StateBasedGame game) throws SlickException {


		//Crafting.init(Demigods.getPlayerInventory(), engine.getActiveArea());
	}

	//Shortcut to render()
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		if(Engine.getInput().isKeyDown(Input.KEY_0)){

//			((AppGameContainer) gc).setDisplayMode(
//					1500,
//					600,
//					false
//			);
			Demigods.setScreenSize(800, 600, gc);
		}
		else if(Engine.getInput().isKeyDown(Input.KEY_9)){
//			((AppGameContainer) gc).setDisplayMode(
//					Demigods.DEFAULT_SCREEN_WIDTH,
//					Demigods.DEFAULT_SCREEN_HEIGHT,
//					false
//			);
			Demigods.setScreenSize(Demigods.DEFAULT_SCREEN_WIDTH, Demigods.DEFAULT_SCREEN_HEIGHT, gc);
		}
//		gc.setDisplayMode(800, 600, false);
//		if(lighting == null){
//			lighting = new Image(1056, 600);
//			overlay = lighting.getGraphics();
//		}
		startOfTick = System.nanoTime();
		//long duration = System.nanoTime();
		//System.out.println("GameScreen.render(){");
		g.setAntiAlias(false);
		g.setLineWidth(1);

//		Rectangle screenBounds = Engine.getScreen();
		float screenX = Engine.getScreenX(), screenY = Engine.getScreenY();
		float screenW = Engine.getScreenW(), screenH = Engine.getScreenH();
		NewGameArea area = Engine.getActiveArea();

//		/*
//		 * The renderXYZ() methods form the core of the actual game drawing. The
//		 * rest of the stuff that is still in this method can be left here
//		 * pretty static.
//		 */
//
		render002(g, screenX, screenY, screenW, screenH);
//		render003(g, screenX, screenY, screenW, screenH);

		//render003 saves like, 2 fps :/


		float z = Engine.getZoom();

		// ///////
		// ITEMS//
		// ///////
		for (DroppedItem i : area.getDroppedItems()) {
			Images.itemSheet.getSprite(
					i.item.getImageID()[0],i.item.getImageID()[1]).draw(
					(i.getX() - screenX) * z,
					(i.getY() - i.getZ() - screenY) * z,
					16 * z,
					16 * z
			);
		}


		// /////////////////
		// Skill Targeting//
		// /////////////////
		if(Engine.getPlayer().getCharacter().getSkillReadied()){
			Engine.getPlayer().getCharacter().getReadiedSkill().readiedDraw(screenX, screenY, z, g);
		}
		// /////////////////////////////
		// EQUIPPED FOUNDATION OUTLINE//
		// /////////////////////////////
		try {
			if (Engine.getPlayer().getEquippedItem().isFoundation()) {
				Foundation f = (Foundation)Engine.getPlayer().getEquippedItem();
				int factor = 16;
				int mgx = Engine.getMouse().getX() / factor * factor;
				int mgy = Engine.getMouse().getY() / factor * factor;
//				if(mgx < 0)mgx -= 16;
//				if(mgy < 0)mgy -= 16;

				int potentialWidth = f.getPotentialBounds(mgx, mgy).getWidth();
				int potentialHeight = f.getPotentialBounds(mgx, mgy).getHeight();
				g.setColor(Color.black);
				g.drawRect((mgx - screenX) * z, (mgy - screenY) * z, (potentialWidth * 16) * z, (potentialHeight * 16) * z);
			}
		} catch (NullPointerException e) {}



		// ///////////////
		// SPELL EFFECTS//
		// ///////////////
		g.setColor(Color.red);
		for (Effect effect : area.getEffects()) {
			if(drawDebug){
				Rectangle hitbox = effect.getAbsBounds();
				g.fillRect((hitbox.getX()-screenX)*z, (hitbox.getY()-screenY)*z, hitbox.getWidth()*z, hitbox.getHeight()*z);
			}
			effect.draw(screenX, screenY, g, z);
		}

//*********************************************************************************************************************************
//*********************************************************************************************************************************
//		g.texture(new Circle(x,y,r), Images.lightingImage);//**********************************************************************
//*********************************************************************************************************************************
//*********************************************************************************************************************************



		// ///////////
		// PARTICLES//
		// ///////////
		int count = 0;
		ArrayList<Particle> temp = new ArrayList<Particle>();
		for (Particle p : getParticles()) {
			p.tick();
			if (p.isOff()) {
				temp.add(p);
			}
			p.draw(screenX, screenY, g, z);
		}

		for (Particle p : temp) {
			getParticles().remove(p);
		}



		// ////////////////
		// GUI COMPONENTS//
		// ////////////////
		if(Engine.isPaused() && GUIManager.getActivePanel() != null)PausePanel.drawBG(g);
		GUIManager.draw(g);
//		if(guiPanel!=null)guiPanel.draw(g);
		if(Engine.isPaused() && GUIManager.getActivePanel() == null)PausePanel.draw(g);
		if(!area.isBase()&&area.isCleared()){
			areaClearedNotification = true;
			if(sixtyCountTicker <10)
				Images.notification02.draw(800, 10);
			else Images.notification.draw(800, 10);
		}
		else{
			areaClearedNotification = false;
		}
		// FPS
		util.Font.drawTriniganFgString(Integer.toString(gc.getFPS()), Demigods.getScreenWidth() - 64, Demigods.getScreenHeight() - 64, 16, Color.white);
//		util.Font.drawTriniganFgString(engine.getPlayer().getCharacter().getLoc(), 1000, 550, 16, Color.white);
		if (drawDebug) {
			util.Font.drawTriniganFgString(Engine.getPlayer().getCharacter().getLocString(), 1000, 550, 16, Color.white);
			util.Font.drawTriniganFgString(Engine.getPlayer().getMapLoc().getX() + ", " + Engine.getPlayer().getMapLoc().getY(), 1000, 530, 16, Color.white);
			util.Font.drawTriniganFgString(area.getPersonas().size() + " " + area.getPersonas().toString(), 0, 110, 16, Color.white);
		}



		// ////////////
		// CONDITIONS//
		// ////////////

//		for (int i = 0; i < Demigods.getPlayer().getCharacter().getConditions().size(); ++i) {
//			Condition condition = Demigods.getPlayer().getCharacter().getConditions().get(i);
//			if (condition.isVisible()) {
//				Images.skillSheet.getSprite(condition.getImageLoc().getX(), condition.getImageLoc().getY()).draw(5 + (i * 25), 470, 25, 25);
//			}
//		}



	}

	// TODO Shortcut to render002()

	public void render002(Graphics g, float sx, float sy, float sw, float sh) {

		float z = Engine.getZoom();
		NewGameArea area = Engine.getActiveArea();
		int x0 = (int)(sx / 16) - 5;
		int y0 = (int)(sy / 16) - 5;

		int xf = (int)((sx + sw) / 16) + 5;
		int yf = (int)((sy + sh) / 16) + 5;


		for(Persona p : Engine.getActiveArea().getPersonas().getPersonas()){
			p.undraw();
		}

		g.setColor(Color.black);

		//////////////
		//FIRST LOOP//
		//////////////
		for (int c = 0; c < sh/16 + 10; ++c) {
			for (int r = 0; r < sw/16 + 10; ++r) {

				int x = (int)((x0 + r));
				int y = (int)((y0 + c));

				try {
						Tiles.simpleDrawTile((x0 + r)*16, (y0 + c)*16, sx, sy, area.getMap()[x][y], z);
//						Tiles.drawTile((x0 + r)*16, (y0 + c)*16, r, c, sx, sy, area.getMap()[x][y],area.getMap(), z);
//						g.setColor(Tiles.MINIMAP_COLORS[drawingMap.getMap()[x][y]]);
//						g.fillRect((r * 16 - sx) * z, (c * 16 - sy) * z, 16 * z, 16 * z);
						// Entrances
						if (area.getEntrances()[x][y] != null) {
							drawEntrance(area.getEntrances()[x][y], r, c, sx, sy, z);
						}
						Interactable interactable = area.getInteractables()[x][y];
						if (interactable != null) {
							if(!interactable.drawnAbove()){
								interactable.draw(sx, sy, z, g);
							}
						}
				} catch (ArrayIndexOutOfBoundsException e) {
				} catch (NullPointerException e){
				}
			}
		}


		// ///////////////
		// SPELL EFFECTS//
		// ///////////////
		g.setColor(Color.red);
		for (Effect effect : area.getEffects()) {
			if(drawDebug){
				Rectangle hitbox = effect.getAbsBounds();
				g.fillRect((hitbox.getX()-sx)*z, (hitbox.getY()-sy)*z, hitbox.getWidth()*z, hitbox.getHeight()*z);
			}
			effect.drawBelow(sx, sy, g, z);
		}

		if(drawDebug){
			for(Persona p : area.getPersonas().getPersonas()){
				Character pc = p.getCharacter();

				g.setColor(Color.orange);
				g.fillRect(
						(p.getCharacter().getAbsHitbox().getX() - sx)*z,
						(p.getCharacter().getAbsHitbox().getY() - sy)*z,
						p.getCharacter().getAbsHitbox().getWidth()*z,
						p.getCharacter().getAbsHitbox().getHeight()*z
				);
				g.setColor(Color.cyan);
				g.fillRect(
						(p.getCharacter().getX() - sx)*z,
						(p.getCharacter().getY() - sy)*z,
						5*z,
						5*z
				);
				//p.draw(
				//		(-(pc.getBaseMap().getX() * Map.SIZE * 16) + sx),
				//		(-(pc.getBaseMap().getY() * Map.SIZE * 16) + sy),
				//		z,
				//		g);
			}
		}
		//else{
			///////////////
			//SECOND LOOP//
			///////////////
			for (int c = 0; c < sh/16 + 10; ++c) {
				for (int r = 0; r < sw/16 + 10; ++r) {

					int x = (int)((x0 + r));
					int y = (int)((y0 + c));

					try {
						// /////////////
						// CHARCACTERS//
						// /////////////
						for(Persona p : area.getPersonas().get(new Rectangle(x*16, y*16, 16, 16))){
							p.draw(sx, sy, z, g, x, y);
						}
						Tiles.drawSecondLayerSimple((x0 + r) * 16, (y0 + c) * 16, sx, sy, area.getMap()[x][y], z);
						// ///////////////
						// INTERACTABLES//
						// ///////////////
						Interactable interactable = area.getInteractables()[x][y];
						if (interactable != null) {
							if(interactable.drawnAbove()){
								interactable.draw(sx, sy, z, g);
							}
						}



						// ////////////////
						// RESOURCE TILES//
						// ////////////////
						if (area.getResources()[x][y] != null) {
							area.getResources()[x][y].draw(sx, sy, z,g);
						}



					} catch (ArrayIndexOutOfBoundsException e) {
					} catch (NullPointerException e){
					}
				}
			}
		//}

	}


	private void drawEntrance(Entrance e, int r, int c, float sx, float sy, float z){

		if (e.isFacingUp()) {
			if (e.isOn()) {
				Images.tileSheet.getSprite(1, 4).draw((r * 16 - sx) * z, (c * 16 - sy) * z, 16 * z, 16 * z);
			} else {
				Images.tileSheet.getSprite(0, 4).draw((r * 16 - sx) * z, (c * 16 - sy) * z, 16 * z, 16 * z);
			}
		} else {
			if (e.isOn()) {
				Images.tileSheet.getSprite(1, 4).draw((r * 16 - sx + 16) * z, (c * 16 - sy + 16) * z, (r * 16 - sx) * z, (c * 16 - sy) * z, 16, 16, 0, 0);
			} else {
				Images.tileSheet.getSprite(0, 4).draw((r * 16 - sx + 16) * z, (c * 16 - sy + 16) * z, (r * 16 - sx) * z, (c * 16 - sy) * z, 16, 16, 0, 0);
			}
		}
		e.tick();
	}


	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		startOfTick = System.nanoTime();
		Engine.update();
//		if(guiPanel!=null)guiPanel.tick();
		GUIManager.tick();
		startOfTick = System.nanoTime() - startOfTick;
		startOfTick = startOfTick/1000000000;
		if(startOfTick < 0.016){
		}
		if(tenCountTicker < 10)tenCountTicker++;
		else tenCountTicker = 0;

		if(sixtyCountTicker < 60)sixtyCountTicker++;
		else sixtyCountTicker = 0;
		//System.out.println(startOfTick);

	}

    public int getID() {
        return Demigods.GAME_SCREEN;
    }

	public ArrayList<Particle> getParticles() {
		return particles;
	}
}