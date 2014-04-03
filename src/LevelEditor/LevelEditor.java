package LevelEditor;

import javax.swing.JPanel;

import jgame.JGColor;
import jgame.platform.JGEngine;

public class LevelEditor extends JGEngine {
	LevelMover myMover;
	private final String defaultImage="Resources/red.gif";
public LevelEditor(String background/*, int[] size*/){
	super();
	int height = 600;
	int width=2000;
	initEngineComponent((int) height, width);
	defineImage("srball","",0,defaultImage,"-");
	myMover=new LevelMover(this);
	defineImage("background1","",0,background,"-");
	setBGImage("background1");
}
	@Override
	public void initCanvas() {
		setCanvasSettings(1, 1, 300, 300, null, JGColor.white, null);
	}

	@Override
	public void initGame() {
		setFrameRate(250, 3);
		setPFSize(100,100);
		}
public void doFrame(){
	moveObjects(null,0);
	setViewOffset((int)myMover.x, (int)myMover.y, true);
}


}
