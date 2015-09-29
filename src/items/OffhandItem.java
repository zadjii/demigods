package items;

import entities.characters.Character;
import util.Images;
import util.RotatedBigPicture;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 8/7/13
 * Time: 7:00 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class OffhandItem extends Item{
	public abstract void offhandEquip(Character user);
	public abstract void offhandUnequip(Character user);
	public RotatedBigPicture getRotatedBelowPicture(){
		return Images.rotatedBigPictures.get(this.getName().toLowerCase()+"_below");
	}
	public RotatedBigPicture getRotatedAbovePicture(){
		return Images.rotatedBigPictures.get(this.getName().toLowerCase()+"_above");
	}
}
