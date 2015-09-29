package util;



//import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 1/14/13
 * Time: 12:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class TextToImageConverter {

	static String fileName;


	public static void main(String[] args){
		if(args.length != 2)throw new IllegalArgumentException("2 args: <filenumber> <0=GS or 1=RGB>");
		fileName = args[0];
		int ID = Integer.parseInt(fileName);
		fileName = fileName.concat("_output.txt");
		File textFile = new File(fileName);

		if(args[1].equalsIgnoreCase("0"))saveBWImage(textFile);
		else if(args[1].equalsIgnoreCase("1"))saveRGBImage(textFile, ID);


	}

	public static void saveBWImage(File file){}
	public static void saveRGBImage(File file, int ID){
		try{
			Scanner data = new Scanner(file);
			int size = Integer.parseInt(data.nextLine());

			BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			data.useDelimiter("\\s+");
			int x = 0;
			int y = 0;
//			g.setColor(new Color(255,255,255));
//			for(int x0 = 0; x0 < image.getWidth(); x0++)for(int y0 = 0; y0 < image.getHeight(); y0++)
//				g.fillRect(x0,y0,1,1);
			while(data.hasNext()){
				String nextPixel = data.next();
//				System.out.println(
//						nextPixel
//				);
				String redString = nextPixel.substring(0, 3);
				String grnString = nextPixel.substring(4,7);
				String bluString = nextPixel.substring(8,11);

//				System.out.println(
//						redString + ", " + grnString + ", " + bluString + "\t:" + x + ", " + y
//				);

				int red = Integer.parseInt(nextPixel.substring(0, 3));
				int grn = Integer.parseInt(nextPixel.substring(4,7));
				int blu = Integer.parseInt(nextPixel.substring(8,11));

				g.setColor(new Color(red,grn,blu));
				g.fillRect(x,y,1,1);

				x++;

				if(x>(size-1)){
					x = 0;
					y++;
					//System.out.println(data.next());

				}
				if(y>size*2)System.err.println("Off by one?");
				image.flush();
			}
			g.dispose();
			try {
				File imgOut = new File(ID + ".png");
				ImageIO.write(image, "png", imgOut);
				System.out.println(
						"Wrote a " +size+ "x" +size+ "image, " + ID + ".png"
				);
			} catch (IOException e) {
				System.err.println("didnt work.... \n\tCaught an IOException: \n"  +e.getMessage());
			}

		}
		catch (FileNotFoundException e){}

	}



}
