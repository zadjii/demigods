package util.data;

import entities.Entity;
import entities.characters.personas.Persona;
import entities.characters.Character;
import game.Demigods;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import util.Maths;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Stores all of the personas. Holds the personas in the array, then each tick
 * rehashes all of them into the map. Each Grid space that a person occupies
 * will be in the map.
 */
public class PersonaMap implements Serializable {

	private HashMap<Point, ArrayList<Persona>> map = new HashMap<Point, ArrayList<Persona>>();
	private ArrayList<Persona> personas = new ArrayList<Persona>();

	public void add(Persona person){
		personas.add(person);
		hashPersona(person);
	}
	public void clear(){
		this.personas = new ArrayList<Persona>();
		reseed();
	}
	private void hashPersona(Persona persona){
		Rectangle hitbox = persona.getCharacter().getAbsHitbox();
		int gx = hitbox.getX()/16;
		int gy = hitbox.getY()/16;
		if(hitbox.getX() < 0)gx--;
		if(hitbox.getY() < 0)gy--;
		int gwidth =  (hitbox.getWidth()/16) + 1;
		int gheight = (hitbox.getHeight()/16) + 1;
		for(int dx = 0; dx <= gwidth; dx++){
			for(int dy = 0; dy <= gheight; dy++){
				Point newGridPoint = new Point(gx+dx, gy+dy);
				if(map.get(newGridPoint)==null){
//					System.out.println(
//							"added an arraylist at " + print(newGridPoint)
//					);
					map.put(newGridPoint, new ArrayList<Persona>());
				}
				map.get(newGridPoint).add(persona);
			}
		}
		//System.out.println("Hashed " + persona);

	}
	private String print(Point p ){
		return Demigods.getLoc(p);
	}
//	public Persona remove(Point p){
//		Persona removing = map.remove(p);
//		personas.remove(removing);
//		return removing;
//	}
	public void remove(Persona person){
		personas.remove(person);
		//map.remove(person.getCharacter().getAbsoluteLoc());
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
		map = new HashMap<Point, ArrayList<Persona>>();
		ArrayList<Persona> temp = new ArrayList<Persona>();
		for(Persona persona : personas){
			if(persona.getCharacter().getHP() > 0){
				hashPersona(persona);
			}
			else{
				temp.add(persona);
				//remove the person EVERYWHERE;
			}
		}
		for(Persona p : temp){personas.remove(p);}
//		for(Point p : map.keySet()){
//			System.out.println(
//					Demigods.getLoc(p) + map.get(p)
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
	public ArrayList<Persona> get(Rectangle rectangle){
		int gx = rectangle.getX()/16;
		int gy = rectangle.getY()/16;

		if(rectangle.getX() < 0)gx--;
		if(rectangle.getY() < 0)gy--;

		int gwidth =  (rectangle.getWidth()/16) + 1;
		int gheight = (rectangle.getHeight()/16) + 1;
		ArrayList<Persona> returning = new ArrayList<Persona>();
		boolean useRect = gwidth*gheight < personas.size();
		if(!useRect){
			for (Persona persona : personas){
				if(persona.getCharacter().getAbsHitbox().intersects(rectangle)){
					if(!returning.contains(persona))returning.add(persona);
				}
			}
		}
		else{
			for(int dx = 0;dx<gwidth;dx++){
				for(int dy = 0;dy<gheight;dy++){
					Point point = new Point(gx + dx, gy + dy);
					if(map.get(point)!=null){
						for(Persona persona : map.get(point)){
							if(!returning.contains(persona))returning.add(persona);
						}
					}
				}
			}
		}
		return returning;
	}
	/**
	 * The point is an absolute bounds.
	 * @param point
	 * @return
	 */
//	public ArrayList<Persona> get(Point point){
//		System.out.println(Demigods.getLoc(point));
//		int gx = point.getX()/16;
//		int gy = point.getY()/16;
//
//		if(point.getX() < 0)gx--;
//		if(point.getY() < 0)gy--;
//		Point gridPoint = new Point(gx, gy);
//		System.out.println(Demigods.getLoc(gridPoint));
//		ArrayList<Persona> returning = new ArrayList<Persona>();
//
//
//		if(map.get(gridPoint)!=null){
//			for(Persona persona : map.get(gridPoint)){
//				if(!returning.contains(persona))returning.add(persona);
//			}
//		}
//
//
//		return returning;
//	}
	public ArrayList<Persona> get(Point point){
		Rectangle rect = new Rectangle(point.getX()-8, point.getY()-8, 16, 16);
		return get(rect);
	}
	public Persona getClosest(Point point){
		return getClosest(point, false, false, 0);
	}
	public Persona getClosest(Point point, boolean sameTeam, boolean differentTeam, int team){
		double closestDistance = -1;
		Persona closest = null;
		for(Persona persona : personas){
			double distance = Maths.dist(persona.getCharacter().getLoc(), point);
			if(closest == null || distance < closestDistance){
				if(sameTeam){
					if(persona.getTeam() == team){
						closest = persona;
						closestDistance = distance;
					}
				}
				else if(differentTeam){
					if(persona.getTeam() != team){
						closest = persona;
						closestDistance = distance;
					}
				}
				else{
					closest = persona;
					closestDistance = distance;
				}
			}
		}
		return closest;
	}

	/**
	 * Returns the closest character to this character
	 * @param character
	 * @return
	 */
	public Persona getClosest(Character character){
		Point point = new Point(character.getLoc());
		double closestDistance = -1;
		Persona closest = null;
		for(Persona persona : personas){
			double distance = Maths.dist(persona.getCharacter().getLoc(), point);
			if(closest == null || distance < closestDistance){
				if(persona.getCharacter() != character){
					closest = persona;
					closestDistance = distance;
				}
			}
		}
		return closest;
	}

	public Persona getClosestEnemy(Entity entity, int team){
		return getClosestEnemy(entity.getLoc(), team);
	}
	public Persona getClosestEnemy(Point point, int team){
		double closestDistance = -1;
		Persona closest = null;
		for(Persona persona : personas){
			double distance = Maths.dist(persona.getCharacter().getLoc(), point);
			if(closest == null || distance < closestDistance){
				if(persona.getTeam() != team){
					closest = persona;
					closestDistance = distance;
				}
			}
		}
		return closest;
	}
	public Persona getClosestEnemy(Point point, int team, Character[] ignored){
		double closestDistance = -1;
		Persona closest = null;
		for(Persona persona : personas){
			double distance = Maths.dist(persona.getCharacter().getLoc(), point);
			if(closest == null || distance < closestDistance){
				DECIDING_STEP:if(persona.getTeam() != team){
					for(Character character : ignored){
						if(character.equals(persona.getCharacter())) break DECIDING_STEP;
					}
					closest = persona;
					closestDistance = distance;
				}
			}
		}
		return closest;
	}
	public int size(){
		return personas.size();
	}
	public ArrayList<Persona> getPersonas(){
		return this.personas;
	}
	public boolean contains(Persona persona){
		return personas.contains(persona);
	}
	public String toString(){
		return personas.toString();
	}
}
