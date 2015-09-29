package util.runnable;

import items.Item;
import java.io.File;
import java.util.ArrayList;

public class ItemInit {

	public static void main(String[] args){

		String myDirectoryPath = "src/items";
		ArrayList<String> items = new ArrayList<String>();
		File dir = new File(myDirectoryPath);
		System.out.println("opened " + dir);
		  for (File child : dir.listFiles()) {
		    if (".".equals(child.getName()) || "..".equals(child.getName()) || ".DS_Store".equals(child.getName())) {
		      continue;  // Ignore the self and parent aliases.
		    }
		    // Do something with child
			  String name = child.getName();
			  if(name.equalsIgnoreCase("armor")||name.equalsIgnoreCase("foundations")){
				  for(File grandchild:child.listFiles()){
					  name = grandchild.getName();

					  if (!name.equals("RangedWeapon.java") &&
							  !name.equals("Armor.java") &&
							  name.length() > 5){
						  items.add(name);
					  }
				  }
			  }
			  else if (!name.equals("RangedWeapon.java") &&
					  !name.equals("Armor.java") &&
					  name.length() > 5){
				  items.add(name);
			  }
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

}
