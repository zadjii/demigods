package util.data;

import entities.Entity;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import util.Maths;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Stores all of the entities. Holds the entities in the array, then each tick
 * rehashes all of them into the map. Each Grid space that a person occupies
 * will be in the map.
 */
public class EntityMap implements Serializable {

	private HashMap<Point, ArrayList<Entity>> map = new HashMap<Point, ArrayList<Entity>>();
	private ArrayList<Entity> entities = new ArrayList<Entity>();

	public void add(Entity entity){
		entities.add(entity);
		hashEntity(entity);
	}
	private void hashEntity(Entity entity){
		Rectangle hitbox = entity.getAbsBounds();
		int gx = hitbox.getX()/16;
		int gy = hitbox.getY()/16;
		if(hitbox.getX() < 0)gx--;
		if(hitbox.getY() < 0)gy--;
		int gwidth =  (hitbox.getWidth()/16) + 1;
		int gheight = (hitbox.getHeight()/16) + 1;
		for(int dx = 0; dx < gwidth; dx++){
			for(int dy = 0; dy < gheight; dy++){
				Point newGridPoint = new Point(gx+dx, gy+dy);
				if(map.get(newGridPoint)==null){
//					System.out.println(
//							"added an arraylist at " + print(newGridPoint)
//					);
					map.put(newGridPoint, new ArrayList<Entity>());
				}
				map.get(newGridPoint).add(entity);
			}
		}
		//System.out.println("Hashed " + persona);

	}
//	private String print(Point p ){
//		return RSP.getLoc(p);
//	}
	//	public Persona remove(Point p){
//		Persona removing = map.remove(p);
//		entities.remove(removing);
//		return removing;
//	}
	public void remove(Entity entity){
		entities.remove(entity);
		//map.remove(person.entity().getAbsoluteLoc());
	}
	//	public void reseed(){
//		ArrayList<Point> toBeReseeded = new ArrayList<Point>();
//		for (Point point : map.keySet()){
//			Persona person = map.get(point);
//			if(!person.getCharacter().getAbsoluteLoc().equals(point)){
//				toBeReseeded.add(point);
//			}
//		}
//		for(Point point:toBeReseeded){
//			Persona person = map.remove(point);
//			map.put(person.getCharacter().getAbsoluteLoc(), person);
//		}
//	}
	public void reseed(){
		map = new HashMap<Point, ArrayList<Entity>>();
		for(Entity entity : entities){
			hashEntity(entity);
		}
//		for(Point p : map.keySet()){
//			System.out.println(
//					RSP.getLoc(p) + map.get(p)
//			);
//		}
//		System.out.println(
//		);
	}

	/**
	 * The rectangle is an absolute bounds.
	 * @param rectangle
	 * @return
	 */
	public ArrayList<Entity> get(Rectangle rectangle){
		int gx = rectangle.getX()/16;
		int gy = rectangle.getY()/16;

		if(rectangle.getX() < 0)gx--;
		if(rectangle.getY() < 0)gy--;

		int gwidth =  (rectangle.getWidth()/16) + 1;
		int gheight = (rectangle.getHeight()/16) + 1;
		ArrayList<Entity> returning = new ArrayList<Entity>();
		boolean useRect = gwidth*gheight < entities.size();
		if(!useRect){
			for (Entity entity : entities){
				if(entity.getAbsBounds().intersects(rectangle)){
					if(!returning.contains(entity))returning.add(entity);
				}
			}
		}
		else{
			for(int dx = 0;dx<gwidth;dx++){
				for(int dy = 0;dy<gheight;dy++){
					Point point = new Point(gx + dx, gy + dy);
					if(map.get(point)!=null){
						for(Entity entity : map.get(point)){
							if(!returning.contains(entity))returning.add(entity);
						}
					}
				}
			}
		}
		return returning;
	}

	public ArrayList<Entity> get(Point point){
		Rectangle rect = new Rectangle(point.getX()-8, point.getY()-8, 16, 16);
		return get(rect);
	}



	/**
	 * Returns the closest character to this character
	 * @param other
	 * @return
	 */
	public Entity getClosest(Entity other){
		Point point = new Point(other.getLoc());
		double closestDistance = -1;
		Entity closest = null;
		for(Entity entity : entities){
			double distance = Maths.dist(entity.getLoc(), point);
			if(closest == null || distance < closestDistance){
				if(entity != other){
					closest = entity;
					closestDistance = distance;
				}
			}
		}
		return closest;
	}


	public int size(){
		return entities.size();
	}
	public ArrayList<Entity> getEntities(){
		return this.entities;
	}
	public boolean contains(Entity entity){
		return entities.contains(entity);
	}
	public String toString(){
		return entities.toString();
	}
}
