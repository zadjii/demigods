package util.runnable;

public class ExperienceFunction {

	public static void main(String[] args){
		int[] xp2next = new int[250];
		xp2next[2] = 100;
		for(int i = 3; i < xp2next.length; ++i){
			xp2next[i] = xp2next[i - 1] + (i-1)*50 + (int)(Math.random()*50) + (int)(Math.random()*50);
		}

		int[] totalXP = new int[250];
		for(int i = 1; i < totalXP.length; ++i){
			totalXP[i] = totalXP[i-1] + xp2next[i];
		}
		
		for(int i = 0; i < xp2next.length; ++i){
			System.out.println(xp2next[i] + ", // " + i);
		}
		
		System.out.println();
		System.out.println();
		
		for(int i = 0; i < xp2next.length; ++i){
			System.out.println(totalXP[i] + ", // " + i);
		}
	}
	
	
	
}
