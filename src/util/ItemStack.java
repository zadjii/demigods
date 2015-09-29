package util;

import items.Item;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 9/16/12
 * Time: 7:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class ItemStack implements Serializable {
	private ArrayList<Item> items = new ArrayList<Item>();
	public ArrayList<Item> getItems(){
		return this.items;
	}
	public ItemStack getHalfStack(){
		ItemStack returnStack = new ItemStack();
		int quantity = items.size()/2;
		for(int i = 0; i < quantity; i++){
			if(this.items.get(0) == null)break;
			returnStack.items.add(this.items.get(0));
			this.items.remove(0);
		}

		return returnStack;
	}
	public String getItemToString(){
		if(items.size()>0)return items.get(0).toString();
		else return null;
	}
	public boolean addItem(Item item){
		if(items.size()==0){
			items.add(item);
			return true;
		}
		else if(item.toString().equalsIgnoreCase(getItemToString())){
			items.add(item);
			return true;
		}
		else{
			return false;
		}
	}
}
