package items.materials;

import items.Item;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 5/18/12
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class Bone extends Item {

	private static final long serialVersionUID = 1L;

	public Bone() {
		super();
		setWeight(.2);
		recipeValue = 2;
		setImageID(7, 0);
		this.name = "Bone";
	}

}