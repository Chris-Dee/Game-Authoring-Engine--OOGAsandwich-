package collision;

import jgame.JGObject;

public class CollisionEngine {
	public static CollisionType getCollisionType(JGObject o1, JGObject o2){
		if(o1.getBBox().intersects(o2.getBBox())){
			if(o1.getLastX() + o1.getBBox().width < o2.getLastX()){ // o1 is LEFT of o2
				if(o1.getLastY() + o1.getBBox().height < o2.getLastY()){ // o1 is TOP of o2
					return CollisionType.TOPLEFT;
				} else if(o2.getLastY() + o2.getBBox().height < o1.getLastY()){ // o1 is BOT of o2
					return CollisionType.BOTLEFT;
				} else { // o1's height intersects o2's height
					return CollisionType.LEFT;
				}
			} else if(o2.getLastX() + o2.getBBox().width < o1.getLastX()){ // o1 is RIGHT of o2
				if(o1.getLastY() + o1.getBBox().height < o2.getLastY()){ // o1 is TOP of o2
					return CollisionType.TOPRIGHT;
				} else if(o2.getLastY() + o2.getBBox().height < o1.getLastY()){ // o1 is BOT of o2
					return CollisionType.BOTRIGHT;
				} else { // o1's height intersects o2's height
					return CollisionType.RIGHT;
				}
			} else { // o1's width intersects o2's width
				if(o1.getLastY() + o1.getBBox().height < o2.getLastY()){ // o1 is TOP of o2
					return CollisionType.TOP;
				} else if(o2.getLastY() + o2.getBBox().height < o1.getLastY()){ // o1 is BOT of o2
					return CollisionType.BOT;
				} else { // o1's height intersects o2's height
					return CollisionType.CONTINUED;
				}
			}
		}else{
			return CollisionType.NONE;
		}
	}
}
