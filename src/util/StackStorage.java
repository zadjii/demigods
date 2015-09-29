package util;

import items.Item;

import java.io.Serializable;

public class StackStorage implements Serializable {

	ItemStack[] stacks;

	public StackStorage(int size){
		stacks = new ItemStack[size];
	}

	public boolean addItem(Item item){
		for (int i = 0; i < stacks.length; i++) {
			if(stacks[i] == null){
				stacks[i] = new ItemStack();
				stacks[i].addItem(item);
				return true;
			}
			else if(stacks[i].getItemToString().equalsIgnoreCase(item.toString())){
				stacks[i].addItem(item);
				return true;
			}

		}
		return false;
	}
	public boolean addItem(Item item, int index){
		if(stacks[index] == null){
			stacks[index] = new ItemStack();
			stacks[index].addItem(item);
			return true;
		}
		else if(stacks[index].getItemToString().equalsIgnoreCase(item.toString())){
			stacks[index].addItem(item);
			return true;
		}
		return false;
	}
	public int getSize(){
		return stacks.length;
	}

}
