package gamePlayer;

import jgame.JGPoint;
import jgame.platform.JGEngine;

public class GamePlayerGUI extends JGEngine{
	public GamePlayerGUI(JGPoint size){
		initEngine(size.x, size.y);
	}

	@Override
	public void initCanvas() {
		setCanvasSettings(getX(), getY(), 1, 1, null, null, null);
	}

	@Override
	public void initGame() {
		setFrameRate(30, 2);
		//class creation through data (getMapOfObjects)
	}

	public void doFrame(){
		moveObjects(
				null,// object name prefix of objects to move (null means any)
				0    // object collision ID of objects to move (0 means any)
				);
	}
	public void paintFrame() {}
}
