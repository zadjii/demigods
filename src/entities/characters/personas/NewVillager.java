package entities.characters.personas;

import entities.characters.Character;
import conditions.etc.Cooldown;
import game.Engine;
import gui.guiPanels.MessagePanel;
import items.armor.*;
import items.tools.BronzePick;
import items.weapons.BronzeAxe;
import items.weapons.Stick;
import util.Constants;
import gui.trading.Trading;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 2/3/13
 * Time: 5:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewVillager extends SimpleMob {

    private boolean moving = true;
    private Cooldown stoppedCD = new Cooldown(3 * Constants.TICKS_PER_SECOND);

    enum VillagerType
    {
        PLAIN, WOODCUTTER, BUILDER, SMITHY, MINER, ALCHEMIST, FARMER
    }

    private int numValues = 6;

    private VillagerType type;

    public void moveAI() {
        if (moving) super.moveAI();
        else {
            this.character.setDXDYDZ(0, 0, 0);
            stoppedCD.tick();
            if (stoppedCD.offCooldown()) moving = true;
        }
    }

    public NewVillager(Character c) {
        super(c);
        init();
    }

    public NewVillager() {
        super();
        init();
    }

    private void init() {
        switch ((int)(Math.random() * (numValues * 1.5))) {
            case 1:
                this.type = VillagerType.WOODCUTTER;
                character.setEquippedItem(new BronzeAxe());
                character.getEquippedItem().equip(character);
                break;
            case 2:
                this.type = VillagerType.BUILDER;
                character.setEquippedItem(new Stick());
                character.getEquippedItem().equip(character);
                break;
            case 3:
                this.type = VillagerType.SMITHY;
                character.setEquippedItem(new IronHat());
                character.getEquippedItem().equip(character);
                break;
            case 4:
                this.type = VillagerType.MINER;
                character.setEquippedItem(new BronzePick());
                character.getEquippedItem().equip(character);
                break;
            case 5:
                this.type = VillagerType.ALCHEMIST;
                BlueWizardHat wizardHat = new BlueWizardHat();
                wizardHat.equip(this.character);
                break;
            case 6:
                this.type = VillagerType.FARMER;
                FarmerHat farmerHat = new FarmerHat();
                farmerHat.equip(this.character);
                break;
            default:
                this.type = VillagerType.PLAIN;
                break;
        }
    }

    public void talk() {
        MessagePanel gui = new MessagePanel("Hello, world!");
        switch (type) {
            case PLAIN:
                gui.addMessage("I am boring");
                break;
            case WOODCUTTER:
                gui.addMessage("I am a woodcutter");
                break;
            case BUILDER:
                gui.addMessage("I am a builder");
                break;
            case MINER:
                gui.addMessage("I am a miner");
                break;
            case SMITHY:
                gui.addMessage("I am a smithy");
                break;
            case ALCHEMIST:
                gui.addMessage("I am an alchemist");
                break;
            case FARMER:
                gui.addMessage("I am a farmer");
                break;
        }
        switch (type) {
            case WOODCUTTER:
                gui.setMerchant(Trading.WOODCUTTER);
                break;
            case BUILDER:
                gui.setMerchant(Trading.BUILDER);
                break;
            case MINER:
                gui.setMerchant(Trading.MINER);
                break;
            case SMITHY:
                gui.setMerchant(Trading.SMITHY);
                break;
        }
        gui.setSpeaker(this);
        Engine.talk(gui);
    }

    public void stopMoving() {
        this.moving = false;
        stoppedCD.reset();
        this.character.setDirection(Character.Direction.DOWN);
    }
}
