package gui;

import areas.NewGameArea;
import entities.characters.personas.Player;
import game.Demigods;
import game.Engine;
import game.KeyHandler;
import gui.guiPanels.AreaClearedPanel;
import items.Item;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import skills.Skill;
import util.*;
import entities.characters.Character;
import util.Font;

public class HUD {

    private Rectangle[] bounds;
    private Player player;
    private boolean mousedOver;
    private int sixtyCountTicker = 0;
    private boolean notificationOn = false;

    private static final Color darkRed = new Color(100, 0, 0);
    private static final Color darkBlu = new Color(0, 0, 100);

	private static final Color darkGray50 = new Color(75, 75, 75, 60);
	private static final Color lightGray50 = new Color(175, 175, 175, 60);
	private static final Color black50 = new Color(0, 0, 0, 60);
	private static final Color white50 = new Color(255, 255, 255, 60);

    public HUD() {
        bounds = new Rectangle[3];
        bounds[0] = new Rectangle(20, 20, 500, 100);
        bounds[1] = new Rectangle(990, 20, 40, 200);
        bounds[2] = new Rectangle(800, 10, 32, 32);
        player = Demigods.getPlayer();
    }

    public Rectangle[] getBounds() {
        return bounds;
    }

    public void tick() {
        if (sixtyCountTicker < 60) sixtyCountTicker++;
        else sixtyCountTicker = 0;
    }

    public void draw(Graphics g) {
	    player = Demigods.getPlayer();

        Character c = Demigods.getPlayer().getCharacter();

        g.setColor(Color.gray);
        g.fillRoundRect(20, 20, 500, 50, 6);
//	    Images.panelBG.draw(20,20,500,50);
        g.setColor(Color.black);
        g.drawRoundRect(20, 20, 500, 50, 6);
        g.setColor(Color.gray);
        g.fillRect(20, 64, 320, 56);
//	    Images.panelBG.draw(20, 64, 320, 56);

        g.setColor(Color.black);
        g.drawLine(20, 64, 20, 120);
        g.drawLine(20, 120, 340, 120);
        g.drawLine(340, 120, 340, 70);

        g.setColor(darkRed);
        g.fillRoundRect(23, 88, (int)(((double)c.getHP() / (double)c.getMaxHp()) * 315), 4, 2);
        g.setColor(Color.red);
        g.fillRoundRect(23, 73, (int)(((double)c.getHP() / (double)c.getMaxHp()) * 315), 18, 4);

        g.setColor(darkBlu);
        g.fillRoundRect(23, 108, (int)(((double)c.getMP() / (double)c.getMaxMana()) * 315), 4, 2);
        g.setColor(Color.blue);
        g.fillRoundRect(23, 93, (int)(((double)c.getMP() / (double)c.getMaxMana()) * 315), 18, 4);
        g.setColor(Constants.XP_COLOR);
        g.fillRect(23, 113, Constants.getXpLengthFormattedForHudBar(c.getLvl(), c.getExp()), 5);

        Font.drawMonospacedFontString("Lvl " + Integer.toString(c.getLvl()), 345, 72, 24, Color.black);

        g.setColor(Color.black);
        for (int i = 0; i < player.getEquippedItems().length; i++) {
	        if(player.getEquippedItems()[i] != null)
	        Images.itemSheet.getSprite(player.getEquippedItems()[i].getImageID()[0], player.getEquippedItems()[i].getImageID()[1]).draw(25 + i * 50, 25, 40, 40);
        }

        g.drawRect(25 + player.getSelectedItemNum() * 50, 25, 40, 40);

        for (int i = 0; i < player.getEquippedSkills().length; i++) {
            try {
                g.setColor(Color.darkGray);
                g.fillOval(270 + i * 50, 20, 50, 50);
                Images.skillSheet.getSprite(
                        player.getEquippedSkills()[i].getGridCoord().getX(), player.getEquippedSkills()[i].getGridCoord().getY()
                ).draw(
                        270 + i * 50, 20, 50, 50
                );

                Font.drawTriniganFgString(Integer.toString(player.getEquippedSkills()[i].getLevel()), 270 + 30 + i * 50, 40, 24, Color.white);
                if (!player.getEquippedSkills()[i].offCooldown()) {

                    g.setColor(new Color(0, 0, 0, 100));
                    g.fillOval(270 + i * 50, 20, 50, 50);
                }
                if (player.getCharacter().getSkillReadied()) {
                    if (player.getEquippedSkills()[i] == player.getCharacter().getReadiedSkill()) {
                        g.setColor(Color.yellow);
                        g.setLineWidth(2);
                        g.drawOval(270 + i * 50, 20, 50, 50);
                    }
                }
            } catch (NullPointerException ignored) {
            }
        }

//        g.setColor(mousedOver ? Color.darkGray : darkGray50);
//        g.setLineWidth(4);
//        g.drawLine(1010, 30, 1010, 210);
//        g.setColor(mousedOver ? Color.lightGray : lightGray50);
//        g.fillOval(1000, 20, 20, 20);
//        g.fillOval(1000, 200, 20, 20);
//        g.setColor(mousedOver ? Color.black : black50);
//        g.setLineWidth(1);
//        g.drawOval(1000, 20, 20, 20);
//        g.drawOval(1000, 200, 20, 20);
//        g.setLineWidth(2);
//        g.drawLine(1004, 30, 1016, 30);
//        g.drawLine(1010, 24, 1010, 36);
//        g.drawLine(1004, 210, 1016, 210);
//        g.setColor(mousedOver ? Color.white : white50);
//        g.setLineWidth(3);
//        g.drawLine(1006, 45, 1013, 45);
//        g.drawLine(1006, 95, 1013, 95);
//        g.drawLine(1006, 145, 1013, 145);
//        g.drawLine(1006, 195, 1013, 195);
//        Color fontColor = mousedOver ? Color.white : white50;
//        Font.drawMonospacedFontString("x4", 1018, 35, 16, fontColor);
//        Font.drawMonospacedFontString("x2", 1018, 85, 16, fontColor);
//        Font.drawMonospacedFontString("x1", 1018, 135, 16, fontColor);
//        Font.drawMonospacedFontString("x0.5", 1018, 185, 16, fontColor);
//        g.setColor(mousedOver ? Color.black : black50);
//        g.fillOval(1006, (int)(191 - getLogScaleFactor() * 50), 8, 8);

        NewGameArea area = Engine.getActiveArea();
        if (!area.isBase() && area.isCleared()) {
            notificationOn = true;
            if (sixtyCountTicker < 10)
                Images.notification02.draw(800, 10);
            else Images.notification.draw(800, 10);
        } else notificationOn = false;
    }

