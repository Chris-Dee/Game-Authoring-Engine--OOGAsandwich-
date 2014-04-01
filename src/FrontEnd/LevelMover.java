package FrontEnd;
//TODO make this work...
import jgame.JGObject;
import jgame.platform.JGEngine;
//The invisible little object that moves around in the levels
public class LevelMover extends JGObject {
	JGEngine myEngine;
	public LevelMover(LevelEditor level) {
		super("srball",true, 20, 20,4,"srball",0,0,2.0,2.0,-1);
		myEngine=level;
	}
	private boolean checkKey(int key){
		Boolean b=myEngine.getKey(key);
		myEngine.clearLastKey();
		System.out.println(b);
		return b;
	}
	public void move(){
		xdir=0;
		ydir=0;
		if(checkKey(myEngine.KeyLeft))
			xdir=-1;
		if(checkKey(myEngine.KeyRight))
			xdir=1;
		if(checkKey(myEngine.KeyUp))
			ydir=-1;
		if(checkKey(myEngine.KeyDown))
			ydir=1;
	}



}
