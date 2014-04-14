package collision;

import util.PositionalType;
import jgame.JGObject;

public class CollisionEngine {
	public static PositionalType getCollisionType(JGObject o1, JGObject o2){
		if(o1.getBBox().intersects(o2.getBBox())){
			if(o1.getLastX() + o1.getBBox().width < o2.getLastX()){ // o1 is LEFT of o2
				if(o1.getLastY() + o1.getBBox().height < o2.getLastY()){ // o1 is TOP of o2
					return PositionalType.TOPLEFT;
				} else if(o2.getLastY() + o2.getBBox().height < o1.getLastY()){ // o1 is BOT of o2
					return PositionalType.BOTLEFT;
				} else { // o1's height intersects o2's height
					return PositionalType.LEFT;
				}
			} else if(o2.getLastX() + o2.getBBox().width < o1.getLastX()){ // o1 is RIGHT of o2
				if(o1.getLastY() + o1.getBBox().height < o2.getLastY()){ // o1 is TOP of o2
					return PositionalType.TOPRIGHT;
				} else if(o2.getLastY() + o2.getBBox().height < o1.getLastY()){ // o1 is BOT of o2
					return PositionalType.BOTRIGHT;
				} else { // o1's height intersects o2's height
					return PositionalType.RIGHT;
				}
			} else { // o1's width intersects o2's width
				if(o1.getLastY() + o1.getBBox().height < o2.getLastY()){ // o1 is TOP of o2
					return PositionalType.TOP;
				} else if(o2.getLastY() + o2.getBBox().height < o1.getLastY()){ // o1 is BOT of o2
					return PositionalType.BOT;
				} else { // o1's height intersects o2's height
					return PositionalType.INTERSECTION;
				}
			}
		}else{
			return PositionalType.NONE;
		}
	}
}
