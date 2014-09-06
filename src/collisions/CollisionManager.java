package collisions;

import gameengine.GameObject;
import gameplayer.GamePlayerFrame;
import gameplayer.GamePlayerEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.positionalsystem.PositionalType;
import util.positionalsystem.RelativeHorizontalPosition;
import util.positionalsystem.RelativePosition;
import util.positionalsystem.RelativeVerticalPosition;
import util.tuples.Tuple;
import jgame.JGObject;
import jgame.JGRectangle;

public class CollisionManager {
	private GamePlayerEngine gamePlayer;
	public CollisionManager(GamePlayerEngine gamePlayer){
		this.gamePlayer = gamePlayer;
	}
	/**	Generates a map of all collisions categorized by interaction type. 
	 * 
	 * @return
	 */
	public Map<Tuple<Integer>, ArrayList<Tuple<GameObject>>> getCollisions(){
		Map<Tuple<Integer>, ArrayList<Tuple<GameObject>>> collisionsByCollisionIDs = new HashMap<Tuple<Integer>, ArrayList<Tuple<GameObject>>>();
		for(GameObject i: gamePlayer.getCurrentObjects()){
			for(GameObject j: gamePlayer.getCurrentObjects()){
				if(i != j && i.colid <= j.colid){
					if(intersects(i, j)){
						Tuple<Integer> collisionRelation = new Tuple<Integer>(i.colid, j.colid);
						if(!collisionsByCollisionIDs.keySet().contains(collisionRelation)){
							collisionsByCollisionIDs.put(collisionRelation, new ArrayList<Tuple<GameObject>>());
						}
						collisionsByCollisionIDs.get(collisionRelation).add(new Tuple<GameObject>(i, j));
					}
				}
			}
		}
		return collisionsByCollisionIDs;
	}

	public HashMap<Tuple<Integer>, ArrayList<Tuple<GameObject>>> getCollisions(ArrayList<Tuple<Integer>> collisions){
		HashMap<Tuple<Integer>, ArrayList<Tuple<GameObject>>> collisionsByCollisionIDs = new HashMap<Tuple<Integer>, ArrayList<Tuple<GameObject>>>();
		for(Tuple<Integer> collisionIDs: collisions){
			collisionsByCollisionIDs.put(collisionIDs, new ArrayList<Tuple<GameObject>>());
		}
		for(GameObject i: gamePlayer.getCurrentObjects()){
			for(GameObject j: gamePlayer.getCurrentObjects()){
				if(i != j){
					Tuple<Integer> collisionRelation = new Tuple<Integer>(i.colid, j.colid);
					if(collisionsByCollisionIDs.containsKey(collisionRelation)){
						collisionsByCollisionIDs.get(collisionRelation).add(new Tuple<GameObject>(i, j));
					}
				}
			}
		}
		return collisionsByCollisionIDs;
	}
	public static RelativeHorizontalPosition getLastRelativeHorizontalPosition(JGObject o1, JGObject o2){
		RelativeHorizontalPosition rhp;
		if(o1.getLastX() + o1.getBBox().width < o2.getLastX() + 1){ //o1 is left of o2
			rhp = RelativeHorizontalPosition.LEFT;
		}else if(o1.getLastX() > o2.getLastX() + o2.getBBox().width){ // o1 is right of o2
			rhp = RelativeHorizontalPosition.RIGHT;
		}else{
			rhp = RelativeHorizontalPosition.MIDDLE;
		}
		return rhp;
	}
	public static RelativeVerticalPosition getLastRelativeVerticalPosition(JGObject o1, JGObject o2){
		RelativeVerticalPosition rvp;
		if(o1.getLastY() + o1.getBBox().height <= o2.getLastY() + 1){ //o1 is left of o2
			rvp = RelativeVerticalPosition.TOP;
		}else if(o1.getLastY() > o2.getLastY() + o2.getBBox().height){ // o1 is right of o2
			rvp = RelativeVerticalPosition.BOT;
		}else{
			rvp = RelativeVerticalPosition.MIDDLE;
		}
		return rvp;
	}
	public static RelativePosition getLastRelativePosition(JGObject o1, JGObject o2){
		RelativePosition rp;
		if(o1.getLastY() + o1.getBBox().height <= o2.getLastY() + 1){ //o1 is left of o2
			rp = RelativePosition.TOP;
		}else if(o1.getLastY() > o2.getLastY() + o2.getBBox().height){ // o1 is right of o2
			rp = RelativePosition.BOT;
		}else{
			if(o1.getLastX() + o1.getBBox().width < o2.getLastX() + 1){ //o1 is left of o2
				rp = RelativePosition.LEFT;
			}else if(o1.getLastX() > o2.getLastX() + o2.getBBox().width){ // o1 is right of o2
				rp = RelativePosition.RIGHT;
			}else{
				rp = RelativePosition.MIDDLE;
			}
		}
		return rp;
	}
	public static boolean willIntersectNextFrame(JGObject o1, JGObject o2){
		return o1.getNextBBox().intersects(o2.getNextBBox());
	}
	public static boolean intersects(JGObject o1, JGObject o2){
		return o1.getBBox().intersects(o2.getBBox());
	}
}