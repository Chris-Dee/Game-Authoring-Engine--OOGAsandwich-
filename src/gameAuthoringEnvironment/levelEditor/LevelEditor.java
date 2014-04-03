package gameAuthoringEnvironment.levelEditor;

import javax.swing.JPanel;

import jgame.JGColor;
import jgame.platform.JGEngine;

public class LevelEditor extends JGEngine {
	LevelMover myMover;
	private final String defaultImage="Resources/red.gif";
public LevelEditor(){
	super();
	int height = 900;
	double aspect = 0.5;
	initEngineComponent((int) (height * aspect), height);
	defineImage("srball","",0,defaultImage,"-");
	myMover=new LevelMover(this);
}
	@Override
	public void initCanvas() {
		setCanvasSettings(1, 1, 300, 300, null, JGColor.white, null);
	}

	@Override
	public void initGame() {
		setFrameRate(250, 3);
		setPFSize(60,60);
		}
public void doFrame(){
	moveObjects(null,0);
}


}
