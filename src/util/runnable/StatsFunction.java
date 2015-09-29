package util.runnable;

/**
Used for out.printing all of the stats, their values, Arrays, etc.
 */
public class StatsFunction {

	/*
	    * Need to out print the names as constants,
	    * then three arrays of equal length.
	 */

	static String[] stats = new String[]{
			"Attack",
			"Armor",
			"Magic Resist",
			"Ability",
			"Fire Power",
			"Water Power",
			"Air Power",
			"Earth Power",
			"Purple Power",
			"Light Power",
			"Dark Power",
			"Fire Resist",
			"Water Resist",
			"Air Resist",
			"Earth Resist",
			"Purple Resist",
			"Light Resist",
			"Dark Resist",
			"Crit Chance",
			"HP",
			"MP",
			"HPRegen",
			"MPRegen",
			"Vampirism",
	};

	public static void main(String[] args){

		System.out.println(
				"/**\n" +
				" *  These constants represent each stat's location in the stat arrays.\n" +
				" */");


		int count = 0;
		for(String s : stats){

			String printed = "";
			for(char c : s.toCharArray()){
				if(c == ' ')
					c = '_';
				printed += c;
			}

			System.out.println("public static final int " + printed.toUpperCase() + " = " + count + ";");
			count++;
		}
		System.out.println();

		System.out.println("private double[] baseStats = new double[" + count + "];");
		System.out.println("private double[] flatStats = new double[" + count + "];");
		System.out.println("private double[] pcntStats = new double[" + count + "];");

	}

}
