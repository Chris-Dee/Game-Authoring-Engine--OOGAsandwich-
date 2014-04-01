package FrontEnd;
//TODO make this work...
import jgame.JGObject;
import jgame.platform.JGEngine;
//The invisible little object that moves around in the levels
public class LevelMover extends JGObject {
	JGEngine myEngine;
	public LevelMover(String name, boolean unique_id, double x, double y,
			int collisionid, String gfxname, LevelEditor level) {
		super(name, unique_id, x, y, collisionid, gfxname);
		myEngine=level;
	}
	private boolean checkKey(int key){
		Boolean b=myEngine.getKey(key);
		myEngine.clearLastKey();
		return b;
	}
	public void move(){
		xdir=0;
		ydir=0;
		if(checkKey(myEngine.KeyLeft)){
			xdir=-1;
		}
		if(checkKey(myEngine.KeyRight)){
			xdir=1;
		}
		if(checkKey(myEngine.KeyUp))
			ydir=1;
		if(checkKey(myEngine.KeyDown))
			ydir=-1;
	}



}
