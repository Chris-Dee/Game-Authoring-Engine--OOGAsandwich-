package gameAuthoringEnvironment.levelEditor;

import gameEngine.Level;

import javax.swing.JPanel;

import jgame.JGColor;
import jgame.platform.JGEngine;

public class LevelEditor extends JGEngine {
	LevelMover myMover;
	Level level;
	private final String defaultImage="Resources/red.gif";
	public LevelEditor(Level l){
		super();
		level=l;


		initEngine((int) 600, 600);

		defineImage("srball","",0,defaultImage,"-");
		myMover=new LevelMover(this);
		defineImage("background1","",0,level.getBackground(),"-");
		setBGImage("background1");
	}
	@Override
	public void initCanvas() {
		setCanvasSettings(1, 1, 300, 300, null, JGColor.white, null);
	}

	@Override
	public void initGame() {
		setFrameRate(250, 3);
		//width in spot 0, height in spot 1
		setPFSize(level.getLevelSize().get(0),level.getLevelSize().get(1));
	}
	private void checkInBounds(){
		if(myMover.x>=myMover.pfwidth){
			//myMover.x=el.xofs;
			myMover.x=myMover.pfwidth-20;
		}
		if(myMover.y>=myMover.pfheight){
			myMover.y=myMover.pfheight-20;
		}
		if(myMover.x<=0){
			myMover.x=5;
		}
		if(myMover.y<=0){
			myMover.y=5;
		}
		System.out.println(el.xofs+" "+el.yofs+" "+myMover.x+" "+myMover.y+" "+myMover.pfwidth);
	}
	public void doFrame(){
		checkInBounds();
		moveObjects(null,0);
		setViewOffset((int)myMover.x, (int)myMover.y, true);
	}
	//TODO add method to check for collisions with screen boundary, and decide what to do when screen
	//shrinks and doesn't include mover.
	//TODO push a button to place selected object?
}