    public void mousePressed(int button, int x, int y) {
        if (button == 0) {
            if (x > 20 && x < 270 && y > 20 && y < 70) {
                if (KeyHandler.shiftKeyPressed) {
                    if (x < 70) player.unequipItem(0);
                    else if (x < 120) player.unequipItem(1);
                    else if (x < 170) player.unequipItem(2);
                    else if (x < 220) player.unequipItem(3);
                    else player.unequipItem(4);
                } else {
                    if (x < 70) player.setSelectedItemNum(0);
                    else if (x < 120) player.setSelectedItemNum(1);
                    else if (x < 170) player.setSelectedItemNum(2);
                    else if (x < 220) player.setSelectedItemNum(3);
                    else player.setSelectedItemNum(4);
                }
            }

            if (new Rectangle(1000, 20, 20, 20).contains(x, y)) {
                if (Engine.getZoom() < 4.0)
                    Engine.setZoom(Engine.getZoom() + 0.1f);
                if (Engine.getZoom() > 4.0)
                    Engine.setZoom(4.0f);
            } else if (new Rectangle(1000, 200, 20, 20).contains(x, y)) {
                if (Engine.getZoom() > 0.5)
                    Engine.setZoom(Engine.getZoom() - 0.1f);
                if (Engine.getZoom() < 0.5)
                    Engine.setZoom(0.5f);
            } else if (new Rectangle(1000, 48, 20, 150).contains(x, y)) {
                setLogScaleFactor((double)(197 - y) / 50D);
            }

            if (bounds[2].contains(x, y) && notificationOn) {
                GUIManager.setActivePanel(new AreaClearedPanel());
            }
        }
    }

    public boolean mouseMoved(int oldx, int oldy, int newx, int newy) {
        if (bounds[1].contains(newx, newy)) {
            mousedOver = true;
            return false;
        }
        mousedOver = false;
        return true;
    }

    public boolean mouseDragged(int oldx, int oldy, int newx, int newy) {
        if (new Rectangle(995, 48, 30, 150).contains(newx, newy)) {
            setLogScaleFactor((double) (197 - newy) / 50D);
            return false;
        }
        return true;
    }

    private void removeSkillIfExists(Skill skill) {
        for (int i = 0; i < 5; ++i) {
            try {
                if (player.getEquippedSkills()[i].toString().equals(skill.toString())) {
                    player.getEquippedSkills()[i] = null;
                }
            } catch (NullPointerException ignored) {
            }
        }
    }

    private double getLogScaleFactor() {
        return (Math.log(Engine.getZoom()) / Math.log(2)) + 1;
    }

    private void setLogScaleFactor(double x) {
        Engine.setZoom((float)Math.exp((x - 1) * Math.log(2)));
    }
	public static Rectangle getSkillBarBounds(){
		return new Rectangle(270, 20, Engine.getPlayer().getEquippedSkills().length * 50, 50);
	}
}
