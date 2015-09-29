package entities.characters.personas;

import areas.NewGameArea;
import entities.characters.Character;
import entities.characters.aiUtil.AStarNode;
import entities.tiles.Tiles;
import game.Engine;
import org.lwjgl.util.Point;
import util.Maths;

import java.util.ArrayList;
import java.util.TreeSet;

public class SimpleAStarAI extends SimpleMob {

	private static final long serialVersionUID = 1L;
	Persona target = null;
	ArrayList<Point> path = new ArrayList<Point>();

	public SimpleAStarAI(Character c) {
		character = c;
	}
	public SimpleAStarAI() {

	}
	private static final double findTargetChance = .00833;
	public void moveAI() {
		FINDING_TARGET:if(Math.random() < findTargetChance){
//			System.out.println(this + " ACQUIRING TARGET");
			Persona p = Engine.getActiveArea().getPersonas().getClosest(this.getCharacter().getLoc(), false, true, this.getTeam());

				if(p != null && Maths.dist(this, p) < 20*16){
					if(p.getTeam() != this.getTeam()){
						target = p;
//						System.out.println("     TARGET ACQUIRED: " + target);
						this.path = calculateRoute(this.character, target.getCharacter());
						break FINDING_TARGET;
					}
				}

			target = null;
			if(path != null){
				path.clear();
			}
		}

		if(target != null){
			if(path != null && path.size() != 0){
				Point nextGridSpot = path.get(0);
				Point nextSpot = new Point(nextGridSpot.getX()*16, nextGridSpot.getY() * 16);
				moveTowardsTarget(this.getCharacter(), nextSpot);
				Point charGPoint = new Point(this.getCharacter().getXi()/16, this.getCharacter().getYi()/16);
				if(charGPoint.equals(nextGridSpot)){
					path.remove(0);
				}
			}
			else{
				moveTowardsTarget(this.character, target.getCharacter().getXi(), target.getCharacter().getYi());
			}
		}
		else{
			super.moveAI();
		}

	}

	public String toString(){
		return "SimpleAStarAI - " + character;
	}

	protected static ArrayList<Point> calculateRoute(Character src, Character tgt){
		return calculateRoute(src, tgt.getLoc());
	}
	protected static ArrayList<Point> calculateRoute(Character src, Point tgt){
//		System.out.println("-----CALCULATING PATH-----");
		//USE GRID LOCATIONS. (ABSGX, ABSGY). ALWAYS.

		Point startGLoc = new Point(src.getXi()/16, src.getYi()/16);
		Point targetGLoc = new Point(tgt.getX()/16, tgt.getY()/16);

		//System.out.println("Start: " + Demigods.getLoc(startGLoc) );
		//System.out.println("Target: " + Demigods.getLoc(targetGLoc) );

		TreeSet<AStarNode> openSet = new TreeSet<AStarNode>();
		TreeSet<AStarNode> closedSet = new TreeSet<AStarNode>();

		NewGameArea area = Engine.getActiveArea();

		ArrayList<Point> path = new ArrayList<Point>();

		openSet.add(new AStarNode(startGLoc, targetGLoc, null, 0));

		boolean targetFound = false;

		AStarNode curr = openSet.first();


		while(!targetFound){

			if(openSet.size() == 0){
				targetFound = false;
				break;
			}
			//System.out.println("                        openSet.size() = " + openSet.size());
			//System.out.println("openSet = " + openSet);
			if(openSet.size() == 100000){
				targetFound = false;
				break;
			}

			curr = openSet.pollFirst();

			//System.out.println(openSet.remove(curr));

			closedSet.add(curr);

//			System.out.print(Demigods.getLoc(curr.getCoordinates()) + " - ");
//			System.out.println(Maths.dist(curr.getCoordinates(), targetGLoc));

			if(curr.getCoordinates().equals(targetGLoc)){
				targetFound = true;
				break;
			}


//			attempts++;
//			if(attempts > 20){
//				if(Maths.dist(curr.getCoordinates(), targetGLoc) > (1.5*Maths.dist(startGLoc, targetGLoc))){
//					continue;
//				}
//			}

//			else if(attempts > 50){
//				if(Maths.dist(curr.getCoordinates(), targetGLoc) > (1.5*Maths.dist(startGLoc, targetGLoc))){
//					continue;
//				}
//			}
//			else if(attempts > 100){
//				if(Maths.dist(curr.getCoordinates(), targetGLoc) > (1.3*Maths.dist(startGLoc, targetGLoc))){
//					continue;
//				}
//			}


			DX:for(int dx = -1; dx < 2; ++dx)
			DY:for(int dy = -1; dy < 2; ++dy){
				if(dx == 0 && dy == 0)continue;

				Point next = new Point(curr.getX() + dx, curr.getY() + dy);
				//System.out.println("    next = " + Demigods.getLoc(next));
				Point nextAbsLoc = new Point(next.getX()*16, next.getY()*16);
				if(!area.getPassable()[next.getX()][next.getY()])continue;
				float terrainValue = Tiles.terrainValue(area.getMap()[next.getX()][next.getY()]);
				AStarNode nextNode = new AStarNode(next, targetGLoc, curr, terrainValue);

				for(AStarNode node : closedSet){
					if(nextNode.getCoordinates().equals(node.getCoordinates())){
						continue DY;
					}
				}

				if(closedSet.contains(nextNode))continue;


				boolean openSetContainsNode = false;

				for(AStarNode node : openSet){
					if(nextNode.getCoordinates().equals(node.getCoordinates())){
						openSetContainsNode = true;
						break;
					}
				}

				if(!openSetContainsNode){
					openSet.add(nextNode);
				}
				else{
					float newCost = nextNode.getCostSoFar();

					for(AStarNode node : openSet){
						if(nextNode.getCoordinates().equals(node.getCoordinates())){
							nextNode = node;
							break;
						}
					}
					if(newCost < nextNode.getCostSoFar()){
						nextNode.setCostSoFar(newCost);
						nextNode.setParent(curr);
					}

				}

			}

		}

		if(!targetFound){
			System.out.println("-----PATH NOT FOUND-----");
			return null;
		}
		else{
			System.out.println("-----PATH DETERMINED-----");
			while(curr != null){
				path.add(0, curr.getCoordinates());
				curr = curr.getParent();
			}
		}


		return path;
	}
}
