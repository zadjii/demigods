package gui.titleScreen;

import conditions.*;
import entities.characters.*;
import entities.characters.personas.Player;
import game.Demigods;
import areas.NewGameArea;
import items.weapons.*;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.*;
import skills.*;
import util.*;
import util.Font;
import util.exceptions.DemigodsException;

import java.util.ArrayList;

public class NewPlayerScreen extends BasicGameState {

	/*
	*   These constants are for the starter classes.
	*/
	private static final int NONE = -1;
	private static final int KNIGHT = 0;
	private static final int WARRIOR = 1;
	private static final int MARKSMAN = 2;
	private static final int THIEF = 3;
	private static final int MAGE = 4;
	private static final int WIZARD = 5;
	private static final int CHARACTER_CUSTOMIZATION_STATE = 0;
	private static final int CHARACTER_CLASS_SELECT_STATE = 1;

	private int backgroundX, backgroundY;
    private Input input;
    private boolean[] hovering;

	//TODO: Remove giving the player a default, random class.
    private int selectedStarterClass = (int)(Math.random() * WIZARD);
//    private int selectedStarterClass = NONE;

    private int skinR = 214, skinG = 200, skinB = 127, torsoR = 0, torsoG = 0, torsoB = 0, pantsR = 60, pantsG = 60, pantsB = 60;
    private boolean leftClicked = false;
    private int timeCount, nameFieldCursorCount;
    private boolean nameFieldSelected = true, nameFieldCursorOn;
    private String name = "";
    private int step = CHARACTER_CUSTOMIZATION_STATE;
    private int nameErrorCount;
    Player player;

    public NewPlayerScreen() {
    }

    public void init(GameContainer gc, StateBasedGame game) {
        input = new Input(600);
        input.addListener(this);
	    player = new Player();
        initTorsoColor();
	    resetSelectionBooleans();
	    this.step = CHARACTER_CUSTOMIZATION_STATE;
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        Images.terrain.draw(backgroundX, backgroundY);
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, 1056, 600);

        Font.drawDkNorthumbriaString("NEW PLAYER", 360, 75, 50, null, util.Font.FontType.RENDERED);
        Font.drawDkNorthumbriaString("BACK", 20, 545, 40, hovering[0] ? Color.white : null, hovering[0] ? null : util.Font.FontType.RENDERED);
        Font.drawDkNorthumbriaString("CONTINUE", 795, 545, 40, hovering[1] ? Color.white : null, hovering[1] ? null : util.Font.FontType.RENDERED);

        Images.baldmanHandBW.draw(250, 316, 62.5f, 62.5f, new Color(skinR, skinG, skinB));
        Images.humanHeadBWSheet.getSprite(0, 1).draw(0, 100, 400, 400, new Color(skinR, skinG, skinB));
        Images.humanChestBWSheet.getSprite(0, 1).draw(0, 100, 400, 400, new Color(torsoR, torsoG, torsoB));
        Images.humanLegsBWSheet.getSprite(0, 1).draw(0, 100, 400, 400, new Color(pantsR, pantsG, pantsB));
        Images.baldmanHandBW.draw(100, 316, 62.5f, 62.5f, new Color(skinR, skinG, skinB));

