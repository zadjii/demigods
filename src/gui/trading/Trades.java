package gui.trading;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 2/15/13
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class Trades {
    ///IT IS IMPORTANT TO PROPERLY CAPITALIZE THOSE NAMES!
    //                                            buy from npc, item, quant, $$
    public static Trade normBuyWood = new Trade(true, "Wood", 10, 25);
    public static Trade normSelWood = new Trade(false, "Wood", 25, 10);
    public static Trade specBuyWood = new Trade(true, "Wood", 10, 20);
    public static Trade specSelWood = new Trade(false, "Wood", 25, 15);

    public static Trade normSelScrap = new Trade(false, "Scrap", 10, 25);

    public static Trade normBuyStone = new Trade(true, "Stone", 10, 35);
    public static Trade normSelStone = new Trade(false, "Stone", 25, 15);
    public static Trade specBuyStone = new Trade(true, "Stone", 10, 25);
    public static Trade specSelStone = new Trade(false, "Stone", 25, 20);

    public static Trade normBuyCopperOre = new Trade(true, "Copper Ore", 10, 25);
    public static Trade normSelCopperOre = new Trade(false, "Copper Ore", 25, 10);
    public static Trade specBuyCopperOre = new Trade(true, "Copper Ore", 10, 20);
    public static Trade specSelCopperOre = new Trade(false, "Copper Ore", 25, 15);

    public static Trade normBuyTinOre = new Trade(true, "Tin Ore", 10, 25);
    public static Trade normSelTinOre = new Trade(false, "Tin Ore", 25, 10);
    public static Trade specBuyTinOre = new Trade(true, "Tin Ore", 10, 20);
    public static Trade specSelTinOre = new Trade(false, "Tin Ore", 25, 15);

    public static Trade normBuyIronOre = new Trade(true, "Iron Ore", 10, 25);
    public static Trade normSelIronOre = new Trade(false, "Iron Ore", 25, 10);
    public static Trade specBuyIronOre = new Trade(true, "Iron Ore", 10, 20);
    public static Trade specSelIronOre = new Trade(false, "Iron Ore", 25, 15);

    public static Trade normBuyBronzeBar = new Trade(true, "Bronze Bar", 10, 25);
    public static Trade normSelBronzeBar = new Trade(false, "Bronze Bar", 25, 10);
    public static Trade specBuyBronzeBar = new Trade(true, "Bronze Bar", 10, 20);
    public static Trade specSelBronzeBar = new Trade(false, "Bronze Bar", 25, 15);

    public static Trade normBuyIronBar = new Trade(true, "Iron Bar", 10, 25);
    public static Trade normSelIronBar = new Trade(false, "Iron Bar", 25, 10);
    public static Trade specBuyIronBar = new Trade(true, "Iron Bar", 10, 20);
    public static Trade specSelIronBar = new Trade(false, "Iron Bar", 25, 15);


}
