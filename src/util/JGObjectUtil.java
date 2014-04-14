package util;

import java.util.ArrayList;
import java.util.List;

import jgame.JGObject;
import jgame.JGPoint;

public class JGObjectUtil {
//	public static JGPoint getCenter(List<? extends JGObject> objs){
//		List<JGPoint> listOfPoints = new ArrayList<JGPoint>();
//		for(JGObject o : objs){
//			listOfPoints.add(o.getPos());
//		}
//		return JGPoint.divide(JGPoint.add(listOfPoints), objs.size());
//	}
	public static void placeAt(JGObject movingObject, JGObject referenceObject, PositionalType position, double xOffset, double yOffset){
		switch(position){
			case TOP:
				movingObject.x = referenceObject.x + xOffset;
				movingObject.y = referenceObject.y - movingObject.getBBox().height + yOffset;
				break;
			case LEFT:
				movingObject.y = referenceObject.y + yOffset;
				movingObject.x = referenceObject.x - movingObject.getBBox().width + xOffset;
				break;
			case RIGHT:
				movingObject.y = referenceObject.y + yOffset;
				movingObject.x = referenceObject.x + referenceObject.getBBox().width + xOffset;
				break;
			case BOT:
				movingObject.x = referenceObject.x + xOffset;
				movingObject.y = referenceObject.y + referenceObject.getBBox().height + yOffset;
				break;
			case TOPLEFT:
				movingObject.y = referenceObject.y - movingObject.getBBox().height + yOffset;
				movingObject.x = referenceObject.x - movingObject.getBBox().width + xOffset;
				break;
			case TOPRIGHT:
				movingObject.y = referenceObject.y - movingObject.getBBox().height + yOffset;
				movingObject.x = referenceObject.x + referenceObject.getBBox().width + xOffset;
				break;
			case BOTLEFT:
				movingObject.y = referenceObject.y + referenceObject.getBBox().height + yOffset;
				movingObject.x = referenceObject.x - movingObject.getBBox().width + xOffset;
				break;
			case BOTRIGHT:
				movingObject.y = referenceObject.y + referenceObject.getBBox().height + yOffset;
				movingObject.x = referenceObject.x + referenceObject.getBBox().width + xOffset;
				break;
			case INTERSECTION:
				movingObject.x = referenceObject.x + xOffset;
				movingObject.y = referenceObject.y + yOffset;
				break;
			case NONE:
				break;
		}
	}
}
