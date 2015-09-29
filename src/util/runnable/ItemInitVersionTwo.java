package util.runnable;

import java.io.File;
import java.util.ArrayList;

public class ItemInitVersionTwo {

	public static void main(String[] args){

		String myDirectoryPath = "src/items";

		ArrayList<String> items = new ArrayList<String>();

		File dir = new File(myDirectoryPath);
		System.out.println("opened " + dir);

		String name;
		for (File child : dir.listFiles()) {

			if (child.getName().charAt(0)=='.') {
				continue;  // Ignore the self and parent aliases.
			}



			if(child.isDirectory()){
				for(File grandchild:child.listFiles()){
					name = grandchild.getName();

					if (!isExceptionFile(name) && name.length() > 5){
						items.add(name);
					}
				}
			}
			else{
				name = child.getName();
				if (!isExceptionFile(name) && name.length() > 5){
					items.add(name);
				}
			}

//			// Do something with child
//			if(name.equalsIgnoreCase("armor")||name.equalsIgnoreCase("foundations")){
//				for(File grandchild:child.listFiles()){
//					name = grandchild.getName();
//
//					if (!name.equals("RangedWeapon.java") &&
//							!name.equals("Armor.java") &&
//							name.length() > 5){
//						items.add(name);
//					}
//				}
//			}
//			else if (!name.equals("RangedWeapon.java") &&
//					!name.equals("Armor.java") &&
//					name.length() > 5){
//				items.add(name);
//			}
		}
		System.out.println(items);
		System.out.println(items.size());

		System.out.println();
		System.out.println();

		System.out.println("public static final Item[] allTheItems = new Item[] {");
		for(String itemClass : items){
			System.out.print("     new ");
			for(int i = 0; i < itemClass.length()-5; ++i){
				System.out.print(itemClass.charAt(i));
			}
			System.out.println("(), ");
		}
		System.out.println("};");

		System.out.println();
		System.out.println();

		System.out.println("public static final int[] blankItemIntArray = new int[] {");
		for(String itemClass : items){

			System.out.print("     0, //   ");
			for(int i = 0; i < itemClass.length()-5; ++i){
				System.out.print(itemClass.charAt(i));
			}
			System.out.println();
		}
		System.out.println("};");
	}

	private static boolean isExceptionFile(String fileName){
		if(fileName.equalsIgnoreCase("RangedWeapon.java")
				||fileName.equalsIgnoreCase("Armor.java")
				||fileName.equalsIgnoreCase("Item.java")
				||fileName.equalsIgnoreCase("OffhandItem.java")
				||fileName.equalsIgnoreCase("AmmoInterface.java")
				||fileName.equalsIgnoreCase("InteractableItem.java")
				||fileName.equalsIgnoreCase("LightItem.java")
				||fileName.equalsIgnoreCase("Tool.java")
				||fileName.equalsIgnoreCase("ConsumableItem.java")
				){
			return true;
		}
		return false;
	}
}
