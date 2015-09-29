package gui.trading;

import java.io.Serializable;
import java.util.HashMap;
import entities.characters.Character;
import items.Item;
import util.Constants;
import util.exceptions.DemigodsException;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 2/15/13
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class Trade implements Serializable {
	public static final int WHAT_THE_FUCK       = -1;
	public static final int TRADE_COMPLETE      = 0;
	public static final int NOT_ENOUGH_INPUT    = 1;
	public static final int NOT_ENOUGH_SPACE    = 2;

	private HashMap<String,Integer> input = new HashMap<String, Integer>();
	private HashMap<String,Integer> output = new HashMap<String, Integer>();
	private boolean buy = false;

	/**
	 *
	 * @param buy - if true, this trade is a purchase from vendor
	 * @param item - item to buy/sell.
	 * @param quantity - quantity of item bought/sold at once
	 * @param price - buy/sell price for the quantity of the given item
	 */
	public Trade(boolean buy, String item, int quantity, int price){
		//item = item.toLowerCase();
		if(buy){
			output.put(item, quantity);
			input.put("Coins", price);
		}
		else{
			input.put(item, quantity);
			output.put("Coins", price);
		}
		this.buy = buy;
	}




	public int trade(Character buyer) throws DemigodsException {

		HashMap<String, Integer> invCounts = buyer.getInventory().getItemCounts();
		for(String s:input.keySet()){
			if(!invCounts.containsKey(s))return NOT_ENOUGH_INPUT;
			else if(invCounts.get(s) < input.get(s))return NOT_ENOUGH_INPUT;
		}
		//Buyer has enough resources

		//chech that there is enough space HERE
		//check it
		//DO THA THANGS

		for(String s:input.keySet()){
			for (int i = 0; i < input.get(s); i++) {
				buyer.getInventory().removeItem(s);
			}
		}
		for(String s:output.keySet()){
			for (int i = 0; i < output.get(s); i++) {
				Item item = Constants.getItemFromString(s);
				buyer.getInventory().addItem(item);
			}
		}

		throw new DemigodsException("TRADING ISN'T DONE; FORCE ERROR CHECKING");
	}
	public String getItemName(){
		String inputString = input.keySet().iterator().next();
		String outputString = output.keySet().iterator().next();

		return buy? outputString:inputString;
	}
	public int getItemCount(){
		int inputPrice = input.get(input.keySet().iterator().next());
		int outputPrice = output.get(output.keySet().iterator().next());

		return buy? outputPrice:inputPrice;
	}
	public int getPrice(){
		int inputPrice = input.get(input.keySet().iterator().next());
		int outputPrice = output.get(output.keySet().iterator().next());

		return buy? inputPrice:outputPrice;
	}
	public boolean isBuy(){return buy;}
}
