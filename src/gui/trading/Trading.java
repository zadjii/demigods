package gui.trading;

import gui.guiPanels.ShopPanel;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 2/15/13
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class Trading {

	public static final int WOODCUTTER      = 0;
	public static final int BUILDER         = 1;
	public static final int MINER           = 2;
	public static final int SMITHY          = 3;
	public static final int ALCHEMIST       = 4;
	public static final int FARMER          = 5;

	public static ShopPanel get(int shopID){
		ArrayList<Trade> trades = new ArrayList<Trade>();
		//all traders buy all of the goods.
		//trades.add(Trades.normSelWood); //0

		switch (shopID){
			case WOODCUTTER:
				//As you can see, the trader does not have the normal wood buying price
				//instead, they sell wood for cheap.
				//trades.remove(Trades.normSelWood);
				trades.add(Trades.specSelWood);

				trades.add(Trades.normBuyWood);
				trades.add(Trades.normSelScrap);
				break;
			case BUILDER:
				trades.add(Trades.normSelScrap);

				trades.add(Trades.specSelWood);
				trades.add(Trades.normBuyWood);

				trades.add(Trades.specSelStone);
				trades.add(Trades.normBuyStone);

				break;
			case MINER:

				trades.add(Trades.specBuyStone);
				trades.add(Trades.normSelStone);

				trades.add(Trades.normSelCopperOre);
				trades.add(Trades.normBuyCopperOre);

				trades.add(Trades.normSelTinOre);
				trades.add(Trades.normBuyTinOre);

				trades.add(Trades.normSelIronOre);
				trades.add(Trades.normBuyIronOre);

				trades.add(Trades.specSelBronzeBar);
				trades.add(Trades.specSelIronBar);
				break;
			case SMITHY:

				trades.add(Trades.normSelCopperOre);
				//trades.add(Trades.specBuyCopperOre);

				trades.add(Trades.normSelTinOre);
				//trades.add(Trades.specBuyTinOre);

				trades.add(Trades.normSelIronOre);
				//trades.add(Trades.specBuyTinOre);

				trades.add(Trades.specBuyBronzeBar);
				trades.add(Trades.specBuyIronBar);
				break;


		}

		return new ShopPanel(trades);
	}

}
