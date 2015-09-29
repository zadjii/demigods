package gui.guiPanels;

import areas.World;
import entities.characters.personas.Player;
import game.Engine;
import gui.util.Button;
import gui.util.OnClickListener;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import util.Constants;
import util.Images;

import static areas.World.*;
import static util.Constants.DARK_BEIGE;

public class MapPanel extends GUIPanel {

	private static final int MAP_SIZE = 320;

	private static final float MIN_ZOOM = 0.5f;
	private static final float MAX_ZOOM = 16.0f;

	private static final int BORDER = 32;

	private boolean dragging = false;




	private float zoom = 12.0f;

	private Rectangle mapBounds;

	private Rectangle centerMapButtonBounds;

//	private Point mapScreenPoint;//like sx,sy in gamescreen, but for the map view
//	private float mapScreenSizeAtZoomOfOne = 128.0f;//i don't even...
	//this should just be MAP_SIZE.
	private Rectangle mapScreenBounds; //like the screen bounds

	private Point playerLocation;

	private float playerLocBlinky = 0.0f;
	private float playerLocBlinkyInc = 0.05f;
	private boolean playerLocBlinkyOn = false;


	private Point selectedTile;

	private Button fastTravelButton = new Button();
	private Button enterUnderworldButton = new Button();

	private String fastTravelButtonText = "Fast Travel";
	private String enterUnderworldButtonText = "Enter Underworld";
	private String descriptiveTextLineOne = "Some Text";
	private String descriptiveTextLineOTwo = "More Text";

	private String popupText;

	private boolean popupOn = false;
	private Button popupActionButton = new Button();
	private Button popupCancelButton = new Button();
	private Rectangle popupBounds;


