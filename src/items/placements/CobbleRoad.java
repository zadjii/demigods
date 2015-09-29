package items.placements;

public class CobbleRoad extends Road {

	private static final long serialVersionUID = 1L;
	private static final int TILE_ID = 16;

	public CobbleRoad() {
		super();
		this.name = "Cobblestone Road";
	}

	public int getID() {
		return TILE_ID;
	}

}
