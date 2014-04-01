package FrontEnd;

import javax.swing.JPanel;

import jgame.JGColor;
import jgame.platform.JGEngine;

public class LevelEditor extends JGEngine {
public LevelEditor(){
	super();
	int height = 900;
	double aspect = 0.5;
	initEngineComponent((int) (height * aspect), height);
}
	@Override
	public void initCanvas() {
		setCanvasSettings(1, 1, 300, 300, null, JGColor.white, null);
	}

	@Override
	public void initGame() {
		setFrameRate(250, 3);
		setPFSize(30,30);
		
		}



}
