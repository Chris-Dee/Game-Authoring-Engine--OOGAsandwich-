package util.positionalsystem;

import jgame.JGObject;

public class ObjectPlacementManager {
	public static void placeBeside(JGObject o1, JGObject o2,
			RelativeHorizontalPosition lastRelativeHorizontalPosition,
			RelativeVerticalPosition lastRelativeVerticalPosition) {
		if(lastRelativeVerticalPosition == RelativeVerticalPosition.TOP){
			o1.y = o2.y - o1.getBBox().height;
			o1.yspeed = 0;
		}else if(lastRelativeVerticalPosition == RelativeVerticalPosition.BOT){
			o1.y = o2.y + o2.getBBox().height + 1;
			o1.yspeed = 0;
		}else{
			if(lastRelativeHorizontalPosition == RelativeHorizontalPosition.LEFT){
				o1.x = o2.x - o1.getBBox().width - 1;
				o1.xspeed = 0;
			}else if(lastRelativeHorizontalPosition == RelativeHorizontalPosition.RIGHT){
				o1.x = o2.x + o2.getBBox().width + 1;
				o1.xspeed = 0;
			}else if(lastRelativeHorizontalPosition == RelativeHorizontalPosition.MIDDLE){
				o1.y = o2.y - o1.getBBox().height;
				o1.yspeed = 0;
			}
		}
	}
}