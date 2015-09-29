package entities.characters;

import items.*;
import items.armor.IronHat;
import items.armor.PaladinChest;
import items.armor.PaladinHat;
import items.armor.PaladinLegs;
import items.weapons.GodSword;
import items.weapons.SteelAxe;
import items.weapons.SteelSpear;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 6/22/13
 * Time: 10:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class ZealotChampionCharacter extends Human {
	public ZealotChampionCharacter(int gx, int gy) {
		super(gx, gy);
		init();
	}
	public ZealotChampionCharacter(){
		super();
		init();
	}
	protected void init(){
		super.init();
		this.modifyBaseStat(HP, 5000);
		this.modifyBaseStat(MP, 30);
		while(this.getLvl() < 15){
			this.levelUp();
		}
		//this.addXP(5000);
		this.setBaseStat(ARMOR, 50);
		this.setBaseStat(MAGIC_RESIST, 50);
		this.modifyBaseStat(MPREGEN, .05);
		this.setBaseStat(ATTACK, 10);
		switch ((int)(Math.round(Math.random() * 1))){
			case 1:
				equippedItem = new SteelSpear();
				break;
			case 2:
				equippedItem = new SteelAxe();//TODO: Replace with better weapon choices
				break;
			default:
				equippedItem = new GodSword();
				break;

		}
		equippedItem.equip(this);

		PaladinHat hat = new PaladinHat();hat.equip(this);
		PaladinChest chest= new PaladinChest();chest.equip(this);
		PaladinLegs legs = new PaladinLegs();legs.equip(this);
	}
}