        if (step == CHARACTER_CUSTOMIZATION_STATE) {
            Font.drawTriniganFgString("Name:", 350, 175, 30, (nameErrorCount > 0) ? Color.red : Color.black);
            Font.drawTriniganFgString("Skin color:", 350, 225, 30, Color.black);
            Font.drawTriniganFgString("Shirt color:", 350, 275, 30, Color.black);
            Font.drawTriniganFgString("Pants color:", 350, 325, 30, Color.black);

            Font.drawMonospacedFontString(name, 470, 185, 30, Color.black);
            if (nameFieldSelected && nameFieldCursorOn) {
                g.setColor(Color.black);
                g.setLineWidth(3);
                g.drawLine(470 + name.length() * 18.5f, 210, 490 + name.length() * 18.5f, 210);
            }

            Polygon leftArrowSR = new Polygon(new float[]{470, 247, 485, 232, 485, 262});
            g.setColor(hovering[2] ? Color.white : Color.red);
            g.fill(leftArrowSR);
            Polygon leftArrowSG = new Polygon(new float[]{580, 247, 595, 232, 595, 262});
            g.setColor(hovering[4] ? Color.white : Color.green);
            g.fill(leftArrowSG);
            Polygon leftArrowSB = new Polygon(new float[]{690, 247, 705, 232, 705, 262});
            g.setColor(hovering[6] ? Color.white : Color.blue);
            g.fill(leftArrowSB);
            Polygon leftArrowTR = new Polygon(new float[]{470, 297, 485, 282, 485, 312});
            g.setColor(hovering[8] ? Color.white : Color.red);
            g.fill(leftArrowTR);
            Polygon leftArrowTG = new Polygon(new float[]{580, 297, 595, 282, 595, 312});
            g.setColor(hovering[10] ? Color.white : Color.green);
            g.fill(leftArrowTG);
            Polygon leftArrowTB = new Polygon(new float[]{690, 297, 705, 282, 705, 312});
            g.setColor(hovering[12] ? Color.white : Color.blue);
            g.fill(leftArrowTB);
            Polygon leftArrowPR = new Polygon(new float[]{470, 347, 485, 332, 485, 362});
            g.setColor(hovering[14] ? Color.white : Color.red);
            g.fill(leftArrowPR);
            Polygon leftArrowPG = new Polygon(new float[]{580, 347, 595, 332, 595, 362});
            g.setColor(hovering[16] ? Color.white : Color.green);
            g.fill(leftArrowPG);
            Polygon leftArrowPB = new Polygon(new float[]{690, 347, 705, 332, 705, 362});
            g.setColor(hovering[18] ? Color.white : Color.blue);
            g.fill(leftArrowPB);

            Polygon rightArrowSR = new Polygon(new float[]{560, 232, 560, 262, 575, 247});
            g.setColor(hovering[3] ? Color.white : Color.red);
            g.fill(rightArrowSR);
            Polygon rightArrowSG = new Polygon(new float[]{670, 232, 670, 262, 685, 247});
            g.setColor(hovering[5] ? Color.white : Color.green);
            g.fill(rightArrowSG);
            Polygon rightArrowSB = new Polygon(new float[]{780, 232, 780, 262, 795, 247});
            g.setColor(hovering[7] ? Color.white : Color.blue);
            g.fill(rightArrowSB);
            Polygon rightArrowTR = new Polygon(new float[]{560, 282, 560, 312, 575, 297});
            g.setColor(hovering[9] ? Color.white : Color.red);
            g.fill(rightArrowTR);
            Polygon rightArrowTG = new Polygon(new float[]{670, 282, 670, 312, 685, 297});
            g.setColor(hovering[11] ? Color.white : Color.green);
            g.fill(rightArrowTG);
            Polygon rightArrowTB = new Polygon(new float[]{780, 282, 780, 312, 795, 297});
            g.setColor(hovering[13] ? Color.white : Color.blue);
            g.fill(rightArrowTB);
            Polygon rightArrowPR = new Polygon(new float[]{560, 332, 560, 362, 575, 347});
            g.setColor(hovering[15] ? Color.white : Color.red);
            g.fill(rightArrowPR);
            Polygon rightArrowPG = new Polygon(new float[]{670, 332, 670, 362, 685, 347});
            g.setColor(hovering[17] ? Color.white : Color.green);
            g.fill(rightArrowPG);
            Polygon rightArrowPB = new Polygon(new float[]{780, 332, 780, 362, 795, 347});
            g.setColor(hovering[19] ? Color.white : Color.blue);
            g.fill(rightArrowPB);

            Font.drawMonospacedFontString(Integer.toString(skinR), 495, 233, 30, Color.red);
            Font.drawMonospacedFontString(Integer.toString(skinG), 605, 233, 30, Color.green);
            Font.drawMonospacedFontString(Integer.toString(skinB), 715, 233, 30, Color.blue);
            Font.drawMonospacedFontString(Integer.toString(torsoR), 495, 283, 30, Color.red);
            Font.drawMonospacedFontString(Integer.toString(torsoG), 605, 283, 30, Color.green);
            Font.drawMonospacedFontString(Integer.toString(torsoB), 715, 283, 30, Color.blue);
            Font.drawMonospacedFontString(Integer.toString(pantsR), 495, 333, 30, Color.red);
            Font.drawMonospacedFontString(Integer.toString(pantsG), 605, 333, 30, Color.green);
            Font.drawMonospacedFontString(Integer.toString(pantsB), 715, 333, 30, Color.blue);
        } else if (step == CHARACTER_CLASS_SELECT_STATE) {
            g.setColor(new Color(255, 200, 0));
            g.fillOval(400, 200, 50, 50);
            g.setColor(new Color(255, 100, 100));
            g.fillOval(460, 200, 50, 50);
            g.setColor(new Color(100, 255, 100));
            g.fillOval(520, 200, 50, 50);
            g.setColor(new Color(200, 200, 200));
            g.fillOval(580, 200, 50, 50);
            g.setColor(new Color(100, 100, 255));
            g.fillOval(640, 200, 50, 50);
            g.setColor(new Color(200, 100, 255));
            g.fillOval(700, 200, 50, 50);

            g.setLineWidth(4);
            g.setColor(selectedStarterClass == 0 ? Color.white : (hovering[20] ? Color.lightGray : Color.black));
            g.drawOval(400, 200, 50, 50);
            g.setColor(selectedStarterClass == 1 ? Color.white : (hovering[21] ? Color.lightGray : Color.black));
            g.drawOval(460, 200, 50, 50);
            g.setColor(selectedStarterClass == 2 ? Color.white : (hovering[22] ? Color.lightGray : Color.black));
            g.drawOval(520, 200, 50, 50);
            g.setColor(selectedStarterClass == 3 ? Color.white : (hovering[23] ? Color.lightGray : Color.black));
            g.drawOval(580, 200, 50, 50);
            g.setColor(selectedStarterClass == 4 ? Color.white : (hovering[24] ? Color.lightGray : Color.black));
            g.drawOval(640, 200, 50, 50);
            g.setColor(selectedStarterClass == 5 ? Color.white : (hovering[25] ? Color.lightGray : Color.black));
            g.drawOval(700, 200, 50, 50);

            g.setColor(Color.gray);
            g.fillRect(400, 275, 400, 200);
            g.setColor(Color.black);
            g.drawRect(400, 275, 400, 200);

            if (selectedStarterClass != -1) {
                ArrayList<String> description = getSelectedClassDescription();
                for (int line = 0; line < description.size(); ++line) {
                    Font.drawTriniganFgString(description.get(line), 410, 285 + 24 * line, 20, Color.black);
                }
            }
        }
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) {
        resetSelectionBooleans();

        int x = input.getMouseX(), y = input.getMouseY();
        backgroundX = -419 * x / 528 - 1365;
        backgroundY = -533 * y / 300 - 1365;

        if (x > 20 && x < 140 && y > 545 && y < 580) hovering[0] = true;
        else if (x > 787 && x < 1030 && y > 547 && y < 583) hovering[1] = true;

        if (step == CHARACTER_CUSTOMIZATION_STATE) {
            if (x > 470 && x < 485 && y > 232 && y < 262) hovering[2] = true;
            else if (x > 560 && x < 575 && y > 232 && y < 262) hovering[3] = true;
            else if (x > 580 && x < 595 && y > 232 && y < 262) hovering[4] = true;
            else if (x > 670 && x < 685 && y > 232 && y < 262) hovering[5] = true;
            else if (x > 690 && x < 705 && y > 232 && y < 262) hovering[6] = true;
            else if (x > 780 && x < 795 && y > 232 && y < 262) hovering[7] = true;
            else if (x > 470 && x < 485 && y > 282 && y < 312) hovering[8] = true;
            else if (x > 560 && x < 575 && y > 282 && y < 312) hovering[9] = true;
            else if (x > 580 && x < 595 && y > 282 && y < 312) hovering[10] = true;
            else if (x > 670 && x < 685 && y > 282 && y < 312) hovering[11] = true;
            else if (x > 690 && x < 705 && y > 282 && y < 312) hovering[12] = true;
            else if (x > 780 && x < 795 && y > 282 && y < 312) hovering[13] = true;
            else if (x > 470 && x < 485 && y > 332 && y < 362) hovering[14] = true;
            else if (x > 560 && x < 575 && y > 332 && y < 362) hovering[15] = true;
            else if (x > 580 && x < 595 && y > 332 && y < 362) hovering[16] = true;
            else if (x > 670 && x < 685 && y > 332 && y < 362) hovering[17] = true;
            else if (x > 690 && x < 705 && y > 332 && y < 362) hovering[18] = true;
            else if (x > 780 && x < 795 && y > 332 && y < 362) hovering[19] = true;

            if (leftClicked) {
                if (timeCount == 0 || timeCount > 20) {
                    if (x > 470 && x < 485 && y > 232 && y < 262 && skinR > 0) skinR--;
                    else if (x > 560 && x < 575 && y > 232 && y < 262 && skinR < 255) skinR++;
                    else if (x > 580 && x < 595 && y > 232 && y < 262 && skinG > 0) skinG--;
                    else if (x > 670 && x < 685 && y > 232 && y < 262 && skinG < 255) skinG++;
                    else if (x > 690 && x < 705 && y > 232 && y < 262 && skinB > 0) skinB--;
                    else if (x > 780 && x < 795 && y > 232 && y < 262 && skinB < 255) skinB++;
                    else if (x > 470 && x < 485 && y > 282 && y < 312 && torsoR > 0) torsoR--;
                    else if (x > 560 && x < 575 && y > 282 && y < 312 && torsoR < 255) torsoR++;
                    else if (x > 580 && x < 595 && y > 282 && y < 312 && torsoG > 0) torsoG--;
                    else if (x > 670 && x < 685 && y > 282 && y < 312 && torsoG < 255) torsoG++;
                    else if (x > 690 && x < 705 && y > 282 && y < 312 && torsoB > 0) torsoB--;
                    else if (x > 780 && x < 795 && y > 282 && y < 312 && torsoB < 255) torsoB++;
                    else if (x > 470 && x < 485 && y > 332 && y < 362 && pantsR > 0) pantsR--;
                    else if (x > 560 && x < 575 && y > 332 && y < 362 && pantsR < 255) pantsR++;
                    else if (x > 580 && x < 595 && y > 332 && y < 362 && pantsG > 0) pantsG--;
                    else if (x > 670 && x < 685 && y > 332 && y < 362 && pantsG < 255) pantsG++;
                    else if (x > 690 && x < 705 && y > 332 && y < 362 && pantsB > 0) pantsB--;
                    else if (x > 780 && x < 795 && y > 332 && y < 362 && pantsB < 255) pantsB++;
                }
                timeCount++;
            }

            nameFieldCursorCount++;
            nameFieldCursorOn = (nameFieldCursorCount < 20);
            if (nameFieldCursorCount == 40) nameFieldCursorCount = 0;

            if (nameErrorCount > 0) nameErrorCount--;
        } else if (step == CHARACTER_CLASS_SELECT_STATE) {
            if (x > 400 && x < 450 && y > 200 && y < 250) hovering[20] = true;
            else if (x > 460 && x < 510 && y > 200 && y < 250) hovering[21] = true;
            else if (x > 520 && x < 570 && y > 200 && y < 250) hovering[22] = true;
            else if (x > 580 && x < 630 && y > 200 && y < 250) hovering[23] = true;
            else if (x > 640 && x < 690 && y > 200 && y < 250) hovering[24] = true;
            else if (x > 700 && x < 750 && y > 200 && y < 250) hovering[25] = true;
        }
    }

    public int getID() {
        return Demigods.NEW_PLAYER_SCREEN;
    }

    public void mousePressed(int button, int x, int y) {
        if (button == 0) {
            leftClicked = true;

        }
    }

    public void mouseReleased(int button, int x, int y) {
        if (button == 0) {
	        if (x > 20 && x < 140 && y > 545 && y < 580) {
		        if (step == CHARACTER_CUSTOMIZATION_STATE) {
			        Demigods.enterMenuState(Demigods.PLAYER_SELECT_SCREEN);
		        } else {
			        step = CHARACTER_CUSTOMIZATION_STATE;
		        }
	        } else if (x > 787 && x < 1030 && y > 547 && y < 583) {
		        if (step == CHARACTER_CUSTOMIZATION_STATE) {
			        if (name.length() > 0) step++;
			        else {
				        nameFieldSelected = true;
				        nameErrorCount = 20;
			        }
		        } else if(selectedStarterClass != NONE){
			        Demigods.enterMenuState(Demigods.NEW_WORLD_SCREEN);
			        initializePlayer();
		        }
	        }
	        if (step == CHARACTER_CUSTOMIZATION_STATE) {
//		        nameFieldSelected = (x > 470 && y > 195 && y < 215 || nameErrorCount == 20);
		        nameFieldSelected = true;
	        } else {
		        if (x > 400 && x < 450 && y > 200 && y < 250) selectedStarterClass = 0;
		        else if (x > 460 && x < 510 && y > 200 && y < 250) selectedStarterClass = 1;
		        else if (x > 520 && x < 570 && y > 200 && y < 250) selectedStarterClass = 2;
		        else if (x > 580 && x < 630 && y > 200 && y < 250) selectedStarterClass = 3;
		        else if (x > 640 && x < 690 && y > 200 && y < 250) selectedStarterClass = 4;
		        else if (x > 700 && x < 750 && y > 200 && y < 250) selectedStarterClass = 5;
	        }
            leftClicked = false;
            timeCount = 0;
        }
    }

    public void keyPressed(int key, char c) {
        if (nameFieldSelected) {
            if (Constants.isAcceptedCharacter(c) && name.length() <= 24) {
                name += c;
            } else if (key == Input.KEY_BACK && name.length() > 0) {
                name = name.substring(0, name.length() - 1);
            }
        }
        if (key == Input.KEY_ENTER) {
            if (step == CHARACTER_CUSTOMIZATION_STATE) {
                if (name.length() > 0) step = CHARACTER_CLASS_SELECT_STATE;
                else {
                    nameFieldSelected = true;
                    nameErrorCount = 20;
                }
            } else if (selectedStarterClass != NONE){
	            initializePlayer();
	            Demigods.enterMenuState(Demigods.WORLD_SELECT_SCREEN);
            }
        }
    }

    private void resetSelectionBooleans() {
        hovering = new boolean[26];
    }

    private void initTorsoColor() {
        int color = (int) (Math.random() * 6);
        switch (color) {
            case 0:
                torsoR = 255;
                torsoG = 0;
                torsoB = 0;
                break;
            case 1:
                torsoR = 255;
                torsoG = 127;
                torsoB = 0;
                break;
            case 2:
                torsoR = 255;
                torsoG = 255;
                torsoB = 0;
                break;
            case 3:
                torsoR = 0;
                torsoG = 255;
                torsoB = 0;
                break;
            case 4:
                torsoR = 0;
                torsoG = 0;
                torsoB = 255;
                break;
            case 5:
                torsoR = 175;
                torsoG = 0;
                torsoB = 255;
        }
    }

    private ArrayList<String> getSelectedClassDescription() {
        ArrayList<String> description = new ArrayList<String>();
        switch (selectedStarterClass) {
            case -1:
                return null;
            case KNIGHT:
                description.add("Knight:");
                description.add("");
                description.add("Items: Sword & Shield");
                description.add("Skills: Triple Cut, Rallying Strike");
                break;
            case WARRIOR:
                description.add("Warrior:");
                description.add("");
                description.add("Items: Greatsword");
                description.add("Skills: Great Slash, Spin Attack");
                break;
            case MARKSMAN:
                description.add("Marksman:");
                description.add("");
                description.add("Items: Wooden Bow & 200 Arrows");
                description.add("Skills: Quick Draw");
                break;
            case THIEF:
                description.add("Thief:");
                description.add("");
                description.add("Items: 2 Daggers & 50 Throwing Knives");
                description.add("Skills: Shadow Step");
                break;
            case MAGE:
                description.add("Mage:");
                description.add("");
                description.add("Items: Energy Staff");
                description.add("Skills: Mage Blast, Energy Rune");
                break;
            case WIZARD:
                description.add("Wizard:");
                description.add("");
                description.add("Items: Shock Scepter & Earth Scepter");
                description.add("Skills: Icicle, Burn");
                break;
        }
        return description;
    }

    private void initializePlayer() {
	    player = new Player();
        player.setName(name);

	    Human playerHuman = new Human(NewGameArea.DEFAULT_SIZE/2, NewGameArea.DEFAULT_SIZE/2);
	    playerHuman.setSkinColor(new Color(skinR, skinG, skinB));
	    playerHuman.setTorsoColor(new Color(torsoR, torsoG, torsoB));
	    playerHuman.setPantsColor(new Color(pantsR, pantsG, pantsB));

        player.setCharacter(playerHuman);
        player.getCharacter().setXY((NewGameArea.DEFAULT_SIZE/2)*16,(NewGameArea.DEFAULT_SIZE/2)*16);
//        player.setLastBase(-1);


//		player.getCharacter().setSkill(0, new Smite());
//		player.getCharacter().setSkill(1, new QuickStrike());
//		player.getCharacter().setSkill(2, new ShiftBlast());
//		player.getCharacter().setSkill(3, new Icicle());
//		player.getCharacter().setSkill(4, new DarkTendril());
//		player.getCharacter().getSkills().add(new Smash());
//		player.getCharacter().getSkills().add(new Flamethrower());
//        player.getCharacter().getSkills().add(new Jump());
//        player.getCharacter().getSkills().add(new Lunge());
//		player.getCharacter().getSkills().add(new ThunderStorm());

//        while(player.getCharacter().getLvl()<30){
//            player.getCharacter().addXP(50);
//        }

	    switch (selectedStarterClass) {
		    case NONE:
			    //this shouldn't be allowed even.
			    break;
		    case KNIGHT:
			    player.getCharacter().setSkill(0, new RallyingStrike());
			    player.getCharacter().setSkill(1, new TripleSlash());
			    SharpenedStick sharpStick = new SharpenedStick();
			    WoodenShield woodenShield = new WoodenShield();
			    try{
				    player.getCharacter().getInventory().addItem(sharpStick);
				    player.getCharacter().getInventory().addItem(woodenShield);
				    sharpStick.equip(player.getCharacter());
				    player.equipItem(sharpStick);
				    player.getCharacter().equipOffhand(woodenShield);
			    }catch (DemigodsException e){}
			    break;
		    case WARRIOR:
				player.getCharacter().addCondition(
						new Adrenaline(player.getCharacter(), player.getCharacter(), 0)
				);

			    LargeSharpStick largeSharpStick= new LargeSharpStick();
			    try{
				    player.getCharacter().getInventory().addItem(largeSharpStick);

				    largeSharpStick.equip(player.getCharacter());
				    player.equipItem(largeSharpStick);

			    }catch (DemigodsException e){}

			    break;
		    case MARKSMAN:

			    WoodenBow woodenBow= new WoodenBow();
			    try{
				    player.getCharacter().getInventory().addItem(woodenBow);
				    for (int i = 0; i < 200; i++) {
					    player.getCharacter().getInventory().addItem(new Arrow());
				    }
				    woodenBow.equip(player.getCharacter());
				    player.equipItem(woodenBow);

			    }catch (DemigodsException e){}

			    break;
		    case THIEF:
			    player.getCharacter().setSkill(0, new ThiefsGamble());
//			    player.getCharacter().setSkill(1, new ShadowStep());
			    SmallPointyStick dagger1 = new SmallPointyStick();
			    SmallPointyStick dagger2 = new SmallPointyStick();
			    try{
				    player.getCharacter().getInventory().addItem(dagger1);
				    dagger1.equip(player.getCharacter());
				    player.equipItem(dagger1);
				    player.getCharacter().equipOffhand(dagger2);
				    for (int i = 0; i < 200; i++) {
					    player.getCharacter().getInventory().addItem(new ThrowingKnife());
				    }
			    }catch (DemigodsException e){}
			    break;
		    case MAGE:
			    EnergyStaff energyStaff = new EnergyStaff();
			    player.getCharacter().setSkill(0, new EnergyRune());
//			    Enchantment.addEnchantment(energyStaff, "Apprentice");
			    try{
				    player.getCharacter().getInventory().addItem(energyStaff);
				    energyStaff.equip(player.getCharacter());
				    player.equipItem(energyStaff);
			    }catch (DemigodsException e){}
			    break;
		    case WIZARD:

			    break;
	    }
//		player.getCharacter().setBaseStat(Character.HPREGEN, 5.0f);
        player.setTeam(Constants.PLAYERTEAM);
//	    player.getCharacter().addCondition(
//			    new SandAttackCondition(player.getCharacter(), player.getCharacter(), 0)
//	    );
	    player.getCharacter().setSkill(4, new SandAttack());
	    player.getCharacter().setSkill(3, new SandPillar());
//	    playerHuman.setBaseSpeed(10.0f);

//	    try {
//		    player.getCharacter().getInventory().addItem(new SteelDagger());
//		    player.getCharacter().getInventory().addItem(new TestShield());
//	    } catch (DemigodsException e) {}

	    Demigods.setPlayer(player);
	    SaveManager.saveNewPlayerDir(player);

    }
}