package util;

import entities.characters.Character;
/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 8/6/13
 * Time: 7:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface OnHitListener {
	public void onHit(Character src, Character tgt);
}