    public MapPanel() {
        x = 376;
	    y = 48;
	    width = MAP_SIZE + (2*BORDER);//2 16px borders
	    height = BORDER + MAP_SIZE + 128 + BORDER;//border, map, text, border

	    mapBounds = new Rectangle(
			    x + BORDER,
			    y + BORDER,
			    MAP_SIZE,
			    MAP_SIZE
	    );

	    zoom = 8.0f;
	    Player player = Engine.getPlayer();
	    playerLocation = new Point(player.getMapLoc().getX(), player.getMapLoc().getY());

	    centerScreen(playerLocation.getX(), playerLocation.getY());
		selectedTile = getCenterOfMap();

		fastTravelButton.setBounds(new Rectangle(
			    (this.getBounds().getX() + this.getBounds().getWidth()/2) - (80),
			    (this.getBounds().getY() + this.getBounds().getHeight()) - (96),
			    160,
			    32
	    ));
	    enterUnderworldButton.setBounds(new Rectangle(
			    fastTravelButton.getBounds().getX(),
			    fastTravelButton.getBounds().getY() + fastTravelButton.getBounds().getHeight() + 8,
			    fastTravelButton.getBounds().getWidth(),
			    fastTravelButton.getBounds().getHeight()
	    ));
	    enterUnderworldButton.setEnabled(false);

	    popupBounds = new Rectangle(
			    (this.getBounds().getX() + this.getBounds().getWidth()/2) - (100),
			    (this.getBounds().getY() + this.getBounds().getHeight()/2) - (100),
			    200,
			    200
	    );
	    popupActionButton.setBounds(new Rectangle(
			    (this.getBounds().getX() + this.getBounds().getWidth()/2) - (80),
			    (popupBounds.getY() + popupBounds.getHeight()) - (96),
			    160,
			    32
	    ));
	    popupCancelButton.setBounds(new Rectangle(
			    popupActionButton.getBounds().getX(),
			    popupActionButton.getBounds().getY() + popupActionButton.getBounds().getHeight() + 8,
			    popupActionButton.getBounds().getWidth(),
			    popupActionButton.getBounds().getHeight()
	    ));
	    popupOn = false;
		popupCancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				popupOn = false;
			}
		});


	    updateFastTravelButton();
	    updateUnderworldButton();
	    updateDescriptiveText();
    }


    public void draw(Graphics g) {
	    super.draw(g);
	    g.setColor(DARK_BEIGE);

	    g.fillRect(
			    mapBounds.getX() - 2,
			    mapBounds.getY() - 2,
			    mapBounds.getWidth() + 4,
			    mapBounds.getHeight() + 4
	    );


	    World world = Engine.getWorld();

	    Player player = Engine.getPlayer();
	    playerLocation = new Point(player.getMapLoc().getX(), player.getMapLoc().getY());

//	    centerScreen(playerLocation.getX(), playerLocation.getY());

	    //More things copied from gamescreen, albeit differently
	    float sx = mapScreenBounds.getX();
	    float sy = mapScreenBounds.getY();
	    float sw = mapScreenBounds.getWidth();
	    float sh = mapScreenBounds.getHeight();

	    int x0 = (int)(sx);
	    int y0 = (int)(sy);

//	    System.out.println(sx + ", " + sy);
//	    System.out.println(world);

	    for (int x = -1; x < sw+1; ++x) {
		    for (int y = -1; y < sh+1; ++y) {

			    int r = (int)((x0 + x));
			    int c = (int)((y0 + y));

			    //r,c is the array index to query
			    //x,y is where on the map screen to draw

			    try {
//				    Tiles.simpleDrawTile((x0 + y) * 16, (y0 + x) * 16, sx, sy, area.getMap()[r][c], z);
					drawSingleMap(
							x*zoom + this.x + BORDER,
							y*zoom + this.y + BORDER,
							world.getBiome(new Point(r, c)),
							world.getFeature(new Point(r, c)),
							world.isExplored(new Point(r, c)),
							g
					);
			    } catch (ArrayIndexOutOfBoundsException e) {
				    System.out.println("AIOoBE");
			    } catch (NullPointerException e){
				    System.out.println("NPE");
			    }

//		        if(r == playerLocation.getX() && c == playerLocation.getY()){
//			        if(playerLocBlinkyOn){
//				        g.setColor(Color.white);
//				        g.fillRect(
//						        x * zoom + this.x + BORDER,
//						        y * zoom + this.y + BORDER,
//						        zoom,
//						        zoom
//				        );
//			        }
//		        }
//			    if(r == selectedTile.getX() && c == selectedTile.getY()){
//
//
//
//			    }
		    }
	    }
	    if(mapScreenBounds.contains(selectedTile.getX(), selectedTile.getY())){
		    g.setColor(Color.black);
		    g.setLineWidth(zoom/3.0f);
		    g.drawRect(
				    (selectedTile.getX()-x0) * zoom + this.x + BORDER,
				    (selectedTile.getY()-y0) * zoom + this.y + BORDER,
				    zoom,
				    zoom
		    );
		    g.setLineWidth(1.0f);
	    }


	    if(mapScreenBounds.contains(playerLocation.getX(), playerLocation.getY()) && playerLocBlinkyOn){
	        g.setColor(Color.white);
	        g.fillRect(
		    	    (playerLocation.getX()-x0) * zoom + this.x + BORDER,
		    	    (playerLocation.getY()-y0) * zoom + this.y + BORDER,
		    	    zoom,
		    	    zoom
	        );
	    }

	    fastTravelButton.draw(g);
	    enterUnderworldButton.draw(g);

	    if(popupOn){
		    Images.panelBG.draw(popupBounds.getX(), popupBounds.getY(), popupBounds.getWidth(), popupBounds.getHeight());
		    popupActionButton.draw(g);
		    popupCancelButton.draw(g);
	    }

    }

	public void tick() {

		playerLocBlinky += playerLocBlinkyInc;
		if(playerLocBlinky >= 1.0f){
			playerLocBlinkyOn = !playerLocBlinkyOn;
			playerLocBlinky = 0.0f;
		}

	}
    public void mousePressed(int button, int x, int y) {
        super.mousePressed(button, x, y);
	    if (button == Input.MOUSE_LEFT_BUTTON) {
	    }
    }

    public void mouseReleased(int button, int x, int y) {
	    super.mouseReleased(button, x, y);
	    if (button == Input.MOUSE_LEFT_BUTTON) {

		    fastTravelButton.click(new Point(x, y));
		    enterUnderworldButton.click(new Point(x, y));

//		    System.out.println("dragging " + dragging);
//		    System.out.println("popupon " + popupOn);
//		    System.out.println("mapScreenBounds.contains(x, y) " + mapBounds.contains(x, y));
			if(!dragging && !popupOn && mapBounds.contains(x, y)){//select a tile
//				System.out.println("clicked a tile");
				int transformedX = x - (BORDER+this.x);
				int transformedY = y - (BORDER+this.y);

//				selectedTile.setLocation(
//						(int) ( ((x-(2)) / zoom) + mapScreenBounds.getX()),
//						(int) ( ((y-(2)) / zoom) + mapScreenBounds.getY())
//				);
				selectedTile.setLocation(
						(int) ( ( (mapScreenBounds.getWidth()/MAP_SIZE) * transformedX) + mapScreenBounds.getX() ) -1,
						(int) ( ( (mapScreenBounds.getHeight()/MAP_SIZE)* transformedY) + mapScreenBounds.getY() ) -1
				);

				updateFastTravelButton();

				updateUnderworldButton();

				updateDescriptiveText();

			}
		    if(popupOn){
			    popupActionButton.click(new Point(x, y));
			    popupCancelButton.click(new Point(x, y));
		    }
		    else{
			    fastTravelButton.click(new Point(x, y));
			    enterUnderworldButton.click(new Point(x, y));
		    }
        }

	    dragging = false;
    }

	private void updateFastTravelButton(){

		//check if there is a path to the target map, if there is,
		// then enable the fast travel button

		//for now, we'll assume there is a path.
		boolean pathExists = true;

		//first, we'll just try moving the player to that location
		if(pathExists && Engine.getWorld().isExplored(selectedTile)){
			fastTravelButton.enable();
			fastTravelButtonText = "Fast Travel";
			popupActionButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick() {
					//This is where the magic happens.
					//Will need to communicate with the Engine here....
					System.out.println("Popup Action Button: " + selectedTile.getX() + ", " + selectedTile.getY());
					Engine.fastTravelToLoc(selectedTile);
					Engine.toggleMap();
				}
			});
		}
		else{
			fastTravelButton.disable();
//			fastTravelButtonText = "Cannot Fast Travel";
		}
		fastTravelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				//open a popup promting certainty
				popupOn = true;
			}
		});



	}
	private void updateUnderworldButton(){

		//check if the target has an underworld, and is cleared,
		// and there is a path to the target,
		// and the player isn't already in the underworld

		//NOif the player is already in the underworld, change the enter underworld button to an exit underworld button.NONO
		//if the player is in the underworld, they need to find their own damn way out.
	}
	private void updateDescriptiveText(){

	}
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        super.mouseDragged(oldx, oldy, newx, newy);
		dragging = true;
	    if(mapBounds.contains(oldx, oldy)){
		    mapScreenBounds.setX(mapScreenBounds.getX() + (oldx - newx)/zoom);
		    mapScreenBounds.setY(mapScreenBounds.getY() + (oldy - newy)/zoom);
	    }
    }

    public void mouseWheelMoved(int change) {
        super.mouseWheelMoved(change);
//	    System.out.println(change);
	    Point center = getCenterOfMap();


	    zoom += change/100.0f;

	    if(zoom < MIN_ZOOM){
		    zoom = MIN_ZOOM;
	    }
	    if(zoom > MAX_ZOOM){
		    zoom = MAX_ZOOM;
	    }

	    centerScreen(center.getX(), center.getY());
    }

	private void drawSingleMap(float x, float y, short biome, short feature, boolean explored, Graphics g){
		CHOOSE_COLOR:{
//			if(!explored){
//				g.setColor(Color.gray);
//				break CHOOSE_COLOR;
//			}
		////////////////////////////TERRAIN/////////////////////////////
			if(biome == WATER){
				g.setColor(WATER_COLOR);
			}
			else if(biome == BEACH){
				g.setColor(SAND_COLOR);
			}
			else if(biome == PLAINS){
				g.setColor(PLAINS_COLOR);
			}
			else if(biome == MOUNTAINS){
				g.setColor(MOUNTAINS_COLOR);
			}
			else if(biome == CLIFFS){
				g.setColor(CLIFFS_COLOR);
			}
			else if(biome == SWAMP){
				g.setColor(SWAMP_COLOR);
			}
			else if(biome == DESERT){
				g.setColor(DESERT_COLOR);
			}
			else if(biome == JUNGLE){
				g.setColor(JUNGLE_COLOR);
			}
			else if(biome == TAIGA){
				g.setColor(TAIGA_COLOR);
			}
			else if(biome == TUNDRA){
				g.setColor(TUNDRA_COLOR);
			}
			else if(biome == FOREST){
				g.setColor(FOREST_COLOR);
			}
			else {
				g.setColor(Color.black);
			}
			////////////////////////////TERRAIN/////////////////////////////

			////////////////////////////FEATURES////////////////////////////

			if(feature == CAVE){
				g.setColor(CAVE_COLOR);
			}
			else if(feature == DUNGEON){
				g.setColor(DUNGEON_COLOR);
			}
			else if(feature == PASSIVE_VILLAGE_FEATURE){
				g.setColor(PASSIVE_VILLAGE_COLOR);
			}
			////////////////////////////FEATURES////////////////////////////
		}
		g.fillRect(x, y, 1.0f * zoom, 1.0f * zoom);
		if(!explored){
			g.setColor(Constants.TRANSPARENT_BLACK_200);
			g.fillRect(x, y, 1.0f * zoom, 1.0f * zoom);
		}
//		System.out.println("drew a map: " + biome);
	}


	/**
	 * Like straight up copied out of Engine.
	 * @param x
	 * @param y
	 */
	private void centerScreen(int x, int y) {//centers the map on a point in world coords.
		if(mapScreenBounds == null)mapScreenBounds = new Rectangle(0,0,0,0);

		mapScreenBounds.setWidth(    (int)(MAP_SIZE / zoom));
		mapScreenBounds.setHeight(   (int)(MAP_SIZE / zoom));

		mapScreenBounds.setBounds(
				(int)(x - (mapScreenBounds.getWidth() / 2)),
				(int)(y - (mapScreenBounds.getHeight() / 2)),
				mapScreenBounds.getWidth(),
				mapScreenBounds.getHeight());

//	    System.out.println(screen.getX() + ", " + screen.getY() + ";\t" + screen.getWidth() + ", " + screen.getHeight());
//	    System.out.println(Demigods.getScreenWidth() + ", " + Demigods.getScreenHeight() + ": " + zoomFactor);
	}
	private Point getCenterOfMap(){
		if(mapScreenBounds == null)return new Point(0,0);
		return new Point(
				(int)(mapScreenBounds.getX() + (mapScreenBounds.getWidth() / 2)),
				(int)(mapScreenBounds.getY() + (mapScreenBounds.getWidth() / 2))
		);
	}
}
