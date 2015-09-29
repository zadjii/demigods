package util;

import items.weapons.IronSword;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 7/10/13
 * Time: 7:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class VersioningTest {
	private static final int version = 3;
	public static void main(String[] args) {
		TesterObject bob = new TesterObject(3,3,"carlos");
		writeObject("File.v" + version, bob);
		if(version > 0){
			Object obj = readObject("File.v" + (version-1));
			System.out.println(obj);
		}
	}
	private static void writeObject(String path, Object obj){
		try{
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.close();
			fos.close();
			System.out.println("Save complete");
		} catch (FileNotFoundException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}
	private static Object readObject(String path){
		try{
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();
			System.out.println("Read complete");
			return obj;
		} catch (FileNotFoundException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (ClassNotFoundException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		return null;
	}
}
class TesterObject implements Serializable{
	private static final long serialVersionUID = 0L;
	int var0;
	int var1;
	int var2 = 2;
	int var3 = 4;
	IronSword friend;// = new TesterObject(16, 16, "Cameron");
	String name;
	TesterObject(int var0, int var1, String name){
		this.name = name;
		this.var0 = var0;
		this.var1 = var1;
		friend = new IronSword();
	}

	@Override
	public String toString() {
		return "TesterObject{" +
				"var0=" + var0 +
				", var1=" + var1 +
				", var2=" + var2 +
				", var3=" + var3 +
				", friend=" + friend +
				", name='" + name + '\'' +
				'}';
	}
}